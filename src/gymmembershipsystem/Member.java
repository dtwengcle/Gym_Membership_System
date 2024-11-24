package gymmembershipsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
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

            int act = -1;
            boolean validInput = false;

            while (!validInput) {
                System.out.print("Enter selection: ");
                try {
                    act = sc.nextInt(); 
                    sc.nextLine(); 

                    if (act >= 1 && act <= 5) {
                        validInput = true; 
                    } else {
                        System.out.println("Invalid selection. Please enter a number between 1 and 5.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number between 1 and 5.");
                    sc.nextLine(); 
                }
            }

            switch (act) {
                case 1:
                    addMembers();
                    viewMembers();
                    break;
                case 2:
                    viewMembers();
                    break;
                case 3:
                    viewMembers();
                    updateMembers();
                    viewMembers();
                    break;
                case 4:
                    viewMembers();
                    deleteMembers();
                    viewMembers();
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
        System.out.print("Date (YYYY-MM-DD): ");
        String date = sc.nextLine();
        System.out.print("Location: ");
        String loc = sc.nextLine();

        String sql = "INSERT INTO tbl_member (m_name, m_dt, m_loc) VALUES (?, ?, ?)";
        config con = new config();
        con.addRecord(sql, name, date, loc);
        System.out.println("Member added successfully!");
    }

    public void viewMembers() {
        String qry = "SELECT * FROM tbl_member";
        String[] columnHeaders = {"ID", "Name", "Date and Time", "Location"};
        String[] columnNames = {"m_id", "m_name", "m_dt", "m_loc"};
        config con = new config();
        con.viewRecords(qry, columnHeaders, columnNames);
    }

    public void updateMembers() {
        System.out.print("Enter ID to Update: ");
        int id = sc.nextInt();
        sc.nextLine(); 
        
        config con = new config();
        while (con.getSingleValue("SELECT m_id FROM tbl_member WHERE m_id = ?", id) == 0) {
            System.out.println("Selected ID doesn't exist!");
            System.out.print("Select member ID Again: ");
            id = sc.nextInt();
            sc.nextLine(); 
        }

        System.out.print("New Name: ");
        String name = sc.nextLine();
        System.out.print("New Date (YYYY-MM-DD): ");
        String dt = sc.nextLine();
        System.out.print("New Location: ");
        String loc = sc.nextLine();
        
        String qry = "UPDATE tbl_member SET m_name = ?, m_dt = ?, m_loc = ? WHERE m_id = ?";
        con.updateRecord(qry, name, dt, loc, id);
        System.out.println("Member updated successfully!");
    }

    public void deleteMembers() {
        System.out.print("Enter ID to Delete: ");
        int id = sc.nextInt();
        sc.nextLine();  
        
        config con = new config();
        while (con.getSingleValue("SELECT m_id FROM tbl_member WHERE m_id = ?", id) == 0) {
            System.out.println("Selected ID doesn't exist!");
            System.out.print("Select member ID Again: ");
            id = sc.nextInt();
            sc.nextLine(); 
        }
        
        String qry = "DELETE FROM tbl_member WHERE m_id = ?";
        con.deleteRecord(qry, id);
        System.out.println("Member deleted successfully!");
    }

}
