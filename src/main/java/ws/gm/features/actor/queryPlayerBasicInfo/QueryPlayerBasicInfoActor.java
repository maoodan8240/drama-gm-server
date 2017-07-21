package ws.gm.features.actor.queryPlayerBasicInfo;

import com.sun.net.httpserver.HttpExchange;
import ws.gm.features.actor.queryPlayerBasicInfo.ctrl.QueryPlayerBasicInfoCtrl;
import ws.gm.features.actor.queryPlayerBasicInfo.ctrl._QueryPlayerBasicInfoCtrl;
import ws.gm.system.httpServer.msg.HttpRequestMsg;
import ws.gm.system.httpServer.msg.HttpResponseMsg;
import ws.gm.system.httpServer.msg.QueryPlayerBasicInfoMsg;
import ws.gm.system.httpServer.msg.QueryPlayerBasicInfoMsg.Request;
import ws.gm.system.httpServer.utils.ResponseUtils;
import ws.relationship.base.actor.WsActor;

/**
 * Created by lee on 17-2-22.
 */
public class QueryPlayerBasicInfoActor extends WsActor {
    private QueryPlayerBasicInfoCtrl ctrl;

    public QueryPlayerBasicInfoActor() {
        _init();
    }

    private void _init() {
        ctrl = new _QueryPlayerBasicInfoCtrl();
    }

    @Override
    public void onRecv(Object msg) throws Exception {
        if (msg instanceof HttpRequestMsg) {
            HttpExchange exchange = ((HttpRequestMsg) msg).getExchange();
            try {
                if (msg instanceof QueryPlayerBasicInfoMsg.Request) {
                    onQueryPlayerBasicInfoMsgRequest((QueryPlayerBasicInfoMsg.Request) msg);
                }
            } catch (Exception e) {
                HttpResponseMsg responseMsg = HttpResponseMsg.createErrorResponse(e.getMessage());
                ResponseUtils.send(responseMsg, exchange);
                throw e;
            }
        }
    }

    private void onQueryPlayerBasicInfoMsgRequest(Request request) throws Exception {
        int simpleId = request.getSimpleId();
        ctrl.query(simpleId, request.getExchange());
    }
}
