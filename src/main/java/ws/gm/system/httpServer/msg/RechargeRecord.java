package ws.gm.system.httpServer.msg;

/**
 * Created by lee on 7/14/17.
 */
public class RechargeRecord {
    private String date;
    private String platformType;
    private int outerRealmId;
    private int simpleId;
    private String goodName;
    private int rmb;
    private String outerOrderId;
    private String endDateTime; // 用endDate和endTime拼起来


    public RechargeRecord(String date, String platformType, int outerRealmId, int simpleId, String goodName, int rmb, String outerOrderId, String endDateTime) {
        this.date = date;
        this.platformType = platformType;
        this.outerRealmId = outerRealmId;
        this.simpleId = simpleId;
        this.goodName = goodName;
        this.rmb = rmb;
        this.outerOrderId = outerOrderId;
        this.endDateTime = endDateTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    public int getOuterRealmId() {
        return outerRealmId;
    }

    public void setOuterRealmId(int outerRealmId) {
        this.outerRealmId = outerRealmId;
    }

    public int getSimpleId() {
        return simpleId;
    }

    public void setSimpleId(int simpleId) {
        this.simpleId = simpleId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public int getRmb() {
        return rmb;
    }

    public void setRmb(int rmb) {
        this.rmb = rmb;
    }

    public String getOuterOrderId() {
        return outerOrderId;
    }

    public void setOuterOrderId(String outerOrderId) {
        this.outerOrderId = outerOrderId;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }
}
