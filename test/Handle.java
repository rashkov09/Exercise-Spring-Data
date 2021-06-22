package test;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface Handle {
    void setStatement(String query);
    ResultSet execute();
    int executeUpdate();
    PreparedStatement getStatement();
}
