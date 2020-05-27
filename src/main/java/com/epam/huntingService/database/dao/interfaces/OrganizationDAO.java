package com.epam.huntingService.database.dao.interfaces;

import com.epam.huntingService.entity.Organization;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface OrganizationDAO extends BaseDAO<Organization> {

    Long takeOrganizationIdByName(String orgName) throws SQLException;

    List<Organization> takeAllLocalizedOrganizations() throws SQLException, IOException;

    List<Organization> takeAllLocalizedOrganizationsByID(Long huntingGroundID) throws SQLException, IOException;

    Long takeLastID() throws SQLException;
}
