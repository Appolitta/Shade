package stories.model.custom;

/**
 * Class containing all backend settings for single backend instance (rest settings, database settings and etc.).
 */
public class BackendSettings {
    private final String serverUrl;
    private final String serverPort;
    private final String serverSsl;
    private final String serverSslDefault;
    private final String databaseServer;
    private final String databaseUrl;
    private final String databasePort;
    private final String databaseName;
    private final String databaseUsername;
    private final String databasePassword;
    private final String baseApiPath;

    private BackendSettings(Builder builder) {
        this.serverUrl = builder.serverUrl;
        this.serverPort = builder.serverPort;
        this.serverSsl = builder.serverSsl;
        this.serverSslDefault = builder.serverSslDefault;
        this.databaseServer = builder.databaseServer;
        this.databaseUrl = builder.databaseUrl;
        this.databasePort = builder.databasePort;
        this.databaseName = builder.databaseName;
        this.databaseUsername = builder.databaseUsername;
        this.databasePassword = builder.databasePassword;
        this.baseApiPath = builder.baseApiPath;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public String getServerPort() {
        return serverPort;
    }

    public String getServerSsl() {
        return serverSsl;
    }

    public String getServerSslDefault() {
        return serverSslDefault;
    }

    public String getDatabaseServer() {
        return databaseServer;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public String getDatabasePort() {
        return databasePort;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getDatabaseUsername() {
        return databaseUsername;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public String getBaseApiPath() {
        return baseApiPath;
    }

    public static class Builder {
        private String serverUrl;
        private String serverPort;
        private String serverSsl;
        private String serverSslDefault;
        private String databaseServer;
        private String databaseUrl;
        private String databasePort;
        private String databaseName;
        private String databaseUsername;
        private String databasePassword;
        private String baseApiPath;

        public BackendSettings build() {
            return new BackendSettings(this);
        }

        public Builder setServerUrl(String serverUrl) {
            this.serverUrl = serverUrl;
            return this;
        }

        public Builder setServerPort(String serverPort) {
            this.serverPort = serverPort;
            return this;
        }

        public Builder setServerSsl(String serverSsl) {
            this.serverSsl = serverSsl;
            return this;
        }

        public Builder setServerSslDefault(String serverSslDefault) {
            this.serverSslDefault = serverSslDefault;
            return this;
        }

        public Builder setDatabaseServer(String databaseServer) {
            this.databaseServer = databaseServer;
            return this;
        }

        public Builder setDatabaseUrl(String databaseUrl) {
            this.databaseUrl = databaseUrl;
            return this;
        }

        public Builder setDatabasePort(String databasePort) {
            this.databasePort = databasePort;
            return this;
        }

        public Builder setDatabaseName(String databaseName) {
            this.databaseName = databaseName;
            return this;
        }

        public Builder setDatabaseUsername(String databaseUsername) {
            this.databaseUsername = databaseUsername;
            return this;
        }

        public Builder setDatabasePassword(String databasePassword) {
            this.databasePassword = databasePassword;
            return this;
        }

        public Builder setBaseApiPath(String baseApiPath) {
            this.baseApiPath = baseApiPath;
            return this;
        }
    }
}
