package ws.gm.features.actor.data.msg;

import ws.gm.features.actor.data.base.PaymentData;
import ws.gm.system.httpServer.msg.HttpRequestMsg;
import ws.gm.system.httpServer.msg.HttpResponseMsg;

import java.util.List;

/**
 * Created by lee on 7/18/17.
 */
public class QueryPaymentByDate {

    public static class Request extends HttpRequestMsg {
        private String beginDate;
        private String endDate;
        private String platformType; //渠道
        private int orid;//服务器号

        public Request() {
        }

        public Request(String beginDate, String endDate, String platformType, int orid) {
            this.beginDate = beginDate;
            this.endDate = endDate;
            this.platformType = platformType;
            this.orid = orid;
        }

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

    }

    public static class Response extends HttpResponseMsg {
        private List<PaymentData> paymentDataList;


        public Response() {
        }

        public Response(List<PaymentData> paymentDataList) {
            this.paymentDataList = paymentDataList;
        }

        public List<PaymentData> getPaymentDataList() {
            return paymentDataList;
        }

        public void setPaymentDataList(List<PaymentData> paymentDataList) {
            this.paymentDataList = paymentDataList;
        }
    }
}
