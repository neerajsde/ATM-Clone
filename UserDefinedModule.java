import java.sql.*;
import java.util.Random;
import java.util.Scanner;

// This is Customer-Details Registration Module
class Register {
    protected int cusId;
    protected String name;
    protected String email;
    protected String contact;
    protected String address;
    private String accountNo;
    private String ifscCode;
    private String atmNo;
    private String pine;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/atm";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Neeraj@7564";

    Register() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Customer Name: ");
        this.name = sc.nextLine();
        System.out.print("Enter Customer Email: ");
        this.email = sc.nextLine();
        Scanner sc2 = new Scanner(System.in);
        String contact;
        do {
            System.out.print("Enter Customer Mobile Number: ");
            contact = sc2.nextLine();
            if (contact.length() != 10) {
                System.err.println("Please Re-enter 10-digit mobile number");
                System.out.println();
            }
        } while (contact.length() != 10);
        this.contact = contact;
        System.out.print("Enter Customer Address: ");
        this.address = sc.nextLine();
        this.accountNo = generateAccountNumber();
        this.ifscCode = generateIFSCode();
        this.atmNo = generateATMNumber();
        this.pine = generateATMPin();
        insert();
    }

    public static String generateAccountNumber() {
        String PREFIX = "GITAC200";
        int counter = (int)(Math.random()*10000);
        return PREFIX + counter;
    }
    public static String generateIFSCode() {
        String PREFIX = "23BOI10";
        int counter = (int)(Math.random()*1000);
        return PREFIX + counter;
    }
    // Method to generate a random 4-digit ATM PIN
    public static String generateATMPin() {
        Random rand = new Random();
        // Generate a random integer between 0000 and 9999 (inclusive)
        int pin = rand.nextInt(10000);
        if (pin < 1000) {
            pin += 1000;
        }
        String hexadecimalString = Integer.toHexString(pin);
        return hexadecimalString;
    }
    public static String generateATMNumber() {
        // Define the range of numbers for the first digit (usually 4 for Visa or 5 for MasterCard)
        int firstDigit = getRandomNumberInRange(4, 5);

        // Generate the next 14 digits randomly
        StringBuilder middleDigits = new StringBuilder();
        for (int i = 0; i < 14; i++) {
            middleDigits.append(getRandomNumberInRange(0, 9));
        }

        // Calculate the last digit (checksum) using Luhn's algorithm
        int checksum = calculateLuhnChecksum(firstDigit + middleDigits.toString());

        // Combine all digits to create the final 16-digit ATM card number
        String atmNumber = firstDigit + middleDigits.toString() + checksum;

        return atmNumber;
    }

    public static int getRandomNumberInRange(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    public static int calculateLuhnChecksum(String cardNumber) {
        int sum = 0;
        boolean doubleDigit = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int digit = Integer.parseInt(String.valueOf(cardNumber.charAt(i)));
            if (doubleDigit) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
            doubleDigit = !doubleDigit;
        }
        return (10 - (sum % 10)) % 10;
    }

    private void insert() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sqlQuery = "INSERT INTO customer_details (Name, Email, Contact, Address) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                preparedStatement.setString(1, this.name);
                preparedStatement.setString(2, this.email);
                preparedStatement.setString(3, this.contact);
                preparedStatement.setString(4, this.address);

                int rowsAffected = preparedStatement.executeUpdate();
            }
            String sqlQuery2 = "INSERT INTO account_details (CusId, AccountNo, IFSCode, AtmNo, Pine) VALUES (?, ?, ?, ?, ?)";
            this.cusId = getCusId();
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery2)) {
                preparedStatement.setInt(1,this.cusId);
                preparedStatement.setString(2, this.accountNo);
                preparedStatement.setString(3, this.ifscCode);
                preparedStatement.setString(4, this.atmNo);
                preparedStatement.setString(5, this.pine);
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("\n________________________________________________________________");
                    System.out.println(" Congratulations, " + this.name + " has been successfully registered.");
                    System.out.println("\t\tCustomer Id: "+getCusId());
                    System.out.println("\t\tAccount No: "+this.accountNo);
                    System.out.println("\t\tATM No: "+ this.atmNo);
                    System.out.println("\t\tIFSC code: "+this.ifscCode);
                    System.out.println("________________________________________________________________");
                } else {
                    System.err.println("Registration failed.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error: Something went wrong during registration.");
        }
    }
    public int getCusId() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sqlQuery = "SELECT Id FROM customer_details WHERE Name=? AND Contact=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                preparedStatement.setString(1, this.name);
                preparedStatement.setString(2, this.contact);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    int cus_id = resultSet.getInt("Id");
                    return cus_id;
                } else {
                    return 0; // No matching record found
                }
            }
        } catch (SQLException e) {
            return 0; // Handle database error
        }
    }
}


// This is Interact User Data
class UserData {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/atm";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Neeraj@7564";
    public int customer_id;
    public String name;
    protected String email;
    protected String contact;
    protected String address;
    protected String reg_date;
    private String accountNo;
    private String atmNo;
    private String ifsccode;
    private String atmpin;
    protected double balance;


