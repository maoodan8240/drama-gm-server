package ws.gm.features.actor.queryPlayerBasicInfo.msg;

import ws.gm.system.httpServer.msg.HttpRequestMsg;

public class QueryPlayerBasicInfoMsg {
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
