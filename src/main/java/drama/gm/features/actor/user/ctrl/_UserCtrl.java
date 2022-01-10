package drama.gm.features.actor.user.ctrl;

import com.alibaba.fastjson.JSON;
import com.sun.net.httpserver.HttpExchange;
import dm.relationship.base.MagicWords_Mongodb;
import dm.relationship.exception.BusinessLogicMismatchConditionException;
import dm.relationship.exception.CmMessageIllegalArgumentException;
import drama.gm.features.session.SessionManager;
import drama.gm.system.httpServer.inf.AbstractHttpMessageCtrl;
import drama.gm.system.httpServer.msg.HttpResponseMsg;
import drama.gm.system.httpServer.utils.ResponseUtils;
import drama.protos.EnumsProtos;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.mongoDB.interfaces.MongoDBClient;
import ws.common.utils.di.GlobalInjector;
import ws.common.utils.message.interfaces.PrivateMsg;
import drama.gm.features.actor.user.dao.UserDao;
import drama.gm.features.actor.user.pojo.Permission;
import drama.gm.features.actor.user.pojo.User;
import drama.gm.features.actor.user.msg.QueryPermissionResponseMsg;
import drama.gm.features.actor.user.msg.QueryUsersResponseMsg;
import drama.gm.features.actor.user.msg.UserLoginResponseMsg;
import drama.gm.features.actor.user.msg.UserRegisterResponsetMsg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 17-2-23.
 */
public class _UserCtrl extends AbstractHttpMessageCtrl<User> implements UserCtrl {
    private static final MongoDBClient MONGO_DB_CLIENT = GlobalInjector.getInstance(MongoDBClient.class);
    private static final Logger logger = LoggerFactory.getLogger(_UserCtrl.class);
    private static final SessionManager SESSION_MANAGER = GlobalInjector.getInstance(SessionManager.class);
    private static final UserDao userDao = GlobalInjector.getInstance(UserDao.class);

    @Override
    public void sendPrivateMsg(PrivateMsg privateMsg) {

    }

    @Override
    public HttpExchange getHttpExchange() {
        return this.httpExchange;
    }

    @Override
    public void setHttpExchange(HttpExchange httpExchange) {
        this.httpExchange = httpExchange;
    }

    @Override
    public void register(String account, String password) {
        //TODO 校验参数合法性
        if (StringUtils.isBlank(account)) {
            String msg = String.format("Invalid arg id=%s", account);
            throw new CmMessageIllegalArgumentException(msg);
        }
        if (StringUtils.isBlank(password)) {
            String msg = String.format("Invalid arg password=%s", password);
            throw new CmMessageIllegalArgumentException(msg);
        }
        userDao.init(MONGO_DB_CLIENT, MagicWords_Mongodb.DbName_Gm);
        User dbUser = userDao.query(account.trim());
        if (dbUser != null) {
            String msg = String.format("account exist account=%s", account);
            throw new BusinessLogicMismatchConditionException(msg, EnumsProtos.ErrorCodeEnum.SECURITY_CODE_CHECK_FAIL);
        }
        User user = new User(ObjectId.get().toString());
        user.setPassword(password.trim());
        user.setAccount(account.trim());
        user.setPermission(Permission.allPermission());
        userDao.insert(user);
        logger.info("Register Success, newAccount={}", account);

        String sessionId = SESSION_MANAGER.newSessionId();
        SESSION_MANAGER.put(sessionId, user);
        UserRegisterResponsetMsg msg = new UserRegisterResponsetMsg(sessionId);
        HttpResponseMsg response = HttpResponseMsg.createResponse(UserRegisterResponsetMsg.class.getName(), JSON.toJSONString(msg));
        ResponseUtils.send(response, getHttpExchange());
    }

