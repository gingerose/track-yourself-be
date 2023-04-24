package com.track.yourself.util;

public class Util {
    public static final int defaultPage = 0;
    public static final int defaultCollectionsAmount = 16;
    public static String getParam(String param) {
        if (param == null) {
            param = "";
        }
        return "%" + param + "%";
    }
}
