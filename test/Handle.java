package test;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface Handle {
    void setStatement(String query);
    void setCallableStatement(String query);
    ResultSet execute();
    ResultSet executeCall();
    int executeUpdate();
    PreparedStatement getStatement();
    CallableStatement getCallableStatement();
    void commit();
    void rollback();
}
