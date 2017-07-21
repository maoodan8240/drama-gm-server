package ws.gm.system.httpServer.msg;

import java.util.List;

/**
 * Created by lee on 7/3/17.
 */
public class GroupActivityJson {

    private int groupAcId;                                    // 组活动Id
    private int groupActType;                                 // 活动类型
    private List<Integer> subIds;                              // 子活动Ids
    private String tagTitle;                                  // 活动标签标题
    private String contentTitle;                              // 活动内容标题
    private String contentDesc;                               // 活动内容的描述
    private String tagIcon;                                   // 活动标签图标


    public GroupActivityJson() {
    }

    public GroupActivityJson(int groupAcId, int groupActType, List<Integer> subIds, String tagTitle, String contentTitle, String contentDesc, String tagIcon) {
        this.groupAcId = groupAcId;
        this.groupActType = groupActType;
        this.subIds = subIds;
        this.tagTitle = tagTitle;
        this.contentTitle = contentTitle;
        this.contentDesc = contentDesc;
        this.tagIcon = tagIcon;
    }

    public int getGroupAcId() {
        return groupAcId;
    }

    public void setGroupAcId(int groupAcId) {
        this.groupAcId = groupAcId;
    }

    public int getGroupActType() {
        return groupActType;
    }

    public void setGroupActType(int groupActType) {
        this.groupActType = groupActType;
    }

    public List<Integer> getSubIds() {
        return subIds;
    }

    public void setSubIds(List<Integer> subIds) {
        this.subIds = subIds;
    }

    public String getTagTitle() {
        return tagTitle;
    }

    public void setTagTitle(String tagTitle) {
        this.tagTitle = tagTitle;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public String getContentDesc() {
        return contentDesc;
    }

    public void setContentDesc(String contentDesc) {
        this.contentDesc = contentDesc;
    }

    public String getTagIcon() {
        return tagIcon;
    }

    public void setTagIcon(String tagIcon) {
        this.tagIcon = tagIcon;
    }
}
