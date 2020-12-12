package utilities.DBModels;


import lombok.Data;
import lombok.NoArgsConstructor;
import utilities.DBUtil.DBUtil;
import utilities.DBUtil.DBUtilV2;

import java.util.List;

@Data
@NoArgsConstructor
public class Employee {
    private int employeeNumber;
    private String lastName;
    private String firstName;
    private String email;
    private final static String queryToGetByNumber = "Select employeeNumber, lastName, firstName, email FROM employees where employeeNumber = ?;";
    private final static String queryAll = "Select employeeNumber, lastName, firstName, email FROM employees;";


    public int getEmployeeNumber() {
        return employeeNumber;
    }

    public static List<Employee> getAll() {
        return DBUtilV2.query(queryAll, Employee.class);
    }

    public static Employee getById(int id) {
        List<Employee> employeeList = DBUtilV2.query(queryToGetByNumber, Employee.class, id);
        if (employeeList.isEmpty()) return null;
        else return employeeList.get(0);
    }
}
