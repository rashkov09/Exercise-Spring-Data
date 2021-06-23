package test;
import java.sql.*;
import java.util.Properties;

public class DbHandler implements Handle{
    private final String database;
    private Connection connection;
    private Properties properties;
    private PreparedStatement stmt;
    private CallableStatement callableStatement;

    public DbHandler(String database) {
        this.database = database;
        setProperties();
        setConnection();
    }



    private void setProperties() {
        this.properties = new Properties();
        this.properties.setProperty("user", "test");
        this.properties.setProperty("password","D12345-dD");

    }


    private void setConnection() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://79.132.12.253:3306/"+this.database,this.properties);
            this.connection.setAutoCommit(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void setStatement(String query) {
        try {
            this.stmt = this.connection.prepareStatement(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void setCallableStatement(String query) {
        try {
            this.callableStatement = this.connection.prepareCall(query);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet execute() {
        ResultSet rs = null;
        try {
            rs = this.stmt.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rs ;
    }

    @Override
    public ResultSet executeCall() {
        ResultSet rs = null;
        try {
            rs = this.callableStatement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rs ;
    }

    @Override
    public int executeUpdate() {
        try {
            return  this.stmt.executeUpdate();
        }catch (SQLException e){
            return -1;
        }
    }

    @Override
    public PreparedStatement getStatement() {
        return this.stmt;
    }

    @Override
    public CallableStatement getCallableStatement() {
        return this.callableStatement;
    }


    @Override
    public void commit() {
        try {
            this.connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void rollback() {
        try {
            this.connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
