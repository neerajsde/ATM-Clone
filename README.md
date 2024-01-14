# ATM Services Project

## Overview

This Java-based ATM Services project provides a simple command-line interface for basic banking transactions. The system allows users to perform various operations such as new user registration, cash withdrawal, balance inquiry, fast cash withdrawals, updating user details (name, PIN, balance), and viewing transaction history.

## Features

1. **New User Registration**
   - Users can register with the system by providing necessary details.

2. **Cash Withdrawal**
   - Registered users can withdraw cash after entering their Customer-ID and ATM PIN.
   - Users can choose the withdrawal amount, and the system ensures that it is a multiple of 100 and within the available balance.

3. **Check Available Balance**
   - Users can check their available balance by entering their Customer-ID and ATM PIN.

4. **Fast Cash Withdrawals**
   - This feature allows users to perform quick cash withdrawals for fixed amounts (1,000, 2,000, 5,000, 10,000).
   - The system checks the user's balance before processing the withdrawal.

5. **Updating User Details (Name, PIN, Balance)**
   - Users can update their name or reset their ATM PIN.
   - Balance can be updated by depositing money into the account.

6. **Viewing Transaction History**
   - Users can view their transaction history, including details like transaction type and amount.

## How to Run

1. **Compile and Run**
   - Compile the `ATM.java` file.
   - Run the compiled file to start the ATM Services.

2. **Navigation**
   - Follow on-screen instructions to navigate through the options.
   - Enter 'next' to continue or any other key to exit.

3. **Input Validation**
   - The system includes input validation to handle incorrect inputs gracefully.

## Project Structure

- `ATM.java`: Main class containing the ATM Services logic.
- `Register.java`: Class for user registration.
- `CheckUser.java`: Class for checking user existence, updating user details, and maintaining transaction history.
- `UserData.java`: Class representing user data and account information.

## Usage Guidelines

- Ensure correct inputs (Customer-ID, PIN, amounts) to avoid errors.
- Follow the on-screen prompts to navigate through the system.
- Regularly check transaction history for accurate record-keeping.

Feel free to explore and use this simple ATM Services project for educational purposes or as a starting point for more advanced banking systems.

## Note

- This project is a console-based implementation and lacks advanced security features typically found in real-world banking systems.
- Use responsibly and only for educational purposes.