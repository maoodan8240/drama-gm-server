package drama.gm.features.actor.whiteblacklist.base;

/**
 * Created by lee on 7/27/17.
 */
public class WhiteBlackList {
    private int simpleId;
    private String playerId;
    private String platformType;
    private int outerRealmId;
    private String createAtDate;
    private String playerName;
    private long remainLockTime;

    public WhiteBlackList() {
    }


    public WhiteBlackList(int simpleId, String playerId, String platformType, int outerRealmId, String createAtDate, String playerName) {
        this.simpleId = simpleId;
        this.playerId = playerId;
        this.platformType = platformType;
        this.outerRealmId = outerRealmId;
        this.createAtDate = createAtDate;
        this.playerName = playerName;
    }

    public int getSimpleId() {
        return simpleId;
    }

    public void setSimpleId(int simpleId) {
        this.simpleId = simpleId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
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

    public String getCreateAtDate() {
        return createAtDate;
    }

    public void setCreateAtDate(String createAtDate) {
        this.createAtDate = createAtDate;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public long getRemainLockTime() {
        return remainLockTime;
    }

    public void setRemainLockTime(long remainLockTime) {
        this.remainLockTime = remainLockTime;
    }
}
