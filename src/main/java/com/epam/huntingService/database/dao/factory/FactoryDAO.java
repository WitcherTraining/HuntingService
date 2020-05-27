package com.epam.huntingService.database.dao.factory;

import com.epam.huntingService.database.dao.impl.*;
import com.epam.huntingService.database.dao.interfaces.BaseDAO;

import java.util.HashMap;
import java.util.Map;

import static com.epam.huntingService.database.dao.factory.ImplEnum.*;

public class FactoryDAO {

    private static final Map<Enum, BaseDAO> DAO_MAP = new HashMap<>();
    private static final FactoryDAO DAO_FACTORY = new FactoryDAO();

    static {
        DAO_MAP.put(ANIMAL_DAO, new AnimalDAOImpl());
        DAO_MAP.put(ANIMAL_QUOTA_HISTORY_DAO, new AnimalQuotaHistoryDAOImpl());
        DAO_MAP.put(ANIMAL_LIMIT_HISTORY_DAO, new AnimalLimitHistoryDAOImpl());
        DAO_MAP.put(HUNTING_GROUND_DAO, new HuntingGroundDAOImpl());
        DAO_MAP.put(ORGANIZATION_DAO, new OrganizationDAOImpl());
        DAO_MAP.put(PERMIT_DAO, new PermitDAOImpl());
        DAO_MAP.put(USER_DAO, new UserDAOImpl());
        DAO_MAP.put(LANGUAGE_DAO, new LanguageDAOImpl());
        DAO_MAP.put(ROLE_DAO, new RoleDAOImpl());
    }

    public static FactoryDAO getInstance() {
        return DAO_FACTORY;
    }

    public BaseDAO getDAO(Enum DAOImpl) {
        return DAO_MAP.get(DAOImpl);
    }
}
