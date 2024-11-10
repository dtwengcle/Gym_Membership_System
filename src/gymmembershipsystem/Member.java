package gymmembershipsystem;

import java.util.Scanner;

public class Member {

    private Scanner sc = new Scanner(System.in); 

    public void mTransaction() {
        String response;

        do {
            System.out.println("\n ----------------------");
            System.out.println("|     MEMBER PANEL     |");
            System.out.println(" ----------------------");
            System.out.println("| 1.    ADD MEMBER     |");
            System.out.println(" ----------------------");
            System.out.println("| 2.    VIEW MEMBER    |");
            System.out.println(" ----------------------");
            System.out.println("| 3.   UPDATE MEMBER   |");
            System.out.println(" ----------------------");
            System.out.println("| 4.   DELETE MEMBER   |");
            System.out.println(" ----------------------");
            System.out.println("| 5.      EXIT         |");
            System.out.println(" ----------------------");

            System.out.print("Enter selection: ");
            int act = sc.nextInt();
            sc.nextLine();  
            Member mem = new Member();
            switch (act) {
                case 1:
                    mem.addMembers();
                    mem.viewMembers();
                    break;
                case 2:
                    mem.viewMembers();
                    break;
                case 3:
                    mem.addMembers();
                    mem.updateMembers();
                    mem.addMembers();
                    break;
                case 4:
                    mem.addMembers();
                    mem.updateMembers();
                    mem.addMembers();
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
    public void addMembers() {
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Payment: ");
        String pay = sc.nextLine();
        System.out.print("Selected Plan: ");
        String sp = sc.nextLine();
        System.out.print("Date and Time: ");
        String date = sc.nextLine();
        System.out.print("Instructor: ");
        String instru = sc.nextLine();
        System.out.print("Location: ");
        String loc = sc.nextLine();

        String sql = "INSERT INTO tbl_member (m_name, m_payment, m_selectedplan, m_dt, m_instru, m_loc) VALUES (?, ?, ?, ?, ?, ?)";
        config con = new config();
        con.addRecord(sql, name, pay, sp, date, instru, loc);
        System.out.println("Member added successfully!");
    }
    public void viewMembers() {
        String qry = "SELECT * FROM tbl_membership";
        String[] columnHeaders = {"ID", "Name", "Payment", "Date and Time", "Selected Plan", "Instructor", "Location"};
        String[] columnNames = {"m_id", "m_name", "m_payment", "m_dt", "m_selectedplan", "m_instru", "m_loc"};
        config con = new config();
        con.viewRecords(qry, columnHeaders, columnNames);
    }
        private void updateMembers() {
        Scanner sc = new Scanner(System.in);
        config con = new config();
        System.out.print("Enter ID to Update: ");
        int id = sc.nextInt();
        sc.nextLine(); 
        
        while(con.getSingleValue("SELECT m_id FROM tbl_member WHERE m_id = ?", id) == 0) {
            System.out.println("Selected ID doesn't exist! ");
            System.out.print("Select member ID Again: ");
            id = sc.nextInt();
            sc.nextLine(); 
        }

        System.out.print("New Name: ");
        String name = sc.nextLine();
        System.out.print("New Payment: ");
        String pay = sc.nextLine();
        System.out.print("New Date & Time: ");
        String dt = sc.nextLine();
        System.out.print("New Selected Plan: ");
        String sp = sc.nextLine();
        System.out.print("New Instructor: ");
        String instru = sc.nextLine();
        System.out.print("New Location: ");
        String loc = sc.nextLine();
        
        String qry = "UPDATE tbl_member SET m_name = ?, m_payment = ?, m_dt = ?, m_selectedplan = ?, m_instru = ?, m_loc = ? WHERE m_id = ?";
        config conf = new config();
        conf.updateRecord(qry, name, pay, dt, sp, instru, loc, id);
        System.out.println("Member updated successfully!");
    }
         private void deleteMembers() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        System.out.print("Enter ID to Delete: ");
        int id = sc.nextInt();
        sc.nextLine();  // Consume the newline character
        
        while (conf.getSingleValue("SELECT m_id FROM tbl_member WHERE m_id = ?", id) == 0) {
            System.out.println("Selected ID doesn't exist! ");
            System.out.print("Select member ID Again: ");
            id = sc.nextInt();
            sc.nextLine(); 
        }
        
        String qry = "DELETE FROM tbl_member WHERE m_id = ?";
        config con = new config();
        con.deleteRecord(qry, id);
        System.out.println("Member deleted successfully!");
    }
}