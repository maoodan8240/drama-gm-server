import ws.common.mongoDB.interfaces.MongoDBClient;
import ws.common.utils.di.GlobalInjector;
import ws.gm.Launcher;
import ws.gm.system.config.AppConfig;
import ws.gm.system.di.GlobalInjectorUtils;
import ws.relationship.base.MagicWords_Mongodb;
import ws.relationship.daos.paymentOrder.PaymentOrderDao;

/**
 * Created by zhangweiwei on 17-7-12.
 */
public class T {
    public static void main(String[] args) throws Exception {
        AppConfig.init();
        GlobalInjectorUtils.init();
        Launcher._mongodbInit();
        MongoDBClient MONGO_DB_CLIENT = GlobalInjector.getInstance(MongoDBClient.class);
        PaymentOrderDao dao = GlobalInjector.getInstance(PaymentOrderDao.class);
        dao.init(MONGO_DB_CLIENT, MagicWords_Mongodb.TopLevelPojo_All_Common);
        System.out.println(dao.findAll().size());
    }
}
