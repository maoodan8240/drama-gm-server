package ws.gm.system.httpServer.msg;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lee on 7/3/17.
 */
public class GetActivityMsg {

    public static class Request extends HttpRequestMsg {

    }


    public static class Response extends HttpResponseMsg {
        private Map<Integer, GroupActivityJson> groupActIdToGroupAct = new HashMap<>();

        public Response() {
        }

        public Response(String msgType, String data) {
            super(msgType, data);
        }

        public Response(Map<Integer, GroupActivityJson> groupActIdToGroupAct) {
            this.groupActIdToGroupAct = groupActIdToGroupAct;
        }

        public Map<Integer, GroupActivityJson> getGroupActIdToGroupAct() {
            return groupActIdToGroupAct;
        }

        public void setGroupActIdToGroupAct(Map<Integer, GroupActivityJson> groupActIdToGroupAct) {
            this.groupActIdToGroupAct = groupActIdToGroupAct;
        }
    }
}
