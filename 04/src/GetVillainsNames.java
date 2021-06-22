import test.DbHandler;
import test.Handle;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GetVillainsNames {
    public static void main(String[] args) {
        Handle handler = new DbHandler("minions_db");
        handler.setStatement("SELECT v.`name` , COUNT(m.id) AS minions_count\n" +
                "FROM villains AS v\n" +
                "JOIN\tminions_villains AS mv\n" +
                "ON\tmv.villain_id = v.id\n" +
                "JOIN minions AS m\n" +
                "ON\tmv.minion_id  = m.id\n" +
                "GROUP BY\tv.`name`\n" +
                "HAVING minions_count > 15\n" +
                "ORDER BY minions_count DESC;");
        ResultSet rs = handler.execute();

        try {
            while (rs.next()){
                System.out.printf("%s %d%n", rs.getString("name"),rs.getInt("minions_count"));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

}
