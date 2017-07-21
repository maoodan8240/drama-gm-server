package ws.gm.features.actor.mail.ctrl;

import com.sun.net.httpserver.HttpExchange;

/**
 * author: lilin
 * 2017/4/11
 */
public interface MailCtrl {
    void sendMailToPlayers(String ids, String title, String content, String expireTime, boolean deleteAfterRead, String attachments, String senderName, HttpExchange exchange);

    void sendMailToAll(String outerRealmIdsIn, String outerRealmIdsOut, String limitPlatforms, String limitCreateAtMin,
                       String limitCreateAtMax, int limitLevelMin, int limitLevelMax, int limitVipLevelMin, int limitVipLevelMax,
                       String title, String content, String expireTime, boolean deleteAfterRead, String attachments, String senderName, HttpExchange exchange);
}
