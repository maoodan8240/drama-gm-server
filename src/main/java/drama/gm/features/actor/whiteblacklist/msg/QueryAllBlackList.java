package drama.gm.features.actor.whiteblacklist.msg;

import drama.gm.system.httpServer.msg.HttpRequestMsg;
import drama.gm.system.httpServer.msg.HttpResponseMsg;
import drama.gm.features.actor.whiteblacklist.base.WhiteBlackList;

import java.util.List;

/**
 * Created by lee on 7/27/17.
 */
public class QueryAllBlackList {
    public static class Request extends HttpRequestMsg {

    }

    public static class Response extends HttpResponseMsg {
        private List<WhiteBlackList> list;

        public Response() {
        }


        public Response(int errorCode, String msgType, String data) {
            super(errorCode, msgType, data);
        }

        public Response(List<WhiteBlackList> list) {
            this.list = list;
        }

        public List<WhiteBlackList> getList() {
            return list;
        }

        public void setList(List<WhiteBlackList> list) {
            this.list = list;
        }
    }
}
