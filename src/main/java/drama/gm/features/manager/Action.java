package drama.gm.features.manager;

/**
 * 实现此接口的类必须是无状态的类
 */
@FunctionalInterface
public interface Action {
    public String handle(String funcName, String args);
}
