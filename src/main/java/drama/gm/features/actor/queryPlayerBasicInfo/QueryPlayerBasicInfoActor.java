package drama.gm.features.actor.queryPlayerBasicInfo;

import com.sun.net.httpserver.HttpExchange;
import dm.relationship.base.actor.DmActor;
import drama.gm.features.actor.queryPlayerBasicInfo.ctrl._QueryPlayerBasicInfoCtrl;
import drama.gm.system.httpServer.msg.HttpRequestMsg;
import drama.gm.system.httpServer.msg.HttpResponseMsg;
import drama.gm.system.httpServer.utils.ResponseUtils;
import drama.gm.features.actor.queryPlayerBasicInfo.ctrl.QueryPlayerBasicInfoCtrl;
import drama.gm.features.actor.queryPlayerBasicInfo.msg.QueryPlayerBasicInfoMsg;
import drama.gm.features.actor.queryPlayerBasicInfo.msg.QueryPlayerBasicInfoMsg.Request;

/**
 * Created by lee on 17-2-22.
 */
public class QueryPlayerBasicInfoActor extends DmActor {
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
