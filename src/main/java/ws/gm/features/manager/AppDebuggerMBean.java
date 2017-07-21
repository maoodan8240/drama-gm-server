package ws.gm.features.manager;

public interface AppDebuggerMBean {
    /**
     * 管理的统一接口
     *
     * @param actionName
     * @param funcName
     * @param jsonStr
     * @return
     */
    String allManager(String actionName, String funcName, String jsonStr);
}
