package drama.gm.features.actor.user.msg;

import drama.gm.system.httpServer.msg.HttpRequestMsg;

import java.util.ArrayList;
import java.util.List;

/**
 * author: lilin
 * 2017/4/20
 */
public class UserRequestMsg {

    public static class AddUserMsg extends HttpRequestMsg {
        private String account;
        private String password;
        private List<Integer> permission = new ArrayList<>();

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

        public List<Integer> getPermission() {
            return permission;
        }

        public void setPermission(List<Integer> permission) {
            this.permission = permission;
        }
    }

    public static class RequestAllUserMsg extends HttpRequestMsg {

    }

    public static class ModifyUserMsg extends HttpRequestMsg {
        private String id;
        private String account;
        private String password;
        private List<Integer> permission = new ArrayList<>();

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

        public List<Integer> getPermission() {
            return permission;
        }

        public void setPermission(List<Integer> permission) {
            this.permission = permission;
        }
    }

    public static class DeleteUserMsg extends HttpRequestMsg {
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class RequestPermissionMsg extends HttpRequestMsg {
    }

}
