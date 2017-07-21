package ws.gm.features.manager;


import ws.gm.features.manager.msg.GmMail_Action;

public enum ActionContainer {

    GmMail_Action(new GmMail_Action()), //
    ;//

    private Action action;

    private ActionContainer(Action action) {
        this.action = action;
    }


    public Action getAction() {
        return action;
    }

    public static String handle(String actionName, String funcName, String args) {
        for (ActionContainer ac : values()) {
            if (ac.toString().equals(actionName)) {
                return ac.getAction().handle(funcName, args);
            }
        }
        return null;
    }

}
