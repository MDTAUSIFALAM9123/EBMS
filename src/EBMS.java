package src;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Random;
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
			System.out.println("=======================");
			System.out.println(" 1. User");
			System.out.println(" 2. Vendor");
			System.out.println(" 3. Admin");
			System.out.println(" 4. Exit");
			System.out.println("=======================");
			System.out.print("Enter your choice: ");
			int choice = sc.nextInt();
			if (choice == 1) {

				User();
			} else if (choice == 2) {

			} else if (choice == 3) {

				Login_Admin();
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
				return;

			} else {
				System.out.println("Invalid choice, please try again.");
			}
		}

	}

	public void User_Register() {
		try {
			String url = "jdbc:mysql://localhost:3306/EBMS";
			String uname = "root";
			String password = "8252";
			Scanner sc = new Scanner(System.in);
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

			System.out.print("Enter Phone: ");
			long Phone = Long.valueOf(br.readLine());
			ps.setLong(4, Phone);

			System.out.print("Enter Consumer No.: CN/SBPDCL/");
			String numPart = br.readLine();
			String consumerNo = "CN/SBPDCL/" + numPart;
			ps.setString(5, consumerNo);

			int random = 0, i, cc, sum = 0;
			Random rand = new Random();

			System.out.print("Captcha Code: ");
			for (i = 0; i < 3; i++) {
				random = rand.nextInt(10);
				System.out.print(random);
				if (i < 2) {
					System.out.print(" + ");
				}
				sum = sum + random;
			}

			System.out.println("");
			System.out.print("Enter the Captcha Code (Sum of Numbers) = ");
			cc = sc.nextInt();

			if (sum == cc) {
				ps.executeUpdate();
				System.out.println("Captcha Matched");
				System.out.println("Register Successfully!...");
			} else {
				System.out.println("Captcha Code Not Matched");
				System.out.println("Registration Failed. Please Try Again!...");
			}

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
			String q = "select * from register_user where Email='" + UserID + "'And Password='" + UPassword + "'";
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
						System.out.println(" 4. View Consumption Details");
						System.out.println(" 5. Register Complaint");
						System.out.println(" 6. Update Profile/Address");
						System.out.println(" 7. Contact Vendor's");
						System.out.println(" 8. Apply For New Connection");
						System.out.println(" 9. Back");
						System.out.println("==========================");
						System.out.print("Enter your choice: ");
						int choice = sc.nextInt();
						if (choice == 1) {

						} else if (choice == 2) {

						} else if (choice == 5) {

							Register_Complain();

						} else if (choice == 6) {

							System.out.println("\n----------------- Update Profile -----------------");
							System.out.println("Current Profile:");
							System.out.println("Name: " + rs.getString("Name"));
							System.out.println("Phone: " + rs.getString("Phone"));
							System.out.println("Email: " + rs.getString("Email"));

							System.out.println("What do you want to update?");
							System.out.println("==========================");
							System.out.println("1. Name");
							System.out.println("2. Phone");
							System.out.println("3. Email");
							System.out.println("4. Back");
							System.out.println("==========================");
							System.out.print("Enter your choice: ");
							int opt = sc.nextInt();
							sc.nextLine();

							if (opt == 1) {
								System.out.print("Enter New Name: ");
								String newName = sc.nextLine();
								String update = "UPDATE register_user SET Name=? WHERE Email=?";
								PreparedStatement ps = con.prepareStatement(update);
								ps.setString(1, newName);
								ps.setString(2, UserID);
								ps.executeUpdate();
								System.out.println("Profile Updated Successfully!....");

							} else if (opt == 2) {
								System.out.print("Enter New Phone Number: ");
								String newPhone = sc.nextLine();
								String update = "UPDATE register_user SET Phone=? WHERE Email=?";
								PreparedStatement ps = con.prepareStatement(update);
								ps.setString(1, newPhone);
								ps.setString(2, UserID);
								ps.executeUpdate();
								System.out.println("Profile Updated Successfully!...");

							} else if (opt == 3) {
								System.out.print("Enter New Email: ");
								String newEmail = sc.nextLine();
								String update = "UPDATE register_user SET Email=? WHERE Email=?";
								PreparedStatement ps = con.prepareStatement(update);
								ps.setString(1, newEmail);
								ps.setString(2, UserID);
								ps.executeUpdate();
								UserID = newEmail;
								System.out.println("Profile Updated Successfully!...");

							} else if (opt == 4) {
								return;
							} else {
								System.out.println("Invalid option, try again!");
							}

							String refreshQuery = "SELECT * FROM register_user WHERE Email=?";
							PreparedStatement ps2 = con.prepareStatement(refreshQuery);
							ps2.setString(1, UserID);
							ResultSet rs2 = ps2.executeQuery();
							if (rs2.next()) {
								System.out.println("Updated Profile:");
								System.out.println("Name: " + rs2.getString("Name"));
								System.out.println("Phone: " + rs2.getString("Phone"));
								System.out.println("Email: " + rs2.getString("Email"));
							}

						} else if (choice == 9) {
							return;
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

	public void Register_Complain() {
		try {
			String url = "jdbc:mysql://localhost:3306/EBMS";
			String uname = "root";
			String password = "8252";
			Connection con = DriverManager.getConnection(url, uname, password);
			String getLastId = "SELECT Complain_Id FROM complains ORDER BY Complain_Id DESC LIMIT 1";
			Scanner sc = new Scanner(System.in);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(getLastId);

			String newComplainId = "CMP001";
			if (rs.next()) {
				String lastId = rs.getString("Complain_Id");
				int num = Integer.parseInt(lastId.substring(3));
				num++;
				newComplainId = String.format("CMP%03d", num);
			}

			String q = "INSERT INTO complains(Complain_Id, Consumer_No, Complain_Type, Description, Status, Date) VALUES (?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(q);
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

			ps.setString(1, newComplainId);

			System.out.print("Enter Consumer No.: CN/SBPDCL/");
			String numPart = br.readLine();
			String consumerNo = "CN/SBPDCL/" + numPart;
			ps.setString(2, consumerNo);

			System.out.println("Complain Type: ");
			System.out.println("==========================");
			System.out.println(" 1. Meter Issue");
			System.out.println(" 2. Wrong Bill");
			System.out.println(" 3. Power Supply Issue");
			System.out.println(" 4. High Bill");
			System.out.println(" 5. Other");
			System.out.println("==========================");
			System.out.print("Enter your choice: ");
			String complainType = "";
			int choice = sc.nextInt();
			if (choice == 1) {

				complainType = "Meter Issue";
				ps.setString(3, complainType);

			} else if (choice == 2) {

				complainType = "Wrong Bill";
				ps.setString(3, complainType);

			} else if (choice == 3) {

				complainType = "Power Supply Issue";
				ps.setString(3, complainType);

			} else if (choice == 4) {

				complainType = "High Bill";
				ps.setString(3, complainType);

			} else if (choice == 5) {
				System.out.print("Enter Your Problem: ");
				complainType = br.readLine();
				ps.setString(3, complainType);

			} else {
				System.out.println("Invalid choice, please try again.");
			}

			System.out.print("Enter Description: ");
			String desc = br.readLine();
			ps.setString(4, desc);

			ps.setString(5, "Pending");

			java.util.Date utilDate = new java.util.Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String formattedDate = sdf.format(utilDate);
			ps.setString(6, formattedDate);

			ps.executeUpdate();
			System.out.println("Complain Registered Successfully!...");
			System.out.println("Complain ID: " + newComplainId);
			System.out.println("Status: Pending");
			System.out.println("Date: " + formattedDate);

			ps.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void Login_Admin() {
		try {
			Scanner sc = new Scanner(System.in);
			System.out.print("\nEnter User ID: ");
			String AdminID = sc.nextLine();
			System.out.print("Enter User Password: ");
			int APassword = sc.nextInt();
			if (AdminID.equals("2030") && APassword == 1234) {
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
					System.out.println(" 8. Logout");
					System.out.println("==========================");
					System.out.print("Enter your choice: ");
					int choice = sc.nextInt();
					if (choice == 1) {

						Add_Consumer();

					} else if (choice == 2) {

						View_All_Consumer();

					} else if (choice == 3) {

						Add_Vendor();

					} else if (choice == 4) {

						View_complains();

					} else if (choice == 6) {

						View_All_Vendor();

					} else if (choice == 8) {

						System.out.println("Logout Successfully!...\n");
						return;

					} else {
						System.out.println("Invalid choice, please try again.");
					}
				}
			} else {
				System.out.println("Invalid User ID and Password");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void Add_Consumer() {
		try {
			String url = "jdbc:mysql://localhost:3306/EBMS";
			String uname = "root";
			String password = "8252";
			Connection con = DriverManager.getConnection(url, uname, password);
			Scanner sc = new Scanner(System.in);
			// Get last Consumer_No
			String getLastId = "SELECT Consumer_No FROM consumer ORDER BY Consumer_No DESC LIMIT 1";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(getLastId);

			String newConsumerNo = "CN/SBPDCL/0001";
			if (rs.next()) {
				String lastId = rs.getString("Consumer_No");
				String[] parts = lastId.split("/");
				int num = Integer.parseInt(parts[2]);
				num++;
				newConsumerNo = String.format("CN/SBPDCL/%04d", num);
			}

			String q = "insert into consumer(Consumer_No, Full_Name, Father_Name, DOB, Age, Gender, Phone, Aadhar, Address, House_No, City, State, Pincode, Connection_Type, Load_Cap, Date) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(q);
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

			System.out.println("\n-----------Personal Details------------");

			ps.setString(1, newConsumerNo);

			System.out.print("Enter Full Name: ");
			String fullName = br.readLine();
			ps.setString(2, fullName);

			System.out.print("Enter Father Name: ");
			String fatherName = br.readLine();
			ps.setString(3, fatherName);

			System.out.print("Enter DOB (dd-mm-yyyy): ");
			String dob = br.readLine();
			ps.setString(4, dob);

			System.out.print("Enter Age: ");
			int age = Integer.parseInt(br.readLine());
			if (age < 18) {
				System.out.println("Age must be 18 or older to register.");
				return;
			}
			ps.setInt(5, age);

			System.out.print("Enter Gender: ");
			String gender = br.readLine();
			ps.setString(6, gender);

			System.out.print("Enter Phone: ");
			long phone = Long.valueOf(br.readLine());
			ps.setLong(7, phone);

			System.out.print("Enter Aadhar: ");
			long aadhar = Long.valueOf(br.readLine());
			ps.setLong(8, aadhar);

			System.out.println("-----------Address Details------------");
			System.out.print("Enter Address: ");
			String address = br.readLine();
			ps.setString(9, address);

			System.out.print("Enter House No.: ");
			int houseNo = Integer.parseInt(br.readLine());
			ps.setInt(10, houseNo);

			System.out.print("Enter City: ");
			String city = br.readLine();
			ps.setString(11, city);

			System.out.print("Enter State: ");
			String state = br.readLine();
			ps.setString(12, state);

			System.out.print("Enter Pincode: ");
			int pincode = Integer.parseInt(br.readLine());
			ps.setInt(13, pincode);

			System.out.println("-----------Connection Details------------");
			System.out.println("Connection Type: ");
			System.out.println("==========================");
			System.out.println(" 1. Domestic");
			System.out.println(" 2. Commercial ");
			System.out.println("==========================");
			System.out.print("Enter your choice: ");
			String connectionType = "";
			int choice = sc.nextInt();
			if (choice == 1) {

				connectionType = "Domestic";
				ps.setString(14, connectionType);

			} else if (choice == 2) {

				connectionType = "Commercial";
				ps.setString(14, connectionType);

			} else {
				System.out.println("Invalid choice, please try again.");
			}

			System.out.print("Enter Load Capacity (in kW): ");
			String loadCap = br.readLine();
			ps.setString(15, loadCap);

			java.util.Date utilDate = new java.util.Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String formattedDate = sdf.format(utilDate);

			ps.setString(16, formattedDate);

			ps.executeUpdate();
			System.out.println("Consumer Added Successfully!......");
			System.out.println("-------------------Receipt-------------------");
			System.out.println("Consumer No.: " + newConsumerNo);
			System.out.println("Full Name: " + fullName);
			System.out.println("Father Name: " + fatherName);
			System.out.println("DOB: " + dob);
			System.out.println("Age: " + age);
			System.out.println("Phone: " + phone);
			System.out.println("Aadhar: " + aadhar);
			System.out.println("Connection Type: " + connectionType);
			System.out.println("Load: " + loadCap);
			System.out.println("Address: " + address + "," + city + "," + state + "(" + pincode + ")");
			System.out.println("---------------------------------------------");
			ps.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void View_All_Consumer() {
		try {
			String url = "jdbc:mysql://localhost:3306/EBMS";
			String uname = "root";
			String password = "8252";
			Connection con = DriverManager.getConnection(url, uname, password);
			String q = "select * from consumer";
			PreparedStatement ps = con.prepareStatement(q);
			ResultSet rs = ps.executeQuery();

			int consumerCount = 1;
			System.out.println(
					"\n----------------------------------------------------------- Consumer List -----------------------------------------------------------");
			System.out.printf("%-5s %-20s %-20s %-20s %-12s %-15s %-40s%n",
					"No.", "Consumer No", "Full Name", "Father's Name", "DOB", "Phone", "Address");
			System.out.println(
					"-------------------------------------------------------------------------------------------------------------------------------------");

			while (rs.next()) {

				System.out.printf("%-5d %-20s %-20s %-20s %-12s %-15s %-40s%n",
						consumerCount,
						rs.getString("Consumer_No"),
						rs.getString("Father_Name"),
						rs.getString("Father_Name"),
						rs.getString("DOB"),
						rs.getString("Phone"),
						rs.getString("Address")
								+ "," + rs.getString("City")
								+ "," + rs.getString("State")
								+ "(" + rs.getString("Pincode") + ")");

				consumerCount++;
			}
			System.out.println(
					"-------------------------------------------------------------------------------------------------------------------------------------");

			if (consumerCount == 1) {
				System.out.println("No consumer found.");
			}

			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void Add_Vendor() {
		try {
			String url = "jdbc:mysql://localhost:3306/EBMS";
			String uname = "root";
			String password = "8252";
			Connection con = DriverManager.getConnection(url, uname, password);
			Scanner sc = new Scanner(System.in);
			String q = "insert into vendor(Vendor_Id, Full_Name, Father_Name, DOB, Age, Gender, Phone, Aadhar, Email, Qualification, Skills, Certificates, Service_Type, Experince, Address, House_No, City, State, Pincode) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(q);
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

			System.out.println("\n-----------Personal Details------------");

			System.out.print("Enter Vendor Id: ");
			String vendorId = br.readLine();
			ps.setString(1, vendorId);

			System.out.print("Enter Full Name: ");
			String fullName = br.readLine();
			ps.setString(2, fullName);

			System.out.print("Enter Father Name: ");
			String fatherName = br.readLine();
			ps.setString(3, fatherName);

			System.out.print("Enter DOB (dd-mm-yyyy): ");
			String dob = br.readLine();
			ps.setString(4, dob);

			System.out.print("Enter Age: ");
			int age = Integer.parseInt(br.readLine());
			if (age < 18) {
				System.out.println("Age must be 18 or older to register.");
				return;
			}
			ps.setInt(5, age);

			System.out.print("Enter Gender: ");
			String gender = br.readLine();
			ps.setString(6, gender);

			System.out.print("Enter Phone: ");
			long phone = Long.valueOf(br.readLine());
			ps.setLong(7, phone);

			System.out.print("Enter Aadhar: ");
			long aadhar = Long.valueOf(br.readLine());
			ps.setLong(8, aadhar);

			System.out.print("Enter Email: ");
			String email = br.readLine();
			ps.setString(9, email);

			System.out.print("Enter Qualification: ");
			String qualification = br.readLine();
			ps.setString(10, qualification);

			System.out.print("Enter Skills: ");
			String skills = br.readLine();
			ps.setString(11, skills);

			System.out.print("Enter Certificates: ");
			String certificates = br.readLine();
			ps.setString(12, certificates);

			System.out.println("-----------Connection Details------------");
			System.out.println("Service Type: ");
			System.out.println("==========================");
			System.out.println(" 1. Meter Reading");
			System.out.println(" 2. Meter Installation ");
			System.out.println(" 3. Maintenance Work ");
			System.out.println("==========================");
			System.out.print("Enter your choice: ");
			String serviceType = "";
			int choice = sc.nextInt();
			if (choice == 1) {

				serviceType = "Meter Reading";
				ps.setString(13, serviceType);

			} else if (choice == 2) {

				serviceType = "Meter Installation";
				ps.setString(13, serviceType);

			} else if (choice == 3) {

				serviceType = "Maintenance Work";
				ps.setString(13, serviceType);

			} else {
				System.out.println("Invalid choice, please try again.");
			}

			System.out.print("Enter Experinces: ");
			String experinces = br.readLine();
			ps.setString(14, experinces);

			System.out.println("-----------Address Details------------");
			System.out.print("Enter Address: ");
			String address = br.readLine();
			ps.setString(15, address);

			System.out.print("Enter House No.: ");
			int houseNo = Integer.parseInt(br.readLine());
			ps.setInt(16, houseNo);

			System.out.print("Enter City: ");
			String city = br.readLine();
			ps.setString(17, city);

			System.out.print("Enter State: ");
			String state = br.readLine();
			ps.setString(18, state);

			System.out.print("Enter Pincode: ");
			int pincode = Integer.parseInt(br.readLine());
			ps.setInt(19, pincode);

			ps.executeUpdate();
			System.out.println("Vendor Added Successfully!......");
			System.out.println("-------------------Receipt-------------------");
			System.out.println("Vendor Id: " + vendorId);
			System.out.println("Full Name: " + fullName);
			System.out.println("Father Name: " + fatherName);
			System.out.println("DOB: " + dob);
			System.out.println("Age: " + age);
			System.out.println("Phone: " + phone);
			System.out.println("Aadhar: " + aadhar);
			System.out.println("Service Type: " + serviceType);
			System.out.println("Email: " + email);
			System.out.println("Address: " + address + "," + city + "," + state + "(" + pincode + ")");
			System.out.println("---------------------------------------------");
			ps.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void View_complains() {
		try {
			String url = "jdbc:mysql://localhost:3306/EBMS";
			String uname = "root";
			String password = "8252";
			Connection con = DriverManager.getConnection(url, uname, password);
			String q = "select * from complains";
			PreparedStatement ps = con.prepareStatement(q);
			ResultSet rs = ps.executeQuery();

			int complainCount = 1;
			System.out.println(
					"\n--------------------------------------------------------- Complains List ---------------------------------------------------------");
			System.out.printf("%-5s %-12s %-18s %-25s %-40s %-12s %-12s%n", "No.", "ComplainId", "ConsumerNo", "Type",
					"Description", "Status", "Date");
			System.out.println(
					"----------------------------------------------------------------------------------------------------------------------------------");

			while (rs.next()) {
				String complainId = rs.getString("Complain_Id");
				String consumerNo = rs.getString("Consumer_No");
				String complainType = rs.getString("Complain_Type");
				String description = rs.getString("Description");
				String status = rs.getString("Status");
				String date = rs.getString("Date");

				System.out.printf("%-5d %-12s %-18s %-25s %-40s %-12s %-12s%n", complainCount, complainId, consumerNo,
						complainType, description, status, date);

				complainCount++;
			}
			System.out.println(
					"-----------------------------------------------------------------------------------------------------------------------------------\n");

			if (complainCount == 1) {
				System.out.println("No complains found.");
			}

			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void View_All_Vendor() {
		try {
			String url = "jdbc:mysql://localhost:3306/EBMS";
			String uname = "root";
			String password = "8252";
			Connection con = DriverManager.getConnection(url, uname, password);
			String q = "select * from vendor";
			PreparedStatement ps = con.prepareStatement(q);
			ResultSet rs = ps.executeQuery();

			System.out.println(
					"\n----------------------------------------------------------------------------- Vendor's List -----------------------------------------------------------------------------");
			System.out.printf("%-5s %-10s %-20s %-20s %-22s %-15s %-15s %-25s %-40s%n",
					"No.", "Vendor_ID", "Full Name", "Father Name", "Service", "Experience", "Phone", "Email",
					"Address");
			System.out.println(
					"--------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

			int vendorCount = 1;
			while (rs.next()) {
				System.out.printf("%-5d %-10s %-20s %-20s %-22s %-15s %-15s %-25s %-45s%n",
						vendorCount,
						rs.getString("vendor_Id"),
						rs.getString("Full_Name"),
						rs.getString("Father_Name"),
						rs.getString("Service_Type"),
						rs.getString("Experince"),
						rs.getString("Phone"),
						rs.getString("Email"),
						rs.getString("Address") + "," + rs.getString("City"));
				vendorCount++;
			}
			System.out.println(
					"--------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

			if (vendorCount == 1) {
				System.out.println("No vendor found.");
			}

			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}