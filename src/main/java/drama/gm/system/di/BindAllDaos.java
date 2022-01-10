package drama.gm.system.di;

import com.google.inject.Binder;
import drama.gm.features.actor.user.dao._UserDao;
import ws.common.mongoDB.interfaces.BaseDao;
import drama.gm.features.actor.user.dao.UserDao;
import dm.relationship.daos.AllDaoClassHolder;
import dm.relationship.logServer.daos.AllLogDaoClassHolder;

import java.util.Map.Entry;

/**
 * 绑定全部Dao,所有都不能是单例
 */
public class BindAllDaos {
    public static void bind(Binder binder) {
        for (Entry<Class<? extends BaseDao>, Class<? extends BaseDao>> entry : AllDaoClassHolder.getIntefaceClassToInstanceClass().entrySet()) {
            forceBind(binder, entry.getKey(), entry.getValue());
        }
        for (Entry<Class<? extends BaseDao>, Class<? extends BaseDao>> entry : AllLogDaoClassHolder.getIntefaceClassToInstanceClass().entrySet()) {
            forceBind(binder, entry.getKey(), entry.getValue());
        }
        binder.bind(UserDao.class).to(_UserDao.class);
    }


    /**
     * 强制两个Class绑定为 interface - implements 关系
     *
     * @param binder
     * @param interfaceClass
     * @param instanceClass
     * @param <X>
     * @param <Y>
     */
    private static <X extends BaseDao, Y extends X> void forceBind(Binder binder, Class<? extends BaseDao> interfaceClass, Class<? extends BaseDao> instanceClass) {
        Class<X> interfaceClassX = (Class<X>) interfaceClass;
        Class<Y> instanceClassY = (Class<Y>) instanceClass;
        binder.bind(interfaceClassX).to(instanceClassY);
    }
}
