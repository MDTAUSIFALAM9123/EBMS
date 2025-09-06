package src;

import java.sql.DriverManager;
import java.util.Scanner;
import java.sql.Connection;

public class Master {
	Admin a1 = new Admin();
	User u1 = new User();
	Vendor v1 = new Vendor();

	public void Connection() {
		try {
			String url = "jdbc:mysql://localhost:3306/";
			String uname = "root";
			String password = "8252";
			Connection con = DriverManager.getConnection(url, uname, password);
			System.out.println("Data Base connected");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void showMenu() {
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("\n--------------- Welcome to Electricity Billing Management System ------------------");
			System.out.println("====================");
			System.out.println(" 1. User");
			System.out.println(" 2. Vendor");
			System.out.println(" 3. Admin");
			System.out.println(" 4. Exit");
			System.out.println("====================");
			System.out.print("Enter your choice: ");
			int choice = sc.nextInt();
			if (choice == 1) {
				User();
			} else if (choice == 2) {
				v1.Vendor_Login();
			} else if (choice == 3) {
				a1.Login_Admin();
			} else if (choice == 4) {
				System.out.println("Exiting...");
				break;
			} else {
				System.out.println("Invalid choice, please try again.");
			}
		}

	}

	public void User() {
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("====================");
			System.out.println(" 1. Register");
			System.out.println(" 2. Login");
			System.out.println(" 3. Back");
			System.out.println("====================");
			System.out.print("Enter your choice: ");
			int choice = sc.nextInt();
			if (choice == 1) {
				u1.User_Register();
			} else if (choice == 2) {
				u1.User_Login();
			} else if (choice == 3) {
				return;
			} else {
				System.out.println("Invalid choice, please try again.");
			}
		}

	}

}