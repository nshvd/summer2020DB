package DBUtil;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DbUtilTest {
    @BeforeClass
    public static void setUp() {
        DBUtil.openConnection();
    }

    @AfterClass
    public static void tearDown() {
        DBUtil.closeConnection();
    }

    static String queryToGetEmpAnTheirCust = "" +
            "SELECT T1.firstName, T1.lastName, T2.customerName\n" +
            "FROM employees T1\n" +
            "LEFT JOIN customers T2 \n" +
            "ON employeeNumber = salesRepEmployeeNumber\n" +
            "WHERE T2.customerName = ?;";

    static String queryEmployee = "Select * FROM employees WHERE employeeNumber = ?" ;
    static String q = "Select * FROM employees WHERE employeeNumber = 1002";
    static String qToGetNumOfEmployees = "SELECT count(*) AS num FROM employees;";
    static String qToGetEmpId = "SELECT employeeNumber FROM employees WHERE lastName = ? AND firstName = ?;";

    @Test
    public void test1() {
        long expectedNumberOfEmployees = 23;
        long actualNumberOfEmployees = (Long) DBUtil.query(qToGetNumOfEmployees).get(0).get("num");
        Assert.assertTrue("Number of employees is wrong", Objects.equals(expectedNumberOfEmployees, actualNumberOfEmployees));
    }

    @Test
    public void test2() {
        int empNumExpected = 1002;
        int empNumActual = (Integer) DBUtil.queryWithParam(qToGetEmpId,  "Murphy", "Diane").get(0).get("employeeNumber");
        Assert.assertEquals("ID doesn't match", empNumExpected, empNumActual);
    }



}
