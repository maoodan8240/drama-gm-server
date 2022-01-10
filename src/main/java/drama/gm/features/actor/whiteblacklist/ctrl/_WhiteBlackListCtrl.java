package drama.gm.features.actor.whiteblacklist.ctrl;

import com.alibaba.fastjson.JSON;
import com.sun.net.httpserver.HttpExchange;
import drama.gm.features.actor.whiteblacklist.base.WhiteBlackList;
import drama.gm.features.actor.whiteblacklist.msg.AddBlackList;
import drama.gm.features.actor.whiteblacklist.msg.QueryAllBlackList;
import drama.gm.features.actor.whiteblacklist.msg.QueryAllLockPlayer;
import drama.gm.system.httpServer.inf.AbstractHttpMessageCtrl;
import drama.gm.system.httpServer.msg.HttpResponseMsg;
import drama.gm.system.httpServer.utils.ResponseUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.mongoDB.interfaces.MongoDBClient;
import ws.common.utils.di.GlobalInjector;
import ws.common.utils.message.interfaces.PrivateMsg;
import drama.gm.features.actor.whiteblacklist.msg.AddWhiteList;
import drama.gm.features.actor.whiteblacklist.msg.LockPlayer;
import drama.gm.features.actor.whiteblacklist.msg.QueryAllWhiteList;
import drama.gm.features.actor.whiteblacklist.msg.RemoveFromBlackList;
import dm.relationship.base.MagicNumbers;
import dm.relationship.base.MagicWords_Mongodb;
import dm.relationship.daos.DaoContainer;
import dm.relationship.daos.centerPlayer.CenterPlayerDao;
import dm.relationship.exception.BusinessLogicMismatchConditionException;
import dm.relationship.topLevelPojos.centerPlayer.CenterPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 17-2-23.
 */
public class _WhiteBlackListCtrl extends AbstractHttpMessageCtrl<CenterPlayer> implements WhiteBlackListCtrl {
    private static final Logger LOGGER = LoggerFactory.getLogger(_WhiteBlackListCtrl.class);
    private static final MongoDBClient MONGO_DB_CLIENT = GlobalInjector.getInstance(MongoDBClient.class);
    private static final CenterPlayerDao CENTER_PLAYER_DAO = DaoContainer.getDao(CenterPlayer.class);

    static {
        CENTER_PLAYER_DAO.init(MONGO_DB_CLIENT, MagicWords_Mongodb.TopLevelPojo_All_Common);
    }

    @Override
    public void sendPrivateMsg(PrivateMsg privateMsg) {

    }

    @Override
    public void addBlackList(int simpleId) {
        boolean result = CENTER_PLAYER_DAO.addToBlackList(simpleId);
        if (!result) {
            String msg = String.format("addBlackList can not find player by this simpleId :%s", simpleId);
            throw new BusinessLogicMismatchConditionException(msg);
        }
        HttpResponseMsg responseMsg = HttpResponseMsg.createResponse(AddBlackList.Response.class.getName());
        ResponseUtils.send(responseMsg, getHttpExchange());
    }

    @Override
    public void addWhiteList(int simpleId) {
        boolean result = CENTER_PLAYER_DAO.addToWhiteList(simpleId);
        if (!result) {
            String msg = String.format("addWhiteList can not find player by this simpleId :%s", simpleId);
            throw new BusinessLogicMismatchConditionException(msg);
        }
        HttpResponseMsg responseMsg = HttpResponseMsg.createResponse(AddWhiteList.Response.class.getName());
        ResponseUtils.send(responseMsg, getHttpExchange());
    }

    @Override
    public void removeFromBlackList(int simpleId) {
        boolean result = CENTER_PLAYER_DAO.removeFromBlackList(simpleId);
        if (!result) {
            String msg = String.format("addBlackList can not find player by this simpleId :%s", simpleId);
            throw new BusinessLogicMismatchConditionException(msg);
        }
        HttpResponseMsg responseMsg = HttpResponseMsg.createResponse(RemoveFromBlackList.Response.class.getName());
        ResponseUtils.send(responseMsg, getHttpExchange());
    }

