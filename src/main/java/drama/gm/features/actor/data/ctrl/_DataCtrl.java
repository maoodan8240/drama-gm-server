package drama.gm.features.actor.data.ctrl;

import com.alibaba.fastjson.JSON;
import com.sun.net.httpserver.HttpExchange;
import drama.gm.features.actor.data.base.PaymentData;
import drama.gm.features.actor.data.base.RechargeRecord;
import drama.gm.features.actor.data.msg.QueryTodayData;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.mongoDB.interfaces.MongoDBClient;
import ws.common.utils.date.WsDateFormatEnum;
import ws.common.utils.date.WsDateUtils;
import ws.common.utils.di.GlobalInjector;
import ws.common.utils.message.interfaces.PrivateMsg;
import drama.gm.system.httpServer.inf.AbstractHttpMessageCtrl;
import drama.gm.system.httpServer.msg.HttpResponseMsg;
import drama.gm.features.actor.data.msg.NewlyAndActivityData;
import drama.gm.features.actor.data.msg.QueryLvDistribution;
import drama.gm.features.actor.data.msg.QueryNewlyAndActivePlayerCount;
import drama.gm.features.actor.data.msg.QueryPaymentByDate;
import drama.gm.features.actor.data.msg.QueryPveDistribution;
import drama.gm.features.actor.data.msg.QueryRechargeRecord;
import drama.gm.features.actor.data.msg.QueryRemainData;
import drama.gm.system.httpServer.utils.ResponseUtils;
import dm.relationship.base.MagicNumbers;
import dm.relationship.base.MagicWords_Mongodb;
import dm.relationship.daos.DaoContainer;
import dm.relationship.daos.paymentOrder.PaymentOrderDao;
import dm.relationship.logServer.daos.LogDaoContainer;
import dm.relationship.logServer.daos.playerLoginLog.PlayerLoginLogDao;
import dm.relationship.logServer.daos.playerLvUpLog.PlayerLvUpLogDao;
import dm.relationship.logServer.pojos.PlayerLoginLog;
import dm.relationship.logServer.pojos.PlayerLvUpLog;
import dm.relationship.topLevelPojos.data.DayRemain;
import dm.relationship.topLevelPojos.paymentOrder.PaymentOrder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by lee on 7/12/17.
 */
public class _DataCtrl extends AbstractHttpMessageCtrl<PlayerLoginLog> implements DataCtrl {
    private static final Logger LOGGER = LoggerFactory.getLogger(_DataCtrl.class);
    private static final MongoDBClient MONGO_DB_CLIENT = GlobalInjector.getInstance(MongoDBClient.class);
    private static final PlayerLoginLogDao PLAYER_LOGIN_LOG_DAO = LogDaoContainer.getDao(PlayerLoginLog.class);
    private static final PaymentOrderDao PAYMENT_ORDER_DAO = DaoContainer.getDao(PaymentOrder.class);
    private static final PlayerLvUpLogDao PLAYER_LV_UP_LOG_DAO = LogDaoContainer.getDao(PlayerLvUpLog.class);
    private static final int MAX_SEARCH_DAY = 30;


    static {
        PLAYER_LOGIN_LOG_DAO.init(MONGO_DB_CLIENT, MagicWords_Mongodb.TopLevelPojo_All_Logs);
        PAYMENT_ORDER_DAO.init(MONGO_DB_CLIENT, MagicWords_Mongodb.TopLevelPojo_All_Common);
        PLAYER_LV_UP_LOG_DAO.init(MONGO_DB_CLIENT, MagicWords_Mongodb.TopLevelPojo_All_Logs);
    }

    @Override
    public HttpExchange getHttpExchange() {
        return this.httpExchange;
    }

    @Override
    public void setHttpExchange(HttpExchange httpExchange) {
        this.httpExchange = httpExchange;
    }

    @Override
    public void onQueryTodayData() {
        int newCount = PLAYER_LOGIN_LOG_DAO.findNewPlayer();
        int activeDeviceCount = PLAYER_LOGIN_LOG_DAO.findActiveDevice();
        int activeAccount = PLAYER_LOGIN_LOG_DAO.findActiveAccount();
        int todayPaymentSum = PAYMENT_ORDER_DAO.findPaymentSum();
        int newPlayerNewPayment = PAYMENT_ORDER_DAO.findNewPlayerNewPaymentCount();
        int todayPaymentCount = PAYMENT_ORDER_DAO.findPaymentCount();
        QueryTodayData.Response msg = new QueryTodayData.Response(newCount, activeDeviceCount, activeAccount, newPlayerNewPayment, todayPaymentCount, todayPaymentSum);
        HttpResponseMsg response = HttpResponseMsg.createResponse(QueryTodayData.Response.class.getName(), JSON.toJSONString(msg));

        ResponseUtils.send(response, getHttpExchange());
    }


