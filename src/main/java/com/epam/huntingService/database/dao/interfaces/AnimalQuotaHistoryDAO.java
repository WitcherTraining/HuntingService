package com.epam.huntingService.database.dao.interfaces;

import com.epam.huntingService.entity.AnimalQuotaHistory;

import java.sql.SQLException;
import java.util.List;

public interface AnimalQuotaHistoryDAO extends BaseDAO<AnimalQuotaHistory> {

    List<AnimalQuotaHistory> getAllByID(Long huntingGroundID) throws SQLException;

    void updateQuotaAfterOrder(Integer updatedQuota, Long animalID, Long huntingGroundID) throws SQLException;

    Integer getQuotaByHuntingGroundAndAnimalID(Long huntingGroundID, Long AnimalID) throws SQLException;

    AnimalQuotaHistory getThisYearHistory(Long huntingGroundID, Long animalID) throws SQLException;

    Double getSeasonPrice(Long animalID, Long huntingGroundID) throws SQLException;

    Double getDailyPrice(Long animalID, Long huntingGroundID) throws SQLException;
}
