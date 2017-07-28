package ws.gm.features.actor.mail;

import com.sun.net.httpserver.HttpExchange;
import ws.gm.features.actor.mail.ctrl.MailCtrl;
import ws.gm.features.actor.mail.ctrl._MailCtrl;
import ws.gm.system.httpServer.msg.HttpRequestMsg;
import ws.gm.system.httpServer.msg.HttpResponseMsg;
import ws.gm.features.actor.mail.msg.MailMsg;
import ws.gm.system.httpServer.utils.ResponseUtils;
import ws.relationship.base.actor.WsActor;

/**
 * author: lilin
 * 2017/4/11
 */
public class MailActor extends WsActor {
    private MailCtrl ctrl;

    public MailActor() {
        _init();
    }

    private void _init() {
        ctrl = new _MailCtrl();
    }

    @Override
    public void onRecv(Object msg) throws Exception {
        if (msg instanceof HttpRequestMsg) {
            HttpExchange exchange = ((HttpRequestMsg) msg).getExchange();
            try {
                if (msg instanceof MailMsg.SendMailToPlayers) {
                    sendMailToPlayers((MailMsg.SendMailToPlayers) msg);
                } else if (msg instanceof MailMsg.SendMailToAll) {
                    sendMailToAll((MailMsg.SendMailToAll) msg);
                }
            } catch (Exception e) {
                HttpResponseMsg responseMsg = HttpResponseMsg.createErrorResponse(e.getMessage());
                ResponseUtils.send(responseMsg, exchange);
                throw e;
            }
        }

    }

    private void sendMailToPlayers(MailMsg.SendMailToPlayers request) throws Exception {
        ctrl.sendMailToPlayers(request.getIds(), request.getTitle(), request.getContent(),
                request.getExpireTime(), request.isDeleteAfterRead(), request.getAttachments(), request.getSenderName(),request.getExchange());
    }

    private void sendMailToAll(MailMsg.SendMailToAll request) {
        ctrl.sendMailToAll(request.getOuterRealmIdsIn(), request.getOuterRealmIdsOut(), request.getLimitPlatforms(), request.getLimitCreateAtMin()
                , request.getLimitCreateAtMax(), request.getLimitLevelMin(), request.getLimitLevelMax(), request.getLimitVipLevelMin(), request.getLimitVipLevelMax()
                , request.getTitle(), request.getContent(), request.getExpireTime(), request.isDeleteAfterRead(), request.getAttachments(), request.getSenderName(),request.getExchange());
    }
}
