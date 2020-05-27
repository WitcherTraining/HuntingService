package com.epam.huntingService.database.dao.interfaces;

import com.epam.huntingService.entity.Permit;

import java.sql.SQLException;
import java.util.List;

public interface PermitDAO extends BaseDAO<Permit> {

    List<Permit> getAllByUserID(Long userID, Integer languageID) throws SQLException;
}
