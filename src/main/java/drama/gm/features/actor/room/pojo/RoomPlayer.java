package drama.gm.features.actor.room.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomPlayer {
    private String playerId;
    private String roomId;
    private String playerName;
    private int roleId;
    private String playerIcon;
    /**
     * 是否已经投凶
     */
    private boolean voteMurder;
    /**
     * 是否已经举手准备
     */
    private boolean isReady = false;
    /**
     * 是否配音
     */
    private int isDub = -1;
    /**
     * 搜证次数
     */
    private int srchTimes;
    /**
     * 投票搜证次数
     */
    private int voteSrchTimes;
    /**
     * 独白答题
     */
    private Map<Integer, Integer> soloAnswer = new HashMap<>();
    /**
     * 是否选择心魔
     */
    private boolean selectDraft = false;

    private List<Integer> clueIds = new ArrayList<>();

    public RoomPlayer() {
    }

    public RoomPlayer(String playerId, String roomId) {
        this.playerId = playerId;
        this.roomId = roomId;
        this.playerName = "";
        this.playerIcon = "";
    }


    public boolean isVoteMurder() {
        return voteMurder;
    }

    public void setVoteMurder(boolean voteMurder) {
        this.voteMurder = voteMurder;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerIcon() {
        return playerIcon;
    }

    public void setPlayerIcon(String playerIcon) {
        this.playerIcon = playerIcon;
    }

    public boolean isSelectDraft() {
        return selectDraft;
    }

    public void setSelectDraft(boolean selectDraft) {
        this.selectDraft = selectDraft;
    }

    public Map<Integer, Integer> getSoloAnswer() {
        return soloAnswer;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }


    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getIsDub() {
        return isDub;
    }

    public void setIsDub(int isDub) {
        this.isDub = isDub;
    }

    public List<Integer> getClueIds() {
        return clueIds;
    }


    public int getSrchTimes() {
        return srchTimes;
    }

    public void setSrchTimes(int srchTimes) {
        this.srchTimes = srchTimes;
    }

    public int getVoteSrchTimes() {
        return voteSrchTimes;
    }

    public void setVoteSrchTimes(int voteSrchTimes) {
        this.voteSrchTimes = voteSrchTimes;
    }


    @Override
    public String toString() {
        return "RoomPlayer{" +
                "playerId='" + playerId + '\'' +
                ", roomId='" + roomId + '\'' +
                ", roleId=" + roleId +
                ", isReady=" + isReady +
                ", isDub=" + isDub +
                ", srchTimes=" + srchTimes +
                ", voteSrchTimes=" + voteSrchTimes +
                ", soloAnswer=" + soloAnswer +
                ", selectDraft=" + selectDraft +
                ", clueIds=" + clueIds +
                '}';
    }
}
