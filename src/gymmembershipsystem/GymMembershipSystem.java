package gymmembershipsystem;

import java.util.Scanner;

public class GymMembershipSystem {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        do {
            System.out.println("\n-----------------------------------------");
            System.out.println("|   WELCOME TO GYM MEMBERSHIP SYSTEM     |");
            System.out.println("-----------------------------------------");
            System.out.println("1. ADD MEMBERS ");
            System.out.println("2. PAYMENTS ");
            System.out.println("3. VIEW ");
            System.out.println("4. EXIT");

            System.out.print("Enter Action: ");
            int action = sc.nextInt();
            sc.nextLine(); 
            
            switch(action) {
                case 1:
                    Member mem = new Member();
                    mem.mTransaction();
                    break;
                case 2:
                    payments pm = new payments();
                    pm.pTransaction();
                    break;
                case 3:
                   
                    ViewRecords vr = new ViewRecords();
                    vr.recordsMenu(); 
                    break;
                case 4:
                    System.out.print("Exit Selected... Type 'yes' to confirm: ");
                    String resp = sc.nextLine();
                    if (resp.equalsIgnoreCase("yes")) {
                        running = false; 
                    }
                    break;
                default:
                    System.out.println("Invalid selection. Please try again.");
                    break;
            }
        } while (running);

        sc.close();
        System.out.println("Exiting Gym Membership System. Goodbye!");
    }
}
