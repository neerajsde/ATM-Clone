import java.sql.*;

public class CreateDB {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/";
        String user = "root";
        String password = "Neeraj@7564";
        String databaseName = "atm";

        // Initialize database connection objects
        Connection connection = null;
        Statement statement = null;

        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Check if the database exists
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getCatalogs();
            boolean databaseExists = false;

            while (resultSet.next()) {
                String dbName = resultSet.getString("TABLE_CAT");
                if (dbName != null && dbName.equals(databaseName)) {
                    databaseExists = true;
                    break;
                }
            }
            resultSet.close();

            // If the database doesn't exist, create it
            if (!databaseExists) {
                String createDatabaseQuery = "CREATE DATABASE " + databaseName;
                statement.executeUpdate(createDatabaseQuery);
                System.out.println("Database '" + databaseName + "' created successfully.");

                url = "jdbc:mysql://localhost:3306/atm";
                connection = DriverManager.getConnection(url, user, password);
                statement = connection.createStatement();
                // Create customer_details table
                String createCustomerTableSQL = "CREATE TABLE customer_details ("
                        + "Id INT AUTO_INCREMENT PRIMARY KEY,"
                        + "Name VARCHAR(80) NOT NULL,"
                        + "Email VARCHAR(255) NOT NULL UNIQUE,"
                        + "Contact CHAR(10) UNIQUE,"
                        + "Address VARCHAR(400),"
                        + "RegisterDateTime DATETIME DEFAULT NOW()"
                        + ")";
                statement.executeUpdate(createCustomerTableSQL);
                statement.executeUpdate("ALTER TABLE customer_details AUTO_INCREMENT = 101;");

                // Create account_details table
                String createAccountTableSQL = "CREATE TABLE account_details ("
                        + "CusId INT,"
                        + "AccountNo CHAR(20),"
                        + "IFSCode VARCHAR(12),"
                        + "AtmNo CHAR(16),"
                        + "Pine VARCHAR(20),"
                        + "AvlBalance FLOAT DEFAULT 0,"
                        + "FOREIGN KEY (CusId) REFERENCES customer_details(Id)"
                        + " ON UPDATE CASCADE"
                        + " ON DELETE CASCADE"
                        + ")";
                statement.executeUpdate(createAccountTableSQL);

                // Create transaction table
                String createTransactionTableSQL = "CREATE TABLE transaction ("
                        + "CusId INT,"
                        + "TransactionId CHAR(20),"
                        + "TransactionType VARCHAR(50),"
                        + "Amount INT,"
                        + "DateTime DATETIME DEFAULT NOW(),"
                        + "FOREIGN KEY (CusId) REFERENCES customer_details(Id)"
                        + " ON UPDATE CASCADE"
                        + " ON DELETE CASCADE"
                        + ")";
                statement.executeUpdate(createTransactionTableSQL);
                System.out.println("Tables created successfully.");
            } else {
                System.out.println("Database '" + databaseName + "' already exists.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}