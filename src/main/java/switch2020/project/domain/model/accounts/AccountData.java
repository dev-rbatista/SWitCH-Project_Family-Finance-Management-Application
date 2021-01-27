package switch2020.project.domain.model.accounts;

import switch2020.project.domain.model.categories.StandardCategory;
import switch2020.project.domain.sandbox.CashTransaction;
import switch2020.project.domain.sandbox.Transaction;
import switch2020.project.domain.utils.TransferenceDTO;
import switch2020.project.domain.utils.exceptions.InvalidAccountDesignationException;

import java.util.ArrayList;
import java.util.List;

public class AccountData {

    private double balance = 0;
    private String description;
    private int accountID;
    private List<Transaction> transactions;

    public AccountData(double balance, String designation, int accountID) {
        validateDesignation(designation);
        this.balance = balance;
        this.description = designation;
        this.accountID = accountID;
        this.transactions = new ArrayList<>();
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void changeBalance(double value) {
        this.balance += value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAccountID() {
        return accountID;
    }

    public boolean isIDOfThisAccount(int accountID) {
        return this.accountID == accountID;
    }

    private void validateDesignation(String designation) {
        if (designation == null || designation.isEmpty() || designation.isBlank()) {
            throw new InvalidAccountDesignationException("Invalid account designation");
        }
    }

    @Override
    public boolean equals(Object otherAccountData) {
        if (this == otherAccountData) return true;
        if (otherAccountData == null || !(otherAccountData instanceof AccountData)) return false;
        AccountData other = (AccountData) otherAccountData;
        return Double.compare(other.balance, balance) == 0 &&
                accountID == other.accountID &&
                description.equals(other.description);
    }

    public boolean hasEnoughMoneyForTransaction(double transferenceAmount){
        return (this.balance-transferenceAmount)>=0;
    }

    public boolean registerTransaction(Account targetAccount, StandardCategory category, TransferenceDTO transferenceDTO){
        CashTransaction cashTransaction = new CashTransaction(targetAccount, category, transferenceDTO);

        transactions.add(cashTransaction);
        return true;
    }

}