    @Override
    public void onQueryRemainData(String beginDate, String endDate, String platformType, int orid) {
        List<DayRemain> dayRemainList = new ArrayList<>();
        _findRemain(dayRemainList, beginDate, endDate, platformType, orid);
        HttpResponseMsg responseMsg = HttpResponseMsg.createResponse(QueryRemainData.Response.class.getName(), JSON.toJSONString(dayRemainList));
        ResponseUtils.send(responseMsg, getHttpExchange());
    }


    @Override
    public void onQueryRechargeRecord(String beginDate, String endDate, String platformType, int orid, int simpleId) {
        List<RechargeRecord> rechargeRecordList = new ArrayList<>();
        if (simpleId != 0) {
            List<PaymentOrder> paymentOrderList = PAYMENT_ORDER_DAO.findReChargeRecordBySimpleId(simpleId);
            _paymentOrderParseToRechargeOrder(rechargeRecordList, paymentOrderList);
        } else {
            _findRechargeRecord(rechargeRecordList, beginDate, endDate, platformType, orid);
        }
        System.out.println("RESSS:" + JSON.toJSONString(rechargeRecordList));
        HttpResponseMsg responseMsg = HttpResponseMsg.createResponse(QueryRechargeRecord.Response.class.getName(), JSON.toJSONString(rechargeRecordList));
        ResponseUtils.send(responseMsg, getHttpExchange());
    }

    @Override
    public void onQueryLvDistribution(String creatAtDate, String date, String platformType, int orid) {
        List<Integer> lvs = PLAYER_LV_UP_LOG_DAO.findLvByDate(creatAtDate, date, platformType, orid);
        Collections.sort(lvs);

        int newCount = PLAYER_LOGIN_LOG_DAO.findNewPlayer();

        QueryLvDistribution.Response response = new QueryLvDistribution.Response(lvs, newCount);
        System.out.println("RESS:" + JSON.toJSONString(response));
        HttpResponseMsg responseMsg = HttpResponseMsg.createResponse(QueryLvDistribution.Response.class.getName(), JSON.toJSONString(response));
        System.out.println(JSON.toJSONString(responseMsg));
        ResponseUtils.send(responseMsg, getHttpExchange());
    }


    @Override
    public void onQueryPveDistribution(String creatAtDate, String date, String platformType, int orid) {

    }

    @Override
    public void onQueryPaymentByDate(String beginDate, String endDate, String platformType, int orid) {
        List<PaymentData> paymentDataList = new ArrayList<>();
        //计算开始日期
        int bgDateInt = Integer.valueOf(beginDate);
        Date bgdate = WsDateUtils.dateToFormatDate(beginDate, WsDateFormatEnum.yyyyMMdd);
        int edInt = Integer.valueOf(endDate);
        beginDate = Math.min(bgDateInt, edInt) + "";

        //计算结束日期
        Date maxDate = DateUtils.addDays(bgdate, MAX_SEARCH_DAY);
        String maxDateStr = WsDateUtils.dateToFormatStr(maxDate, WsDateFormatEnum.yyyyMMdd);
        endDate = Math.min(edInt, Integer.valueOf(maxDateStr)) + "";
        edInt = Integer.valueOf(endDate);
        endDate = Math.max(bgDateInt, edInt) + "";
        Date edDate = WsDateUtils.dateToFormatDate(endDate, WsDateFormatEnum.yyyyMMdd);

        //下一日
        Date date1 = DateUtils.addDays(bgdate, MagicNumbers.DEFAULT_ONE);

        _findPaymentDataByDate(paymentDataList, beginDate, platformType, orid);
        while ((date1.getTime() <= edDate.getTime()) && date1.getTime() > bgdate.getTime()) {
            String nextDay = WsDateUtils.dateToFormatStr(date1, WsDateFormatEnum.yyyyMMdd);
            _findPaymentDataByDate(paymentDataList, nextDay, platformType, orid);
            date1 = DateUtils.addDays(date1, MagicNumbers.DEFAULT_ONE);
        }
        System.out.println("RESSS:" + JSON.toJSONString(paymentDataList));
        HttpResponseMsg responseMsg = HttpResponseMsg.createResponse(QueryPaymentByDate.Response.class.getName(), JSON.toJSONString(paymentDataList));
        ResponseUtils.send(responseMsg, getHttpExchange());
    }


