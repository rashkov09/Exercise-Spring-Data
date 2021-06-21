import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
      DbHandler handler = new DbHandler();
      handler.setProperties("dobrin_r","D0brin-r");
      handler.setConnection();
      String salary = sc.nextLine();
      handler.setStatement("SELECT * FROM employees WHERE salary >"+ Double.parseDouble(salary));
        ResultSet rs = handler.execute();
        try {
            while (rs.next()) {
                System.out.printf("%s %s%n",
                        rs.getString("first_name"),
                        rs.getString("last_name"));
            }
        }catch (SQLException e){
            System.out.println("Error");
        }
    }
}
