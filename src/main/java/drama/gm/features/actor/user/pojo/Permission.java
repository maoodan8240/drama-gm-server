package drama.gm.features.actor.user.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * author: lilin
 * 2017/4/19
 */
public enum Permission {
    Mail("邮件"),
    QueryPlayerBasic("查询玩家"),
    SendGmCommand("GM命令"),
    GmUser("Gm用户管理"),
    Remain("留存"),
    DayReport("日报"),;

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    Permission(String description) {
        this.description = description;
    }


    public static List<Integer> allPermission() {
        List<Integer> all = new ArrayList<>();
        for (Permission p : Permission.values()) {
            all.add(p.ordinal());
        }
        return all;
    }
}