    private void _findPaymentDataByDate(List<PaymentData> paymentDataList, String date, String platformType, int orid) {
        int newCount = PLAYER_LOGIN_LOG_DAO.findNewPlayerByDate(date, platformType, orid).size();
        int activeAccount = PLAYER_LOGIN_LOG_DAO.findActiveAccountByDate(date, platformType, orid);
        int paymentSum = PAYMENT_ORDER_DAO.findPaymentSumByDate(date, platformType, orid);
        int newPlayerNewPayment = PAYMENT_ORDER_DAO.findNewPlayerNewPaymentCountByDate(date, platformType, orid);
        int paymentCount = PAYMENT_ORDER_DAO.findPaymentCountByDate(date, platformType, orid);
        if (newCount != 0 || activeAccount != 0 || paymentSum != 0 || newPlayerNewPayment != 0) {
            PaymentData paymentData = new PaymentData(date, newPlayerNewPayment, paymentCount, paymentSum,
                    newCount, activeAccount);
            paymentDataList.add(paymentData);
        }
    }


    @Override
    public void onQueryNewlyAndActivePlayerCount(String beginDate, String endDate, String platformType, int orid) {
        List<NewlyAndActivityData> newlyAndActivityDataList = new ArrayList<>();
        //计算开始日期
        int bgDateInt = Integer.valueOf(beginDate);
        Date bgdate = WsDateUtils.dateToFormatDate(beginDate, WsDateFormatEnum.yyyyMMdd);
        int edInt = Integer.valueOf(endDate);
        beginDate = Math.min(bgDateInt, edInt) + "";

        //计算结束日期
        Date maxDate = DateUtils.addDays(bgdate, MAX_SEARCH_DAY);
        String maxDateStr = WsDateUtils.dateToFormatStr(maxDate, WsDateFormatEnum.yyyyMMdd);
        endDate = Math.min(edInt, Integer.valueOf(maxDateStr)) + "";
        edInt = Integer.valueOf(endDate);
        endDate = Math.max(bgDateInt, edInt) + "";
        Date edDate = WsDateUtils.dateToFormatDate(endDate, WsDateFormatEnum.yyyyMMdd);

        //下一日
        Date date1 = DateUtils.addDays(bgdate, MagicNumbers.DEFAULT_ONE);

        _findnewLyAndActivityData(newlyAndActivityDataList, beginDate, platformType, orid);
        while ((date1.getTime() <= edDate.getTime()) && date1.getTime() > bgdate.getTime()) {
            String nextDay = WsDateUtils.dateToFormatStr(date1, WsDateFormatEnum.yyyyMMdd);
            _findnewLyAndActivityData(newlyAndActivityDataList, nextDay, platformType, orid);
            date1 = DateUtils.addDays(date1, MagicNumbers.DEFAULT_ONE);
        }
        System.out.println("RESSS:" + JSON.toJSONString(newlyAndActivityDataList));
        HttpResponseMsg responseMsg = HttpResponseMsg.createResponse(QueryNewlyAndActivePlayerCount.Response.class.getName(), JSON.toJSONString(newlyAndActivityDataList));
        ResponseUtils.send(responseMsg, getHttpExchange());
    }


    public void _findnewLyAndActivityData(List<NewlyAndActivityData> newlyAndActivityDataList, String date, String platformType, int orid) {
        int newCount = PLAYER_LOGIN_LOG_DAO.findNewPlayerByDate(date, platformType, orid).size();
        int activeAccount = PLAYER_LOGIN_LOG_DAO.findActiveAccountByDate(date, platformType, orid);
        int activeDeviceByDate = PLAYER_LOGIN_LOG_DAO.findActiveDeviceByDate(date, platformType, orid);
        if (newCount != 0 || activeAccount != 0 || activeDeviceByDate != 0) {
            NewlyAndActivityData newlyAndActivityData = new NewlyAndActivityData(date, newCount, activeDeviceByDate, activeAccount);
            newlyAndActivityDataList.add(newlyAndActivityData);
        }
    }


    public void _findRechargeRecord(List<RechargeRecord> rechargeRecordList, String beginDate, String platformType, int orid) {
        List<PaymentOrder> paymentOrderList = PAYMENT_ORDER_DAO.findRechargeRecordByCondition(beginDate, platformType, orid);
        _paymentOrderParseToRechargeOrder(rechargeRecordList, paymentOrderList);
    }


