package switchtwentytwenty.project.domain.services;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.DTOs.input.AddCashAccountDTO;
import switchtwentytwenty.project.domain.DTOs.output.TransactionDataDTO;
import switchtwentytwenty.project.domain.model.FamilyMember;
import switchtwentytwenty.project.domain.model.accounts.Account;
import switchtwentytwenty.project.domain.model.accounts.CashAccount;
import switchtwentytwenty.project.domain.model.categories.StandardCategory;
import switchtwentytwenty.project.domain.sandbox.CashTransaction;
import switchtwentytwenty.project.domain.sandbox.Transaction;
import switchtwentytwenty.project.domain.utils.TransferenceDTO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest {

    // FamilyMember
    int familyID = 1;
    String selfCC = "0000000000ZZ4";

    // Category
    StandardCategory parentStandard = new StandardCategory("root",null,1);
    StandardCategory categoria1 = new StandardCategory("Serviços",parentStandard,2);
    StandardCategory categoria2 = new StandardCategory("Serviços",parentStandard,2);

    // CashAccount
    String description = "Cash Account do Ze Manel";
    Double balance = 450.00;
    int accountID = 1;
    AddCashAccountDTO cashAccountDTO = new AddCashAccountDTO(balance,description,selfCC,familyID);
    CashAccount contaCash = new CashAccount(cashAccountDTO,accountID);

    // CashTransaction
    double transferedValue = 200;
    int categoryID = 2;
    String transactionDesignation = "Luz Novembro";
    Date transactionDateOne = new Date(2021,0,21);
    Date transactionDateTwo = new Date(2021,0,22);
    Date transactionDateThree = new Date(2021,0,20);
    TransferenceDTO transacaoDTO1 = new TransferenceDTO(familyID,selfCC,accountID,transferedValue,categoryID,transactionDesignation,transactionDateOne);
    TransferenceDTO transacaoDTO2 = new TransferenceDTO(familyID,selfCC,accountID,600,categoryID,transactionDesignation,transactionDateTwo);
    TransferenceDTO transacaoDTO3 = new TransferenceDTO(familyID,selfCC,accountID,-100,categoryID,transactionDesignation,transactionDateThree);
    CashTransaction cashTransactionOne = new CashTransaction(contaCash, categoria1, transacaoDTO1);
    CashTransaction cashTransactionTwo = new CashTransaction(contaCash, categoria1, transacaoDTO2);
    CashTransaction cashTransactionThree = new CashTransaction(contaCash, categoria1, transacaoDTO3);
    TransactionDataDTO transactionDataDTOOne = new TransactionDataDTO(cashTransactionOne.getTransactionData());
    TransactionDataDTO transactionDataDTOTwo = new TransactionDataDTO(cashTransactionTwo.getTransactionData());
    TransactionDataDTO transactionDataDTOThree = new TransactionDataDTO(cashTransactionThree.getTransactionData());


    @Test
    void RegisterPaymentMyCashAccount() {
        TransactionService service = new TransactionService();
        assertTrue(service.registerPaymentMyCashAccount(contaCash,categoria1,transacaoDTO1));
    }

    @Test
    void NoRegisterPaymentMyCashAccount_NotEnoughMoney() {
        TransactionService service = new TransactionService();
        assertFalse(service.registerPaymentMyCashAccount(contaCash,categoria1,transacaoDTO2));
    }

    @Test
    void NoRegisterPaymentMyCashAccount_NegativeAmmount() {
        TransactionService service = new TransactionService();
        assertFalse(service.registerPaymentMyCashAccount(contaCash,categoria1,transacaoDTO3));
    }

    @Test
    void createListOfMovementsBetweenDates_ResultEmptyListNoMovements() {
        // arrange
        CashAccount cashAccount = new CashAccount(cashAccountDTO,accountID);
        TransactionService service = new TransactionService();
        Date startDate = new Date(2021, Calendar.JANUARY, 22);
        Date endDate = new Date(2021, Calendar.JANUARY, 30);
        List<TransactionDataDTO> expected = new ArrayList<>();

        // act
        List<TransactionDataDTO> result = service.createListOfMovementsBetweenDates(cashAccount, startDate, endDate);

        // assert
        assertEquals(expected, result);
        assertTrue(result.isEmpty());
    }

    @Test
    void createListOfMovementsBetweenDates_ResultEmptyListNoMovementsBetweenDates() {
        // arrange
        CashAccount cashAccount = new CashAccount(cashAccountDTO,accountID);
        TransactionService service = new TransactionService();
        service.registerPaymentMyCashAccount(cashAccount, categoria1, transacaoDTO1);
        Date startDate = new Date(2021, Calendar.JANUARY, 22);
        Date endDate = new Date(2021, Calendar.JANUARY, 30);
        List<TransactionDataDTO> expected = new ArrayList<>();

        // act
        List<TransactionDataDTO> result = service.createListOfMovementsBetweenDates(cashAccount, startDate, endDate);

        // assert
        assertEquals(expected, result);
        assertTrue(result.isEmpty());
    }

    @Test
    void createListOfMovementsBetweenDates_ResultOneMovementList() {
        // arrange
        CashAccount cashAccount = new CashAccount(cashAccountDTO,accountID);
        TransactionService service = new TransactionService();
        service.registerPaymentMyCashAccount(cashAccount, categoria1, transacaoDTO1);
        Date startDate = new Date(2021, Calendar.JANUARY, 20);
        Date endDate = new Date(2021, Calendar.JANUARY, 30);
        List<TransactionDataDTO> expected = new ArrayList<>();
        expected.add(transactionDataDTOOne);
        int expectedSize = 1;

        // act
        List<TransactionDataDTO> result = service.createListOfMovementsBetweenDates(cashAccount, startDate, endDate);

        // assert
        assertEquals(expected, result);
        assertEquals(expectedSize, result.size());
    }

    @Test
    void createListOfMovementsBetweenDates_ResultManyMovementsList() {
        // arrange
        String description = "Cash Account do Ze Manel";
        Double balance = 10000.00;
        int accountID = 1;
        AddCashAccountDTO cashAccountDTO = new AddCashAccountDTO(balance,description,selfCC,familyID);
        CashAccount cashAccount = new CashAccount(cashAccountDTO,accountID);
        TransactionService service = new TransactionService();
        service.registerPaymentMyCashAccount(cashAccount, categoria1, transacaoDTO1);
        service.registerPaymentMyCashAccount(cashAccount, categoria1, transacaoDTO2);
        Date startDate = new Date(2021, Calendar.JANUARY, 20);
        Date endDate = new Date(2021, Calendar.JANUARY, 30);
        List<TransactionDataDTO> expected = new ArrayList<>();
        expected.add(transactionDataDTOOne);
        expected.add(transactionDataDTOTwo);
        int expectedSize = 2;

        // act
        List<TransactionDataDTO> result = service.createListOfMovementsBetweenDates(cashAccount, startDate, endDate);

        // assert
        assertEquals(expected, result);
        assertEquals(expectedSize, result.size());
    }

    @Test
    void createListOfMovementsBetweenDates_SwitchedDates() {
        // arrange
        String description = "Cash Account do Ze Manel";
        Double balance = 10000.00;
        int accountID = 1;
        AddCashAccountDTO cashAccountDTO = new AddCashAccountDTO(balance,description,selfCC,familyID);
        CashAccount cashAccount = new CashAccount(cashAccountDTO,accountID);
        TransactionService service = new TransactionService();
        service.registerPaymentMyCashAccount(cashAccount, categoria1, transacaoDTO1);
        service.registerPaymentMyCashAccount(cashAccount, categoria1, transacaoDTO2);
        service.registerPaymentMyCashAccount(cashAccount, categoria1, transacaoDTO1);
        Date endDate = new Date(2021, Calendar.JANUARY, 20);
        Date startDate = new Date(2021, Calendar.JANUARY, 30);
        List<TransactionDataDTO> expected = new ArrayList<>();
        expected.add(transactionDataDTOOne);
        expected.add(transactionDataDTOTwo);
        expected.add(transactionDataDTOOne);
        int expectedSize = 3;

        // act
        List<TransactionDataDTO> result = service.createListOfMovementsBetweenDates(cashAccount, startDate, endDate);

        // assert
        assertEquals(expected, result);
        assertEquals(expectedSize, result.size());
    }

    @Test
    void createListOfMovementsBetweenDates_DifferentTransactionDates() {
        // arrange
        String description = "Cash Account do Ze Manel";
        Double balance = 10000.00;
        int accountID = 1;
        AddCashAccountDTO cashAccountDTO = new AddCashAccountDTO(balance,description,selfCC,familyID);
        CashAccount cashAccount = new CashAccount(cashAccountDTO,accountID);
        TransactionService service = new TransactionService();
        service.registerPaymentMyCashAccount(cashAccount, categoria1, transacaoDTO1);
        service.registerPaymentMyCashAccount(cashAccount, categoria1, transacaoDTO2);
        service.registerPaymentMyCashAccount(cashAccount, categoria1, transacaoDTO2);
        Date endDate = new Date(2021, Calendar.JANUARY, 21);
        Date startDate = new Date(2021, Calendar.JANUARY, 30);
        List<TransactionDataDTO> expected = new ArrayList<>();
        expected.add(transactionDataDTOTwo);
        expected.add(transactionDataDTOTwo);
        int expectedSize = 2;

        // act
        List<TransactionDataDTO> result = service.createListOfMovementsBetweenDates(cashAccount, startDate, endDate);

        // assert
        assertEquals(expected, result);
        assertEquals(expectedSize, result.size());
    }

}