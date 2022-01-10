package drama.gm.features.actor.mail.ctrl;

import com.sun.net.httpserver.HttpExchange;
import org.apache.commons.lang3.StringUtils;
import ws.common.mongoDB.utils.WsJsonUtils;
import ws.common.table.table.interfaces.cell.TupleListCell;
import ws.common.table.table.utils.CellTypeEnum;
import ws.common.table.table.utils.CellUtils;
import drama.gm.system.httpServer.msg.HttpResponseMsg;
import drama.gm.system.httpServer.utils.ResponseUtils;
import drama.gm.system.jmx.JmxJolokiaClient;

import java.util.ArrayList;
import java.util.List;

/**
 * author: lilin
 * 2017/4/11
 */
public class _MailCtrl implements MailCtrl {

    @Override
    public void sendMailToPlayers(String ids, String title, String content, String expireTime, boolean deleteAfterRead, String attachments, String senderName, HttpExchange exchange) {
        List<Integer> playerIds = new ArrayList<>();
        for (String s : ids.split(",")) {
            playerIds.add(Integer.parseInt(s));
        }
        String[] obj = new String[]{"GmMail_Action", "sendMailToPlayers", WsJsonUtils.javaObjectToJSONStr(null)};
        String parameter = "allManager";
        JmxJolokiaClient.sendUtil(parameter, obj);
        HttpResponseMsg responseMsg = HttpResponseMsg.createResponse("xxxxx", "");
        ResponseUtils.send(responseMsg, exchange);
    }

    /**
     * @param outerRealmIdsIn  限制 - 不是哪一个服的邮件,(不为空时忽略outerRealmIdsIn字段) (空忽略该字段,再去检查outerRealmIdsIn字段)
     * @param outerRealmIdsOut 限制 - 哪一个服的邮件, 空为全服邮件
     * @param limitPlatforms   限制 - 渠道
     * @param limitCreateAtMin 限制 - 注册时间下限
     * @param limitCreateAtMax 限制 - 注册时间上限
     * @param limitLevelMin    限制 - 等级下限
     * @param limitLevelMax    限制 - 等级上限
     * @param limitVipLevelMin 限制 - VIP等级下限
     * @param limitVipLevelMax 限制 - VIP等级上限
     */
    @Override
    public void sendMailToAll(String outerRealmIdsIn, String outerRealmIdsOut, String limitPlatforms, String limitCreateAtMin,
                              String limitCreateAtMax, int limitLevelMin, int limitLevelMax, int limitVipLevelMin, int limitVipLevelMax,
                              String title, String content, String expireTime, boolean deleteAfterRead, String attachments, String senderName, HttpExchange exchange) {

        String[] obj = new String[]{"GmMail_Action", "sendMailToAll", WsJsonUtils.javaObjectToJSONStr(null)};
        String parameter = "allManager";
        JmxJolokiaClient.sendUtil(parameter, obj);
        HttpResponseMsg responseMsg = HttpResponseMsg.createResponse("xxxxx", "");
        ResponseUtils.send(responseMsg, exchange);
    }


}
