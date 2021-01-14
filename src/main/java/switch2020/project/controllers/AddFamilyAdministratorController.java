package switch2020.project.controllers;

import switch2020.project.model.Application;
import switch2020.project.model.Relation;
import switch2020.project.services.FamilyService;
import switch2020.project.utils.FamilyWithoutAdministratorDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddFamilyAdministratorController {
    private Application ffmApplication;

    public AddFamilyAdministratorController(Application ffmApplication) {
        this.ffmApplication = ffmApplication;
    }

    /** Isto e para a UI | Penso que nao seja necessario para ja **/
    public List<FamilyWithoutAdministratorDTO> familiesWithoutAdministrator() {
        FamilyService familyService = ffmApplication.getFamilyService();

        return familyService.familiesWithoutAdministrator();
    }

    public boolean addFamilyAdministrator(String ccNumber, String name, Date birthDate, Integer phone, String email, Integer vat, String street, String codPostal, String local, String city, int familyID){
        try {
            FamilyService familyService = this.ffmApplication.getFamilyService();
            return familyService.addFamilyAdministrator(ccNumber, name, birthDate, phone, email, vat, street, codPostal, local, city, familyID);
        } catch (Exception exception) {
            return false;
        }
    }
}