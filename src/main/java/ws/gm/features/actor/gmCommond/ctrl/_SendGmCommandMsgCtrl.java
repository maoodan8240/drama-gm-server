package ws.gm.features.actor.gmCommond.ctrl;

import com.sun.net.httpserver.HttpExchange;
import org.apache.commons.lang3.StringUtils;
import ws.common.mongoDB.utils.WsJsonUtils;
import ws.gm.system.httpServer.msg.HttpResponseMsg;
import ws.gm.system.httpServer.utils.ResponseUtils;
import ws.gm.system.jmx.JmxJolokiaClient;
import ws.relationship.gm.GmCommandContent;
import ws.relationship.gm.GmCommandGroupNameConstants;
import ws.relationship.gm.GmCommandGroupNameConstants.ItemIoGmSupport;

import java.util.ArrayList;
import java.util.List;

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
            GmCommandContent commandContent = new GmCommandContent();
            List<Integer> simpleIdLis = new ArrayList<>();
            for (String str : simpleIdLisStr.split(" ")) {
                simpleIdLis.add(Integer.valueOf(str));
            }
            commandContent.setSimpleIdLis(simpleIdLis);
            commandContent.setCommand(GmCommandGroupNameConstants.ItemIo + " " + ItemIoGmSupport.Op.getStr() + " " + command);
            String[] obj = new String[]{"GmCommand_Action", "gmCommand", WsJsonUtils.javaObjectToJSONStr(commandContent)};
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
