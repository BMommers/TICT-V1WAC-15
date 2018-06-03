package nl.hu.v1wac.firstapp.persistence;

import org.apache.commons.dbcp.BasicDataSource;

import java.net.URI; import java.sql.Connection;
import javax.naming.InitialContext; import javax.sql.DataSource;

public class BaseDAO {
    private static DataSource connectionPool;
    public BaseDAO() {
        if (connectionPool == null) {
            try {
                final String DATABASE_URL_PROP = System.getenv("DATABASE_URL");
                if (DATABASE_URL_PROP != null) { // de applicatie draait op Heroku
                    URI dbUri = new URI(DATABASE_URL_PROP);
                    String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();
                    BasicDataSource pool = new BasicDataSource();
                    if (dbUri.getUserInfo() != null) {
                        pool.setUsername(dbUri.getUserInfo().split(":")[0]);
                        pool.setPassword(dbUri.getUserInfo().split(":")[1]);
                    }
                    pool.setMaxIdle(4); // maximaal 4 ongebruikte connecties
                    pool.setMaxActive(20); // maximaal 20 connecties in Heroku
                    pool.setDriverClassName("org.postgresql.Driver");
                    pool.setUrl(dbUrl);
                    pool.setInitialSize(1);
                    connectionPool = pool;
                } else { // de applicatie draait lokaal
                    InitialContext ic = new InitialContext();
                    connectionPool = (DataSource)ic.lookup("java:comp/env/jdbc/PostgresDS");
                }
            } catch (Exception e) { throw new RuntimeException(e); }
        }
    }
    protected final Connection getConnection() {
        try {
            return connectionPool.getConnection();
        } catch (Exception ex) { throw new RuntimeException(ex); }
    }
}