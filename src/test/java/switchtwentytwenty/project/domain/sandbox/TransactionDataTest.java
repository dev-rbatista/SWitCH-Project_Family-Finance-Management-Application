package switchtwentytwenty.project.domain.sandbox;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.dtos.MoneyValue;
import switchtwentytwenty.project.domain.model.categories.Category;
import switchtwentytwenty.project.domain.model.categories.StandardCategory;
import switchtwentytwenty.project.domain.utils.CurrencyEnum;

import java.util.Date;

class TransactionDataTest {

    Date date = new Date();
    MoneyValue ammount = new MoneyValue(22.2, null);
    Category category = new StandardCategory("cat", null, 2);
    TransactionData transactionData = new TransactionData("test", new MoneyValue(22.2, CurrencyEnum.EURO),true, date, category);


    @Test
    void getTransactionDate() {
        Date result = date;
        Date expected = transactionData.getTransactionDate();
        Assertions.assertEquals(expected, result);
    }

    @Test
    void getRegistrationDate() {
        Date expected = transactionData.getRegistrationDate();
        Assertions.assertNotNull(expected);
        Assertions.assertNotSame(expected, transactionData.getTransactionDate());
    }

    @Test
    void getCategory() {
        Category expected = category;
        Category result = transactionData.getCategory();
        Assertions.assertEquals(expected, result);
    }

    @Test
    void getDesignation() {
        String expected = "test";
        String result = transactionData.getDesignation();
        Assertions.assertEquals(expected, result);
    }

    @Test
    void getAmmount() {
        MoneyValue expected = new MoneyValue(22.2,null);
        MoneyValue result = transactionData.getAmmount();
        Assertions.assertEquals(expected, result);
    }

    @Test
    void getRemainingBalance() {
        MoneyValue expected = new MoneyValue(0.0, null);
        MoneyValue result = transactionData.getRemainingBalance();
        Assertions.assertEquals(expected,result);
    }

    @Test
    void isCredit_True() {
        boolean result = transactionData.isCredit();
        Assertions.assertTrue(result);
    }

    @Test
    void isCredit_False() {
        TransactionData transactionData = new TransactionData("test", new MoneyValue(22.2, CurrencyEnum.EURO),false, date, category);
        boolean result = transactionData.isCredit();
        Assertions.assertFalse(result);
    }

    @Test
    void isDebit_True() {
        TransactionData transactionData = new TransactionData("test", new MoneyValue(22.2, CurrencyEnum.EURO),false, date, category);
        boolean result = transactionData.isDebit();
        Assertions.assertTrue(result);
    }

    @Test
    void isDebit_False() {
        boolean result = transactionData.isDebit();
        Assertions.assertFalse(result);
    }
}