package drama.gm.features.actor.data;

import com.sun.net.httpserver.HttpExchange;
import dm.relationship.base.actor.DmActor;
import drama.gm.features.actor.data.ctrl.DataCtrl;
import drama.gm.features.actor.data.msg.QueryTodayData;
import ws.common.utils.di.GlobalInjector;
import drama.gm.system.httpServer.msg.HttpRequestMsg;
import drama.gm.system.httpServer.msg.HttpResponseMsg;
import drama.gm.features.actor.data.msg.QueryLvDistribution;
import drama.gm.features.actor.data.msg.QueryNewlyAndActivePlayerCount;
import drama.gm.features.actor.data.msg.QueryPaymentByDate;
import drama.gm.features.actor.data.msg.QueryPveDistribution;
import drama.gm.features.actor.data.msg.QueryRechargeRecord;
import drama.gm.features.actor.data.msg.QueryRemainData;
import drama.gm.system.httpServer.utils.ResponseUtils;

/**
 * Created by lee on 17-2-23.
 */
public class DataActor extends DmActor {
    private DataCtrl dataCtrl;

    public DataActor() {
        this.enableWsActorLogger = false;
        DataCtrl dataCtrl = GlobalInjector.getInstance(DataCtrl.class);
        this.dataCtrl = dataCtrl;
    }

    @Override
    public void onRecv(Object msg) throws Exception {
        if (msg instanceof HttpRequestMsg) {
            HttpExchange exchange = ((HttpRequestMsg) msg).getExchange();
            dataCtrl.setHttpExchange(exchange);
            try {
                if (msg instanceof QueryTodayData.Request) {
                    onQueryTodayNewPlayer((QueryTodayData.Request) msg);
                } else if (msg instanceof QueryRemainData.Request) {
                    onQueryRemainData((QueryRemainData.Request) msg);
                } else if (msg instanceof QueryRechargeRecord.Request) {
                    onQueryRechargeRecord((QueryRechargeRecord.Request) msg);
                } else if (msg instanceof QueryLvDistribution.Request) {
                    onQueryLvDistribution((QueryLvDistribution.Request) msg);
                } else if (msg instanceof QueryPveDistribution.Request) {
                    onQueryPveDistribution((QueryPveDistribution.Request) msg);
                } else if (msg instanceof QueryPaymentByDate.Request) {
                    onQueryPaymentByDate((QueryPaymentByDate.Request) msg);
                } else if (msg instanceof QueryNewlyAndActivePlayerCount.Request) {
                    onQueryNewlyAndActivePlayerCount((QueryNewlyAndActivePlayerCount.Request) msg);
                }
            } catch (Exception e) {
                HttpResponseMsg responseMsg = HttpResponseMsg.createErrorResponse(e.getMessage());
                ResponseUtils.send(responseMsg, exchange);
                throw e;
            }
        }
    }

    private void onQueryNewlyAndActivePlayerCount(QueryNewlyAndActivePlayerCount.Request msg) {
        this.dataCtrl.onQueryNewlyAndActivePlayerCount(msg.getBeginDate(), msg.getEndDate(), msg.getPlatformType(), msg.getOrid());
    }

    private void onQueryPaymentByDate(QueryPaymentByDate.Request msg) {
        this.dataCtrl.onQueryPaymentByDate(msg.getBeginDate(), msg.getEndDate(), msg.getPlatformType(), msg.getOrid());
    }

    private void onQueryPveDistribution(QueryPveDistribution.Request msg) {
        this.dataCtrl.onQueryPveDistribution(msg.getCreateAtDate(), msg.getDate(), msg.getPlatformType(), msg.getOrid());
    }

    private void onQueryLvDistribution(QueryLvDistribution.Request msg) {
        this.dataCtrl.onQueryLvDistribution(msg.getCreateAtDate(), msg.getDate(), msg.getPlatformType(), msg.getOrid());
    }

    private void onQueryRechargeRecord(QueryRechargeRecord.Request msg) {
        this.dataCtrl.onQueryRechargeRecord(msg.getBeginDate(), msg.getEndDate(), msg.getPlatformType(), msg.getOrid(), msg.getSimpleId());
    }


    private void onQueryRemainData(QueryRemainData.Request msg) {
        this.dataCtrl.onQueryRemainData(msg.getBeginDate(), msg.getEndDate(), msg.getPlatformType(), msg.getOrid());
    }

    private void onQueryTodayNewPlayer(QueryTodayData.Request msg) {
        this.dataCtrl.onQueryTodayData();

    }


}
