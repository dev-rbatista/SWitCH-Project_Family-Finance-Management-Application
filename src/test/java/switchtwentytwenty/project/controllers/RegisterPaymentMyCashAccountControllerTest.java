package switchtwentytwenty.project.controllers;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.DTOs.input.AddCashAccountDTO;
import switchtwentytwenty.project.domain.model.Application;
import switchtwentytwenty.project.domain.model.Family;
import switchtwentytwenty.project.domain.model.FamilyMember;
import switchtwentytwenty.project.domain.model.accounts.CashAccount;
import switchtwentytwenty.project.domain.model.categories.StandardCategory;
import switchtwentytwenty.project.domain.services.FamilyService;
import switchtwentytwenty.project.domain.utils.TransferenceDTO;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class RegisterPaymentMyCashAccountControllerTest {

    // FamilyMember
    int familyID = 1;
    String selfCC = "166699209ZY8";
    String name = "Ze Manel";
    Date date = new Date(1990,8,26);
    int numero = 919999998;
    String email = "zemanel@gmail.com";
    int nif = 212122000;
    String rua = "Rua";
    String codPostal = "4444-556";
    String local = "Gaia";
    String city = "Porto";

    // CashAccount
    String description = "Cash Account do Ze Manel";
    Double balance = 450.00;
    int accountID = 1;
    AddCashAccountDTO cashAccountDTO = new AddCashAccountDTO(balance,description,selfCC,familyID);
    CashAccount contaCash = new CashAccount(cashAccountDTO,accountID);

    // CashTransaction
    double transferedValue = 200;
    int categoryID = 1;
    String transactionDesignation = "Luz Novembro";
    Date transactionDate = new Date(2021,1,21);
    TransferenceDTO transacaoDTO1 = new TransferenceDTO(familyID,selfCC,accountID,transferedValue,categoryID,transactionDesignation,transactionDate);
    TransferenceDTO transacaoDTO2 = new TransferenceDTO(familyID,selfCC,accountID,-200,categoryID,transactionDesignation,transactionDate);

    @Test
    void registerPaymentMyCashAccount() {
        Application ffmApp = new Application();
        RegisterPaymentMyCashAccountController controller = new RegisterPaymentMyCashAccountController(ffmApp);
        // FamilyService
        FamilyService familyService = ffmApp.getFamilyService();
        Family family = new Family("Ribeiros",familyID);
        familyService.addFamily(family);
        FamilyMember zeManel = new FamilyMember(selfCC,name,date,numero,email,nif,rua,codPostal,local,city);
        family.addFamilyMember(zeManel);
        zeManel.addAccount(contaCash);
        assertTrue(controller.RegisterPaymentMyCashAccount(transacaoDTO1));
    }

    @Test
    void NotRegisterPaymentMyCashAccount_NegativeAmmount() {
        Application ffmApp = new Application();
        RegisterPaymentMyCashAccountController controller = new RegisterPaymentMyCashAccountController(ffmApp);
        // FamilyService
        FamilyService familyService = ffmApp.getFamilyService();
        Family family = new Family("Ribeiros",familyID);
        familyService.addFamily(family);
        FamilyMember zeManel = new FamilyMember(selfCC,name,date,numero,email,nif,rua,codPostal,local,city);
        family.addFamilyMember(zeManel);
        zeManel.addAccount(contaCash);
        assertFalse(controller.RegisterPaymentMyCashAccount(transacaoDTO2));
    }

}