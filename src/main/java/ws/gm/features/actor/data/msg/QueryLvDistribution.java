package ws.gm.features.actor.data.msg;

import ws.gm.system.httpServer.msg.HttpRequestMsg;
import ws.gm.system.httpServer.msg.HttpResponseMsg;

import java.util.List;

/**
 * Created by lee on 7/17/17.
 */
public class QueryLvDistribution {
    public static class Request extends HttpRequestMsg {
        private String createAtDate;
        private String date;
        private String platformType; //渠道
        private int orid;//服务器号

        public Request() {
        }

        public Request(String createAtDate, String date, String platformType, int orid) {
            this.createAtDate = createAtDate;
            this.date = date;
            this.platformType = platformType;
            this.orid = orid;
        }

        public String getCreateAtDate() {
            return createAtDate;
        }

        public void setCreateAtDate(String createAtDate) {
            this.createAtDate = createAtDate;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getPlatformType() {
            return platformType;
        }

        public void setPlatformType(String platformType) {
            this.platformType = platformType;
        }

        public int getOrid() {
            return orid;
        }

        public void setOrid(int orid) {
            this.orid = orid;
        }
    }

    public static class Response extends HttpResponseMsg {
        private List<Integer> lvs;
        private int newPlayerCount;

        public Response() {
        }

        public Response(List<Integer> lvs, int newPlayerCount) {
            this.lvs = lvs;
            this.newPlayerCount = newPlayerCount;
        }

        public int getNewPlayerCount() {
            return newPlayerCount;
        }

        public void setNewPlayerCount(int newPlayerCount) {
            this.newPlayerCount = newPlayerCount;
        }

        public List<Integer> getLvs() {
            return lvs;
        }

        public void setLvs(List<Integer> lvs) {
            this.lvs = lvs;
        }
    }
}
