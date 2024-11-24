package gymmembershipsystem;

import java.util.Scanner;

public class ViewRecords {

    public config con;

    public ViewRecords() {
        con = new config();
    }

    public void recordsMenu() {
        Scanner sc = new Scanner(System.in);
        String response;

        do {
            System.out.println("\n--------------------------------");
            System.out.println("| Welcome to the Records Panel |");
            System.out.println("--------------------------------");
            System.out.println("1. View Payments Records");
            System.out.println("2. Exit");

            System.out.print("Enter Selection: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    viewGeneralRecords();
                    break;
                case 2:
                    System.out.println("Exiting Records Panel...");
                    return; // Exit the menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.print("Do you want to continue? (Yes/No): ");
            response = sc.next();
        } while (response.equalsIgnoreCase("Yes"));
    }

    private void viewGeneralRecords() {
        payments pay = new payments();
        pay.viewPayments(); // Display all payment records

        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter Payment ID to view details (or 0 to exit): ");
        int paymentId = getValidatedIntegerInput(sc);

        if (paymentId == 0) {
            System.out.println("Exiting to Records Menu...");
            return;
        }

        viewPaymentsById(paymentId);
    }

    private void viewPaymentsById(int paymentId) {
        String qry = "SELECT p_id, m_name, m_dt, m_loc, p_payments, p_slcpln, p_instruc FROM tbl_payments " +
                     "LEFT JOIN tbl_member ON tbl_member.m_id = tbl_payments.m_id WHERE p_id = ?";
        String[] columnHeaders = {"PID", "Member", "Date and Time", "Location", "Payments", "Selected Plan", "Instructor"};
        String[] columnNames = {"p_id", "m_name", "m_dt", "m_loc", "p_payments", "p_slcpln", "p_instruc"};

        if (con.getSingleValue("SELECT p_id FROM tbl_payments WHERE p_id = ?", paymentId) == 0) {
            System.out.println("Error: Payment ID " + paymentId + " does not exist.");
        } else {
            con.viewSingleRecord(qry, columnHeaders, columnNames, paymentId);
        }
    }

    private int getValidatedIntegerInput(Scanner sc) {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
    }
}
