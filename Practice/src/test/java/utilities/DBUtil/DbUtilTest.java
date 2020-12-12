package utilities.DBUtil;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import utilities.DBModels.CustEmployee;
import utilities.DBModels.Employee;
import utilities.DBModels.OrderDetails;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class DbUtilTest {
    @BeforeClass
    public static void setUp() {
        DBUtilV2.openConnection();
        DBUtil.openConnection();
    }

    @AfterClass
    public static void tearDown() {
        DBUtil.closeConnection();
        DBUtilV2.closeConnection();
    }

    static String queryToGetEmpAnTheirCust = "" +
            "SELECT T1.firstName, T1.lastName, T2.customerName\n" +
            "FROM employees T1\n" +
            "LEFT JOIN customers T2 \n" +
            "ON employeeNumber = salesRepEmployeeNumber\n" +
            "WHERE T2.customerName = ?;";

    static String queryEmployee = "Select * FROM employees WHERE employeeNumber = ?";
    static String q = "Select * FROM employees WHERE employeeNumber = 1002";
    static String qToGetNumOfEmployees = "SELECT count(*) AS num FROM employees;";
    static String qToGetEmpId = "SELECT employeeNumber FROM employees WHERE lastName = ? AND firstName = ?;";
    static String qToGetEmployees = "SELECT employeeNumber, lastName, firstName, email FROM employees;";
    @Test
    public void test1() {
        long expectedNumberOfEmployees = 23;
        long actualNumberOfEmployees = (Long) DBUtil.query(qToGetNumOfEmployees).get(0).get("num");
        Assert.assertTrue("Number of employees is wrong", Objects.equals(expectedNumberOfEmployees, actualNumberOfEmployees));
    }

    @Test
    public void test2() {
        int empNumExpected = 1002;
        int empNumActual = (Integer) DBUtil.queryWithParam(qToGetEmpId, "Murphy", "Diane").get(0).get("employeeNumber");
        Assert.assertEquals("ID doesn't match", empNumExpected, empNumActual);
    }


    @Test
    public void test3() {
        List<Employee> employees = DBUtilV2.query(qToGetEmployees, Employee.class);

        for (Employee e : employees) {
            System.out.println(e);
        }
    }

    @Test
    public void test4() {
        CustEmployee.getAll().forEach(System.out::println);
    }

    @Test
    public void test5() {
        // was created by other steps (Data table for UI or retrieved using API)
        // Created from API or DataTable
        CustEmployee fromUI = new CustEmployee("Alpha Cognac", "Gerard", "Hernandez");
        CustEmployee fromDB = CustEmployee.getByCustomerName(fromUI.getCustName()).get(0);
        Assert.assertTrue(fromDB != null);
        Assert.assertEquals("Objects are not equal!", fromUI, fromDB);
    }

    @Test
    public void test6() {
        //We deleted a specific Employee with id 1338 through UI
        Assert.assertTrue(Employee.getById(1338) == null);
    }

    @Test
    public void test7() {
        Employee.getAll().forEach(System.out::println);
    }

    @Test
    public void test8() {
        OrderDetails.getAll().forEach(System.out::println);
    }

    @Test
    public void test9() {
        OrderDetails.getBy("customerName", "Toms Spezialitäten, Ltd").forEach(System.out::println);
    }
}
