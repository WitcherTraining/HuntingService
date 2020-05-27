package com.epam.huntingService.util;

import java.util.*;

public class DateConverter {

    public static Integer getCurrentYear() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        calendar.setTime(new Date());
        return calendar.get(Calendar.YEAR);
    }

    public static java.sql.Date convert(java.util.Date uDate) {
        java.sql.Date sqlDate = null;
        if (uDate != null){
            sqlDate = new java.sql.Date(uDate.getTime());
        }
        return sqlDate;
    }
}
