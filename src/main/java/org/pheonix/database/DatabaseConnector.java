package org.pheonix.database;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnector {

    public static DataSource getDataSource(Properties props) throws SQLException {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setServerName(props.getProperty("localhost"));
        ds.setDatabaseName(props.getProperty("MTGDatabase"));
        ds.setUser(props.getProperty("root"));
        ds.setPassword(props.getProperty("root"));
        ds.setServerTimezone(props.getProperty("America/Chicago"));
        ds.setUseSSL(props.getProperty("useSSL").equalsIgnoreCase("true"));
        ds.setAllowPublicKeyRetrieval(props.getProperty("allowPublicKeyRetrieval").equalsIgnoreCase("true"));

        return ds;
    }
}
