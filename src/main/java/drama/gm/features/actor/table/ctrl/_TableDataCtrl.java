package drama.gm.features.actor.table.ctrl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sun.net.httpserver.HttpExchange;
import drama.gm.features.actor.table.msg.UploadTableDataMsg;
import drama.gm.features.actor.table.msg.UploadTableDataMsg.Request;
import drama.gm.system.config.AppConfig;
import org.apache.commons.net.ftp.FTP;
import org.jolokia.client.J4pClient;
import org.jolokia.client.exception.J4pException;
import org.jolokia.client.request.J4pExecRequest;
import org.jolokia.client.request.J4pExecResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import drama.gm.features.actor.table.msg.QueryTableDataListMsg;
import drama.gm.features.actor.table.msg.QueryTableDataMsg;
import drama.gm.features.actor.utils.FTPClientUtils;
import drama.gm.system.httpServer.msg.HttpResponseMsg;
import drama.gm.system.httpServer.utils.ResponseUtils;

import javax.management.MalformedObjectNameException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lee on 2021/9/15
 */
public class _TableDataCtrl implements TableDataCtrl {
    private static final String JMX_ADDRESS = "http://127.0.0.1:24001/jmx/";
    private static final Logger LOGGER = LoggerFactory.getLogger(_TableDataCtrl.class);

    @Override
    public void onQueryTableDateListMsg(QueryTableDataListMsg.Request msg) {
        HttpExchange exchange = msg.getExchange();
        FTPClientUtils ftpClient = new FTPClientUtils();
        if (ftpClient.connectServer()) {
            LOGGER.debug("FtpClient Success....");
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);// 设置传输二进制文件
            ftpClient.changeWorkingDirectory(AppConfig.getString(AppConfig.Key.DM_Common_Config_ftp_configPath));
            LOGGER.debug("ftp dir:{}", AppConfig.getString(AppConfig.Key.DM_Common_Config_ftp_configPath));
        }
        List<String> tableDatas = ftpClient.listRemoteAllFiles();
        ftpClient.closeConnect();
        HttpResponseMsg responseMsg = HttpResponseMsg.createResponse("xxxxx", JSON.toJSONString(tableDatas));
        ResponseUtils.send(responseMsg, exchange);
    }

    @Override
    public void onQueryTableDataMsg(QueryTableDataMsg.Request msg) {
        HttpExchange exchange = msg.getExchange();
        String tableDataName = msg.getTableDataName();
        FTPClientUtils ftpClient = new FTPClientUtils();
        if (ftpClient.connectServer()) {
            LOGGER.debug("FtpClient Success....");
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);// 设置传输二进制文件
            ftpClient.changeWorkingDirectory(AppConfig.getString(AppConfig.Key.DM_Common_Config_ftp_configPath));
        }
        String contentData = ftpClient.loadFileToTableDataString(tableDataName);
        ftpClient.closeConnect();
        HttpResponseMsg responseMsg = HttpResponseMsg.createResponse("xxxxx", JSON.toJSONString(contentData));
        ResponseUtils.send(responseMsg, exchange);
    }

    @Override
    public void onUploadTableDataMsg(UploadTableDataMsg.Request msg) throws Exception {
        String filesArrStr = msg.getFilesArrStr();
        List<File> files = JSON.parseArray(filesArrStr, File.class);
        for (File file : files) {
            System.out.println(file.getName());
        }
        upLoadTableFile(files);
        sendResponse(msg.getExchange());
    }

    private void sendResponse(HttpExchange exchange) throws IOException {
        String str = "<a href='http://121.36.23.124/drama-gm-client/table_data_form_basic.html'>upload success!</a>";
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.sendResponseHeaders(200, str.getBytes().length);
        exchange.getResponseBody().write(str.getBytes());
        exchange.getResponseBody().close();
        exchange.close();
    }

    private void upLoadTableFile(List<File> files) {
        FTPClientUtils ftpClient = new FTPClientUtils();
        if (ftpClient.connectServer()) {
            System.out.println("FtpClient Success....");
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);// 设置传输二进制文件
            ftpClient.changeWorkingDirectory(AppConfig.getString(AppConfig.Key.DM_Common_Config_ftp_configPath));
        }
        List<String> tableDatas = ftpClient.uploadFileList(files, AppConfig.getString(AppConfig.Key.DM_Common_Config_ftp_configPath));
        ftpClient.closeConnect();
        if (tableDatas.size() != 0) {
            refreshTableRow();
        }
    }


    private void refreshTableRow() {
        String[] obj = new String[]{"refresh_table_data", "refresh_table_data", JSON.toJSONString(Integer.valueOf(0))};
        String parameter = "allManager";
        J4pClient j4pClient = new J4pClient(JMX_ADDRESS);
        J4pExecRequest j4pExecRequest;
        J4pExecResponse response;
        String resp = "";
        try {
            j4pExecRequest = new J4pExecRequest("drama.gameServer.features.manager:name=AppDebugger", parameter, obj);
            try {
                response = j4pClient.execute(j4pExecRequest);
                resp = response.getValue();
            } catch (J4pException e) {
                System.out.println("发送返回值异常" + e);
                e.printStackTrace();
            }
        } catch (MalformedObjectNameException e) {
            System.out.println("发送异常" + e);
            e.printStackTrace();
        }
    }

}
