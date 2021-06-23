import test.DbHandler;
import test.Handle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class RemoveVillain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Handle handler = new DbHandler("minions_db");
        int villainId = Integer.parseInt(sc.nextLine());
        try {
            String villainName = getVillainName(villainId,handler);
           if (!villainName.equals("")){
               int freedMinions = deleteVillainMinionConnection(villainId,handler);
               int deleteResult = deleteVillain(villainId,handler);
               if (deleteResult > 0){
                   System.out.printf("%s was deleted%n",villainName);
                   System.out.printf("%d minions released%n",freedMinions);
               }
           } else {
               System.out.println("No such villain was found");
           }
            handler.commit();
        }catch (SQLException e){
            handler.rollback();
        }
    }

    private static int deleteVillain(int villainId, Handle handler) throws SQLException {
        handler.setStatement("DELETE \n" +
                "FROM\tvillains AS v\n" +
                "WHERE v.id = ?;");
        handler.getStatement().setInt(1,villainId);
        return handler.executeUpdate();
    }

    private static int deleteVillainMinionConnection(int villainId, Handle handler) throws SQLException {
        handler.setStatement("DELETE \n" +
                "FROM\tminions_villains AS mv\n" +
                "WHERE\tmv.villain_id = ?;");
        handler.getStatement().setInt(1,villainId);
        return handler.executeUpdate();
    }

    private static String getVillainName(int villainId, Handle handler) throws SQLException {
        String villainName = "";
        handler.setStatement("SELECT v.`name`\n" +
                "FROM\tvillains AS v\n" +
                "WHERE\tv.id = ?;");
        handler.getStatement().setInt(1,villainId);
        ResultSet rs = handler.execute();
        while (rs.next()){
            villainName = rs.getString("name");
        }
        return villainName;
    }
}
