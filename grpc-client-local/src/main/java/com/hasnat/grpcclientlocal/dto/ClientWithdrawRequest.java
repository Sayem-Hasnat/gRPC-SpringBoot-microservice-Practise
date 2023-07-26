package com.hasnat.grpcclientlocal.dto;

public class ClientWithdrawRequest {
    private int accountNumber ;
    private int amount ;


    public ClientWithdrawRequest(int accountNumber, int amount) {
        this.accountNumber = accountNumber;
        this.amount = amount;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
