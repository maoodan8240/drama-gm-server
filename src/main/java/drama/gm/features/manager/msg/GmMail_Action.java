package drama.gm.features.manager.msg;


import drama.gm.features.manager.Action;

public class GmMail_Action implements Action {
    private static final String sendMailToAll = "sendMailToAll";
    private static final String sendMailToPlayers = "sendMailToPlayers";

    @Override
    public String handle(String funcName, String args) {
        if (sendMailToAll.equals(funcName)) {
            sendMailToAll(args);
        } else if (sendMailToPlayers.equals(funcName)) {
            sendMailToPlayers(args);
        }
        return null;
    }

    private String sendMailToAll(String args) {
        return null;
    }

    private String sendMailToPlayers(String args) {
        return null;
    }
}