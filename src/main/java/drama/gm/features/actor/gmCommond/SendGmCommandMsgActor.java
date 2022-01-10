package drama.gm.features.actor.gmCommond;

import com.sun.net.httpserver.HttpExchange;
import dm.relationship.base.actor.DmActor;
import drama.gm.system.httpServer.msg.HttpRequestMsg;
import drama.gm.system.httpServer.msg.HttpResponseMsg;
import drama.gm.system.httpServer.utils.ResponseUtils;
import drama.gm.features.actor.gmCommond.ctrl.SendGmCommandMsgCtrl;
import drama.gm.features.actor.gmCommond.ctrl._SendGmCommandMsgCtrl;
import drama.gm.features.actor.gmCommond.msg.SendGmCommandMsg;

/**
 * Created by lee on 17-2-22.
 */
public class SendGmCommandMsgActor extends DmActor {
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
