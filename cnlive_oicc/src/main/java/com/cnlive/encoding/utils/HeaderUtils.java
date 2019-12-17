package com.cnlive.encoding.utils;

import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by NightRunner on 2017-03-04.
 */
public class HeaderUtils {
    private static Boolean isDevMode = null;

    public static void crossDomain(HttpServletResponse response) {

        if (isDevMode == null) {
            Prop p = PropKit.use("init.properties");
            isDevMode = p.getBoolean("devMode");
        }

        if (isDevMode) {
            response.setHeader("Access-Control-Allow-Origin", "*"); // FIXME: 2017-03-10 指定域名
            response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        }
    }
}
