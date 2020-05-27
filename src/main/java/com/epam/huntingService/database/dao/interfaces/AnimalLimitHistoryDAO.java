package com.epam.huntingService.database.dao.interfaces;

import com.epam.huntingService.entity.AnimalLimitHistory;

import java.sql.SQLException;
import java.util.List;

public interface AnimalLimitHistoryDAO extends BaseDAO<AnimalLimitHistory> {

    AnimalLimitHistory getByAnimalIDInThisYear(Long animalID, Integer languageID) throws SQLException;

    Double getAnimalCost(Long animalID) throws SQLException;

    List<AnimalLimitHistory> getAllByYear(Integer year) throws SQLException;

    AnimalLimitHistory getByIdAndYear(Long id, Integer year) throws SQLException;

    Integer takeFirstYearForLimitHistory() throws SQLException;
}
