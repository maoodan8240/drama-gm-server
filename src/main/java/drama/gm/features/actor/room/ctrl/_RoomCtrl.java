package drama.gm.features.actor.room.ctrl;

import com.alibaba.fastjson.JSON;
import com.sun.net.httpserver.HttpExchange;
import drama.gm.system.config.AppConfig;
import drama.gm.system.httpServer.msg.HttpResponseMsg;
import drama.gm.system.httpServer.utils.ResponseUtils;
import org.jolokia.client.J4pClient;
import org.jolokia.client.exception.J4pException;
import org.jolokia.client.request.J4pExecRequest;
import org.jolokia.client.request.J4pExecResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import drama.gm.features.actor.room.msg.QueryAllRoomBasicInfoMsg;

import javax.management.JMX;
import javax.management.MalformedObjectNameException;

/**
 * Created by lee on 2021/9/10
 */
public class _RoomCtrl implements RoomCtrl {
    private static final Logger LOGGER = LoggerFactory.getLogger(_RoomCtrl.class);
    private static final String JMX_ADDRESS;

    static {
        StringBuilder jmxAddress = new StringBuilder();
        jmxAddress.append("http://");
        jmxAddress.append(AppConfig.getString(AppConfig.Key.DM_Common_Config_jmx_conf_jmx_server_host)).append(":");
        jmxAddress.append(AppConfig.getInt(AppConfig.Key.DM_Common_Config_jmx_conf_jmx_server_port)).append("/");
        jmxAddress.append("jmx/");
        JMX_ADDRESS = jmxAddress.toString();
    }

    @Override
    public void queryAllRoomBasicInfo(QueryAllRoomBasicInfoMsg.Request msg, HttpExchange exchange) {
        int simpleId = msg.getSimpleId();
        String[] obj = new String[]{"sync_room", "", simpleId == 0 ? "" : JSON.toJSONString(simpleId)};
        String parameter = "allManager";
        LOGGER.debug("JMX_ADDRESS:" + JMX_ADDRESS);
        J4pClient j4pClient = new J4pClient(JMX_ADDRESS);
        J4pExecRequest j4pExecRequest;
        J4pExecResponse response;
        String resp = "";
        try {
            j4pExecRequest = new J4pExecRequest("drama.gameServer.features.manager:name=AppDebugger", parameter, obj);
            try {
                response = j4pClient.execute(j4pExecRequest);
                resp = response.getValue();
                HttpResponseMsg responseMsg = HttpResponseMsg.createResponse("xxxxx", resp);
                ResponseUtils.send(responseMsg, exchange);
            } catch (J4pException e) {
                LOGGER.info("发送返回值异常", e);
                e.printStackTrace();
            }
        } catch (MalformedObjectNameException e) {
            LOGGER.info("发送异常", e);
            e.printStackTrace();
        }

    }

    @Override
    public void onKillRoomMsg(int simpleRoomId, HttpExchange exchange) {
        String[] obj = new String[]{"kill_room", "", JSON.toJSONString(simpleRoomId)};
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
                HttpResponseMsg responseMsg = HttpResponseMsg.createResponse("xxxxx", resp);
                ResponseUtils.send(responseMsg, exchange);
            } catch (J4pException e) {
                LOGGER.info("发送返回值异常", e);
                e.printStackTrace();
            }
        } catch (MalformedObjectNameException e) {
            LOGGER.info("发送异常", e);
            e.printStackTrace();
        }
    }

    @Override
    public void onAllRoomPlayerMsg(int simpleRoomId, HttpExchange exchange) {
        String[] obj = new String[]{"all_room_player", "", JSON.toJSONString(simpleRoomId)};
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
                HttpResponseMsg responseMsg = HttpResponseMsg.createResponse("xxxxx", resp);
                ResponseUtils.send(responseMsg, exchange);
            } catch (J4pException e) {
                LOGGER.info("发送返回值异常", e);
                e.printStackTrace();
            }
        } catch (MalformedObjectNameException e) {
            LOGGER.info("发送异常", e);
            e.printStackTrace();
        }
    }
}
