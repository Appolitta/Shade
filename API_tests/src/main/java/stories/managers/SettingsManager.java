package stories.managers;


import stories.model.custom.BackendSettings;

import java.util.ResourceBundle;

/**
 * Main point in app to get settings.
 */
public class SettingsManager {
    private static final String DDT_DATA_PATH = "/ddt/";

    private static final String SUPER_USER_LOGIN;
    private static final String SUPER_USER_PASSWORD;

    private static final BackendSettings DEFAULT_BACKEND_SETTINGS;

    private static SettingsManager settingsManager;

    static {
        ResourceBundle serverResourceBundle = ResourceBundle.getBundle("server");
        ResourceBundle dbResourceBundle = ResourceBundle.getBundle("database");

        DEFAULT_BACKEND_SETTINGS = new BackendSettings.Builder()
                .setServerUrl(serverResourceBundle.getString("_SERVER"))
                .setServerPort(serverResourceBundle.getString("_SERVER_PORT"))
                .setServerSsl(serverResourceBundle.getString("SSL"))
                .setServerSslDefault(serverResourceBundle.getString("SSL_DEFAULT"))
                .setBaseApiPath(serverResourceBundle.getString("_BASE_API_PATH"))
                .setDatabaseServer(dbResourceBundle.getString("db.server"))
                .setDatabasePort(dbResourceBundle.getString("db.port"))
                .setDatabaseName(dbResourceBundle.getString("db.name"))
                .setDatabaseUsername(dbResourceBundle.getString("db.username"))
                .setDatabasePassword(dbResourceBundle.getString("db.password"))
                .setDatabaseUrl(dbResourceBundle.getString("db.url"))
                .build();

        ResourceBundle testBundle = ResourceBundle.getBundle("test");
        SUPER_USER_LOGIN = testBundle.getString("SUPER_USER_LOGIN");
        SUPER_USER_PASSWORD = testBundle.getString("SUPER_USER_PASSWORD");
    }

    private SettingsManager() {
    }

    public static SettingsManager getSettingsManager() {
        if (null == settingsManager) {
            settingsManager = new SettingsManager();
        }
        return settingsManager;
    }

    public BackendSettings getDefaultBackendSettings() {
        return DEFAULT_BACKEND_SETTINGS;
    }

    public String getDdtDataPath() {
        return DDT_DATA_PATH;
    }

    public String getDefaultSuperUserLogin() {
        return SUPER_USER_LOGIN;
    }

    public String getDefaultSuperUserPassword() {
        return SUPER_USER_PASSWORD;
    }
}
