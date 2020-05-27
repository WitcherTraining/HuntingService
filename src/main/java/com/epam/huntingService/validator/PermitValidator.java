package com.epam.huntingService.validator;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.epam.huntingService.util.DateConverter.*;

public class PermitValidator {
    private static final int MAXIMUM_ANIMALS_IN_DAILY_PERMIT = 5;
    private static final int MINIMUM_ANIMALS_IN_PERMIT = 1;
    private static final int FINAL_DAY_IN_MONTH = 31;

    private static boolean isBackDate(Date huntingDay){
        return huntingDay.before(new Date());
    }

    private static boolean isHuntingSeasonOver(Date huntingDay, Date endOfHuntingSeason){
        return huntingDay.after(endOfHuntingSeason);
    }

    private static boolean isHuntingDayBeforeHuntingSeasonBegan(Date huntingDay, Date beginOfHuntingSeason){
        return huntingDay.before(beginOfHuntingSeason);
    }

    private static boolean isYearOver(Date huntingDay){
        Calendar calendar = new GregorianCalendar(getCurrentYear(), Calendar.DECEMBER, FINAL_DAY_IN_MONTH);
        Date endOfYear = calendar.getTime();
        return huntingDay.after(endOfYear);
    }

    public static boolean validateDailyPermitHuntingDate(Date huntingDay, Date endOfHuntingSeason, Date beginOfHuntingSeason){
        return !isBackDate(huntingDay) &&
                !isHuntingSeasonOver(huntingDay, endOfHuntingSeason) &&
                !isYearOver(huntingDay) &&
                !isHuntingDayBeforeHuntingSeasonBegan(huntingDay, beginOfHuntingSeason);
    }

    public static boolean isHuntingSeasonIsOver(Date orderDay, Date endOfHuntingSeason){
        return orderDay.after(endOfHuntingSeason);
    }

    public static boolean validateCountOrderedAnimalsForDailyPermit(Integer countOrderedAnimals, Integer animalQuota){
        return countOrderedAnimals <= animalQuota &&
                countOrderedAnimals >= MINIMUM_ANIMALS_IN_PERMIT &&
                countOrderedAnimals <= MAXIMUM_ANIMALS_IN_DAILY_PERMIT;
    }

    public static boolean validateCountOrderedAnimalsForSeasonPermit(Integer countOrderedAnimals, Integer animalQuota){
        return countOrderedAnimals <= animalQuota &&
                countOrderedAnimals >= MINIMUM_ANIMALS_IN_PERMIT;
    }
}
