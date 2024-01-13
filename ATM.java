import java.util.InputMismatchException;
import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("--------- Welcome to my ATM Services ---------");

        while (true) {
            System.out.print("\nPress 'next' to continue the transaction or any other key to exit: ");
            String str = sc.next();

            if (!str.equals("next")) {
                System.out.println("Exiting ATM Services. Goodbye!");
                break; // Exit the loop if user enters anything other than "next"
            }

            System.out.println();


            int option = 0;
            boolean isValidInput = false;
            while (!isValidInput) {
                try {
                    System.out.print("\nChoose Options:\n1. New user registration\n2. Cash withdrawal\n3. Check Available Balance\n4. Fast cash withdrawals\n5. Updating user details (name, PIN, balance)\n6. Viewing transaction history\n>>> ");
                    option = sc.nextInt();
                    isValidInput = true;
                } catch (java.util.InputMismatchException e) {
                    System.err.println("Invalid input. Please enter a valid number.");
                    sc.nextLine();
                }
            }
            try {
                switch (option) {
                    case 1:
                        Register register = new Register();
                        break;
                    case 2:
                        System.out.print("Enter your Customer-ID: ");
                        int customer_id = sc.nextInt();
                        CheckUser checkUser = new CheckUser();
                        if (checkUser.findUsers(customer_id)){
                            System.out.print("Enter your atm pine: ");
                            int cusPin = sc.nextInt();
                            UserData userData = new UserData(customer_id);
                            if (cusPin == userData.getPine(customer_id)) {
                                System.out.println("\tHi "+ userData.name +" have a good day.");
                                System.out.println("\tYour mail-id is "+ userData.email);
                                System.out.print("Enter Amount: ");
                                double amount = sc.nextDouble();
                                if (amount%100==0 && amount<userData.balance && amount>=1000){
                                    Double avlBalance = userData.balance - amount;
                                    if (checkUser.updateBalance(customer_id, avlBalance) && checkUser.transactionDetails(customer_id, "Cash Withdraw", amount)){
                                        System.out.println("----------------------------------------------");
                                        System.out.println("\tCongratulation "+userData.name);
                                        System.out.println("\tCollect cash & Remove your card.");
                                        System.out.println("----------------------------------------------");
                                    } else {
                                        System.out.println("Re-try !Remove your card.");
                                    }
                                } else if (amount>userData.balance) {
                                    System.out.println("\t!Sorry "+userData.name);
                                    System.out.println("\tYour available Balance is Low");
                                } else {
                                    System.err.println("Please enter correct amount");
                                }
                            }else {
                                System.err.println("Sorry '"+ userData.name +"' You have entered wrong pine");
                            }

                        } else {
                            System.err.println("User not found. try later");
                        }
                        break;
                    case 3:
                        System.out.print("Enter your Customer-ID: ");
                        int customerId = sc.nextInt();
                        CheckUser checkUser2 = new CheckUser();
                        if (checkUser2.findUsers(customerId)){
                            System.out.print("Enter your atm pine: ");
                            int cusPin = sc.nextInt();
                            UserData userData = new UserData(customerId);
                            if (cusPin == userData.getPine(customerId)) {
                                System.out.println("Hi "+userData.name+" have a good day.");
                                System.out.println("Your available balance is : "+userData.balance);
                            }else {
                                System.err.println("'Sorry' "+ userData.name +" You have entered wrong pine");
                            }

                        } else {
                            System.err.println("User not found. try later");
                        }
                        break;
                    case 4:
                        System.out.print("Enter your Customer-ID: ");
                        int custId = sc.nextInt();
                        CheckUser checkUser3 = new CheckUser();
                        if (checkUser3.findUsers(custId)) {
                            System.out.print("Enter your atm pine: ");
                            int cusPin = sc.nextInt();
                            UserData userData = new UserData(custId);
                            if (cusPin == userData.getPine(custId)) {
                                System.out.print("Choose cash one: \n1.> 1,000\n2.> 2,000\n3.> 5,000\n4.> 10,000\n>>> ");
                                int cOption = sc.nextInt();
                                switch (cOption) {
                                    case 1:
                                        if (userData.balance >= 1000){
                                            Double avlBalance = userData.balance - 1000d;
                                            if (checkUser3.updateBalance(custId, avlBalance) && checkUser3.transactionDetails(custId, "Fast Cash Withdraw",1000d)) {
                                                System.out.println("----------------------------------------------");
                                                System.out.println("\tCongratulation "+userData.name);
                                                System.out.println("\tYour email-id is "+ userData.email);
                                                System.out.println("\tCollect cash & Remove your card.");
                                                System.out.println("----------------------------------------------");
                                            } else {
                                                System.out.println("Sorry "+ userData.name+" Please try later");
                                            }
                                        } else {
                                            System.out.println("\t!Sorry "+userData.name);
                                            System.out.println("\tYour Balance Low");
                                        }
                                        break;
                                    case 2:
                                        if (userData.balance >= 2000){
                                            Double avlBalance = userData.balance - 2000d;
                                            if (checkUser3.updateBalance(custId, avlBalance) && checkUser3.transactionDetails(custId, "Fast Cash Withdraw", 2000d)) {
                                                System.out.println("----------------------------------------------");
                                                System.out.println("\tCongratulation "+userData.name);
                                                System.out.println("\tYour email-id is "+ userData.email);
                                                System.out.println("\tCollect cash & Remove your card.");
                                                System.out.println("----------------------------------------------");
                                            } else {
                                                System.out.println("Sorry "+ userData.name+" Please try later");
                                            }
                                        } else {
                                            System.out.println("\t!Sorry "+userData.name);
                                            System.out.println("\tYour Balance Low");
                                        }
                                        break;
                                    case 3:
                                        if (userData.balance >= 5000){
                                            Double avlBalance = userData.balance - 5000d;
                                            if (checkUser3.updateBalance(custId, avlBalance) && checkUser3.transactionDetails(custId, "Fast Cash Withdraw", 5000d)) {
                                                System.out.println("----------------------------------------------");
                                                System.out.println("\tCongratulation "+userData.name);
                                                System.out.println("\tYour email-id is "+ userData.email);
                                                System.out.println("\tCollect cash & Remove your card.");
                                                System.out.println("----------------------------------------------");
                                            } else {
                                                System.out.println("Sorry "+ userData.name+" Please try later");
                                            }
                                        } else {
                                            System.out.println("\t!Sorry "+userData.name);
                                            System.out.println("\tYour Balance Low");
                                        }
                                        break;
                                    case 4:
                                        if (userData.balance >= 10000){
                                            Double avlBalance = userData.balance - 10000d;
                                            if (checkUser3.updateBalance(custId, avlBalance) && checkUser3.transactionDetails(custId, "Fast Cash Withdraw", 10000d)) {
                                                System.out.println("----------------------------------------------");
                                                System.out.println("\tCongratulation "+userData.name);
                                                System.out.println("\tYour email-id is "+ userData.email);
                                                System.out.println("\tCollect cash & Remove your card.");
                                                System.out.println("----------------------------------------------");
                                            } else {
                                                System.out.println("Sorry "+ userData.name+" Please try later");
                                            }
                                        } else {
                                            System.out.println("\t!Sorry "+userData.name);
                                            System.out.println("\tYour Balance Low");
                                        }
                                        break;
                                    default:
                                        System.out.println("Please Choose One");
                                }
                            }else {
                                System.err.println("'Sorry' "+ userData.name +" You have entered wrong pine");
                            }

                        } else {
                            System.err.println("User not found. try later");
                        }
                        break;
                    case 5:
                        System.out.print("If you want to change like that:\n1. Update Name\n2. Reset atm-pine\n3. Update Balance\n>>> ");
                        int choose = sc.nextInt();
                        switch (choose) {
                            case 1:
                                System.out.print("Enter Customer-Id: ");
                                int customerID = sc.nextInt();
                                CheckUser checkUsers = new CheckUser();
                                if (checkUsers.findUsers(customerID)) {
                                    System.out.print("Enter your atm pine: ");
                                    int pine = sc.nextInt();
                                    UserData userData = new UserData(customerID);
                                    if (pine == userData.getPine(customerID)) {
                                        System.out.print("Enter your new Name: ");
                                        Scanner sc1 = new Scanner(System.in);
                                        String name = sc1.nextLine();
                                        if (checkUsers.UpdateName(customerID, name) && (name != null && !name.isEmpty())) {
                                            System.out.println("Hi "+userData.name+" 'Successfully' updated your name");
                                        }
                                        else {
                                            System.out.println("Sorry '"+userData.name+"' Your name not Updated. Please try later.");
                                        }
                                    } else {
                                        System.err.println("You have entered wrong pine. Please try later");
                                    }
                                } else {
                                    System.err.println("User not found. Please enter correct Customer-Id.");
                                }
                                break;
                            case 2:
                                System.out.print("Enter Customer-Id: ");
                                int customersID = sc.nextInt();
                                CheckUser checkUserS = new CheckUser();
                                if (checkUserS.findUsers(customersID)) {
                                    System.out.print("Enter your atm pine: ");
                                    int pine = sc.nextInt();
                                    System.out.print("Enter A/c No: ");
                                    String acNo = sc.next();
                                    System.out.print("Enter atm No: ");
                                    String atmNo = sc.next();
                                    UserData userData = new UserData(customersID);
                                    if (pine == userData.getPine(customersID) || pine == userData.getPine(customersID, acNo, atmNo)) {
                                        System.out.print("Enter New Pin: ");
                                        int newPin = sc.nextInt();
                                        if (userData.setPine(customersID, newPin)) {
                                            System.out.println("Hi '"+userData.name+"' Your atm pine 'Successfully' updated.");
                                        } else {
                                            System.out.println("Sorry '"+userData.name+"' Please try later");
                                        }
                                    } else {
                                        System.err.println("You have entered wrong pine. Please try later");
                                    }
                                } else {
                                    System.err.println("User not found. Please enter correct Customer-Id.");
                                }
                                break;
                            case 3:
                                System.out.print("Enter Customer-Id: ");
                                int cusTomerID = sc.nextInt();
                                CheckUser checkUser1 = new CheckUser();
                                if (checkUser1.findUsers(cusTomerID)) {
                                    System.out.print("Enter your atm pine: ");
                                    int pine = sc.nextInt();
                                    UserData userData = new UserData(cusTomerID);
                                    if (pine == userData.getPine(cusTomerID)) {
                                        System.out.print("Enter amount: ");
                                        Double amount = sc.nextDouble();
                                        Double avlBalance = userData.balance + amount;
                                        if (checkUser1.updateBalance(cusTomerID, avlBalance) && checkUser1.transactionDetails(cusTomerID, "To deposit money",amount)) {
                                            System.out.println("Hi '"+userData.name+"' your balance Successfully updated.");
                                        } else{
                                            System.out.println("Sorry '"+userData.name+"' Please try later.");
                                        }
                                    } else {
                                        System.err.println("You have entered wrong pine. Please try later");
                                    }
                                } else {
                                    System.err.println("User not found. Please enter correct Customer-Id.");
                                }
                                break;
                            default:
                                System.err.println("Please choose one at-least");
                        }
                        break;
                    case 6:
                        System.out.print("Enter Customer-Id: ");
                        int cusTomerID = sc.nextInt();
                        CheckUser checkUser1 = new CheckUser();
                        if (checkUser1.findUsers(cusTomerID)) {
                            System.out.print("Enter your atm pine: ");
                            int pine = sc.nextInt();
                            UserData userData = new UserData(cusTomerID);
                            if (pine == userData.getPine(cusTomerID)) {
                                System.out.println("\n\tHi '"+userData.name+"',This is your transaction Details");
                                System.out.println("\tNow, Your available balance is "+userData.balance);
                                checkUser1.paymentHistory(cusTomerID);
                            } else {
                                System.err.println("You have entered wrong pine. Please try later");
                            }
                        } else {
                            System.err.println("User not found. Please enter correct Customer-Id.");
                        }
                        break;
                    default:
                        System.err.println("Please choose a valid option.");
                        break;
                }
            } catch (InputMismatchException e){
                System.err.println("Invalid input. Please enter correct data.");
            }
        }
    }
}