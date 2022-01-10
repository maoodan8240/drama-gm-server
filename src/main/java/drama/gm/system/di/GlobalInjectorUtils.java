package drama.gm.system.di;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import ws.common.utils.di.GlobalInjector;

public class GlobalInjectorUtils {

    public static void init() throws Exception {
        Injector injector = Guice.createInjector(binder -> {
            _bindAll(binder);
        });
        GlobalInjector.setDefaultInjector(injector);
    }

    private static void _bindAll(Binder binder) throws RuntimeException {
        BindAllDaos.bind(binder);
        BindAllControlers.bind(binder);
        BindAllBeans.bind(binder);
    }
}
