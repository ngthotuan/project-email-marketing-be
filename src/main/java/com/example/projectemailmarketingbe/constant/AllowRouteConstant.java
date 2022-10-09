package com.example.projectemailmarketingbe.constant;

/**
 * Created by ngthotuan on 08/10/2022
 */
public class AllowRouteConstant {
    public static String API_DOC = "/api-docs";
    public static String USER_LOGIN = "/users/login";
    public static String USER_REGISTER = "/users/register";
    public static String H2_CONSOLE = "/h2";

    public static String[] allowRoutesFilter = new String[]{
            API_DOC,
            USER_LOGIN,
            USER_REGISTER,
            H2_CONSOLE
    };

    public static String[] allowRoutesSecurity = new String[]{
            API_DOC + "/**",
            USER_LOGIN,
            USER_REGISTER,
            H2_CONSOLE + "/**"
    };
}
