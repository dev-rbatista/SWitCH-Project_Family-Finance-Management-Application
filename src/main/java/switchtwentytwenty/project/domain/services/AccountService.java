package switchtwentytwenty.project.domain.services;

import switchtwentytwenty.project.domain.DTOs.input.AddCreditCardAccountDTO;
import switchtwentytwenty.project.domain.model.accounts.*;
import switchtwentytwenty.project.domain.model.categories.StandardCategory;
import switchtwentytwenty.project.domain.utils.TransferenceDTO;
import switchtwentytwenty.project.domain.model.Family;
import switchtwentytwenty.project.domain.model.FamilyMember;

import java.util.List;

public class AccountService {
    public boolean createPersonalCashAccount(FamilyMember targetMember, String accountDesignation, double initialBalance) {
        int accountID = generateID(targetMember);
        try {
            Account cashAccount = new CashAccount(accountDesignation, initialBalance, accountID);
            return targetMember.addAccount(cashAccount);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private int generateID(FamilyMember targetMember) {
        int max = 0;
        List<Account> tempList = targetMember.getAccounts();
        for (Account account : tempList) {
            if (max < account.getAccountID()) {
                max = account.getAccountID();
            }
        }
        return max + 1;
    }

    /**
     * Method to create a family cash account for a family object
     *
     * @param targetFamily       identifier of the family object
     * @param accountDesignation designation for the family cash account
     * @param initialBalance     initial balance for the account
     * @return returns true if an account was created and stored by the family object
     */

    public boolean createFamilyCashAccount(Family targetFamily, String accountDesignation, double initialBalance) {
        //if (accountDesignation==null||accountDesignation.trim().length()==0||accountDesignation.isEmpty()) accountDesignation = ("Conta da familia " + targetFamily.getFamilyName());
        Account newCashAccount = new CashAccount(accountDesignation, initialBalance, 0);
        if (!targetFamily.hasCashAccount()) {
            targetFamily.addCashAccount(newCashAccount);
            return true;
        } else {
            throw new IllegalArgumentException("This Family already has a Cash Account");
        }
    }

    public boolean addBankAccount(FamilyMember targetMember, String accountName, Double balance) {
        int accountID = generateID(targetMember);
        try {
            Account bankAccount = new BankAccount(accountName, balance, accountID);
            targetMember.addAccount(bankAccount);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean createPersonalCreditCardAccount(AddCreditCardAccountDTO addCreditCardAccountDTO, FamilyMember targetMember) {
        int accountID = generateID(targetMember);

        Account creditCardAccount = new CreditCardAccount(addCreditCardAccountDTO, accountID);
        return targetMember.addAccount(creditCardAccount);
    }


    public boolean addBankSavingsAccount(FamilyMember targetMember, String accountName, Double balance, Double interestRate) {
        int accountID = generateID(targetMember);
        Account bankSavingsAccount = new BankSavingsAccount(accountID, accountName, balance, interestRate);
        targetMember.addAccount(bankSavingsAccount);
        return true;
    }

    public boolean transferCashFromFamilyToFamilyMember(Account familyAccount, Account targetAccount, StandardCategory category, TransferenceDTO transferCashDTO){
        double transferedValue = transferCashDTO.getTransferedValue();
        if(familyAccount==null) throw new IllegalArgumentException("Family has no account");
        if(!familyAccount.hasEnoughMoneyForTransaction(transferedValue)) return false;

        //Pensar em forma de fazer undo??? criar um metodo para adicionar dinheiro e outro para remover dinheiro??
        familyAccount.changeBalance(transferedValue*-1);
        familyAccount.registerTransaction(targetAccount, category, transferCashDTO);
        targetAccount.changeBalance(transferedValue);
        targetAccount.registerTransaction(familyAccount, category, transferCashDTO);

        return true;
    }

    public boolean transferCashBetweenFamilyMembersCashAccounts(Account originAccount, Account destinationAccount, StandardCategory category, TransferenceDTO transferCashDTO){
        double transferedValue = transferCashDTO.getTransferedValue();
        if(!originAccount.hasEnoughMoneyForTransaction(transferedValue)) return false; //problema a evitar,  return no meio do código
       /* if(destinationAccount==null) {
            transferCashDTO.getFamilyID(createPersonalCashAccount(destinationAccount.

        }*/
        //Pensar em forma de fazer undo??? criar um metodo para adicionar dinheiro e outro para remover dinheiro??
        originAccount.changeBalance(transferedValue*-1);
        originAccount.registerTransaction(destinationAccount, category, transferCashDTO);
        destinationAccount.changeBalance(transferedValue);
        destinationAccount.registerTransaction(originAccount, category, transferCashDTO);

        return true;
    }



}