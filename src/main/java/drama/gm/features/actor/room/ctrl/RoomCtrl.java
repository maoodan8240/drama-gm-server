package drama.gm.features.actor.room.ctrl;

import com.sun.net.httpserver.HttpExchange;
import drama.gm.features.actor.room.msg.QueryAllRoomBasicInfoMsg;


/**
 * Created by lee on 2021/9/10
 */
public interface RoomCtrl {
    void queryAllRoomBasicInfo(QueryAllRoomBasicInfoMsg.Request msg, HttpExchange exchange);

    void onKillRoomMsg(int simpleRoomId, HttpExchange exchange);

    void onAllRoomPlayerMsg(int simpleId, HttpExchange exchange);
}