    private void _paymentOrderParseToRechargeOrder(List<RechargeRecord> rechargeRecordList, List<PaymentOrder> paymentOrderList) {

        for (PaymentOrder paymentOrder : paymentOrderList) {
            RechargeRecord rechargeRecord = new RechargeRecord(
                    paymentOrder.getEndDate() + "",
                    paymentOrder.getPlatformType().name(),
                    paymentOrder.getOuterRealmId(),
                    paymentOrder.getSimpleId(),
                    paymentOrder.getGoodName(),
                    paymentOrder.getRmb(),
                    paymentOrder.getOuterOrderId(),
                    paymentOrder.getEndDate() + "" + paymentOrder.getEndTime());
            rechargeRecordList.add(rechargeRecord);
        }
    }

    private void _findRechargeRecord(List<RechargeRecord> rechargeRecordList, String beginDate, String endDate, String platformType, int orid) {

        //计算开始日期
        int bgDateInt = Integer.valueOf(beginDate);
        Date bgdate = WsDateUtils.dateToFormatDate(beginDate, WsDateFormatEnum.yyyyMMdd);
        int edInt = Integer.valueOf(endDate);
        beginDate = Math.min(bgDateInt, edInt) + "";

        //计算结束日期
        Date maxDate = DateUtils.addDays(bgdate, MAX_SEARCH_DAY);
        String maxDateStr = WsDateUtils.dateToFormatStr(maxDate, WsDateFormatEnum.yyyyMMdd);
        endDate = Math.min(edInt, Integer.valueOf(maxDateStr)) + "";
        edInt = Integer.valueOf(endDate);
        endDate = Math.max(bgDateInt, edInt) + "";
        Date edDate = WsDateUtils.dateToFormatDate(endDate, WsDateFormatEnum.yyyyMMdd);

        //下一日
        Date date1 = DateUtils.addDays(bgdate, MagicNumbers.DEFAULT_ONE);

        _findRechargeRecord(rechargeRecordList, beginDate, platformType, orid);
        while ((date1.getTime() <= edDate.getTime()) && date1.getTime() > bgdate.getTime()) {
            String nextDay = WsDateUtils.dateToFormatStr(date1, WsDateFormatEnum.yyyyMMdd);
            _findRechargeRecord(rechargeRecordList, nextDay, platformType, orid);
            date1 = DateUtils.addDays(date1, MagicNumbers.DEFAULT_ONE);
        }
    }


    @Override
    public void sendPrivateMsg(PrivateMsg privateMsg) {

    }

    public void _findRemain(List<DayRemain> dayRemainList, String beginDate, String platformType, int orid) {
        DayRemain dayRemain = PLAYER_LOGIN_LOG_DAO.findRemainByDate(beginDate, platformType, orid);
        if (!StringUtils.isBlank(dayRemain.getDate())) {
            dayRemainList.add(dayRemain);
        }
    }

    private void _findRemain(List<DayRemain> dayRemainList, String beginDate, String endDate, String platformType, int orid) {

        //计算开始日期
        int bgDateInt = Integer.valueOf(beginDate);
        Date bgdate = WsDateUtils.dateToFormatDate(beginDate, WsDateFormatEnum.yyyyMMdd);
        int edInt = Integer.valueOf(endDate);
        beginDate = Math.min(bgDateInt, edInt) + "";

        //计算结束日期
        Date maxDate = DateUtils.addDays(bgdate, MAX_SEARCH_DAY);
        String maxDateStr = WsDateUtils.dateToFormatStr(maxDate, WsDateFormatEnum.yyyyMMdd);
        endDate = Math.min(edInt, Integer.valueOf(maxDateStr)) + "";
        edInt = Integer.valueOf(endDate);
        endDate = Math.max(bgDateInt, edInt) + "";
        Date edDate = WsDateUtils.dateToFormatDate(endDate, WsDateFormatEnum.yyyyMMdd);

        //下一日
        Date date1 = DateUtils.addDays(bgdate, MagicNumbers.DEFAULT_ONE);

        _findRemain(dayRemainList, beginDate, platformType, orid);
        while ((date1.getTime() <= edDate.getTime()) && date1.getTime() > bgdate.getTime()) {
            String nextDay = WsDateUtils.dateToFormatStr(date1, WsDateFormatEnum.yyyyMMdd);
            _findRemain(dayRemainList, nextDay, platformType, orid);
            date1 = DateUtils.addDays(date1, MagicNumbers.DEFAULT_ONE);
        }
    }
}



