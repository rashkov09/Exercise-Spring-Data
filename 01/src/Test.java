import test.DbHandler;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
      DbHandler handler = new DbHandler("soft_uni");
      handler.setStatement("SELECT * FROM employees WHERE salary > ?");
      String salary = sc.nextLine();

        try {
            handler.getStatement().setDouble(1,Double.parseDouble(salary));
            ResultSet rs = handler.execute();
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
