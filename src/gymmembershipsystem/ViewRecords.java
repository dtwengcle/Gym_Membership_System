package gymmembershipsystem;

import java.util.Scanner;

public class ViewRecords {

    public config con; // Declare the config instance

    // Constructor to initialize the config instance
    public ViewRecords() {
        con = new config(); // Initialize the config instance
    }

    // Records menu to allow user to choose what to view
    public void recordsMenu() {
        Scanner sc = new Scanner(System.in);
        String response;

        do {
            System.out.println("\n--------------------------------");
            System.out.println("| Welcome to the Records Panel |");
            System.out.println("--------------------------------");
            System.out.println("1. View Specific Records");
            System.out.println("2. View General Payments Records");
            System.out.println("3. Exit");

            System.out.print("Enter Selection: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    ViewSpecificRecords(); // Call method for specific records
                    break;
                case 2:
                    ViewGeneralRecords(); // Call method for general payments records
                    break;
                case 3:
                    System.out.println("Exiting Records Panel...");
                    return; // Exit the menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            // Ask user if they want to continue
            System.out.print("Do you want to continue? (Yes/No): ");
            response = sc.next();
        } while (response.equalsIgnoreCase("Yes"));
    }

    // Method to view specific records by member ID
    public void ViewSpecificRecords() {
        Scanner sc = new Scanner(System.in);

        // Ask user for the Member ID they want to search
        System.out.print("Enter Member ID: ");
        int memberId = sc.nextInt();

        // Call the Member class's method to view specific member details
        Member member = new Member();
        member.viewMemberDetails(memberId);  // Call the method in Member class to display the details
    }

    // Method to view general records from the payments table
    public void ViewGeneralRecords() {
        // Instantiate the payments class
        payments pay = new payments();

        // Directly call the viewPayments method from the payments class to view the payment records
        pay.viewPayments();  // This will display the payment records inside a table format
    }
}
