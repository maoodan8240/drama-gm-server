package ws.gm.features.actor.data.msg;

import ws.gm.system.httpServer.msg.HttpRequestMsg;
import ws.gm.system.httpServer.msg.HttpResponseMsg;

/**
 * Created by lee on 7/12/17.
 */
public class QueryTodayData {
    public static class Request extends HttpRequestMsg {

    }

    public static class Response extends HttpResponseMsg {
        private int newCount;            //今日新增
        private int activeDeviceCount;   //今日活跃设备
        private int activeAccount;       //今日活跃帐号
        private int newPlayerNewPayment; //今日新增玩家中充值的玩家
        private int todayPaymentCount;   //今日充值玩家总人数
        private int todayPaymentSum;     //今日总流水


        public Response(int newCount, int activeDeviceCount, int activeAccount, int newPlayerNewPayment, int todayPaymentCount, int todayPaymentSum) {
            this.newCount = newCount;
            this.activeDeviceCount = activeDeviceCount;
            this.activeAccount = activeAccount;
            this.newPlayerNewPayment = newPlayerNewPayment;
            this.todayPaymentCount = todayPaymentCount;
            this.todayPaymentSum = todayPaymentSum;
        }

        public Response(int errorCode, String msgType, String data) {
            super(errorCode, msgType, data);
        }

        public Response() {

        }

        public int getNewCount() {
            return newCount;
        }

        public void setNewCount(int newCount) {
            this.newCount = newCount;
        }

        public int getActiveDeviceCount() {
            return activeDeviceCount;
        }

        public void setActiveDeviceCount(int activeDeviceCount) {
            this.activeDeviceCount = activeDeviceCount;
        }

        public int getActiveAccount() {
            return activeAccount;
        }

        public void setActiveAccount(int activeAccount) {
            this.activeAccount = activeAccount;
        }

        public int getNewPlayerNewPayment() {
            return newPlayerNewPayment;
        }

        public void setNewPlayerNewPayment(int newPlayerNewPayment) {
            this.newPlayerNewPayment = newPlayerNewPayment;
        }

        public int getTodayPaymentCount() {
            return todayPaymentCount;
        }

        public void setTodayPaymentCount(int todayPaymentCount) {
            this.todayPaymentCount = todayPaymentCount;
        }

        public int getTodayPaymentSum() {
            return todayPaymentSum;
        }

        public void setTodayPaymentSum(int todayPaymentSum) {
            this.todayPaymentSum = todayPaymentSum;
        }
    }
}
