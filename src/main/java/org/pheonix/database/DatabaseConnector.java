package org.pheonix.database;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

import static org.pheonix.ConfigStore.*;

public class DatabaseConnector {

    public static DataSource getDataSource(Properties props) throws SQLException {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setServerName(props.getProperty(HOST.getValue()));
        ds.setDatabaseName(props.getProperty(DATABASE.getValue()));
        ds.setUser(props.getProperty(USER.getValue()));
        ds.setPassword(props.getProperty(PASSWORD.getValue()));
        ds.setServerTimezone(props.getProperty(TIME_ZONE.getValue()));
        ds.setUseSSL(props.getProperty(USE_SSL.getValue()).equalsIgnoreCase("true"));
        ds.setAllowPublicKeyRetrieval(props.getProperty(ALLOW_PUBLIC_KEY_RETRIEVAL.getValue()).equalsIgnoreCase("true"));

        return ds;
    }
}
