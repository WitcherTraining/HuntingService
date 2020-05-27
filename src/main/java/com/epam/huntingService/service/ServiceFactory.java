package com.epam.huntingService.service;

import java.util.HashMap;
import java.util.Map;

import static com.epam.huntingService.service.ServiceConstants.*;

public class ServiceFactory {

    private static final Map<String, Service> SERVICE_MAP = new HashMap<>();
    private static final ServiceFactory SERVICE_FACTORY = new ServiceFactory();

    static {
        SERVICE_MAP.put(LOGIN_SERVICE, new LoginService());
        SERVICE_MAP.put(LOGOUT_SERVICE, new LogOutService());
        SERVICE_MAP.put(SHOW_ORGANIZATION_SERVICE, new ShowOrganizationService());
        SERVICE_MAP.put(SHOW_HUNTING_GROUND_SERVICE, new ShowHuntingGroundService());
        SERVICE_MAP.put(FORWARD_SERVICE, new ForwardService());
        SERVICE_MAP.put(REGISTER_USER_SERVICE, new RegisterUserService());
        SERVICE_MAP.put(DELETE_USER_SERVICE, new DeleteUserService());
        SERVICE_MAP.put(EDIT_PROFILE_SERVICE, new EditProfileService());
        SERVICE_MAP.put(CHANGE_PASSWORD_SERVICE, new ChangePasswordService());
        SERVICE_MAP.put(ADD_TO_CART_SERVICE, new AddToCartService());
        SERVICE_MAP.put(REMOVE_FROM_CART_SERVICE, new RemoveFromCartService());
        SERVICE_MAP.put(ORDER_DAILY_PERMIT_SERVICE, new OrderDailyPermitService());
        SERVICE_MAP.put(ORDER_SEASON_PERMIT_SERVICE, new OrderSeasonPermitService());
        SERVICE_MAP.put(UPLOAD_DOCUMENT_SERVICE, new UploadHunterDocumentService());
        SERVICE_MAP.put(DOWNLOAD_HUNTER_DOCUMENT_SERVICE, new DownloadHunterDocumentService());
        SERVICE_MAP.put(SEARCH_SERVICE, new SearchService());
        SERVICE_MAP.put(PREPARE_LIMIT_INFO_SERVICE, new PrepareLimitInfoService());
        SERVICE_MAP.put(SHOW_ORDERED_PERMITS_SERVICE, new ShowOrderedPermitsService());
        SERVICE_MAP.put(CHANGE_ROLE_SERVICE, new ChangeRoleService());
        SERVICE_MAP.put(SHOW_ALL_USERS_BY_CATEGORIES_SERVICE, new ShowAllUsersByCategoriesService());
        SERVICE_MAP.put(SHOW_ALL_HUNTING_GROUNDS_SERVICE, new ShowAllHuntingGroundsService());
        SERVICE_MAP.put(SHOW_HUNTING_GROUND_BY_ANIMAL_SERVICE, new ShowHuntingGroundsByAnimalService());
        SERVICE_MAP.put(ADD_HUNTING_GROUND_SERVICE, new AddHuntingGroundService());
        SERVICE_MAP.put(PREPARE_HUNTING_GROUND_EDITING_SERVICE, new PrepareHuntingGroundEditingService());
        SERVICE_MAP.put(EDIT_HUNTING_GROUND_SERVICE, new EditHuntingGroundService());
        SERVICE_MAP.put(SHOW_ALL_ORGANIZATIONS_SERVICE, new ShowAllOrganizationsService());
        SERVICE_MAP.put(ADD_ORGANIZATION_SERVICE, new AddOrganizationService());
        SERVICE_MAP.put(PREPARE_ORGANIZATION_EDITING_SERVICE, new PrepareOrganizationEditingService());
        SERVICE_MAP.put(EDIT_ORGANIZATION_SERVICE, new EditOrganizationService());
        SERVICE_MAP.put(SHOW_ALL_ANIMALS_SERVICE, new ShowAllAnimalsService());
        SERVICE_MAP.put(ADD_ANIMAL_SERVICE, new AddAnimalService());
        SERVICE_MAP.put(PREPARE_ANIMAL_EDITING_SERVICE, new PrepareAnimalEditingService());
        SERVICE_MAP.put(EDIT_ANIMAL_SERVICE, new EditAnimalService());
        SERVICE_MAP.put(SHOW_ALL_PERMITS_SERVICE, new ShowAllPermitsService());
        SERVICE_MAP.put(SHOW_ANIMALS_LIMIT_SERVICE, new ShowAnimalsLimitService());
        SERVICE_MAP.put(ADD_ANIMAL_LIMIT_SERVICE, new AddAnimalLimitService());
        SERVICE_MAP.put(PREPARE_ANIMAL_LIMIT_EDITING_SERVICE, new PrepareAnimalLimitEditingService());
        SERVICE_MAP.put(EDIT_ANIMAL_LIMIT_SERVICE, new EditAnimalLimitService());
        SERVICE_MAP.put(SHOW_QUOTA_SERVICE, new ShowQuotaService());
        SERVICE_MAP.put(ADD_QUOTA_SERVICE, new AddQuotaService());
        SERVICE_MAP.put(PREPARE_QUOTA_EDITING_SERVICE, new PrepareQuotaEditingService());
        SERVICE_MAP.put(EDIT_QUOTA_SERVICE, new EditQuotaService());
        SERVICE_MAP.put(CHANGE_LANGUAGE_SERVICE, new ChangeLanguageService());
        SERVICE_MAP.put(ERROR_SERVICE, new ErrorService());
    }

    public static ServiceFactory getInstance() {
        return SERVICE_FACTORY;
    }

    public Service getService(String request) {
        Service service = SERVICE_MAP.get(ERROR_SERVICE);

        for (Map.Entry<String, Service> pair : SERVICE_MAP.entrySet()) {
            if (request.equalsIgnoreCase(pair.getKey())) {
                service = SERVICE_MAP.get(pair.getKey());
            }
        }
        return service;
    }
}
