package utilities.DBModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utilities.DBUtil.DBUtilV2;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data // Setters, Getters, ToString, equals
public class CustEmployee {
    private String custName;
    private String empFirstName;
    private String empLastName;

    private static final String getAllQuery = "" +
            "SELECT customerName AS custName, firstName empFirstName, lastName empLastName\n" +
            "FROM employees e\n" +
            "JOIN customers c\n" +
            "ON e.employeeNumber = c.salesRepEmployeeNumber;";

    private static final String getByCustName = "" +
            "SELECT customerName AS custName, firstName empFirstName, lastName empLastName\n" +
            "FROM employees e\n" +
            "JOIN customers c\n" +
            "ON e.employeeNumber = c.salesRepEmployeeNumber\n" +
            "WHERE customerName = ?";

    public static List<CustEmployee> getAll() {
        return DBUtilV2.query(getAllQuery, CustEmployee.class);
    }

    public static List<CustEmployee> getByCustomerName(String custName) {
        return DBUtilV2.query(getByCustName, CustEmployee.class, custName);
    }
}
