package ws.gm.features.actor.data.base;

/**
 * Created by lee on 7/18/17.
 */
public class PaymentData {
    private String date;
    private int newPlayerNewPayment;
    private int paymentCount;
    private int paymentSum;
    private int newPlayerCount;
    private int activeAccount;

    public PaymentData() {
    }

    public PaymentData(String date, int newPlayerNewPayment, int paymentCount, int paymentSum, int newPlayerCount, int activeAccount) {
        this.date = date;
        this.newPlayerNewPayment = newPlayerNewPayment;
        this.paymentCount = paymentCount;
        this.paymentSum = paymentSum;
        this.newPlayerCount = newPlayerCount;
        this.activeAccount = activeAccount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNewPlayerNewPayment() {
        return newPlayerNewPayment;
    }

    public void setNewPlayerNewPayment(int newPlayerNewPayment) {
        this.newPlayerNewPayment = newPlayerNewPayment;
    }

    public int getPaymentCount() {
        return paymentCount;
    }

    public void setPaymentCount(int paymentCount) {
        this.paymentCount = paymentCount;
    }

    public int getPaymentSum() {
        return paymentSum;
    }

    public void setPaymentSum(int paymentSum) {
        this.paymentSum = paymentSum;
    }

    public int getNewPlayerCount() {
        return newPlayerCount;
    }

    public void setNewPlayerCount(int newPlayerCount) {
        this.newPlayerCount = newPlayerCount;
    }

    public int getActiveAccount() {
        return activeAccount;
    }

    public void setActiveAccount(int activeAccount) {
        this.activeAccount = activeAccount;
    }
}
