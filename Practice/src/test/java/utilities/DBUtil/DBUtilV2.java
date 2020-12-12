package utilities.DBUtil;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.junit.Assert;
import utilities.ConfigReader;

import java.sql.*;
import java.util.*;
// Apache DBUtils
public class DBUtilV2 {
    private static Connection connection;
    private static Statement statement;
    private static final String jdbcLink = ConfigReader.getProperty("jdbcLink");
    private static BeanProcessor processor = new BeanProcessor();

    private DBUtilV2() {
    }

    public static void openConnection() {
        try {
            if (connection == null) connection = DriverManager.getConnection(jdbcLink);
            if (statement == null) statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    public static void closeConnection() {
        try {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet queryToRs(String query, Object... params) {
        try {
            openConnection();
            if (params.length == 0) return statement.executeQuery(query);

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> query(String query, Class<T> tClass, Object... params) {
        ResultSet resultSet = queryToRs(query, params);
        try {
            return processor.toBeanList(resultSet, tClass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Lists.newArrayList();
    }
    //Varargs
    //queryWithParam("SELECT * FORM employees", 112, 111) // params = [112, 111]

    /**
     * queryWithParam("Select * form customers where customerNumber = ? and customerName = ?", 112, "Jason Ltd")
     * // Select * form customers where customerNumber = 112 and customerName = 'Jason Ltd'
     */
    public static List<Map<String, Object>> query(String query, Object... params) {
        try {
            openConnection();
            ResultSet resultSet = queryToRs(query, params);
            return getData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
        return Lists.newArrayList();
    }

    private static List<Map<String, Object>> getData(ResultSet resultSet) throws SQLException {
        // Map represents 1 row where key is the column name and value is the value in DB under that column for that particular row
        List<Map<String, Object>> dataTable = new ArrayList<>();
        List<String> columns = getColumnNames(resultSet);
        // Loops through the whole result set
        // with each .next() call moves the pointer to the next row
        while (resultSet.next()) { //rs.next returns true if the pointer was moved to the new row,
            // If row doesn't exist (currently pointing to the last row) it will return false

            //New HashMap is created for each row
            Map<String, Object> row = new HashMap<>();

            // Inner Loop loops through all the column names. We use it to get the data for a specific column within a row
            for (String column : columns) {
                row.put(column, resultSet.getObject(column));
            }
            // After building hashmap it's added to our datatable List
            dataTable.add(row);
        }

        return dataTable;
    }

    private static List<String> getColumnNames(ResultSet resultSet) throws SQLException {
        List<String> colNames = new ArrayList<>();
        ResultSetMetaData metaData = resultSet.getMetaData();
        // store total number of columns retrieved from res set metadata
        int colNum = metaData.getColumnCount();
        // Loop through all the columns and get their names and store them into a list
        for (int i = 1; i <= colNum; i++) {
            colNames.add(metaData.getColumnName(i));
        }
        return colNames;
    }
}
