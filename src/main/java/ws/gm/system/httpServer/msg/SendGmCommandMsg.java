package ws.gm.system.httpServer.msg;

public class SendGmCommandMsg {
    public static class Request extends HttpRequestMsg {
        private String simpleIdLis;
        private String command;

        public String getSimpleIdLis() {
            return simpleIdLis;
        }

        public void setSimpleIdLis(String simpleIdLis) {
            this.simpleIdLis = simpleIdLis;
        }

        public String getCommand() {
            return command;
        }

        public void setCommand(String command) {
            this.command = command;
        }
    }
}
