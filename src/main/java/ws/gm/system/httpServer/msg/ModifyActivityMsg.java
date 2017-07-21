package ws.gm.system.httpServer.msg;

import java.util.List;

/**
 * Created by lee on 7/3/17.
 */
public class ModifyActivityMsg {

    public static class Request extends HttpRequestMsg {
        private GroupActivityJson groupActivityConfig;
        private List<SubActivityJson> subActivityConfig;

        public Request(GroupActivityJson groupActivityConfig, List<SubActivityJson> subActivityConfig) {
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

    public static class Response extends HttpResponseMsg {
        private GroupActivityJson groupActivityConfig;
        private List<SubActivityJson> subActivityConfig;

        public Response() {
        }

        public Response(String msgType, String data) {
            super(msgType, data);
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
