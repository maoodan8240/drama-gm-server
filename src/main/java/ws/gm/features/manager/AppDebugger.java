package ws.gm.features.manager;

public class AppDebugger implements AppDebuggerMBean {

    @Override
    public String allManager(String actionName, String funcName, String jsonStr) {
        String re = ActionContainer.handle(actionName, funcName, jsonStr);
        return re;
    }

}
