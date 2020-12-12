package utilities.DBModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utilities.DBUtil.DBUtilV2;

import java.util.List;

@NoArgsConstructor
@Data
public class OrderDetails {
    private String customerName;
    private int orderNumber;
    String productCode;
    int quantityOrdered;
    double priceEach;

    private static final String getAllQuery = "" +
            "SELECT customerName, orderNumber, productCode, quantityOrdered, priceEach\n" +
            "FROM orderdetails\n" +
            "JOIN orders\n" +
            "USING(orderNumber)\n" +
            "JOIN customers\n" +
            "USING(customerNumber)";

    public static List<OrderDetails> getAll() {
        return DBUtilV2.query(getAllQuery, OrderDetails.class);
    }

    public static List<OrderDetails> getBy(String field, Object val) {
        String finalQuery = String.format("%s\nWHERE %s = ?;", getAllQuery, field);
        //String s = getAllQuery + "\nWHERE " + field + " = ?";
        return DBUtilV2.query(finalQuery, OrderDetails.class, val);
    }
}
