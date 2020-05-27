package com.epam.huntingService.database.dao.interfaces;

import com.epam.huntingService.entity.HuntingGround;

import java.sql.SQLException;
import java.util.List;

public interface HuntingGroundDAO extends BaseDAO<HuntingGround> {

    List<HuntingGround> getAllByOrganizationID(Long orgID, Integer languageID) throws SQLException;

    List<HuntingGround> takeAllHuntingGroundsByAnimalID(Long huntingGroundID, Integer languageID) throws SQLException;

    List<HuntingGround> getAllWithThisAnimal(String animalName,Integer languageID) throws SQLException;

    List<HuntingGround> takeAllHuntingGroundsInDistrict(Integer languageID, String district) throws SQLException;

    String getOrganizationNameByHuntingGroundName(String huntingGroundName, Integer languageID) throws SQLException;

    Long takeIDByName(String name) throws SQLException;

    Long takeLastID() throws SQLException;

    List<HuntingGround> takeAllLocalizedHuntingGrounds() throws SQLException;

    List<HuntingGround> takeAllLocalizedHuntingGroundsByID(Long huntingGroundID) throws SQLException;
}
