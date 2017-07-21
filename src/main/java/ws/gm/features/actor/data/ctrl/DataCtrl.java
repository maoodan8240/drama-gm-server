package ws.gm.features.actor.data.ctrl;

import com.sun.net.httpserver.HttpExchange;
import ws.common.utils.mc.controler.Controler;
import ws.relationship.logServer.pojos.PlayerLoginLog;

/**
 * Created by lee on 7/12/17.
 */
public interface DataCtrl extends Controler<PlayerLoginLog> {

    HttpExchange getHttpExchange();

    void setHttpExchange(HttpExchange httpExchange);

    /**
     * 当日新增活跃数据（今日新增，今日活跃设备，今日活跃帐号,今日新增玩家中充值的玩家数量，充值总人数，充值总流水）
     */
    void onQueryTodayData();


    void onQueryRemainData(String beginDate, String endDate, String platformType, int orid);

    void onQueryRechargeRecord(String beginDate, String endDate, String platformType, int orid, int simpleId);


    void onQueryLvDistribution(String createAtDate, String date, String platformType, int orid);

    void onQueryPveDistribution(String createAtDate, String date, String platformType, int orid);

    void onQueryPaymentByDate(String beginDate, String endDate, String platformType, int orid);

    void onQueryNewlyAndActivePlayerCount(String beginDate, String endDate, String platformType, int orid);
}
