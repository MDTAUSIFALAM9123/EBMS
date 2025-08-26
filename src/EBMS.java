package src;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;

public class EBMS {
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
		System.out.println("  ---------------Welcome to Electricity Billing Management System------------------ \r\n"
				+ "");

		while (true) {
			System.out.println("==========================");
			System.out.println(" 1. User");
			System.out.println(" 2. Vendor");
			System.out.println(" 3. Admin");
			System.out.println(" 4. Exit");
			System.out.println("==========================");
			System.out.print("Enter your choice: ");
			int choice = sc.nextInt();
			if (choice == 1) {

				User();
			} else if (choice == 2) {

			} else if (choice == 3) {

				Login_Admin();
			} else if (choice == 4) {
				System.out.println("Exiting...");
				sc.close();
				System.exit(0);
			} else {

				System.out.println("Invalid choice, please try again.");
			}
		}

	}

	private void User() {
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("==========================");
			System.out.println(" 1. Register");
			System.out.println(" 2. Login");
			System.out.println(" 3. Back");
			System.out.println("==========================");
			System.out.print("Enter your choice: ");
			int choice = sc.nextInt();
			if (choice == 1) {

				User_Register();
			} else if (choice == 2) {
				User_Login();

			} else if (choice == 3) {
				System.out.println("Exiting...");
				sc.close();
				System.exit(0);
			} else {

				System.out.println("Invalid choice, please try again.");
			}
		}

	}

	private void User_Register() {
		try {
			String url = "jdbc:mysql://localhost:3306/EBMS";
			String uname = "root";
			String password = "8252";
			Connection con = DriverManager.getConnection(url, uname, password);
			String q = "insert into register_user(Name, Email, Password, Phone, Consumer_No) values(?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(q);
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

			System.out.print("\nEnter Name: ");
			String Name = br.readLine();
			ps.setString(1, Name);

			System.out.print("Enter Email ID: ");
			String Email_Id = br.readLine();
			ps.setString(2, Email_Id);

			System.out.print("Enter Password: ");
			String Password = br.readLine();
			ps.setString(3, Password);

			System.out.print("Enter Consumer No.: ");
			int Consumer = Integer.parseInt(br.readLine());
			ps.setInt(4, Consumer);

			System.out.print("Enter Phone: ");
			long Phone = Long.valueOf(br.readLine());
			ps.setLong(5, Phone);

			System.out.println("Register Successfully!...");
			ps.execute();
			ps.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void User_Login() {
		try {
			String url = "jdbc:mysql://localhost:3306/EBMS";
			String uname = "root";
			String password = "8252";
			Connection con = DriverManager.getConnection(url, uname, password);
			Scanner sc = new Scanner(System.in);
			System.out.print("\nEnter User ID: ");
			String UserID = sc.nextLine();
			System.out.print("Enter User Password: ");
			String UPassword = sc.nextLine();
			String q = "select *from register_user where Email='" + UserID + "'And Password='" + UPassword + "'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(q);

			if (rs.next()) {
				String uemail = rs.getString("Email");
				String upass = rs.getString("Password");
				if (UserID.equals(uemail) && UPassword.equals(upass)) {
					System.out.println("Login Successfully!....");

					while (true) {
						System.out.println("==========================");
						System.out.println(" 1. View Bill");
						System.out.println(" 2. Pay Bill ");
						System.out.println(" 3. View Payment History");
						System.out.println(" 4. View Consumer Details\r");
						System.out.println(" 5. Register Complaint");
						System.out.println(" 6. Update Profile/Address");
						System.out.println(" 7. Contact Vendor's ");
						System.out.println(" 8. View Consumer Details\r");
						System.out.println(" 9. Back");
						System.out.println("==========================");
						System.out.print("Enter your choice: ");
						int choice = sc.nextInt();
						if (choice == 1) {

						} else if (choice == 2) {

						} else if (choice == 3) {

						} else if (choice == 4) {
							System.out.println("Exiting...");
							sc.close();
							System.exit(0);
						} else {

							System.out.println("Invalid choice, please try again.");
						}
					}
				} else {
					System.out.println("Invalid User ID and Password");
				}
			} else {
				System.out.println("Invalid User ID and Password");
			}
			st.execute(q);
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void Login_Admin() {

		try {
			String url = "jdbc:mysql://localhost:3306/Assets_MS";
			String uname = "root";
			String password = "8252";
			Connection con = DriverManager.getConnection(url, uname, password);
			Scanner sc = new Scanner(System.in);
			System.out.print("\nEnter User ID: ");
			String AdminID = sc.nextLine();
			System.out.print("Enter User Password: ");
			int APassword = sc.nextInt();
			if (AdminID.equals("203040") && APassword == 12345) {
				System.out.println("Login Successfully!..");

				while (true) {
					System.out.println("==========================");
					System.out.println(" 1. Add New Consumer");
					System.out.println(" 2. View All Consumer ");
					System.out.println(" 3. Add New Vendor");
					System.out.println(" 4. View Complains");
					System.out.println(" 5. Update Unit Cost");
					System.out.println(" 6. View vendor's ");
					System.out.println(" 7. View Bill Status");
					System.out.println(" 8. Back");
					System.out.println("==========================");
					System.out.print("Enter your choice: ");
					int choice = sc.nextInt();
					if (choice == 1) {

					} else if (choice == 2) {

					} else if (choice == 3) {

					} else if (choice == 4) {
						System.out.println("Exiting...");
						sc.close();
						System.exit(0);
					} else {

						System.out.println("Invalid choice, please try again.");
					}
				}
			} else {
				System.out.println("Invalid User ID and Password");
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
