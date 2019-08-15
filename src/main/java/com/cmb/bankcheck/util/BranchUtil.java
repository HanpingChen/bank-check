package com.cmb.bankcheck.util;

import java.util.HashMap;
import java.util.Map;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-08-15
 * Time:10:28
 */
public class BranchUtil {
    private static Map<String, String> map = new HashMap<>();

    static {
        map.put("0551","合肥分行");
        map.put("0553","黄山分行");
    }


    public static String getBranchName(String branch){
        return map.get(branch);
    }


}
