package drama.gm.features.actor.whiteblacklist.msg;

import drama.gm.system.httpServer.msg.HttpRequestMsg;
import drama.gm.system.httpServer.msg.HttpResponseMsg;

/**
 * Created by lee on 7/27/17.
 */
public class LockPlayer {
    public static class Request extends HttpRequestMsg {
        private int simpleId;
        private int lockTime;

        public Request() {
        }

        public Request(int simpleId, int lockTime) {
            this.simpleId = simpleId;
            this.lockTime = lockTime;
        }

        public int getSimpleId() {
            return simpleId;
        }

        public void setSimpleId(int simpleId) {
            this.simpleId = simpleId;
        }

        public int getLockTime() {
            return lockTime;
        }

        public void setLockTime(int lockTime) {
            this.lockTime = lockTime;
        }
    }

    public static class Response extends HttpResponseMsg {
        public Response() {
        }

        public Response(int errorCode, String msgType, String data) {
            super(errorCode, msgType, data);
        }
    }
}
