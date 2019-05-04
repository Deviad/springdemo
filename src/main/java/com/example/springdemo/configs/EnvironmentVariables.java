package com.example.springdemo.configs;

public class EnvironmentVariables {
    public static String dbName = System.getenv(EnvVarsEnum.DB_NAME.name());
    public static String dbUserName = System.getenv(EnvVarsEnum.DB_USERNAME.name());
    public static String dbPassword = System.getenv(EnvVarsEnum.DB_PASSWORD.name());
    public static String activeProfile = System.getenv(EnvVarsEnum.ACTIVE_PROFILE.name());
    public static String clientId = System.getenv(EnvVarsEnum.CLIENT_ID.name());
    public static String dbHostname = System.getenv(EnvVarsEnum.DB_HOSTNAME.name());
    public static String clientSecret = System.getenv(EnvVarsEnum.CLIENT_SECRET.name());
    public static String appHostname = System.getenv(EnvVarsEnum.APP_HOSTNAME.name());
    public static String ddlAuto = System.getenv(EnvVarsEnum.DDL_AUTO.name());
    public static String jwtSigningKey = System.getenv(EnvVarsEnum.JWT_SIGNING_KEY.name());
}

