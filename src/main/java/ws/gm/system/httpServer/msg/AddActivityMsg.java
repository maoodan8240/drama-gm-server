package ws.gm.system.httpServer.msg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 6/30/17.
 */
public class AddActivityMsg {
    public static class Request extends HttpRequestMsg {
        private GroupActivityJson groupActivityConfig;
        private List<SubActivityJson> subActivityConfig = new ArrayList<>();


        public Request() {
        }


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
        private List<SubActivityJson> subActivityConfig = new ArrayList<>();

        public Response(int errorCode, String msgType, String data) {
            super(errorCode, msgType, data);
        }

        public Response(GroupActivityJson groupActivityConfig, List<SubActivityJson> subActivityConfig) {
            this.groupActivityConfig = groupActivityConfig;
            this.subActivityConfig = subActivityConfig;
        }

        public Response() {
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
