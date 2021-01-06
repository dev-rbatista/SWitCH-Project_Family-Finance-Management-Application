package switch2020.project.controllers;

import switch2020.project.model.Application;
import switch2020.project.utils.FamilyMemberRelationDTO;

import java.util.ArrayList;


public class GetFamilyMembersListController {
    private Application FFMapp;

    public GetFamilyMembersListController(Application app) {
        if (this.FFMapp == null) {
            throw new IllegalArgumentException("Application cannot be null");
        }
        this.FFMapp = app;
    }

    public ArrayList<FamilyMemberRelationDTO> getFamilyMemberAndRelation(int familyID){
        return this.FFMapp.getDTOList(familyID);
    }

}
