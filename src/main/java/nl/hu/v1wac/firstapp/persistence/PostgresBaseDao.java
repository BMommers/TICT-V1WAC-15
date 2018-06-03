package nl.hu.v1wac.firstapp.persistence;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;

public class PostgresBaseDao {

    protected final Connection getConnection() {
        Connection result = null;
        System.out.println("Start Try");
        try {
            System.out.println("Initiate ic");
            InitialContext ic = new InitialContext();
            System.out.println("inititialize ds");
            DataSource ds = (DataSource) ic.lookup("java:comp/env/jdbc/PostgresDS");
            System.out.println("save results");
            result = ds.getConnection();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return result;
    }

}
