package drama.gm.system.jmx;

import com.alibaba.fastjson.JSON;
import org.jolokia.client.J4pClient;
import org.jolokia.client.exception.J4pException;
import org.jolokia.client.request.J4pExecRequest;
import org.jolokia.client.request.J4pExecResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.utils.date.WsDateFormatEnum;
import ws.common.utils.date.WsDateUtils;
import dm.relationship.gm.GmMailList;
import dm.relationship.topLevelPojos.common.Iac;
import dm.relationship.topLevelPojos.mailCenter.GmMail;

import javax.management.MalformedObjectNameException;
import java.util.Date;

public class JmxJolokiaClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(JmxJolokiaClient.class);
    // private static final String JMX_ADDRESS = "http://192.168.0.56:20401/jmx/";
    private static final String JMX_ADDRESS = "http://106.75.33.217:20401/jmx/";


    public static String sendUtil(String parameter, Object[] obj) {
        J4pClient j4pClient = new J4pClient(JMX_ADDRESS);
        J4pExecRequest j4pExecRequest;
        J4pExecResponse response;
        String resp = "";
        try {
            j4pExecRequest = new J4pExecRequest("PiecesFunctionServerRuntimeInfo:name=AppDebugger", parameter, obj);
            try {
                response = j4pClient.execute(j4pExecRequest);
                resp = response.getValue();
            } catch (J4pException e) {
                LOGGER.info("发送返回值异常", e);
                e.printStackTrace();
            }
        } catch (MalformedObjectNameException e) {
            LOGGER.info("发送异常", e);
            e.printStackTrace();
        }
        return resp;
    }

    public static void testSendGmMail2() {
        GmMail gmMail = new GmMail();
        gmMail.setTitle("测试发送玩家Gm邮件 2222222222222222 ");
        gmMail.setContent("成功了吗???? 2222222222222222 ");
        gmMail.setSenderId("2222222222222222");
        gmMail.setSenderName("2222222222222222");
        long time = System.currentTimeMillis() + 1l * 24 * 60 * 60 * 1000;
        gmMail.setExpireTime(WsDateUtils.dateToFormatStr(new Date(time), WsDateFormatEnum.yyyy_MM_dd$HH_mm_ss));
        String[] obj = new String[]{"GmMail_Action", "sendMailToAll", JSON.toJSONString(gmMail)};
        String parameter = "allManager";
        sendUtil(parameter, obj);
    }

    public static void testSendGmMail3() {
        GmMail gmMail = new GmMail();
        gmMail.setTitle("测试发送玩家Gm邮件 44444444444 ");
        gmMail.setContent("成功了吗???? 44444444444 ");
        gmMail.setSenderId("44444444444");
        gmMail.setSenderName("44444444444");
        long time = System.currentTimeMillis() + 1l * 24 * 60 * 60 * 1000;
        gmMail.setExpireTime(WsDateUtils.dateToFormatStr(new Date(time), WsDateFormatEnum.yyyy_MM_dd$HH_mm_ss));
        gmMail.getAttachments().add(new Iac(1, 100));
        gmMail.getAttachments().add(new Iac(2, 100));
        gmMail.setHasAttachments(true);
        String[] obj = new String[]{"GmMail_Action", "sendMailToAll", JSON.toJSONString(gmMail)};
        String parameter = "allManager";
        sendUtil(parameter, obj);
    }

    public static void testSendGmMail() {
        GmMailList list = new GmMailList();
        list.getSimpleIds().add(1000019);
        GmMail gmMail = new GmMail();
        gmMail.setTitle("测试发送玩家Gm邮件 单独发送  33333333333 ");
        gmMail.setContent("成功了吗???? 33333333333 ");
        gmMail.setSenderId("33333333333");
        gmMail.setSenderName("33333333333");
        long time = System.currentTimeMillis() + 1l * 24 * 60 * 60 * 1000;
        gmMail.setExpireTime(WsDateUtils.dateToFormatStr(new Date(time), WsDateFormatEnum.yyyy_MM_dd$HH_mm_ss));
        list.setGmMail(gmMail);
        String[] obj = new String[]{"GmMail_Action", "sendMailToPlayers", JSON.toJSONString(list)};
        String parameter = "allManager";
        sendUtil(parameter, obj);
    }

    public static void main(String[] args) {
        testSendGmMail3();
    }
}

