package model;

import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<String, Account> accounts = new HashMap<>();

    public Account createAccount(String accountNumber, String holderName) {
        Account account = new Account(accountNumber, holderName);
        accounts.put(accountNumber, account);
        return account;
    }

    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public void transfer(String fromAcc, String toAcc, double amount) throws Exception {
        Account from = getAccount(fromAcc);
        Account to = getAccount(toAcc);

        if (from == null || to == null) throw new Exception("One or both accounts not found.");
        from.withdraw(amount);
        to.deposit(amount);
    }
}