    @Override
    public void login(String account, String password) {
        if (StringUtils.isBlank(account)) {
            String msg = String.format("Invalid arg account=%s", account);
            throw new CmMessageIllegalArgumentException(msg);
        }
        if (StringUtils.isBlank(password)) {
            String msg = String.format("Invalid arg password=%s", password);
            throw new CmMessageIllegalArgumentException(msg);
        }
        userDao.init(MONGO_DB_CLIENT, MagicWords_Mongodb.DbName_Gm);
        User user = userDao.query(account);
        if (user == null) {
            String msg = String.format("can not find this account");
            throw new BusinessLogicMismatchConditionException(msg, EnumsProtos.ErrorCodeEnum.SECURITY_CODE_CHECK_FAIL);
        }
        if (!user.getPassword().equals(password.trim())) {
            String msg = String.format("wrong password");
            throw new BusinessLogicMismatchConditionException(msg, EnumsProtos.ErrorCodeEnum.SECURITY_CODE_CHECK_FAIL);
        }
        // TODO 下面数字定魔鬼数字
        String sessionId = SESSION_MANAGER.newSessionId();
        SESSION_MANAGER.put(sessionId, user, 5);
        UserLoginResponseMsg msg = new UserLoginResponseMsg(sessionId);
        HttpResponseMsg response = HttpResponseMsg.createResponse(UserLoginResponseMsg.class.getName(), JSON.toJSONString(msg));
        ResponseUtils.send(response, getHttpExchange());
    }


    @Override
    public User auth(String sessionId) {
        User user = null;
        if (StringUtils.isBlank(sessionId)) {
            return user;
        }
        user = SESSION_MANAGER.queryBySessionId(sessionId);
        return user;
    }

    @Override
    public void addUser(String account, String password, List<Integer> permission) {
        if (StringUtils.isBlank(account)) {
            String msg = String.format("Invalid arg id=%s", account);
            throw new CmMessageIllegalArgumentException(msg);
        }
        if (StringUtils.isBlank(password)) {
            String msg = String.format("Invalid arg password=%s", password);
            throw new CmMessageIllegalArgumentException(msg);
        }

        User old = userDao.query(account);
        if (old != null) {
            HttpResponseMsg responseMsg = HttpResponseMsg.createErrorResponse("用户已存在");
            ResponseUtils.send(responseMsg, httpExchange);
            return;
        }

        User user = new User(ObjectId.get().toString());
        user.setAccount(account.trim());
        user.setPassword(password.trim());
        user.setPermission(permission);
        userDao.insert(user);
        HttpResponseMsg responseMsg = HttpResponseMsg.createResponse("xxxxx", "");
        ResponseUtils.send(responseMsg, httpExchange);
    }

    @Override
    public void modifyUser(String id, String account, String password, List<Integer> permission) {
        User user = userDao.findOne(id);
//        user.setAccount(account);
        if (StringUtils.isNotBlank(password)) {
            user.setPassword(password);
        }
        user.setPermission(permission);
        userDao.insertIfExistThenReplace(user);
        HttpResponseMsg responseMsg = HttpResponseMsg.createResponse("xxxxx", "");
        ResponseUtils.send(responseMsg, httpExchange);
    }

    @Override
    public void deleteUser(String id) {
        userDao.init(MONGO_DB_CLIENT, MagicWords_Mongodb.DbName_Gm);
        User user = userDao.findOne(id);
        userDao.delete(user);
        HttpResponseMsg responseMsg = HttpResponseMsg.createResponse("xxxxx", "");
        ResponseUtils.send(responseMsg, httpExchange);
    }

    @Override
    public void getAllUser() {
        List<User> all = userDao.findAll();
        QueryUsersResponseMsg msg = new QueryUsersResponseMsg();
        msg.getUsers().addAll(all);
        HttpResponseMsg response = HttpResponseMsg.createResponse(QueryUsersResponseMsg.class.getName(), JSON.toJSONString(msg));
        ResponseUtils.send(response, getHttpExchange());
    }

    @Override
    public void requestPermission() {
        List<String> result = new ArrayList<>();
        for (Permission permission : Permission.values()) {
            result.add(permission.getDescription());
        }
        QueryPermissionResponseMsg msg = new QueryPermissionResponseMsg();
        msg.getPermissions().addAll(result);
        HttpResponseMsg response = HttpResponseMsg.createResponse(QueryPermissionResponseMsg.class.getName(), JSON.toJSONString(msg));
        ResponseUtils.send(response, getHttpExchange());
    }

}