    UserData(int cus_id){
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sqlQuery = "SELECT c.id, c.name, c.email, c.contact, c.address, c.registerdatetime, d.accountNo, d.ifscode, d.atmNo, d.pine, d.avlbalance FROM customer_details c INNER JOIN account_details d ON c.id = d.cusId WHERE c.id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                preparedStatement.setInt(1, cus_id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    this.customer_id = resultSet.getInt("c.id");
                    this.name = resultSet.getString("c.name");
                    this.email = resultSet.getString("c.email");
                    this.contact = resultSet.getString("c.contact");
                    this.address = resultSet.getString("c.address");
                    this.reg_date = resultSet.getString("c.registerdatetime");
                    this.accountNo = resultSet.getString("d.accountNo");
                    this.ifsccode = resultSet.getString("d.ifscode");
                    this.atmNo = resultSet.getString("d.atmNo");
                    this.balance = resultSet.getDouble("d.avlbalance");
                    this.atmpin = resultSet.getString("d.pine");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error");
        }
    }
    // check user atm pin
    private int privatePine(int cus_id){
        if (this.customer_id==cus_id){
            String hexPine = this.atmpin;
            int id = Integer.parseInt(hexPine, 16);
            return id;
        }
        else {
            return 0;
        }
    }
    protected int getPine(int cus_id, String accountNo, String atmNo){
        if (this.customer_id == cus_id && this.accountNo.equals(accountNo) && this.atmNo.equals(atmNo)) {
            int p = privatePine(cus_id); // Corrected function name
            return p;
        }
        return 0;
    }
    protected int getPine(int cus_id){
        if (this.customer_id == cus_id) {
            int p = privatePine(cus_id); // Corrected function name
            return p;
        }
        return 0;
    }
    protected Boolean setPine(int cus_id, int newPine){
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String hexPine = Integer.toHexString(newPine);
            String sqlQuery = "UPDATE account_details SET Pine = ? WHERE CusId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                preparedStatement.setString(1, hexPine);
                preparedStatement.setInt(2, cus_id);

                // Execute the update query using the prepared statement
                int rowsAffected = preparedStatement.executeUpdate();

                // Check if any rows were affected
                if (rowsAffected > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            return false;
        }
    }
}

class CheckUser {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/atm";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Neeraj@7564";
    protected boolean findUsers(int id) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sqlQuery = "SELECT id from customer_details WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int cus_id = resultSet.getInt("id");
                    if (cus_id==id){
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }
    protected boolean updateBalance(int id, Double amount){
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sqlQuery = "UPDATE account_details SET AvlBalance = ? WHERE CusId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                preparedStatement.setDouble(1, amount);
                preparedStatement.setInt(2, id);

                // Execute the update query using the prepared statement
                int rowsAffected = preparedStatement.executeUpdate();

                // Check if any rows were affected
                if (rowsAffected > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            return false;
        }
    }
    protected String generateTransactionId() {
        long timestamp = System.currentTimeMillis();
        int random = new Random().nextInt(100000); // You can adjust the range as needed.

        // Combine timestamp and random number to create the TransactionId.
        String transactionId = timestamp + "-" + random;

        return transactionId;
    }
    protected Boolean transactionDetails(int cusId, String transactionType, Double amount) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sqlQuery = "INSERT INTO transaction (CusId, TransactionId, TransactionType, Amount) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                preparedStatement.setInt(1, cusId);
                preparedStatement.setString(2, generateTransactionId());
                preparedStatement.setString(3, transactionType);
                preparedStatement.setDouble(4, amount);

                // Execute the update query using the prepared statement
                int rowsAffected = preparedStatement.executeUpdate();

                // Check if any rows were affected
                if (rowsAffected > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            return false;
        }
    }
    public Boolean UpdateName(int cusId, String name) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sqlQuery = "UPDATE customer_details SET name = ? WHERE Id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, cusId);

                // Execute the update query using the prepared statement
                int rowsAffected = preparedStatement.executeUpdate();

                // Check if any rows were affected
                if (rowsAffected > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            return false;
        }
    }
    protected void paymentHistory(int cusId){
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sqlQuery = "SELECT * FROM transaction WHERE CusId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                preparedStatement.setInt(1, cusId);
                ResultSet resultSet = preparedStatement.executeQuery();

                System.out.println("+-------+---------------------+-------------------+-----------+---------------------+");
                System.out.println("| CusId | TransactionId       | TransactionType   | Amount    | DateTime            |");
                System.out.println("+-------+---------------------+-------------------+-----------+---------------------+");
                while (resultSet.next()) {
                    int cus_Id = resultSet.getInt("CusId");
                    String transId = resultSet.getString("TransactionId");
                    String transType = resultSet.getString("TransactionType");
                    double avlBalance = resultSet.getDouble("Amount");
                    String dateTime = resultSet.getString("DateTime");
                    System.out.printf("| %-6d| %-20s| %-18s| %-10.2f| %-20s|%n",
                            cus_Id, transId, transType, avlBalance, dateTime);
                }
                System.out.println("+-------+---------------------+-------------------+-----------+---------------------+");
            }
        } catch (SQLException e) {
            System.err.println("Database Error");
        }
    }
}

public class UserDefinedModule {
    public static void main(String[] args) {
        System.out.println("This is Only Module");
    }
}