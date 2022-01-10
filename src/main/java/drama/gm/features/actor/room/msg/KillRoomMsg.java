package drama.gm.features.actor.room.msg;

import drama.gm.system.httpServer.msg.HttpRequestMsg;

/**
 * Created by lee on 2021/9/13
 */
public class KillRoomMsg {
    public static class Request extends HttpRequestMsg {
        private int simpleRoomId;

        public int getSimpleRoomId() {
            return simpleRoomId;
        }

        public void setSimpleRoomId(int simpleRoomId) {
            this.simpleRoomId = simpleRoomId;
        }
    }
}
