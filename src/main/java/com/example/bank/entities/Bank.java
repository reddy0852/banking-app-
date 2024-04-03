package com.example.bank.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Bank {
    @Id
    @Column(name = "account_no")
    private int accountNo;
    private String name;
    private int ifsc;
    private int amount;

    public Bank() {
    }

    public Bank(int accountNo, String name, int ifsc, int amount) {
        this.accountNo = accountNo;
        this.name = name;
        this.ifsc = ifsc;
        this.amount = amount;
    }

    public int getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(int accountNo) {
        this.accountNo = accountNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIfsc() {
        return ifsc;
    }

    public void setIfsc(int ifsc) {
        this.ifsc = ifsc;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
