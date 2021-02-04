package switchtwentytwenty.project.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.dtos.input.AddFamilyMemberDTO;
import switchtwentytwenty.project.domain.dtos.output.CategoryTreeDTO;
import switchtwentytwenty.project.domain.model.Application;
import switchtwentytwenty.project.domain.services.CategoryService;
import switchtwentytwenty.project.domain.services.FamilyService;

import java.util.Date;

class AddCategoryToFamilyTreeControllerTest {

    Application ffmApp = new Application();
    AddCategoryToFamilyTreeController addCategoryToFamilyTreeController = new AddCategoryToFamilyTreeController(ffmApp);
    AddFamilyAdministratorController adminController = new AddFamilyAdministratorController(ffmApp);
    AddFamilyMemberController memberController = new AddFamilyMemberController(ffmApp);
    AddFamilyController familyController = new AddFamilyController(ffmApp);
    AddStandardCategoryController standardCategoryController = new AddStandardCategoryController(ffmApp);
    Date birthdate = new Date(1990, 12, 28);
    String adminCC = "137843828ZX3";
    GetCategoryTreeController getCategoryTreeController = new GetCategoryTreeController(ffmApp);
    AddFamilyMemberDTO familyMemberDTO = new AddFamilyMemberDTO(adminCC, "153017597ZV5", "Diana", birthdate, 910000000, "test2@gmail.com", 205629091, "Rua Dois", "4405-586", "Valadares", "Gaia", 1);
    AddFamilyMemberDTO familyMemberDTO1 = new AddFamilyMemberDTO(adminCC, adminCC, "Ricardo", birthdate, 910000000, "test@gmail.com", 231235470, "Rua Um", "4400-265", "Mafamude", "Gaia", 1);

    @BeforeEach
    void testSetup() {
        familyController.addFamily("Nogueira");// id = 1
        standardCategoryController.addStandardCategory("House", 0);//id=1
        adminController.addFamilyAdministrator(familyMemberDTO1);
        memberController.addFamilyMember(familyMemberDTO);
    }

    @Test
    void addCategoryToFamilyTreeWithAStandardCategoryParent() {
        Assertions.assertTrue(addCategoryToFamilyTreeController.addCategoryToFamilyTree(adminCC, 1, "Rent", 1));
    }

    @Test
    void testAddCategoryToFamilyTreeWithACustomCategoryParent() {
        addCategoryToFamilyTreeController.addCategoryToFamilyTree(adminCC, 1, "Rent", 0);
        Assertions.assertTrue(addCategoryToFamilyTreeController.addCategoryToFamilyTree(adminCC, 1, "Stuff", -1));
    }

    @Test
    void testAddCategoryToFamilyTree() {
        Assertions.assertTrue(addCategoryToFamilyTreeController.addCategoryToFamilyTree(adminCC, 1, "Boat", 0));
    }

    @Test
    void testAddCategoryToFamilyTreeWithNoParent() {
        addCategoryToFamilyTreeController.addCategoryToFamilyTree(adminCC, 1, "Boat", 0);
        CategoryService catService = ffmApp.getCategoryService();
        FamilyService famService = ffmApp.getFamilyService();
        CategoryTreeDTO dto = catService.getCategoryTree(1, famService);
        Assertions.assertTrue(dto.getCustomCategories().get(0).isRootCategory());
    }

    @Test
    void testAddCategoryToFamilyTree_FailNoSuchFamily() {
        Assertions.assertFalse(addCategoryToFamilyTreeController.addCategoryToFamilyTree(adminCC, 2, "Candles", 0));
    }

    @Test
    void testAddCategoryToFamilyTree_FailNotAnAdmin() {
        Assertions.assertFalse(addCategoryToFamilyTreeController.addCategoryToFamilyTree("153017597ZV5", 1, "Candles", 0));
    }

    @Test
    void testAddCategoryToFamilyTree_FailNoSuchStandardParent() {
        Assertions.assertFalse(addCategoryToFamilyTreeController.addCategoryToFamilyTree(adminCC, 1, "Candles", 2));
    }

    @Test
    void testAddCategoryToFamilyTree_FailNoSuchCustomParent() {
        Assertions.assertFalse(addCategoryToFamilyTreeController.addCategoryToFamilyTree(adminCC, 1, "Candles", -3));
    }

    @Test
    void testAddCategoryToFamilyTree_FailEmptyName() {
        Assertions.assertFalse(addCategoryToFamilyTreeController.addCategoryToFamilyTree(adminCC, 1, "", 0));
    }

    @Test
    void testAddCategoryToFamilyTree_FailBlankName() {
        Assertions.assertFalse(addCategoryToFamilyTreeController.addCategoryToFamilyTree(adminCC, 1, "    ", 0));
    }

    @Test
    void testAddCategoryToFamilyTree_FailNullName() {
        Assertions.assertFalse(addCategoryToFamilyTreeController.addCategoryToFamilyTree(adminCC, 1, null, 0));
    }

    @Test
    void addCategoryToFamilyTreeAssertFalseIfNoSuchFamilyID() {
        Assertions.assertFalse(addCategoryToFamilyTreeController.addCategoryToFamilyTree(adminCC, 5, "Rent", 1));

    }

    @Test
    void addCategoryToFamilyTree3Categories() {
        addCategoryToFamilyTreeController.addCategoryToFamilyTree(adminCC, 1, "Boat", 0);
        addCategoryToFamilyTreeController.addCategoryToFamilyTree(adminCC, 1, "Car", 0);
        addCategoryToFamilyTreeController.addCategoryToFamilyTree(adminCC, 1, "House", 0);
        CategoryService catService = ffmApp.getCategoryService();
        FamilyService famService = ffmApp.getFamilyService();
        CategoryTreeDTO dto = catService.getCategoryTree(1, famService);
        int expected = -3;
        int result = dto.getCustomCategories().get(2).getCategoryID();
        Assertions.assertEquals(expected, result);

    }
}