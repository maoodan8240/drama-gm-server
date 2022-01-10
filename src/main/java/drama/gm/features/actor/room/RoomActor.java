package drama.gm.features.actor.room;

import com.sun.net.httpserver.HttpExchange;
import dm.relationship.base.actor.DmActor;
import drama.gm.features.actor.room.ctrl.RoomCtrl;
import drama.gm.system.httpServer.msg.HttpRequestMsg;
import drama.gm.system.httpServer.msg.HttpResponseMsg;
import drama.gm.system.httpServer.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import drama.gm.features.actor.room.ctrl._RoomCtrl;
import drama.gm.features.actor.room.msg.AllRoomPlayerMsg;
import drama.gm.features.actor.room.msg.KillRoomMsg;
import drama.gm.features.actor.room.msg.QueryAllRoomBasicInfoMsg;


/**
 * Created by lee on 2021/9/10
 */
public class RoomActor extends DmActor {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomActor.class);


    private RoomCtrl roomCtrl;

    public RoomActor() {
        _init();
    }

    private void _init() {
        roomCtrl = new _RoomCtrl();
    }

    @Override
    public void onRecv(Object msg) throws Exception {
        if (msg instanceof HttpRequestMsg) {
            HttpExchange exchange = ((HttpRequestMsg) msg).getExchange();
            try {
                if (msg instanceof QueryAllRoomBasicInfoMsg.Request) {
                    onQueryAllRoomBasicInfoMsgRequest((QueryAllRoomBasicInfoMsg.Request) msg, exchange);
                } else if (msg instanceof KillRoomMsg.Request) {
                    onKillRoomMsgRequest((KillRoomMsg.Request) msg, exchange);
                } else if (msg instanceof AllRoomPlayerMsg.Request) {
                    onAllRoomPlayerRequest((AllRoomPlayerMsg.Request) msg, exchange);
                }

            } catch (Exception e) {
                HttpResponseMsg responseMsg = HttpResponseMsg.createErrorResponse(e.getMessage());
                ResponseUtils.send(responseMsg, exchange);
                throw e;
            }
        }
    }

    private void onAllRoomPlayerRequest(AllRoomPlayerMsg.Request msg, HttpExchange exchange) {
        roomCtrl.onAllRoomPlayerMsg(msg.getSimpleRoomId(), exchange);
    }

    private void onQueryAllRoomBasicInfoMsgRequest(QueryAllRoomBasicInfoMsg.Request msg, HttpExchange exchange) {
        roomCtrl.queryAllRoomBasicInfo(msg, exchange);
    }

    private void onKillRoomMsgRequest(KillRoomMsg.Request msg, HttpExchange exchange) {
        roomCtrl.onKillRoomMsg(msg.getSimpleRoomId(), exchange);
    }
}
