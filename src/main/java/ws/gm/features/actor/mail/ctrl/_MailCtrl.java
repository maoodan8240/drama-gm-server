package ws.gm.features.actor.mail.ctrl;

import com.sun.net.httpserver.HttpExchange;
import org.apache.commons.lang3.StringUtils;
import ws.common.mongoDB.utils.WsJsonUtils;
import ws.common.table.table.interfaces.cell.TupleListCell;
import ws.common.table.table.utils.CellTypeEnum;
import ws.common.table.table.utils.CellUtils;
import ws.gm.system.httpServer.msg.HttpResponseMsg;
import ws.gm.system.httpServer.utils.ResponseUtils;
import ws.gm.system.jmx.JmxJolokiaClient;
import ws.protos.EnumsProtos;
import ws.relationship.base.IdMaptoCount;
import ws.relationship.gm.GmMailList;
import ws.relationship.topLevelPojos.mailCenter.GmMail;

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
        GmMailList list = new GmMailList();
        list.setSimpleIds(playerIds);
        GmMail mail = new GmMail();
        createBaseMail(mail, title, content, expireTime, deleteAfterRead, attachments, senderName);
        list.setGmMail(mail);
        String[] obj = new String[]{"GmMail_Action", "sendMailToPlayers", WsJsonUtils.javaObjectToJSONStr(list)};
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
        GmMail gmMail = new GmMail();
        createBaseMail(gmMail, title, content, expireTime, deleteAfterRead, attachments, senderName);
        if (StringUtils.isNotBlank(outerRealmIdsIn)) {
            for (String s : outerRealmIdsIn.split(",")) {
                gmMail.getOuterRealmIdsIn().add(Integer.parseInt(s));
            }
        }
        if (StringUtils.isNotBlank(outerRealmIdsOut)) {
            for (String s : outerRealmIdsOut.split(",")) {
                gmMail.getOuterRealmIdsOut().add(Integer.parseInt(s));
            }
        }
        if (StringUtils.isNotBlank(limitPlatforms)) {
            for (String s : limitPlatforms.split(",")) {
                gmMail.getLimitPlatforms().add(EnumsProtos.PlatformTypeEnum.valueOf(Integer.parseInt(s)));
            }
        }

        gmMail.setLimitCreateAtMax(limitCreateAtMax);
        gmMail.setLimitCreateAtMin(limitCreateAtMin);
        gmMail.setLimitLevelMax(limitLevelMax);
        gmMail.setLimitLevelMin(limitLevelMin);
        gmMail.setLimitVipLevelMax(limitVipLevelMax);
        gmMail.setLimitVipLevelMin(limitVipLevelMin);
        String[] obj = new String[]{"GmMail_Action", "sendMailToAll", WsJsonUtils.javaObjectToJSONStr(gmMail)};
        String parameter = "allManager";
        JmxJolokiaClient.sendUtil(parameter, obj);
        HttpResponseMsg responseMsg = HttpResponseMsg.createResponse("xxxxx", "");
        ResponseUtils.send(responseMsg, exchange);
    }

    private void createBaseMail(GmMail mail, String title, String content, String expireTime, boolean deleteAfterRead,
                                String attachments, String senderName) {
        mail.setTitle(title);
        mail.setContent(content);
        mail.setExpireTime(expireTime);
        mail.setDeleteAfterRead(deleteAfterRead);
        if (StringUtils.isNotBlank(attachments)) {
            try {
                TupleListCell<Integer> tupleListCell = (TupleListCell<Integer>) CellUtils.parse(CellTypeEnum.ARRAY_INT_2.getName(), attachments);
                mail.setAttachments(IdMaptoCount.parse(tupleListCell).getAll(1));
            } catch (Exception e) {
                throw new RuntimeException("attachments =" + attachments + " 附件字段解析失败！", e);
            }
            mail.setHasAttachments(true);
        }
        mail.setSenderName(senderName);
    }
}
