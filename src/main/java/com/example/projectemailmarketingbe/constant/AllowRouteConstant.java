package com.example.projectemailmarketingbe.constant;

/**
 * Created by ngthotuan on 08/10/2022
 */
public class AllowRouteConstant {
    public static String SWAGGER_UI = "/swagger-ui";
    public static String SWAGGER_RESOURCE = "/swagger-resources";
    public static String API_DOC_V2 = "/v2/api-docs";
    public static String API_DOC = "/api-docs";
    public static String USER_LOGIN = "/users/login";
    public static String USER_REGISTER = "/users/register";
    public static String H2_CONSOLE = "/h2";

    public static String[] allowRoutesFilter = new String[]{
            SWAGGER_UI,
            API_DOC_V2,
            SWAGGER_RESOURCE,
            API_DOC,
            USER_LOGIN,
            USER_REGISTER,
            H2_CONSOLE
    };

    public static String[] allowRoutesSecurity = new String[]{
            SWAGGER_UI + "/**",
            SWAGGER_RESOURCE + "/**",
            API_DOC_V2,
            "/**" +API_DOC ,
            USER_LOGIN,
            USER_REGISTER,
            H2_CONSOLE + "/**"
    };
}
