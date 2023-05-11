package pl.apala.ing.transactions.model;

import java.math.BigDecimal;

public class Account {

    private String account;
    private int debitCount;
    private int creditCount;
    private BigDecimal balance;  // wolniejsze niz double, ale mamy operacje na $ wiec nie mozemy sobie pozwolic na bledy zaokraglen

    public Account(String account) {
        this.account = account;
        debitCount = 0;
        creditCount = 0;
        balance = BigDecimal.ZERO; // poczatkowy stan konta to 0
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getDebitCount() {
        return debitCount;
    }

    public void setDebitCount(int debitCount) {
        this.debitCount = debitCount;
    }

    public int getCreditCount() {
        return creditCount;
    }

    public void setCreditCount(int creditCount) {
        this.creditCount = creditCount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
