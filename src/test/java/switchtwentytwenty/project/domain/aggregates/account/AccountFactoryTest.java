package switchtwentytwenty.project.domain.aggregates.account;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import switchtwentytwenty.project.domain.valueobject.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AccountFactoryTest {

    @Autowired
    AccountFactory accountFactory;


    @Test
    public void createAccount() {
        Designation designation = new Designation("Compras");
        Monetary monetary = new Monetary("EUR", BigDecimal.valueOf(20.00));
        OwnerID ownerID = new PersonID("toni@emial.com");
        String accountType = "bank";


        IAccount expected = new BankAccount();
        expected.setDesignation(new Designation("Compras"));
        expected.addMovement(new Movement(new Monetary("EUR", BigDecimal.valueOf(20.00))));
        expected.setOwner(new PersonID("toni@emial.com"));

        IAccount result = accountFactory.createAccount(designation, monetary, ownerID, accountType);

        assertEquals(expected, result);
    }
}