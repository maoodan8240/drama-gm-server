package drama.gm.features.actor.whiteblacklist.msg;

import drama.gm.system.httpServer.msg.HttpRequestMsg;
import drama.gm.system.httpServer.msg.HttpResponseMsg;

/**
 * Created by lee on 7/27/17.
 */
public class RemoveFromBlackList {
    public static class Request extends HttpRequestMsg {
        private int simpleId;

        public Request() {
        }

        public Request(int simpleId) {
            this.simpleId = simpleId;
        }

        public int getSimpleId() {
            return simpleId;
        }

        public void setSimpleId(int simpleId) {
            this.simpleId = simpleId;
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
