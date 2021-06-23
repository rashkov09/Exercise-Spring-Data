import test.DbHandler;
import test.Handle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class IncreaseMinionAge {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] minionIDs =  Arrays
                .stream(sc.nextLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        Handle handler = new DbHandler("minions_db");
        try {
            for (int i = 0; i < minionIDs.length ; i++) {
                handler.setStatement("UPDATE minions AS m\n" +
                        "SET\tm.`name` = LOWER(m.`name`),\n" +
                        "\t\tm.age = m.age+1\n" +
                        "WHERE m.id IN (?);");
                handler.getStatement().setInt(1, minionIDs[i]);
                handler.executeUpdate();
                handler.setStatement("SELECT m.`name`, m.age \n" +
                        "FROM\tminions AS m\n" +
                        "WHERE\tm.id IN (?);");
                handler.getStatement().setInt(1, minionIDs[i]);
                ResultSet rs = handler.execute();
                while (rs.next()) {
                    System.out.printf("%s %d%n", rs.getString("name"), rs.getInt("age"));
                }
                handler.commit();
            }

        }catch (SQLException e){
        handler.rollback();
            System.out.println(e.getMessage());
        }
    }
}
