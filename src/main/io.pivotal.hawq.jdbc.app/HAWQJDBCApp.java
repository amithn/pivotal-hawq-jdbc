import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * An example app showing querying data on HDFS using Pivotal HAWQ using JDBC
 * {@see http://pivotal.io/big-data/white-paper/a-true-sql-engine-for-hadoop-pivotal-hd-hawq}
 *
 */

public class HAWQJDBCApp {

    public static void main(String args[]) throws SQLException, ClassNotFoundException {

        Class.forName("org.postgresql.Driver");
        Connection connection = null;
        connection = DriverManager.getConnection(
                "jdbc:postgresql://HAWQ-MASTER-URI:5432/DATABASENAME", "username", "password");


        // DDL to create external table
        String extTableName = "scores";
        Statement st = connection.createStatement();

        String CREATE_EXT_TABLE_DDL = " CREATE EXTERNAL TABLE " + extTableName  +
                                    "   (id text, score int) " +
                                    "   LOCATION('pxf://NAMENODE-URI:50070/user/anambiar/scores.txt?profile=HdfsTextSimple') " +
                                    "   FORMAT 'CSV'  (DELIMITER = ',');";

        st.execute(CREATE_EXT_TABLE_DDL);
        st.close();


        // Query
        st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM scores ORDER BY score DESC");

        System.out.println("Rows from scores table " + extTableName + " :");

        while (rs.next())
        {
            System.out.println(rs.getString(1) + "," + rs.getInt(2));
        }

        rs.close();
        st.close();
        connection.close();
    }
}
