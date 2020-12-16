package utilities.DBModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;
import utilities.DBUtil.DBUtilV2;

import java.util.List;

@Data
public class Product {
    //productCode, productName, productLine, productVendor
    private String prod_id;
    private String prod_name;
    private String prod_ln;
    private String prod_vendor;

    private static final String QUERY = "" +
            "SELECT productCode AS prod_id, productName AS prod_name," +
            " productLine AS prod_ln, productVendor AS prod_vendor " +
            "FROM products";
    private static final String QUERY_BY_ID = "" +
            "SELECT productCode AS prod_id, productName AS prod_name," +
            " productLine AS prod_ln, productVendor AS prod_vendor " +
            "FROM products\n" +
            "WHERE productCode = ?";
    private static final String QUERY_BY_PROD_LINE = "" +
            "SELECT productCode AS prod_id, productName AS prod_name," +
            " productLine AS prod_ln, productVendor AS prod_vendor " +
            "FROM products\n" +
            "WHERE productLine = ?";

    public static List<Product> getAll() {
        return DBUtilV2.query(QUERY, Product.class);
    }

    public static Product getById(String id) {
        List<Product> products = DBUtilV2.query(QUERY_BY_ID, Product.class, id);
        if (products.isEmpty()) return null;
        return products.get(0);
    }

    public static List<Product> getByProdLine(String prodLine) {
        return DBUtilV2.query(QUERY_BY_PROD_LINE, Product.class, prodLine);
    }

    public static Product getByIdFormApi(String id) {
        return null;
    }

}
