package drama.gm.features.actor.gmCommond.ctrl;

import com.sun.net.httpserver.HttpExchange;
import drama.gm.system.httpServer.msg.HttpResponseMsg;
import drama.gm.system.httpServer.utils.ResponseUtils;
import drama.gm.system.jmx.JmxJolokiaClient;
import org.apache.commons.lang3.StringUtils;
import ws.common.mongoDB.utils.WsJsonUtils;

public class _SendGmCommandMsgCtrl implements SendGmCommandMsgCtrl {

    @Override
    public void send(String simpleIdLisStr, String command, HttpExchange exchange) {
        simpleIdLisStr = StringUtils.strip(simpleIdLisStr);
        if (StringUtils.isEmpty(simpleIdLisStr)) {
            HttpResponseMsg responseMsg = HttpResponseMsg.createErrorResponse("simpleIdLis 不能为空！");
            ResponseUtils.send(responseMsg, exchange);
            return;
        }
        if (StringUtils.isEmpty(command)) {
            HttpResponseMsg responseMsg = HttpResponseMsg.createErrorResponse("command 不能为空！");
            ResponseUtils.send(responseMsg, exchange);
            return;
        }
        try {
            String[] obj = new String[]{"GmCommand_Action", "gmCommand", WsJsonUtils.javaObjectToJSONStr(null)};
            String parameter = "allManager";
            String rs = JmxJolokiaClient.sendUtil(parameter, obj);
            HttpResponseMsg responseMsg = HttpResponseMsg.createResponse("", WsJsonUtils.javaObjectToJSONStr(rs));
            ResponseUtils.send(responseMsg, exchange);
        } catch (Exception e) {
            HttpResponseMsg responseMsg = HttpResponseMsg.createErrorResponse(e.toString());
            ResponseUtils.send(responseMsg, exchange);
        }
    }
}
