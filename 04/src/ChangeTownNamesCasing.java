import test.DbHandler;
import test.Handle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class ChangeTownNamesCasing {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Handle handler = new DbHandler("minions_db");
        String countryName = sc.nextLine();
        handler.setStatement("UPDATE towns AS t\n" +
                "SET t.`name` = UPPER(t.`name`)\n" +
                "WHERE t.country = ? AND BINARY t.`name` != BINARY UPPER(t.`name`);");
        try {
            handler.getStatement().setString(1, countryName);
            int result = handler.executeUpdate();
            if (result > 0){
                System.out.printf("%d town names were affected.%n",result);
                handler.setStatement("SELECT t.`name`\n" +
                        "FROM towns AS t \n" +
                        "WHERE BINARY t.`name` = BINARY UPPER(t.`name`) AND t.country = ?;");
                handler.getStatement().setString(1, countryName);
                ResultSet rs = handler.execute();
              String[] towns = new String[result];
              int index = 0;
              while (rs.next()){
                  towns[index++] = rs.getString("name");

              }
              System.out.println(Arrays.toString(towns));
            } else {
                System.out.println("No town names were affected.");
            }handler.commit();
        }catch (SQLException e){
            System.out.println(e.getMessage());
            handler.rollback();
        }

    }
}
