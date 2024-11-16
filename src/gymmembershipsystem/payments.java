package gymmembershipsystem;

import java.util.Scanner;

public class payments {

    private final Scanner sc = new Scanner(System.in);

    // Main method for handling payment transactions
    public void pTransaction() {
        String response;
        do {
            System.out.println("\n --------------------------------");
            System.out.println("|     MEMBER'S PAYMENTS PANEL   |");
            System.out.println(" --------------------------------");
            System.out.println("| 1.        ADD PAYMENT         |");
            System.out.println(" --------------------------------");
            System.out.println("| 2.        VIEW PAYMENT        |");
            System.out.println(" --------------------------------");
            System.out.println("| 3.       UPDATE PAYMENT       |");
            System.out.println(" --------------------------------");
            System.out.println("| 4.       DELETE PAYMENT       |");
            System.out.println(" --------------------------------");
            System.out.println("| 5.           EXIT             |");
            System.out.println(" --------------------------------");

            System.out.print("Enter selection: ");
            int act = getValidatedIntegerInput();

            // Switch case to handle different payment actions
            switch (act) {
                case 1:
                    addPayments();    // Add payment
                    viewPayments();   // View updated payments
                    break;
                case 2:
                    viewPayments();   // View payment records
                    break;
                case 3:
                    viewPayments();   // View payment records
                    updatePayments(); // Update payment details
                    viewPayments();   // View updated payments
                    break;
                case 4:
                    viewPayments();   // View payment records
                    deletePayments(); // Delete payment
                    viewPayments();   // View updated payments
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return; // Exit the payment panel
                default:
                    System.out.println("Invalid selection. Please try again.");
                    break;
            }

            // Ask if the user wants to continue
            response = getValidatedYesNoInput("Do you want to continue? (yes/no): ");
        } while (response.equalsIgnoreCase("yes"));
    }

    // Method to add payment
    public void addPayments() {
        config con = new config();
        Member mem = new Member();
        mem.viewMembers(); // Show the list of members

        System.out.print("Enter the selected ID of the Member: ");
        int mid = getValidatedIntegerInput(); // Get Member ID

        // Validate that the member ID exists in the database
        String sql = "SELECT m_id FROM tbl_member WHERE m_id = ?";
        while (con.getSingleValue(sql, mid) == 0) {
            System.out.print("Member does not exist, please select again: ");
            mid = getValidatedIntegerInput(); // Re-enter Member ID if invalid
        }

        // Consume the leftover newline character after reading an integer
        sc.nextLine(); // <-- This is important to prevent issues with nextLine() below.

        // Handle Payment Amount input
        System.out.print("Enter Payment Amount: ");
        int payments = getValidatedIntegerInput();

        // Handle Selected Plan input
        System.out.print("Selected Plan: ");
        String slcpln = sc.nextLine().trim(); // Read the selected plan

        // Handle Instructor input
        System.out.print("Instructor: ");
        String instruc = sc.nextLine().trim(); // Read the instructor

        // Validate that both the selected plan and instructor are not empty
        if (slcpln.isEmpty() || instruc.isEmpty()) {
            System.out.println("Selected Plan and Instructor cannot be empty!");
            return;
        }

        // Prepare the SQL query to insert the payment data into the database
        String paymentsqry = "INSERT INTO tbl_payments (m_id, p_payments, p_slcpln, p_instruc) VALUES (?, ?, ?, ?)";
        con.addRecord(paymentsqry, mid, payments, slcpln, instruc);
        System.out.println("Payment added successfully!");
    }

    // Method to view payment records
    public void viewPayments() {
        String qry = "SELECT p_id, m_name, m_dt, m_loc, p_payments, p_slcpln, p_instruc FROM tbl_payments " +
                     "LEFT JOIN tbl_member ON tbl_member.m_id = tbl_payments.m_id";

        String[] columnHeaders = {"PID", "Member", "Date and Time", "Location", "Payments", "Selected Plan", "Instructor"};
        String[] columnNames = {"p_id", "m_name", "m_dt", "m_loc", "p_payments", "p_slcpln", "p_instruc"};

        config con = new config();
        con.viewRecords(qry, columnHeaders, columnNames); // View all payment records
    }

    // Method to update payment
    public void updatePayments() {
        config con = new config();

        System.out.print("Enter Payment ID to update: ");
        int paymentId = getValidatedIntegerInput();

        // Check if the Payment ID exists
        String sql = "SELECT p_id FROM tbl_payments WHERE p_id = ?";
        if (con.getSingleValue(sql, paymentId) == 0) {
            System.out.println("Payment ID does not exist!");
            return;
        }

        // Handle new Payment Amount input
        System.out.print("Enter new Payment Amount: ");
        int newAmount = getValidatedIntegerInput();

        // Handle new Selected Plan input
        System.out.print("Enter new Selected Plan: ");
        String newPlan = sc.nextLine();

        // Handle new Instructor input
        System.out.print("Enter new Instructor: ");
        String newInstructor = sc.nextLine();

        // Update the payment details
        String updateSql = "UPDATE tbl_payments SET p_payments = ?, p_slcpln = ?, p_instruc = ? WHERE p_id = ?";
        con.updateRecord(updateSql, newAmount, newPlan, newInstructor, paymentId);

        System.out.println("Payment updated successfully!");
    }

    // Method to delete payment
    public void deletePayments() {
        config con = new config();

        System.out.print("Enter Payment ID to delete: ");
        int paymentId = getValidatedIntegerInput();

        // Check if the Payment ID exists
        String sql = "SELECT p_id FROM tbl_payments WHERE p_id = ?";
        if (con.getSingleValue(sql, paymentId) == 0) {
            System.out.println("Payment ID does not exist!");
            return;
        }

        // Delete the payment record
        String deleteSql = "DELETE FROM tbl_payments WHERE p_id = ?";
        con.deleteRecord(deleteSql, paymentId);

        System.out.println("Payment deleted successfully!");
    }

    // Helper method to get validated integer input
    private int getValidatedIntegerInput() {
        while (true) {
            try {
                String input = sc.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input, please enter a valid number (e.g., 10/100/1000): ");
            }
        }
    }

    // Helper method to get validated Yes/No input
    private String getValidatedYesNoInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim().toLowerCase();
            if (input.equals("yes") || input.equals("no")) {
                return input;
            } else {
                System.out.println("Invalid input, please enter 'yes' or 'no': ");
            }
        }
    }
}
