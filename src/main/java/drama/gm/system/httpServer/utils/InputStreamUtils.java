package drama.gm.system.httpServer.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import drama.gm.system.config.AppConfig;
import drama.gm.system.config.AppConfig.Key;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lee on 17-2-23.
 */
public class InputStreamUtils {

    private static final String DEFAULT_ENCODING = "UTF-8";//编码
    private static final int PROTECTED_LENGTH = 512000;// 输入流保护 50KB
    private static final String TABLE_FILE_TEMP_PATH = AppConfig.getString(Key.DM_Common_Config_ftp_localTempPath);

    public static String readInfoStream(InputStream input) throws Exception {
        if (input == null) {
            throw new Exception("输入流为null");
        }
        //字节数组
        byte[] bcache = new byte[2048];
        int readSize = 0;//每次读取的字节长度
        int totalSize = 0;//总字节长度
        ByteArrayOutputStream infoStream = new ByteArrayOutputStream();
        try {
            //一次性读取2048字节
            while ((readSize = input.read(bcache)) > 0) {
                totalSize += readSize;
                if (totalSize > PROTECTED_LENGTH) {
                    throw new Exception("输入流超出50K大小限制");
                }
                //将bcache中读取的input数据写入infoStream
                infoStream.write(bcache, 0, readSize);
            }
        } catch (IOException e1) {
            throw new Exception("输入流读取异常");
        } finally {
            try {
                //输入流关闭
                input.close();
            } catch (IOException e) {
                throw new Exception("输入流关闭异常");
            }
        }

        try {
            String str = infoStream.toString(DEFAULT_ENCODING);
            return parsToJsonStr(str);
        } catch (UnsupportedEncodingException e) {
            throw new Exception("输出异常");
        }
    }

    public static String readInfoStream(InputStream input, URI uri) throws Exception {
        if (input == null) {
            throw new Exception("输入流为null");
        }
        //字节数组
        byte[] bcache = new byte[2048];
        int readSize = 0;//每次读取的字节长度
        int totalSize = 0;//总字节长度
        ByteArrayOutputStream infoStream = new ByteArrayOutputStream();
        try {
            //一次性读取2048字节
            while ((readSize = input.read(bcache)) > 0) {
                totalSize += readSize;
                if (totalSize > PROTECTED_LENGTH) {
                    throw new Exception("输入流超出50K大小限制");
                }
                //将bcache中读取的input数据写入infoStream
                infoStream.write(bcache, 0, readSize);
            }
        } catch (IOException e1) {
            throw new Exception("输入流读取异常");
        } finally {
            try {
                //输入流关闭
                input.close();
            } catch (IOException e) {
                throw new Exception("输入流关闭异常");
            }
        }

        try {
            String str = infoStream.toString(DEFAULT_ENCODING);
            String[] split = str.split("\r\n");
            String head = split[0];
            split = replaceHead(split, head);
            //如果不套一个new的ArrayList用iterator操作是会报UnsupportedOperationException
            //因为Arrays.asList返回的是个内部类的ArrayList
            List<String> strings = new ArrayList<>(Arrays.asList(split));
            String sesseionId = getSesseion(strings, head + "\n");
            List<File> files = getFiles(strings, head + "\n");
            return parsToJsonStr(sesseionId, files);
        } catch (UnsupportedEncodingException e) {
            throw new Exception("输出异常");
        }
    }


    public static String[] replaceHead(String[] arr, String head) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].contains(head)) {
                arr[i] = head + "\n";
            }
        }
        return arr;
    }

    public static String getSesseion(List<String> arrayList, String head) {
        String sessionId = "";
        int hpos = 0;
        for (int i = hpos; i < arrayList.size(); i++) {
            if (arrayList.get(i).equals(head)) {
                hpos = i;
                for (int x = hpos + 1; x < arrayList.size(); x++) {
                    if (arrayList.get(x).equals(head)) {
                        String content = arrayList.get(hpos + 1);
                        if (content.contains("sessionId") && content.contains(";")) {
                            if (!arrayList.get(hpos + 3).equals("")) {
                                sessionId = arrayList.get(hpos + 3);
                                break;
                            }
                        }
                    }
                }
            }
        }
        return sessionId;
    }

    public static List<File> getFiles(List<String> arrayList, String head) {
        int hpos = 0;
        int epos = 1;
        List<File> arr = new ArrayList<>();
        for (int i = hpos; i < arrayList.size(); i++) {
            if (arrayList.get(i).equals(head)) {
                hpos = i;
                for (int x = hpos + 1; x < arrayList.size(); x++) {
                    if (arrayList.get(x).equals(head)) {
                        epos = x - 1;
                        String content = arrayList.get(hpos + 1);
                        if (content.contains(";") && !content.contains("sessionId")) {
                            String[] heads = content.split(";");
                            String fileName = heads[2].split("=")[1].replaceAll("\"", "");
                            StringBuffer sb = new StringBuffer();
                            for (int j = hpos + 3; j < epos; j++) {
                                if (!arrayList.get(j).equals("")) {
                                    sb.append(arrayList.get(j) + "\n");
                                }
                            }
                            ByteArrayInputStream stream = new ByteArrayInputStream(sb.toString().getBytes());
                            File file = inputStreamToFile(stream, fileName);
                            arr.add(file);
                            break;
                        }
                    }
                }
            }
        }
        return arr;
    }


    public static File inputStreamToFile(InputStream ins, String fileName) {
        OutputStream os = null;
        File file = null;
        String filePath = System.getProperty("user.dir") + TABLE_FILE_TEMP_PATH;
        try {
            file = new File(filePath + fileName);
            if (file.exists()) {
                file.delete();
            }
            file = new File(filePath + fileName);
            os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
                ins.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }


    public static String parsToJsonStr(String sessionId, List<File> files) {
        JSONObject bodys1 = new JSONObject();
        String string = JSON.toJSONString(files);
        bodys1.put("filesArrStr", string);
        JSONObject bodys = new JSONObject();
        bodys.put("msgType", "UploadTableDataMsg$Request");
        bodys.put("sessionId", sessionId);
        bodys.put("data", bodys1);
        return bodys.toJSONString();
    }

    public static String parsToJsonStr(String s) throws UnsupportedEncodingException {
        JSONObject bodys = new JSONObject();
        for (String one : s.split("&")) {
            String[] oneEles = one.split("=");
            String key = java.net.URLDecoder.decode(oneEles[0], "UTF-8");
            String val = java.net.URLDecoder.decode(oneEles[1], "UTF-8");
            bodys.put(key, val);
        }
        return bodys.toJSONString();
    }
}
