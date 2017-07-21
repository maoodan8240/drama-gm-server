package ws.gm.system.httpServer.msg;

import java.util.List;

/**
 * Created by lee on 7/14/17.
 */
public class QueryRechargeRecord {
    public static class Request extends HttpRequestMsg {
        private String beginDate;
        private String endDate;
        private String platformType;
        private int orid;
        private int simpleId;


        public String getBeginDate() {
            return beginDate;
        }

        public void setBeginDate(String beginDate) {
            this.beginDate = beginDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
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

        public int getSimpleId() {
            return simpleId;
        }

        public void setSimpleId(int simpleId) {
            this.simpleId = simpleId;
        }
    }

    public static class Response extends HttpResponseMsg {
        private List<RechargeRecord> rechargeRecordList;


        public Response() {
        }

        public Response(List<RechargeRecord> rechargeRecordList) {
            this.rechargeRecordList = rechargeRecordList;
        }

        public Response(String msgType, String data) {
            super(msgType, data);
        }


        public List<RechargeRecord> getRechargeRecordList() {
            return rechargeRecordList;
        }

        public void setRechargeRecordList(List<RechargeRecord> rechargeRecordList) {
            this.rechargeRecordList = rechargeRecordList;
        }
    }

}
