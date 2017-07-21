package ws.gm.system.httpServer.msg;

import java.util.List;

/**
 * Created by lee on 7/3/17.
 */
public class GetOneGroupActivityMsg {

    public static class Request extends HttpRequestMsg {
        private int groupAcId;

        public Request() {
        }

        public Request(int groupAcId) {
            this.groupAcId = groupAcId;
        }

        public int getGroupAcId() {
            return groupAcId;
        }

        public void setGroupAcId(int groupAcId) {
            this.groupAcId = groupAcId;
        }
    }

    public static class Response extends HttpResponseMsg {
        private GroupActivityJson groupActivityConfig;
        private List<SubActivityJson> subActivityConfig;

        public Response() {
        }

        public Response(String msgType, String data) {
            super(msgType, data);
        }

        public Response(GroupActivityJson groupActivityConfig, List<SubActivityJson> subActivityConfig) {
            this.groupActivityConfig = groupActivityConfig;
            this.subActivityConfig = subActivityConfig;
        }

        public GroupActivityJson getGroupActivityConfig() {
            return groupActivityConfig;
        }

        public void setGroupActivityConfig(GroupActivityJson groupActivityConfig) {
            this.groupActivityConfig = groupActivityConfig;
        }

        public List<SubActivityJson> getSubActivityConfig() {
            return subActivityConfig;
        }

        public void setSubActivityConfig(List<SubActivityJson> subActivityConfig) {
            this.subActivityConfig = subActivityConfig;
        }
    }
}
