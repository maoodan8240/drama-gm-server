package ws.gm.system.httpServer.msg;

import ws.relationship.base.IdMaptoCount;

/**
 * Created by lee on 7/3/17.
 */
public class SubActivityJson {
    private int subActId;                                   // 子活动Id
    private String subActTitle;                             // 子活动内容
    private String subActComplateType;                      // 子活动完成类型
    private String subActComplateNumber;                    // 子活动完成数量
    private String subActExchangeConsume;                   // 兑换消耗
    private int subActCanExchangeConsumeTs;                 // 可以兑换的次数
    private int subActCompletePlayerLevel;                  // 完成条件（玩家等级）
    private int subActCompletePlayerVipLevel;               // 完成条件（玩家VIP等级）
    private String selectedItems;                           // 任务完成奖励 lable


    public SubActivityJson() {
    }

    public SubActivityJson(int subActId, String subActTitle, String subActComplateType, String subActComplateNumber, String subActExchangeConsume, int subActCanExchangeConsumeTs, int subActCompletePlayerLevel, int subActCompletePlayerVipLevel, String selectedItems) {
        this.subActId = subActId;
        this.subActTitle = subActTitle;
        this.subActComplateType = subActComplateType;
        this.subActComplateNumber = subActComplateNumber;
        this.subActExchangeConsume = subActExchangeConsume;
        this.subActCanExchangeConsumeTs = subActCanExchangeConsumeTs;
        this.subActCompletePlayerLevel = subActCompletePlayerLevel;
        this.subActCompletePlayerVipLevel = subActCompletePlayerVipLevel;
        this.selectedItems = selectedItems;
    }

    public int getSubActId() {
        return subActId;
    }

    public void setSubActId(int subActId) {
        this.subActId = subActId;
    }

    public void setSubActTitle(String subActTitle) {
        this.subActTitle = subActTitle;
    }

    public void setSubActComplateType(String subActComplateType) {
        this.subActComplateType = subActComplateType;
    }

    public void setSubActComplateNumber(String subActComplateNumber) {
        this.subActComplateNumber = subActComplateNumber;
    }

    public void setSelectedItems(String selectedItems) {
        this.selectedItems = selectedItems;
    }

    public void setSubActExchangeConsume(String subActExchangeConsume) {
        this.subActExchangeConsume = subActExchangeConsume;
    }

    public void setSubActCanExchangeConsumeTs(int subActCanExchangeConsumeTs) {
        this.subActCanExchangeConsumeTs = subActCanExchangeConsumeTs;
    }

    public void setSubActCompletePlayerLevel(int subActCompletePlayerLevel) {
        this.subActCompletePlayerLevel = subActCompletePlayerLevel;
    }

    public void setSubActCompletePlayerVipLevel(int subActCompletePlayerVipLevel) {
        this.subActCompletePlayerVipLevel = subActCompletePlayerVipLevel;
    }

    public String getSubActComplateNumber() {
        return subActComplateNumber;
    }

    public String getSelectedItems() {
        return selectedItems;
    }

    public String getSubActTitle() {
        return subActTitle;
    }

    public String getSubActComplateType() {
        return subActComplateType;
    }


    public String getSubActExchangeConsume() {
        return subActExchangeConsume;
    }

    public int getSubActCanExchangeConsumeTs() {
        return subActCanExchangeConsumeTs;
    }

    public int getSubActCompletePlayerLevel() {
        return subActCompletePlayerLevel;
    }

    public int getSubActCompletePlayerVipLevel() {
        return subActCompletePlayerVipLevel;
    }


    public static IdMaptoCount parsStringToMissonReward(String selectedItems) {
        IdMaptoCount idMaptoCount = new IdMaptoCount();
        return idMaptoCount;
    }


}