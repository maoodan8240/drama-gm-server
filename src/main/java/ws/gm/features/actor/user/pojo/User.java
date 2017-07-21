package ws.gm.features.actor.user.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import ws.common.mongoDB.interfaces.TopLevelPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 17-2-23.
 */
public class User implements TopLevelPojo {
    private static final long serialVersionUID = 1L;
    @JSONField(name = "_id")
    private String id;
    private String account;
    private String password;

    private List<Integer> permission=new ArrayList<>();

    public List<Integer> getPermission() {
        return permission;
    }

    public void setPermission(List<Integer> permission) {
        this.permission = permission;
    }

    public User() {
    }

    public User(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getOid() {
        return id;
    }

    @Override
    public void setOid(String id) {
        this.id = id;
    }
}
