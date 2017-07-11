package stories.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import stories.model.custom.BackendSettings;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class responsible for establishing and working with database connection.
 */
public abstract class DBDao {
    protected JdbcTemplate jdbcTemplatePg;
    protected static final ObjectMapper mapper = new ObjectMapper();

    private BackendSettings backendSettings;
    private String SQL_URL;
    private String SQL_USER;
    private String SQL_PASSWORD;

    public DBDao(BackendSettings backendSettings) {
        if (null == backendSettings) {
            throw new IllegalArgumentException("You should provide backend settings.");
        }
        this.backendSettings = backendSettings;

        SQL_URL = this.backendSettings.getDatabaseUrl()
                .replaceAll("(.+?)(\\$\\{db-server})(.+)", "$1" + this.backendSettings.getDatabaseServer() + "$3");
        SQL_USER = this.backendSettings.getDatabaseUsername();
        SQL_PASSWORD = this.backendSettings.getDatabasePassword();
        jdbcTemplatePg = jdbcTemplatePg();
    }

    private JdbcTemplate jdbcTemplatePg() {
        return new JdbcTemplate(dataSourcePg());
    }

    private BasicDataSource dataSourcePg() {
        BasicDataSource dbcp = new BasicDataSource();

        // You should set driver class according to the used database.
        dbcp.setDriverClassName("org.postgresql.Driver");

        dbcp.setUrl(SQL_URL);
        dbcp.setUsername(SQL_USER);
        dbcp.setPassword(SQL_PASSWORD);
        return dbcp;
    }

    public void closeConnection()
            throws SQLException {
        Connection con = DataSourceUtils.getConnection(jdbcTemplatePg.getDataSource());
        con.close();
    }
}