    @Override
    public void removeFromWhiteList(int simpleId) {
        boolean result = CENTER_PLAYER_DAO.removeFromWhiteList(simpleId);
        if (!result) {
            String msg = String.format("addWhiteList can not find player by this simpleId :%s", simpleId);
            throw new BusinessLogicMismatchConditionException(msg);
        }
        HttpResponseMsg responseMsg = HttpResponseMsg.createResponse(RemoveFromBlackList.Response.class.getName());
        ResponseUtils.send(responseMsg, getHttpExchange());
    }

    @Override
    public void queryAllWhiteList() {
        List<CenterPlayer> centerPlayerList = CENTER_PLAYER_DAO.queryAllWhiteList();
        List<WhiteBlackList> list = parseCenterPlayerToWhiteBlackList(centerPlayerList);
        HttpResponseMsg responseMsg = HttpResponseMsg.createResponse(QueryAllWhiteList.Response.class.getName(), JSON.toJSONString(list));
        ResponseUtils.send(responseMsg, getHttpExchange());


    }

    @Override
    public void queryAllBlackList() {
        List<CenterPlayer> centerPlayerList = CENTER_PLAYER_DAO.queryAllBlackList();
        List<WhiteBlackList> list = parseCenterPlayerToWhiteBlackList(centerPlayerList);
        HttpResponseMsg responseMsg = HttpResponseMsg.createResponse(QueryAllBlackList.Response.class.getName(), JSON.toJSONString(list));
        ResponseUtils.send(responseMsg, getHttpExchange());
    }


    private List<WhiteBlackList> parseCenterPlayerToWhiteBlackList(List<CenterPlayer> centerPlayerList) {
        List<WhiteBlackList> list = new ArrayList<>();
        for (CenterPlayer centerPlayer : centerPlayerList) {
            WhiteBlackList whiteBlackList = new WhiteBlackList(
                    centerPlayer.getSimpleId(),
                    centerPlayer.getGameId(),
                    centerPlayer.getPlatformType().name(),
                    centerPlayer.getOuterRealmId(),
                    centerPlayer.getCreateDate() + "" + centerPlayer.getCreateTime(),
                    centerPlayer.getPlayerName());
            if (centerPlayer.getLastLockTime() != 0 && centerPlayer.getLastLockTime() - System.currentTimeMillis() > 0) {
                long remainLockTime = (centerPlayer.getLastLockTime() - System.currentTimeMillis()) / DateUtils.MILLIS_PER_MINUTE;
                if (remainLockTime <= 0) {
                    remainLockTime = MagicNumbers.DEFAULT_ONE;
                }
                whiteBlackList.setRemainLockTime(remainLockTime);
            }
            list.add(whiteBlackList);
        }
        return list;
    }

    @Override
    public void lockPlayer(int simpleId, int time) {
        boolean result = CENTER_PLAYER_DAO.lockPlayer(simpleId, time);
        if (!result) {
            String msg = String.format("lockPlayer can not find player by this simpleId :%s", simpleId);
            throw new BusinessLogicMismatchConditionException(msg);
        }
        HttpResponseMsg responseMsg = HttpResponseMsg.createResponse(LockPlayer.Response.class.getName());
        ResponseUtils.send(responseMsg, getHttpExchange());
    }

    @Override
    public void queryAllLockPlayer() {
        List<CenterPlayer> centerPlayerList = CENTER_PLAYER_DAO.queryAllLockPlayerList();
        List<WhiteBlackList> list = parseCenterPlayerToWhiteBlackList(centerPlayerList);
        HttpResponseMsg responseMsg = HttpResponseMsg.createResponse(QueryAllLockPlayer.Response.class.getName(), JSON.toJSONString(list));
        ResponseUtils.send(responseMsg, getHttpExchange());
    }

    @Override
    public HttpExchange getHttpExchange() {
        return this.httpExchange;
    }

    @Override
    public void setHttpExchange(HttpExchange httpExchange) {
        this.httpExchange = httpExchange;
    }
}
