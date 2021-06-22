import test.DbHandler;
import test.Handle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GetMinionNames {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Handle handler = new DbHandler("minions_db");
        handler.setStatement("SELECT v.`name` FROM villains AS v WHERE v.id = ? LIMIT 1");
        String villainId = sc.nextLine();
        try{
            String villainName = "";
            try {
                handler.getStatement().setInt(1, Integer.parseInt(villainId));
                ResultSet rsVillainName = handler.execute();
                while (rsVillainName.next()) {
                    villainName = rsVillainName.getString("name");
                }
            }catch (NumberFormatException e){
                villainName = "";
            }
            if (!villainName.equals("")) {
                System.out.printf("Villain: %s%n",villainName);
                handler.setStatement("SELECT m.`name`, m.age\n" +
                        "FROM minions AS m\n" +
                        "JOIN\tminions_villains AS mv\n" +
                        "ON\tmv.minion_id = m.id\n" +
                        "JOIN villains AS v\n" +
                        "ON\tmv.villain_id  = v.id\n" +
                        "WHERE mv.villain_id = ?;");
                handler.getStatement().setInt(1, Integer.parseInt(villainId));
                ResultSet rsMinionNamesAndAge = handler.execute();
                int id = 1;
                while (rsMinionNamesAndAge.next()) {
                    System.out.printf("%d. %s %d%n", id, rsMinionNamesAndAge.getString("name"), rsMinionNamesAndAge.getInt("age"));
                    id++;
                }
            } else {
                System.out.printf("No villain with ID %s exists in the database.",villainId);
            }
        }catch (SQLException e ){
            System.out.println(e.getMessage());
        }
    }


}
