package ws.gm.features.actor.gmCommond;

import com.sun.net.httpserver.HttpExchange;
import ws.gm.features.actor.gmCommond.ctrl.SendGmCommandMsgCtrl;
import ws.gm.features.actor.gmCommond.ctrl._SendGmCommandMsgCtrl;
import ws.gm.system.httpServer.msg.HttpRequestMsg;
import ws.gm.system.httpServer.msg.HttpResponseMsg;
import ws.gm.features.actor.gmCommond.msg.SendGmCommandMsg;
import ws.gm.system.httpServer.utils.ResponseUtils;
import ws.relationship.base.actor.WsActor;

/**
 * Created by lee on 17-2-22.
 */
public class SendGmCommandMsgActor extends WsActor {
    private SendGmCommandMsgCtrl ctrl;

    public SendGmCommandMsgActor() {
        _init();
    }

    private void _init() {
        ctrl = new _SendGmCommandMsgCtrl();
    }

    @Override
    public void onRecv(Object msg) throws Exception {
        if (msg instanceof HttpRequestMsg) {
            HttpExchange exchange = ((HttpRequestMsg) msg).getExchange();
            try {
                if (msg instanceof SendGmCommandMsg.Request) {
                    onSendGmCommandMsg((SendGmCommandMsg.Request) msg);
                }
            } catch (Exception e) {
                HttpResponseMsg responseMsg = HttpResponseMsg.createErrorResponse(e.getMessage());
                ResponseUtils.send(responseMsg, exchange);
                throw e;
            }
        }

    }

    private void onSendGmCommandMsg(SendGmCommandMsg.Request request) throws Exception {
        String simpleIdLis = request.getSimpleIdLis();
        ctrl.send(simpleIdLis, request.getCommand(), request.getExchange());
    }
}
