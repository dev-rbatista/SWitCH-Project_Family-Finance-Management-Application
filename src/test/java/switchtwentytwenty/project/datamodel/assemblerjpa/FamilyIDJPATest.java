package switchtwentytwenty.project.datamodel.assemblerjpa;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.datamodel.domainjpa.FamilyIDJPA;

import static org.junit.jupiter.api.Assertions.*;

class FamilyIDJPATest {

    String familyID = "1";

    @Tag("US010")
    @Test
    void testEquals() {
        FamilyIDJPA familyIDJPA = new FamilyIDJPA(familyID);

        assertEquals(familyIDJPA, familyIDJPA);
    }

    @Tag("US010")
    @Test
    void testEqualsTwo() {
        FamilyIDJPA familyIDJPA = new FamilyIDJPA(familyID);
        FamilyIDJPA familyIDJPATwo = new FamilyIDJPA(familyID);

        assertEquals(familyIDJPA, familyIDJPATwo);
    }

    @Tag("US010")
    @Test
    void testToString() {
        String expected = "1";

        FamilyIDJPA familyIDJPA = new FamilyIDJPA(familyID);

        String result = familyIDJPA.toString();

        assertEquals(expected, result);
    }

    @Tag("US010")
    @Test
    void getFamilyID() {
        String expected = "1";

        FamilyIDJPA familyIDJPA = new FamilyIDJPA(familyID);


        String result = familyIDJPA.getFamilyID();

        assertEquals(expected, result);

    }
}