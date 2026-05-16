/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.bankingsystemassignment;

import java.sql.*;
import java.util.Scanner;

public class BankingSystemAssignment {
    // Database credentials - verify these match your local MySQL Server password
    private static final String URL = "jdbc:mysql://localhost:3306/banking_system";
    private static final String USER = "root";
    private static final String PASSWORD = "SupremeK1DD123"; 

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== INITIALIZING BANKING ECOSYSTEM ===");
        try {
            // Test Connection
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database Connection Established Successfully!");
            conn.close();
            
            // Launch System Main Interface Loop
            runMainMenu();
            
        } catch (SQLException e) {
            System.out.println("CRITICAL ERROR: Unable to connect to database!");
            System.out.println("Please check your database URL, credentials, and verify MySQL is running.");
            e.printStackTrace();
        }
    }

    private static void runMainMenu() {
        while (true) {
            System.out.println("\n=================================");
            System.out.println("    BANKING SYSTEM MAIN MENU     ");
            System.out.println("=================================");
            System.out.println("1. Customer Management");
            System.out.println("2. Account Management");
            System.out.println("3. Transaction Processing");
            System.out.println("4. Balance Enquiry");
            System.out.println("5. Exit Application");
            System.out.print("Select an option: ");
            
            int choice = getValidIntegerInput();
            
            switch (choice) {
                case 1: customerMenu(); break;
                case 2: accountMenu(); break;
                case 3: transactionMenu(); break;
                case 4: balanceEnquiry(); break;
                case 5: 
                    System.out.println("Exiting Application. Goodbye.");
                    return;
                default: 
                    System.out.println("Invalid choice! Please select 1-5.");
            }
        }
    }

    // ==========================================
    // MODULE 1: CUSTOMER MANAGEMENT
    // ==========================================
    private static void customerMenu() {
        System.out.println("\n--- Customer Management ---");
        System.out.println("1. Register New Customer");
        System.out.println("2. View Customer Details");
        System.out.println("3. Update Customer Contact");
        System.out.print("Select choice: ");
        int choice = getValidIntegerInput();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            if (choice == 1) {
                System.out.print("Enter First Name: "); String fn = scanner.next();
                System.out.print("Enter Last Name: "); String ln = scanner.next();
                System.out.print("Enter Email Address: "); String email = scanner.next();
                System.out.print("Enter Phone Number: "); String phone = scanner.next();

                String sql = "INSERT INTO customers (first_name, last_name, email, phone) VALUES (?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, fn); stmt.setString(2, ln);
                    stmt.setString(3, email); stmt.setString(4, phone);
                    stmt.executeUpdate();
                    System.out.println("Customer registered successfully!");
                    logAction(conn, "Registered customer: " + email);
                }
            } else if (choice == 2) {
                System.out.print("Enter Customer ID: "); int id = getValidIntegerInput();
                String sql = "SELECT * FROM customers WHERE customer_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, id);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        System.out.println("\nID: " + rs.getInt("customer_id"));
                        System.out.println("Name: " + rs.getString("first_name") + " " + rs.getString("last_name"));
                        System.out.println("Email: " + rs.getString("email"));
                        System.out.println("Phone: " + rs.getString("phone"));
                    } else {
                        System.out.println("Customer not found.");
                    }
                }
            } else if (choice == 3) {
                System.out.print("Enter Customer ID: "); int id = getValidIntegerInput();
                System.out.print("Enter New Phone Number: "); String phone = scanner.next();
                String sql = "UPDATE customers SET phone = ? WHERE customer_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, phone); stmt.setInt(2, id);
                    int rows = stmt.executeUpdate();
                    if (rows > 0) {
                        System.out.println("Customer contact details updated!");
                        logAction(conn, "Updated profile phone for Customer ID: " + id);
                    } else System.out.println("Customer record target not found.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database sub-operation failed: " + e.getMessage());
        }
    }

    // ==========================================
    // MODULE 2: ACCOUNT MANAGEMENT
    // ==========================================
    private static void accountMenu() {
        System.out.println("\n--- Account Management ---");
        System.out.println("1. Create New Account");
        System.out.println("2. Toggle Account Status (Close Account)");
        System.out.print("Select choice: ");
        int choice = getValidIntegerInput();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            if (choice == 1) {
                System.out.print("Enter Customer ID: "); int cid = getValidIntegerInput();
                System.out.print("Enter Unique Account Number: "); String accNum = scanner.next();
                System.out.print("Account Type (1 for Checking, 2 for Savings): ");
                int typeSel = getValidIntegerInput();
                String type = (typeSel == 2) ? "Savings" : "Checking";
                System.out.print("Enter Initial Deposit Amount: ");
                double bal = getValidDoubleInput();

                String sql = "INSERT INTO accounts (customer_id, account_number, account_type, balance) VALUES (?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, cid); stmt.setString(2, accNum);
                    stmt.setString(3, type); stmt.setDouble(4, bal);
                    stmt.executeUpdate();
                    System.out.println("Account opened and verified safely!");
                    logAction(conn, "Opened account: " + accNum);
                }
            } else if (choice == 2) {
                System.out.print("Enter Account Number to close: "); String accNum = scanner.next();
                String sql = "UPDATE accounts SET status = 'Closed' WHERE account_number = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, accNum);
                    int rows = stmt.executeUpdate();
                    if (rows > 0) {
                        System.out.println("Account status updated to Closed.");
                        logAction(conn, "Deactivated account record: " + accNum);
                    } else System.out.println("Account matching entry target not found.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Operation tracking failed: " + e.getMessage());
        }
    }

    // ==========================================
    // MODULE 3: TRANSACTION PROCESSING
    // ==========================================
    private static void transactionMenu() {
        System.out.println("\n--- Transaction Engine ---");
        System.out.println("1. Deposit Fund Package");
        System.out.println("2. Process Account Withdrawal");
        System.out.println("3. Core Inter-Account Bank Transfer");
        System.out.print("Select choice: ");
        int choice = getValidIntegerInput();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            conn.setAutoCommit(false); // Transaction Boundaries Start

            if (choice == 1) {
                System.out.print("Target Account Number: "); String acc = scanner.next();
                System.out.print("Amount to Deposit: "); double amt = getValidDoubleInput();
                
                if (executeDeposit(conn, acc, amt)) {
                    conn.commit();
                    System.out.println("Deposit processing completed!");
                } else conn.rollback();
                
            } else if (choice == 2) {
                System.out.print("Target Account Number: "); String acc = scanner.next();
                System.out.print("Amount to Withdraw: "); double amt = getValidDoubleInput();
                
                if (executeWithdrawal(conn, acc, amt)) {
                    conn.commit();
                    System.out.println("Withdrawal transaction processing successfully posted!");
                } else conn.rollback();
                
            } else if (choice == 3) {
                System.out.print("Source Account Number: "); String src = scanner.next();
                System.out.print("Destination Account Number: "); String dest = scanner.next();
                System.out.print("Amount to Transfer: "); double amt = getValidDoubleInput();

                if (executeWithdrawal(conn, src, amt)) {
                    if (executeDeposit(conn, dest, amt)) {
                        conn.commit();
                        System.out.println("Inter-account Transfer processing successful!");
                        logAction(conn, "Transferred money from " + src + " to " + dest);
                    } else {
                        System.out.println("Transfer failed at destination resolution phase. Executing recovery rollback.");
                        conn.rollback();
                    }
                } else {
                    System.out.println("Transfer rejected. Insufficient baseline clearing funds.");
                    conn.rollback();
                }
            }
        } catch (SQLException e) {
            System.out.println("Transaction Processing Aborted: " + e.getMessage());
        }
    }

    // ==========================================
    // MODULE 4: BALANCE ENQUIRY
    // ==========================================
    private static void balanceEnquiry() {
        System.out.print("\nEnter Account Number: ");
        String accNum = scanner.next();
        String sql = "SELECT balance, status FROM accounts WHERE account_number = ?";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accNum);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("\nAccount Status: " + rs.getString("status"));
                System.out.println("Current Available Settlement Balance: R" + rs.getDouble("balance"));
            } else {
                System.out.println("Account Number was not identified in the system.");
            }
        } catch (SQLException e) {
            System.out.println("Error pulling financial statistics: " + e.getMessage());
        }
    }

    // ==========================================
    // ENGINE HELPER UTILITIES
    // ==========================================
    private static boolean executeDeposit(Connection conn, String acc, double amt) throws SQLException {
        if(amt <= 0) return false;
        String sqlUpdate = "UPDATE accounts SET balance = balance + ? WHERE account_number = ? AND status='Active'";
        try (PreparedStatement stmt = conn.prepareStatement(sqlUpdate)) {
            stmt.setDouble(1, amt); stmt.setString(2, acc);
            int updated = stmt.executeUpdate();
            if (updated == 0) return false;
        }
        
        String sqlLog = "INSERT INTO transactions (account_id, transaction_type, amount) " +
                         "VALUES ((SELECT account_id FROM accounts WHERE account_number = ?), 'Deposit', ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sqlLog)) {
            stmt.setString(1, acc); stmt.setDouble(2, amt);
            stmt.executeUpdate();
        }
        return true;
    }

    private static boolean executeWithdrawal(Connection conn, String acc, double amt) throws SQLException {
        if(amt <= 0) return false;
        String sqlCheck = "SELECT balance FROM accounts WHERE account_number = ? AND status='Active'";
        try (PreparedStatement stmt = conn.prepareStatement(sqlCheck)) {
            stmt.setString(1, acc);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next() || rs.getDouble("balance") < amt) return false;
        }

        String sqlUpdate = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sqlUpdate)) {
            stmt.setDouble(1, amt); stmt.setString(2, acc);
            stmt.executeUpdate();
        }

        String sqlLog = "INSERT INTO transactions (account_id, transaction_type, amount) " +
                         "VALUES ((SELECT account_id FROM accounts WHERE account_number = ?), 'Withdrawal', ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sqlLog)) {
            stmt.setString(1, acc); stmt.setDouble(2, amt);
            stmt.executeUpdate();
        }
        return true;
    }

    private static void logAction(Connection conn, String action) throws SQLException {
        String sql = "INSERT INTO audit_logs (action_performed) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, action);
            stmt.executeUpdate();
        }
    }

    private static int getValidIntegerInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid Entry format. Please supply a whole integer value option.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static double getValidDoubleInput() {
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid numeric balance structure format. Re-enter: ");
            scanner.next();
        }
        return scanner.nextDouble();
    }
}