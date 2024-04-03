package com.example.bank;


public class ChequeDepositRequest {
    private int fromAccountNo;
    private int fromIfsc;
    private int toAccountNo;
    private int toIfsc;
    private int amount;

    public ChequeDepositRequest() {
    }

    public ChequeDepositRequest(int fromAccountNo, int fromIfsc, int toAccountNo, int toIfsc, int amount) {
        this.fromAccountNo = fromAccountNo;
        this.fromIfsc = fromIfsc;
        this.toAccountNo = toAccountNo;
        this.toIfsc = toIfsc;
        this.amount = amount;
    }

    public int getFromAccountNo() {
        return fromAccountNo;
    }

    public void setFromAccountNo(int fromAccountNo) {
        this.fromAccountNo = fromAccountNo;
    }

    public int getFromIfsc() {
        return fromIfsc;
    }

    public void setFromIfsc(int fromIfsc) {
        this.fromIfsc = fromIfsc;
    }

    public int getToAccountNo() {
        return toAccountNo;
    }

    public void setToAccountNo(int toAccountNo) {
        this.toAccountNo = toAccountNo;
    }

    public int getToIfsc() {
        return toIfsc;
    }

    public void setToIfsc(int toIfsc) {
        this.toIfsc = toIfsc;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
