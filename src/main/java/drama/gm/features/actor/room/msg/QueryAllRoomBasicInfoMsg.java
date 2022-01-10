package drama.gm.features.actor.room.msg;

import drama.gm.system.httpServer.msg.HttpRequestMsg;

/**
 * Created by lee on 2021/9/10
 */
public class QueryAllRoomBasicInfoMsg {
    public static class Request extends HttpRequestMsg {
        private int simpleId;

        public int getSimpleId() {
            return simpleId;
        }

        public void setSimpleId(int simpleId) {
            this.simpleId = simpleId;
        }
    }

}
