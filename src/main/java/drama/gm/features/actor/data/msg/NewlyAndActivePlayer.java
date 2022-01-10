package drama.gm.features.actor.data.msg;

/**
 * Created by lee on 7/18/17.
 */
public class NewlyAndActivePlayer {

    private String date;
    private int newPlayerCount;
    private int activeDeviceCount;
    private int activeAccount;

    public NewlyAndActivePlayer() {
    }

    public NewlyAndActivePlayer(String date, int newPlayerCount, int activeDeviceCount, int activeAccount) {
        this.date = date;
        this.newPlayerCount = newPlayerCount;
        this.activeDeviceCount = activeDeviceCount;
        this.activeAccount = activeAccount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNewPlayerCount() {
        return newPlayerCount;
    }

    public void setNewPlayerCount(int newPlayerCount) {
        this.newPlayerCount = newPlayerCount;
    }

    public int getActiveDeviceCount() {
        return activeDeviceCount;
    }

    public void setActiveDeviceCount(int activeDeviceCount) {
        this.activeDeviceCount = activeDeviceCount;
    }

    public int getActiveAccount() {
        return activeAccount;
    }

    public void setActiveAccount(int activeAccount) {
        this.activeAccount = activeAccount;
    }
}
