package drama.gm.features.actor.mail.msg;

import drama.gm.system.httpServer.msg.HttpRequestMsg;

/**
 * author: lilin
 * 2017/4/11
 */
public class MailMsg {
    public static class SendMailToPlayers extends HttpRequestMsg {
        private String ids;
        private String title;
        private String content;
        private String expireTime;
        private boolean deleteAfterRead;
        private String attachments;
        private String senderName;

        public String getIds() {
            return ids;
        }

        public void setIds(String ids) {
            this.ids = ids;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(String expireTime) {
            this.expireTime = expireTime;
        }

        public boolean isDeleteAfterRead() {
            return deleteAfterRead;
        }

        public void setDeleteAfterRead(boolean deleteAfterRead) {
            this.deleteAfterRead = deleteAfterRead;
        }

        public String getAttachments() {
            return attachments;
        }

        public void setAttachments(String attachments) {
            this.attachments = attachments;
        }

        public String getSenderName() {
            return senderName;
        }

        public void setSenderName(String senderName) {
            this.senderName = senderName;
        }
    }

    public static class SendMailToAll extends HttpRequestMsg {
        private String title;
        private String content;
        private String expireTime;
        private boolean deleteAfterRead;
        private String attachments;
        private String senderName;
        private String outerRealmIdsOut;          // 限制 - 不是哪一个服的邮件,(不为空时忽略outerRealmIdsIn字段) (空忽略该字段,再去检查outerRealmIdsIn字段)
        private String outerRealmIdsIn;           // 限制 - 哪一个服的邮件, 空为全服邮件
        private String limitPlatforms;   // 限制 - 渠道
        private String limitCreateAtMin;                                     // 限制 - 注册时间下限
        private String limitCreateAtMax;                                     // 限制 - 注册时间上限
        private int limitLevelMin;                                           // 限制 - 等级下限
        private int limitLevelMax;                                           // 限制 - 等级上限
        private int limitVipLevelMin;                                        // 限制 - VIP等级下限
        private int limitVipLevelMax;                                        // 限制 - VIP等级上限

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(String expireTime) {
            this.expireTime = expireTime;
        }

        public boolean isDeleteAfterRead() {
            return deleteAfterRead;
        }

        public void setDeleteAfterRead(boolean deleteAfterRead) {
            this.deleteAfterRead = deleteAfterRead;
        }

        public String getAttachments() {
            return attachments;
        }

        public void setAttachments(String attachments) {
            this.attachments = attachments;
        }

        public String getSenderName() {
            return senderName;
        }

        public void setSenderName(String senderName) {
            this.senderName = senderName;
        }

        public String getOuterRealmIdsOut() {
            return outerRealmIdsOut;
        }

        public void setOuterRealmIdsOut(String outerRealmIdsOut) {
            this.outerRealmIdsOut = outerRealmIdsOut;
        }

        public String getOuterRealmIdsIn() {
            return outerRealmIdsIn;
        }

        public void setOuterRealmIdsIn(String outerRealmIdsIn) {
            this.outerRealmIdsIn = outerRealmIdsIn;
        }

        public String getLimitPlatforms() {
            return limitPlatforms;
        }

        public void setLimitPlatforms(String limitPlatforms) {
            this.limitPlatforms = limitPlatforms;
        }

        public String getLimitCreateAtMin() {
            return limitCreateAtMin;
        }

        public void setLimitCreateAtMin(String limitCreateAtMin) {
            this.limitCreateAtMin = limitCreateAtMin;
        }

        public String getLimitCreateAtMax() {
            return limitCreateAtMax;
        }

        public void setLimitCreateAtMax(String limitCreateAtMax) {
            this.limitCreateAtMax = limitCreateAtMax;
        }

        public int getLimitLevelMin() {
            return limitLevelMin;
        }

        public void setLimitLevelMin(int limitLevelMin) {
            this.limitLevelMin = limitLevelMin;
        }

        public int getLimitLevelMax() {
            return limitLevelMax;
        }

        public void setLimitLevelMax(int limitLevelMax) {
            this.limitLevelMax = limitLevelMax;
        }

        public int getLimitVipLevelMin() {
            return limitVipLevelMin;
        }

        public void setLimitVipLevelMin(int limitVipLevelMin) {
            this.limitVipLevelMin = limitVipLevelMin;
        }

        public int getLimitVipLevelMax() {
            return limitVipLevelMax;
        }

        public void setLimitVipLevelMax(int limitVipLevelMax) {
            this.limitVipLevelMax = limitVipLevelMax;
        }
    }
}
