package ws.gm.system.httpServer.utils;

import com.alibaba.fastjson.JSON;
import com.sun.net.httpserver.HttpExchange;
import org.apache.commons.lang3.StringUtils;
import ws.common.utils.message.interfaces.Message;
import ws.gm.system.httpServer.RequestJson;
import ws.gm.system.httpServer.msg.HttpRequestMsg;

/**
 * Created by lee on 17-2-23.
 */
public class ParseJsonToMessageUtils {

    public static Message parse(RequestJson requestJson, HttpExchange exchange) throws Exception {
        String packageAddress = HttpRequestMsg.class.getPackage().getName();
        String msgType = requestJson.getMsgType();
        String data = requestJson.getData();
        Class<?> clz = Class.forName(packageAddress + "." + msgType);
        HttpRequestMsg msg = (HttpRequestMsg) JSON.parseObject(data, clz);
        msg.setExchange(exchange);
        if (!StringUtils.isEmpty(requestJson.getSessionId())) {
            msg.setSessionId(requestJson.getSessionId());
        }
        return msg;
    }
}
