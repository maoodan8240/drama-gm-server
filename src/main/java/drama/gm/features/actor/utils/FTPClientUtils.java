package drama.gm.features.actor.utils;

import drama.gm.system.config.AppConfig;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lee on 2021/9/15
 */
public class FTPClientUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(FTPClientUtils.class);

    private static final String userName = AppConfig.getString(AppConfig.Key.DM_Common_Config_ftp_userName);         //FTP 登录用户名
    private static final String password = AppConfig.getString(AppConfig.Key.DM_Common_Config_ftp_password);         //FTP 登录密码
    private static final String ip = AppConfig.getString(AppConfig.Key.DM_Common_Config_ftp_host);                     //FTP 服务器地址IP地址
    private static final int port = AppConfig.getInt(AppConfig.Key.DM_Common_Config_ftp_port);                        //FTP 端口
    //    private static final String userName = "jbs";         //FTP 登录用户名
//    private static final String password = "jbs..123Com";         //FTP 登录密码
//    private static final String ip = "121.36.23.124";                     //FTP 服务器地址IP地址
//    private static final int port = 21;                        //FTP 端口
    private FTPClient ftpClient = null; //FTP 客户端代理
    //时间格式化   
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    //FTP状态码   
    public int i = 1;


    /**
     * 连接到服务器
     *
     * @return true 连接服务器成功，false 连接服务器失败
     */
    public boolean connectServer() {
        boolean flag = true;
        if (ftpClient == null) {
            int reply;
            try {
                ftpClient = new FTPClient();
                ftpClient.setControlEncoding("UTF-8");
                ftpClient.connect(ip, port);
                ftpClient.login(userName, password);
                reply = ftpClient.getReplyCode();
                ftpClient.setDataTimeout(120000);
                ftpClient.enterLocalPassiveMode();
                if (!FTPReply.isPositiveCompletion(reply)) {
                    ftpClient.disconnect();
                    LOGGER.debug("FTP 服务拒绝连接！");
                    flag = false;
                }
            } catch (SocketException e) {
                flag = false;
                e.printStackTrace();
                LOGGER.debug("登录ftp服务器 " + ip + " 失败,连接超时！");
            } catch (IOException e) {
                flag = false;
                e.printStackTrace();
                LOGGER.debug("登录ftp服务器 " + ip + " 失败，FTP服务器无法打开！");
            }
        }
        return flag;
    }

    /**
     * 上传文件
     *
     * @param remoteFile 远程文件路径,支持多级目录嵌套
     * @param localFile  本地文件名称，绝对路径
     */
    public boolean uploadFile(String remoteFile, File localFile)
            throws IOException {
        boolean flag = false;
        InputStream in = new FileInputStream(localFile);
        String remote = new String(remoteFile.getBytes("GBK"), "iso-8859-1");
        if (ftpClient.storeFile(remote, in)) {
            flag = true;
            LOGGER.debug(localFile.getAbsolutePath() + "上传文件成功！");
        } else {
            LOGGER.debug(localFile.getAbsolutePath() + "上传文件失败！");
        }
        in.close();
        return flag;
    }

    /**
     * 上传单个文件，并重命名
     *
     * @param local--本地文件路径
     * @param remote--新的文件名,可以命名为空""
     * @return true 上传成功，false 上传失败
     * @throws IOException
     */
    public boolean uploadFile(String local, String remote) throws IOException {
        boolean flag = true;
        String remoteFileName = remote;
        if (remote.contains("/")) {
            remoteFileName = remote.substring(remote.lastIndexOf("/") + 1);
            // 创建服务器远程目录结构，创建失败直接返回  
            if (!CreateDirecroty(remote)) {
                return false;
            }
        }
        FTPFile[] files = ftpClient.listFiles(new String(remoteFileName));
        File f = new File(local);
        if (existFile(remote)) {
            deleteFile(remote);
        }
        if (!uploadFile(remoteFileName, f)) {
            flag = false;
        }
        return flag;
    }


    public List uploadFileList(List<File> files, String uploadpath) {
        boolean flag = true;
        List l = new ArrayList();
        StringBuffer strBuf = new StringBuffer();
        int n = 0; //上传失败的文件个数
        int m = 0; //上传成功的文件个数
        try {
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
            ftpClient.changeWorkingDirectory("/");
            File fileList[] = files.toArray(new File[files.size()]);
            for (File upfile : fileList) {
                if (upfile.isDirectory()) {
                    uploadManyFile(upfile.getAbsoluteFile().toString(), uploadpath);
                } else {
                    String local = upfile.getCanonicalPath().replaceAll("\\\\", "/");
                    String remote = uploadpath.replaceAll("\\\\", "/") + local.substring(local.lastIndexOf("/"));
                    flag = uploadFile(local, remote);
                    ftpClient.changeWorkingDirectory("/");
                }
                if (!flag) {
                    n++;
                    strBuf.append(upfile.getName() + ",");
                    LOGGER.debug("文件［" + upfile.getName() + "］上传失败");
                } else {
                    m++;
                }
            }
            l.add(0, n);
            l.add(1, m);
            l.add(2, strBuf.toString());
        } catch (NullPointerException e) {
            e.printStackTrace();
            LOGGER.debug("本地文件上传失败！找不到上传文件！", e);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.debug("本地文件上传失败！", e);
        }
        return l;
    }

    /**
     * 上传文件夹内的所有文件
     *
     * @param filename   本地文件夹绝对路径
     * @param uploadpath 上传到FTP的路径,形式为/或/dir1/dir2/../
     * @return true 上传成功，false 上传失败
     * @throws IOException
     */
    public List uploadManyFile(String filename, String uploadpath) {
        boolean flag = true;
        List l = new ArrayList();
        StringBuffer strBuf = new StringBuffer();
        int n = 0; //上传失败的文件个数  
        int m = 0; //上传成功的文件个数  
        try {
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
            ftpClient.changeWorkingDirectory("/");
            File file = new File(filename);
            File fileList[] = file.listFiles();
            for (File upfile : fileList) {
                if (upfile.isDirectory()) {
                    uploadManyFile(upfile.getAbsoluteFile().toString(), uploadpath);
                } else {
                    String local = upfile.getCanonicalPath().replaceAll("\\\\", "/");
                    String remote = uploadpath.replaceAll("\\\\", "/") + local.substring(local.indexOf("/") + 1);
                    flag = uploadFile(local, remote);
                    ftpClient.changeWorkingDirectory("/");
                }
                if (!flag) {
                    n++;
                    strBuf.append(upfile.getName() + ",");
                    LOGGER.debug("文件［" + upfile.getName() + "］上传失败");
                } else {
                    m++;
                }
            }
            l.add(0, n);
            l.add(1, m);
            l.add(2, strBuf.toString());
        } catch (NullPointerException e) {
            e.printStackTrace();
            LOGGER.debug("本地文件上传失败！找不到上传文件！", e);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.debug("本地文件上传失败！", e);
        }
        return l;
    }

    /**
     * 下载文件
     *
     * @param remoteFileName       --服务器上的文件名
     * @param localFileName--本地文件名
     * @return true 下载成功，false 下载失败
     */
    public boolean loadFile(String remoteFileName, String localFileName) {
        boolean flag = true;
        // 下载文件   
        BufferedOutputStream buffOut = null;
        try {
            buffOut = new BufferedOutputStream(new FileOutputStream(localFileName));
            flag = ftpClient.retrieveFile(remoteFileName, buffOut);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.debug("本地文件下载失败！", e);
        } finally {
            try {
                if (buffOut != null)
                    buffOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    /**
     * 删除一个文件
     */
    public boolean deleteFile(String filename) throws IOException {
        boolean flag = true;
        try {
            flag = ftpClient.deleteFile(filename);
            if (flag) {
                LOGGER.debug("删除文件" + filename + "成功！");
            } else {
                LOGGER.debug("删除文件" + filename + "失败！");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除目录
     */
    public void deleteDirectory(String pathname) {
        try {
            File file = new File(pathname);
            if (file.isDirectory()) {
                File file2[] = file.listFiles();
            } else {
                deleteFile(pathname);
            }
            ftpClient.removeDirectory(pathname);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * 删除空目录
     */
    public void deleteEmptyDirectory(String pathname) {
        try {
            ftpClient.removeDirectory(pathname);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * 列出服务器上文件和目录
     *
     * @param regStr --匹配的正则表达式
     */
    public void listRemoteFiles(String regStr) {
        try {
            String files[] = ftpClient.listNames(regStr);
            if (files == null || files.length == 0)
                LOGGER.debug("没有任何文件!");
            else {
                for (int i = 0; i < files.length; i++) {
                    System.out.println(files[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 列出Ftp服务器上的所有文件和目录
     */
    public List<String> listRemoteAllFiles() {
        List<String> arr = null;
        try {
            String[] names = ftpClient.listNames();
            for (int i = 0; i < names.length; i++) {
                LOGGER.debug(names[i]);
            }
            arr = Arrays.asList(names);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    /**
     * 关闭连接
     */
    public void closeConnect() {
        try {
            if (ftpClient != null) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置传输文件的类型[文本文件或者二进制文件]
     *
     * @param fileType--BINARY_FILE_TYPE、ASCII_FILE_TYPE
     */
    public void setFileType(int fileType) {
        try {
            ftpClient.setFileType(fileType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    /**
//     * 设置参数
//     *
//     * @param configFile --参数的配置文件
//     */
//    private boolean setArg(String configFile) {
//        boolean flag = true;
//        property = new Properties();
//        BufferedInputStream inBuff = null;
//        try {
//            inBuff = new BufferedInputStream(new FileInputStream(getClass().getResource("/").getPath() + configFile));
//            property.load(inBuff);
//            userName = property.getProperty("username");
//            password = property.getProperty("password");
//            ip = property.getProperty("ip");
//            port = Integer.parseInt(property.getProperty("port"));
//        } catch (FileNotFoundException e1) {
//            flag = false;
//            logger.debug("配置文件 " + configFile + " 不存在！");
//        } catch (IOException e) {
//            flag = false;
//            logger.debug("配置文件 " + configFile + " 无法读取！");
//        }
//        return flag;
//    }


    /**
     * 进入到服务器的某个目录下
     *
     * @param directory
     */
    public boolean changeWorkingDirectory(String directory) {
        boolean flag = true;
        try {
            flag = ftpClient.changeWorkingDirectory(directory);
            if (flag) {
                LOGGER.debug("进入文件夹" + directory + " 成功！");

            } else {
                LOGGER.debug("进入文件夹" + directory + " 失败！");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return flag;
    }

    /**
     * 返回到上一层目录
     */
    public void changeToParentDirectory() {
        try {
            ftpClient.changeToParentDirectory();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * 重命名文件
     *
     * @param oldFileName --原文件名
     * @param newFileName --新文件名
     */
    public void renameFile(String oldFileName, String newFileName) {
        try {
            ftpClient.rename(oldFileName, newFileName);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * 设置FTP客服端的配置--一般可以不设置
     *
     * @return ftpConfig
     */
    private FTPClientConfig getFtpConfig() {
        FTPClientConfig ftpConfig = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
        ftpConfig.setServerLanguageCode(FTP.DEFAULT_CONTROL_ENCODING);
        return ftpConfig;
    }

    /**
     * 转码[ISO-8859-1 -> GBK] 不同的平台需要不同的转码
     *
     * @param obj
     * @return ""
     */
    private String iso8859togbk(Object obj) {
        try {
            if (obj == null)
                return "";
            else
                return new String(obj.toString().getBytes("iso-8859-1"), "GBK");
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 在服务器上创建一个文件夹
     *
     * @param dir 文件夹名称，不能含有特殊字符，如 \ 、/ 、: 、* 、?、 "、 <、>...
     */
    public boolean makeDirectory(String dir) {
        boolean flag = true;
        try {
            flag = ftpClient.makeDirectory(dir);
            if (flag) {
                LOGGER.debug("创建文件夹" + dir + " 成功！");

            } else {
                LOGGER.debug("创建文件夹" + dir + " 失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    // 检查路径是否存在，存在返回true，否则false    
    public boolean existFile(String path) throws IOException {
        boolean flag = false;
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        for (FTPFile ftpFile : ftpFileArr) {
            if (ftpFile.isDirectory()
                    && ftpFile.getName().equalsIgnoreCase(path)) {
                flag = true;
                break;
            }
        }
        if (ftpFileArr.length > 0) {
            flag = true;
        }
        return flag;
    }

    /**
     * 递归创建远程服务器目录
     *
     * @param remote 远程服务器文件绝对路径
     * @return 目录创建是否成功
     * @throws IOException
     */
    public boolean CreateDirecroty(String remote) throws IOException {
        boolean success = true;
        String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
        // 如果远程目录不存在，则递归创建远程服务器目录  
        if (!directory.equalsIgnoreCase("/") && !changeWorkingDirectory(new String(directory))) {
            int start = 0;
            int end = 0;
            if (directory.startsWith("/")) {
                start = 1;
            } else {
                start = 0;
            }
            end = directory.indexOf("/", start);
            while (true) {
                String subDirectory = new String(remote.substring(start, end).getBytes("GBK"), "iso-8859-1");
                if (changeWorkingDirectory(subDirectory)) {
                    if (makeDirectory(subDirectory)) {
                        changeWorkingDirectory(subDirectory);
                    } else {
                        LOGGER.debug("创建目录[" + subDirectory + "]失败");
                        System.out.println("创建目录[" + subDirectory + "]失败");
                        success = false;
                        return success;
                    }
                }
                start = end + 1;
                end = directory.indexOf("/", start);
                // 检查所有目录是否创建完毕  
                if (end <= start) {
                    break;
                }
            }
        }
        return success;
    }

    public String loadFileToTableDataString(String remoteFile) {
        String str = null;
        InputStreamReader reader = null;
        BufferedReader br = null;
        try {
            File file = File.createTempFile("temp", ".txt");
            loadFile(remoteFile, file.getPath());
            reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
            br = new BufferedReader(reader);
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line);
                builder.append("\r\n");
            }
            str = builder.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return str;

    }

    public static void main(String[] args) {
        FTPClientUtils ftpClient = new FTPClientUtils();
        if (ftpClient.connectServer()) {
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);// 设置传输二进制文件
            ftpClient.changeWorkingDirectory("/drama-project/data.tab");
            String string = ftpClient.loadFileToTableDataString("Table_SceneList.tab");
            System.out.println(string);
            ftpClient.closeConnect();// 关闭连接
        }


    }

}
