package utilities.DBModels;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;
import utilities.DBUtil.DBUtil;
import utilities.DBUtil.DBUtilV2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Employee {
    private int employeeNumber;
    private String lastName;
    private String firstName;
    private String email;
    private final static String queryToGetByNumber = "Select employeeNumber, lastName, firstName, email FROM employees where employeeNumber = ?;";
    private final static String queryAll = "Select employeeNumber AS newName, lastName, firstName, email FROM employees;";
    private static String getAllEmployeesNumbers = "SELECT * FROM employees;";

//    public Employee(ResultSet rs) throws SQLException {
//        this.employeeNumber = rs.getInt("employeeNumber");
//        this.lastName = rs.getString("lastName");
//        //...
//    }

//    public static List<Employee> generateAll(ResultSet resultSet) throws SQLException {
//        List<Employee> ls = new ArrayList<>();
//        while (resultSet.next()) {
//            ls.add(new Employee(resultSet));
//        }
//        return ls;
//    }

    public static List<Employee> getAll() {
        return DBUtilV2.query(queryAll, Employee.class);
    }

    public static Employee getById(int id) {
        List<Employee> employeeList = DBUtilV2.query(queryToGetByNumber, Employee.class, id);
        if (employeeList.isEmpty()) return null;
        else return employeeList.get(0);
    }

    public static List<Employee> getEmployeeNumbers() {
       return DBUtilV2.query(getAllEmployeesNumbers, Employee.class);
    }

    @Test
    public void test9() {
       List<Employee> employees =  Employee.getEmployeeNumbers();
       for (Employee e : employees) {
           System.out.println(e);
       }
    }
}
