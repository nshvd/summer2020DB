package DBUtil;

import org.junit.Assert;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DbUtilTest {
    Object customer;

    static String queryToGetEmpAnTheirCust = "" +
            "SELECT T1.firstName, T1.lastName, T2.customerName\n" +
            "FROM employees T1\n" +
            "LEFT JOIN customers T2 \n" +
            "ON employeeNumber = salesRepEmployeeNumber\n" +
            "WHERE T2.customerName = 'Mini Gifts Distributors Ltd.';";

    public static void main(String[] args) {
        try{
            DBUtil.openConnection();
            List<Map<String, Object>> table = DBUtil.query(queryToGetEmpAnTheirCust);
            Assert.assertTrue("Customer is not Created", table.size() != 0);
        } finally {
            DBUtil.closeConnection();
        }

    }
}
