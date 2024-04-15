package com.service.auth.util;

import org.apache.logging.log4j.util.Strings;

public class StringUtil {

    public static String checkNull(String text) {
        return !Strings.isEmpty(text) ? text : Strings.EMPTY;
    }
}
