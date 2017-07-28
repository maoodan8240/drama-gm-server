package ws.gm.features.actor.whiteblacklist;

import com.sun.net.httpserver.HttpExchange;
import ws.common.utils.di.GlobalInjector;
import ws.gm.features.actor.whiteblacklist.ctrl.WhiteBlackListCtrl;
import ws.gm.features.actor.whiteblacklist.msg.AddBlackList;
import ws.gm.features.actor.whiteblacklist.msg.AddWhiteList;
import ws.gm.features.actor.whiteblacklist.msg.LockPlayer;
import ws.gm.features.actor.whiteblacklist.msg.QueryAllBlackList;
import ws.gm.features.actor.whiteblacklist.msg.QueryAllLockPlayer;
import ws.gm.features.actor.whiteblacklist.msg.QueryAllWhiteList;
import ws.gm.features.actor.whiteblacklist.msg.RemoveFromBlackList;
import ws.gm.features.actor.whiteblacklist.msg.RemoveFromWhiteList;
import ws.gm.system.httpServer.msg.HttpRequestMsg;
import ws.gm.system.httpServer.msg.HttpResponseMsg;
import ws.gm.system.httpServer.utils.ResponseUtils;
import ws.relationship.base.actor.WsActor;

/**
 * Created by lee on 17-2-23.
 */
public class WhiteBlackListActor extends WsActor {
    private WhiteBlackListCtrl whiteBlackListCtrl;

    public WhiteBlackListActor() {
        this.enableWsActorLogger = false;
        WhiteBlackListCtrl whiteBlackListCtrl = GlobalInjector.getInstance(WhiteBlackListCtrl.class);
        this.whiteBlackListCtrl = whiteBlackListCtrl;
    }

    @Override
    public void onRecv(Object msg) throws Exception {
        if (msg instanceof HttpRequestMsg) {
            HttpExchange exchange = ((HttpRequestMsg) msg).getExchange();
            whiteBlackListCtrl.setHttpExchange(exchange);
            try {
                if (msg instanceof AddBlackList.Request) {
                    addBlackList(((AddBlackList.Request) msg).getSimpleId());
                } else if (msg instanceof AddWhiteList.Request) {
                    addWhiteList(((AddWhiteList.Request) msg).getSimpleId());
                } else if (msg instanceof RemoveFromBlackList.Request) {
                    removeFromBlackList(((RemoveFromBlackList.Request) msg).getSimpleId());
                } else if (msg instanceof RemoveFromWhiteList.Request) {
                    removeFromWhiteList(((RemoveFromWhiteList.Request) msg).getSimpleId());
                } else if (msg instanceof QueryAllWhiteList.Request) {
                    queryAllWhiteList();
                } else if (msg instanceof QueryAllBlackList.Request) {
                    queryAllBlackList();
                } else if (msg instanceof LockPlayer.Request) {
                    lockPlayer(((LockPlayer.Request) msg).getSimpleId(), ((LockPlayer.Request) msg).getLockTime());
                } else if (msg instanceof QueryAllLockPlayer.Request) {
                    queryAllLockPlayer();
                }
            } catch (Exception e) {
                HttpResponseMsg responseMsg = HttpResponseMsg.createErrorResponse(e.getMessage());
                ResponseUtils.send(responseMsg, exchange);
                throw e;
            }
        }
    }

    private void queryAllLockPlayer() {
        this.whiteBlackListCtrl.queryAllLockPlayer();
    }

    private void lockPlayer(int simpleId, int time) {
        this.whiteBlackListCtrl.lockPlayer(simpleId, time);
    }

    private void addBlackList(int simpleId) {
        this.whiteBlackListCtrl.addBlackList(simpleId);
    }

    private void addWhiteList(int simpleId) {
        this.whiteBlackListCtrl.addWhiteList(simpleId);
    }

    private void removeFromBlackList(int simpleId) {
        this.whiteBlackListCtrl.removeFromBlackList(simpleId);
    }

    private void removeFromWhiteList(int simpleId) {
        this.whiteBlackListCtrl.removeFromWhiteList(simpleId);
    }

    private void queryAllWhiteList() {
        this.whiteBlackListCtrl.queryAllWhiteList();
    }

    private void queryAllBlackList() {
        this.whiteBlackListCtrl.queryAllBlackList();
    }
}
