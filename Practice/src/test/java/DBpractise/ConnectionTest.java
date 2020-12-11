package DBpractise;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectionTest {
    static Connection connection;
    static Statement statement;
    static ResultSet rs;
    static final String jdbcLink = "jdbc:mysql://3.131.35.165:3306/classicmodels?user=sdet&password=Devx2020$";

    public static void main(String[] args) {
        try {
            connection = DriverManager.getConnection(jdbcLink);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = statement.executeQuery("" +
                    "SELECT *\n" +
                    "FROM employees\n" +
                    "WHERE lastName = 'Fixter';");

            List<Map<String, Object>> data = getData();
            System.out.println(data);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    public static List<Map<String, Object>> getData() throws SQLException {
        // Map represents 1 row where key is the column name and value is the value in DB under that column for that particular row
        List<Map<String, Object>> dataTable = new ArrayList<>();
        List<String> columns = getColumnNames();
        // Loops through the whole result set
        // with each .next() call moves the pointer to the next row
        while (rs.next()) {//rs.next returns true if the pointer was moved to the new row,
                            // If row doesn't exist (currently pointing to the last row) it will return false

            //New HashMap is created for each row
            Map<String, Object> row = new HashMap<>();

            // Inner Loop loops through all the column names. We use it to get the data for a specific column within a row
            for (String columnName : columns) {
                row.put(columnName, rs.getObject(columnName));
            }
            // After building hashmap it's added to our datatable List
            dataTable.add(row);
        }

        return dataTable;
    }

    public static List<String> getColumnNames() throws SQLException {
        List<String> colNames = new ArrayList<>();
        ResultSetMetaData metaData = rs.getMetaData();
        // store total number of columns retrieved from res set metadata
        int colNum = metaData.getColumnCount();
        // Loop through all the columns and get their names and store them into a list
        for (int i = 1; i <= colNum; i++) {
            colNames.add(metaData.getColumnName(i));
        }
        return colNames;
    }


    /**
     *  {
     *      {customerNumber : 415, cusotmerName: Bavarian Collectibles, contactLastName : Donnermeyer ...},
     *      {customerNumber : 314, cusotmerName: Petit Auto, contactLastName : Dewey ...}
     *
     *  }
     *
     *
     *
     */

}
