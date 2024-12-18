package gymmembershipsystem;

import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class payments {
    
    config conf = new config();
    private final Scanner sc = new Scanner(System.in);

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
            int act = conf.validateInt();

            
            switch (act) {
                case 1:
                    addPayments();   
                    viewPayments();   
                    break;
                case 2:
                    viewPayments();   
                    break;
                case 3:
                    viewPayments();   
                    updatePayments(); 
                    viewPayments();   
                    break;
                case 4:
                    viewPayments();  
                    deletePayments(); 
                    viewPayments();  
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return; 
                default:
                    System.out.println("Invalid selection. Please try again.");
                    break;
            }

            response = getValidatedYesNoInput("Do you want to continue? (yes/no): ");
        } while (response.equalsIgnoreCase("yes"));
    }

    public void addPayments() {
        config con = new config();
        Member mem = new Member();
        mem.viewMembers(); 

        System.out.print("Enter the selected ID of the Member: ");
        int mid = conf.validateInt();

        
        String sql = "SELECT m_id FROM tbl_member WHERE m_id = ?";
        while (con.getSingleValue(sql, mid) == 0) {
            System.out.print("Member does not exist, please select again: ");
            mid = getValidatedIntegerInput(); 
        }

        System.out.print("Enter Payment Amount: ");
        int payments = conf.validateInt();

        System.out.print("Selected Plan (Monthly/Yearly): ");
        String slcpln = sc.nextLine();
        
        while(!(slcpln.contains("Monthly") || slcpln.contains("Yearly"))){
            System.out.print("Invalid plan, try again: ");
            slcpln = sc.nextLine();
        }

        System.out.print("Instructor: ");
        String instruc = sc.nextLine().trim(); 

        if (slcpln.isEmpty() || instruc.isEmpty()) {
            System.out.println("Selected Plan and Instructor cannot be empty!");
            return;
        }

        
        String expirationDate = calculateExpirationDate(slcpln);
        String renewalDate = calculateRenewalDate(expirationDate);

        String paymentsqry = "INSERT INTO tbl_payments (m_id, p_payments, p_slcpln, p_instruc, p_expiration_date, p_renewal_date) VALUES (?, ?, ?, ?, ?, ?)";
        con.addRecord(paymentsqry, mid, payments, slcpln, instruc, expirationDate, renewalDate);
        System.out.println("Payment added successfully!");
    }

    public void viewPayments() {
        String qry = "SELECT p_id, m_name, m_dt, m_loc, p_payments, p_slcpln, p_instruc FROM tbl_payments " +
                     "LEFT JOIN tbl_member ON tbl_member.m_id = tbl_payments.m_id";

        String[] columnHeaders = {"PID", "Member", "Date and Time", "Location", "Payments", "Selected Plan", "Instructor"};
        String[] columnNames = {"p_id", "m_name", "m_dt", "m_loc", "p_payments", "p_slcpln", "p_instruc"};

        config con = new config();
        con.viewRecords(qry, columnHeaders, columnNames);
    }

    public void updatePayments() {
        config con = new config();

        System.out.print("Enter Payment ID to update: ");
        int paymentId = getValidatedIntegerInput();

        String sql = "SELECT p_id FROM tbl_payments WHERE p_id = ?";
        if (con.getSingleValue(sql, paymentId) == 0) {
            System.out.println("Payment ID does not exist!");
            return;
        }

        System.out.print("Enter new Payment Amount: ");
        int newAmount = getValidatedIntegerInput();

        System.out.print("Enter new Selected Plan: ");
        String newPlan = sc.nextLine();
        
        while(!(newPlan.contains("Monthly") || newPlan.contains("Yearly"))){
            System.out.print("Invalid plan, try again: ");
            newPlan = sc.nextLine();
        }

        System.out.print("Enter new Instructor: ");
        String newInstructor = sc.nextLine();

        
        String newExpirationDate = calculateExpirationDate(newPlan);
        String newRenewalDate = calculateRenewalDate(newExpirationDate);

        String updateSql = "UPDATE tbl_payments SET p_payments = ?, p_slcpln = ?, p_instruc = ?, p_expiration_date = ?, p_ renewal_date = ? WHERE p_id = ?";
        con.updateRecord(updateSql, newAmount, newPlan, newInstructor, newExpirationDate, newRenewalDate, paymentId);

        System.out.println("Payment updated successfully!");
    }

    public void deletePayments() {
        config con = new config();

        System.out.print("Enter Payment ID to delete: ");
        int paymentId = getValidatedIntegerInput();

        String sql = "SELECT p_id FROM tbl_payments WHERE p_id = ?";
        if (con.getSingleValue(sql, paymentId) == 0) {
            System.out.println("Payment ID does not exist!");
            return;
        }

        String deleteSql = "DELETE FROM tbl_payments WHERE p_id = ?";
        con.deleteRecord(deleteSql, paymentId);

        System.out.println("Payment deleted successfully!");
    }
    
    
    private String calculateExpirationDate(String plan) {
        Calendar cal = Calendar.getInstance();
        
        
        if (plan.equalsIgnoreCase("monthly")) {
            cal.add(Calendar.MONTH, 1);
        } else if (plan.equalsIgnoreCase("yearly")) {
            cal.add(Calendar.YEAR, 1);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }

   
    private String calculateRenewalDate(String expirationDate) {
        Calendar cal = Calendar.getInstance();
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            cal.setTime(sdf.parse(expirationDate));
            cal.add(Calendar.DATE, 7); 

        } catch (Exception e) {
            e.printStackTrace();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }

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
