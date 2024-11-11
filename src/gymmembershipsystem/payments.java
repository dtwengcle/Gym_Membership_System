
package gymmembershipsystem;

import java.util.Scanner;


public class payments {
    
    private Scanner sc = new Scanner(System.in); 

    public void pTransaction() {
        String response;

        do {
            System.out.println("\n ----------------------");
            System.out.println("|     MENBER'S PAYMENTS     |");
            System.out.println(" ----------------------");
            System.out.println("| 1.    ADD PAYMENT     |");
            System.out.println(" ----------------------");
            System.out.println("| 2.    VIEW PAYMENT    |");
            System.out.println(" ----------------------");
            System.out.println("| 3.   UPDATE PAYMENT   |");
            System.out.println(" ----------------------");
            System.out.println("| 4.   DELETE PAYMENT   |");
            System.out.println(" ----------------------");
            System.out.println("| 5.      EXIT         |");
            System.out.println(" ----------------------");

            System.out.print("Enter selection: ");
            int act = sc.nextInt();
            payments pm = new payments();
            sc.nextLine(); 

            switch (act) {
                case 1:
                   
                    pm.addPayments();
                    pm.viewPayments();
                    break;
                case 2:
                    pm.viewPayments();
                    break;
                case 3:
                    
                    break;
                case 4:
                    
                    break;
                
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid selection. Please try again.");
                    break;
            }

            System.out.print("Do you want to continue? (yes/no): ");
            response = sc.nextLine();

        } while (response.equalsIgnoreCase("yes"));
    }
    public void addPayments() {
    config con = new config();
    Member mem = new Member();
    mem.viewMembers();  // Display members for user selection

    System.out.print("Enter the selected ID of the Member: ");
    int mid = sc.nextInt();
    sc.nextLine();  // Clear the newline character after nextInt

    String sql = "SELECT m_id FROM tbl_member WHERE m_id = ?";
    while (con.getSingleValue(sql, mid) == 0) {
        System.out.print("Member does not exist, please select again: ");
        mid = sc.nextInt();
        sc.nextLine();  // Clear the newline character after nextInt again
    }

    System.out.print("Enter Payment Amount: ");
    int payments = sc.nextInt();
    sc.nextLine();  // Clear the newline character after nextInt

    System.out.print("Selected Plan: ");
    String slcpln = sc.nextLine();  // This should now work as expected

    System.out.print("Instructor: ");
    String instruc = sc.nextLine();

    String paymentsqry = "INSERT INTO tbl_payments (m_id, p_payments, p_slcpln, p_instruc) VALUES (?, ?, ?, ?)";
    con.addRecord(paymentsqry, mid, payments, slcpln, instruc);
    System.out.println("Payments added successfully!");
    }
    public void viewPayments() {
    // Correct the JOIN condition to use m_id as the linking field
    String qry = "SELECT p_id, m_name, m_dt, m_loc, p_payments, p_slcpln, p_instruc FROM tbl_payments " +
                 "LEFT JOIN tbl_member ON tbl_member.m_id = tbl_payments.m_id";
    
    String[] columnHeaders = {"PID", "Member", "Date and Time", "Location", "Payments", "Selected Plan", "Instructor"};
    String[] columnNames = {"p_id", "m_name", "m_dt", "m_loc", "p_payments", "p_slcpln", "p_instruc"};
    
    config con = new config();
    con.viewRecords(qry, columnHeaders, columnNames);
}


}
