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

				Vendor_Login();
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
			System.out.println("====================");
			System.out.println(" 1. Register");
			System.out.println(" 2. Login");
			System.out.println(" 3. Back");
			System.out.println("====================");
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
					System.out.println("Login Successfully!.... Welcome To " + rs.getString("Name"));

					while (true) {
						System.out.println("\n------- User Dashboard -------");
						System.out.println(" 1. View Bill");
						System.out.println(" 2. Pay Bill ");
						System.out.println(" 3. View Payment History");
						System.out.println(" 4. View Consumption Details");
						System.out.println(" 5. Register Complaint");
						System.out.println(" 6. Update Profile/Address");
						System.out.println(" 7. Contact Vendor's");
						System.out.println(" 8. Apply For New Connection");
						System.out.println(" 9. Back");
						System.out.println("------------------------------");
						System.out.print("Enter your choice: ");
						int choice = sc.nextInt();
						if (choice == 1) {
							View_Bill();
						} else if (choice == 2) {
							Pay_Bill();
						} else if (choice == 5) {
							Register_Complain();
						} else if (choice == 6) {

							System.out.println("\n----------------- Update Profile -----------------");
							System.out.println("Current Profile:");
							System.out.println("Name: " + rs.getString("Name"));
							System.out.println("Phone: " + rs.getString("Phone"));
							System.out.println("Email: " + rs.getString("Email"));

							System.out.println("What do you want to update?");
							System.out.println("====================");
							System.out.println("1. Name");
							System.out.println("2. Phone");
							System.out.println("3. Email");
							System.out.println("4. Back");
							System.out.println("====================");
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

	public void View_Bill() {
		try {
			String url = "jdbc:mysql://localhost:3306/EBMS";
			String uname = "root";
			String password = "8252";
			Connection con = DriverManager.getConnection(url, uname, password);
			Scanner sc = new Scanner(System.in);

			System.out.print("\nEnter Consumer No.: ");
			String consumer = sc.nextLine();

			String q = "SELECT * FROM consumer WHERE Consumer_No=?";
			PreparedStatement psConsumer = con.prepareStatement(q);
			psConsumer.setString(1, consumer);
			ResultSet rs = psConsumer.executeQuery();

			if (rs.next()) {
				String consumerNo = rs.getString("Consumer_No");
				String consumerName = rs.getString("Full_Name");

				String lastBillQuery = "SELECT * FROM bill_generate WHERE Consumer_No=? ORDER BY Bill_Id DESC LIMIT 1";
				PreparedStatement psLast = con.prepareStatement(lastBillQuery);
				psLast.setString(1, consumerNo);
				ResultSet rsLast = psLast.executeQuery();

				if (rsLast.next()) {
					System.out.println("\n----------- Current Month Bill -----------");
					System.out.println("Consumer No.   : " + consumerNo);
					System.out.println("Consumer Name  : " + consumerName);
					System.out.println("Billing Month  : " + rsLast.getString("Month"));
					System.out.println("Current Reading: " + rsLast.getInt("Current_Reading"));
					System.out.println("Previous Reading : " +
							(rsLast.getInt("Current_Reading") - rsLast.getInt("Units_Consumed")));
					System.out.println("Units Consumed : " + rsLast.getInt("Units_Consumed"));
					System.out.println("Rate per Unit  : ₹" + rsLast.getDouble("Per_Unit_Price"));
					System.out.println("Fixed Charge   : ₹" + rsLast.getInt("Fixed_Charge"));
					System.out.println("------------------------------------");
					System.out.println("Bill Amount    : ₹" + rsLast.getDouble("Total_Amount"));
					System.out.println("Status         : " + rsLast.getString("Status"));
					System.out.println("Bill Date      : " + rsLast.getString("Date"));
					System.out.println("------------------------------------");
				} else {
					System.out.println("No bill found for this consumer!");
				}
			} else {
				System.out.println("Consumer not found!");
			}
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

			System.out.print("\nEnter Consumer No.: CN/SBPDCL/");
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
			System.out.println("\nComplain Registered Successfully!...");
			System.out.println("Complain ID: " + newComplainId);
			System.out.println("Status: Pending");
			System.out.println("Date: " + formattedDate);

			ps.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void Vendor_Login() {
		try {
			String url = "jdbc:mysql://localhost:3306/EBMS";
			String uname = "root";
			String password = "8252";
			Connection con = DriverManager.getConnection(url, uname, password);
			Scanner sc = new Scanner(System.in);
			System.out.print("\nEnter Vendor ID: ");
			String vendorID = sc.nextLine();
			System.out.print("Enter Email: ");
			String vEmail = sc.nextLine();
			System.out.print("Enter Phone: ");
			String phone = sc.nextLine();
			String q = "select * from vendor where vendor_Id='" + vendorID + "'And Email='" + vEmail + "'And Phone='"
					+ phone + "'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(q);

			if (rs.next()) {
				String vId = rs.getString("Vendor_Id");
				String vemail = rs.getString("Email");
				String vphone = rs.getString("Phone");
				if (vendorID.equals(vId) && vEmail.equals(vemail) && phone.equals(vphone)) {
					System.out.println("Login Successfully!.... Welcome " + rs.getString("Full_Name"));

					while (true) {
						System.out.println("\n----- Vendor Dashboard -----");
						System.out.println(" 1. Generate Bill");
						System.out.println(" 2. Pay Bill ");
						System.out.println(" 3. View My profile");
						System.out.println(" 4. View Complains");
						System.out.println(" 5. Back");
						System.out.println("----------------------------");
						System.out.print("Enter your choice: ");
						int choice = sc.nextInt();
						if (choice == 1) {

							Bill_Generate();

						} else if (choice == 2) {

							Pay_Bill();

						} else if (choice == 5) {
							return;
						} else {
							System.out.println("Invalid choice, please try again.");
						}
					}
				} else {
					System.out.println("Invalid Vendor ID and Password");
				}
			} else {
				System.out.println("Invalid Vendor ID and Password");
			}
			st.execute(q);
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void Bill_Generate() {
		try {
			String url = "jdbc:mysql://localhost:3306/EBMS";
			String uname = "root";
			String password = "8252";
			Connection con = DriverManager.getConnection(url, uname, password);
			Scanner sc = new Scanner(System.in);
			System.out.print("\nEnter Consumer No.: ");
			String consumer = sc.nextLine();
			String q = "select * from consumer where Consumer_No='" + consumer + "'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(q);

			if (rs.next()) {
				String consumerNo = rs.getString("Consumer_No");
				String connectionType = rs.getString("Connection_Type");

				System.out.println("Consumer Name: " + rs.getString("Full_Name"));
				System.out.print("Enter Current Reading: ");
				int currentReading = sc.nextInt();

				System.out.println("------- Month List -------");
				System.out.printf("%-15s %-15s%n", "1. January", "7. July");
				System.out.printf("%-15s %-15s%n", "2. February", "8. August");
				System.out.printf("%-15s %-15s%n", "3. March", "9. September");
				System.out.printf("%-15s %-15s%n", "4. April", "10. October");
				System.out.printf("%-15s %-15s%n", "5. May", "11. November");
				System.out.printf("%-15s %-15s%n", "6. June", "12. December");
				System.out.println("------------------------------");
				System.out.print("Select Month: ");
				int choice = sc.nextInt();

				String month = "";
				if (choice == 1) {
					month = "January";
				} else if (choice == 2) {
					month = "February";
				} else if (choice == 3) {
					month = "March";
				} else if (choice == 4) {
					month = "April";
				} else if (choice == 5) {
					month = "May";
				} else if (choice == 6) {
					month = "June";
				} else if (choice == 7) {
					month = "July";
				} else if (choice == 8) {
					month = "August";
				} else if (choice == 9) {
					month = "September";
				} else if (choice == 10) {
					month = "October";
				} else if (choice == 11) {
					month = "November";
				} else if (choice == 12) {
					month = "December";
				} else {
					System.out.println("Invalid option, try again!");
					return;
				}

				String rateQuery = "SELECT Unit_Price FROM unit WHERE Connection_Type=?";
				PreparedStatement psRate = con.prepareStatement(rateQuery);
				psRate.setString(1, connectionType);
				ResultSet rsRate = psRate.executeQuery();

				double ratePerUnit = 0;
				if (rsRate.next()) {
					ratePerUnit = rsRate.getDouble("Unit_Price");
				} else {
					System.out.println("Rate not found for connection type: " + connectionType);
					return;
				}

				String lastBillQuery = "SELECT Current_Reading, Status, Total_Amount FROM bill_generate WHERE Consumer_No=? ORDER BY Bill_Id DESC LIMIT 1";
				PreparedStatement psLast = con.prepareStatement(lastBillQuery);
				psLast.setString(1, consumerNo);
				ResultSet rsLast = psLast.executeQuery();

				int prevReading = 0;
				double unpaidAmount = 0;
				if (rsLast.next()) {
					prevReading = rsLast.getInt("Current_Reading");
					String lastStatus = rsLast.getString("Status");
					if ("Unpaid".equalsIgnoreCase(lastStatus)) {
						unpaidAmount = rsLast.getDouble("Total_Amount");
					}
				}

				int units = currentReading - prevReading;
				if (units < 0) {
					System.out.println("Error: Current reading is smaller than previous reading!");
					return;
				}

				int fixedCharge = 50;
				double totalAmount = (units * ratePerUnit) + fixedCharge;

				if (unpaidAmount > 0) {
					totalAmount = totalAmount + unpaidAmount;
				}

				String insert = "INSERT INTO bill_generate(Consumer_No, Month, Current_Reading, Units_Consumed, Per_Unit_Price, Fixed_Charge, Total_Amount, Date, Status) VALUES(?,?,?,?,?,?,?,?,?)";
				PreparedStatement ps = con.prepareStatement(insert);
				ps.setString(1, consumerNo);
				ps.setString(2, month);
				ps.setInt(3, currentReading);
				ps.setInt(4, units);
				ps.setDouble(5, ratePerUnit);
				ps.setInt(6, fixedCharge);
				ps.setDouble(7, totalAmount);

				java.util.Date utilDate = new java.util.Date();
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				String formattedDate = sdf.format(utilDate);
				ps.setString(8, formattedDate);
				ps.setString(9, "Unpaid");
				ps.executeUpdate();

				System.out.println("\n----------- Bill Receipt -----------");
				System.out.println("Consumer No.: " + consumerNo);
				System.out.println("Consumer Name: " + rs.getString("Full_Name"));
				System.out.println("Connection Type: " + connectionType);
				System.out.println("Billing Month: " + month);
				System.out.println("Current Reading: " + currentReading);
				System.out.println("Previous Reading: " + prevReading);
				System.out.println("Units Consumed: " + units);
				System.out.println("Rate per Unit: ₹" + ratePerUnit);
				System.out.println("Fixed Charge: ₹" + fixedCharge);
				if (unpaidAmount > 0) {
					System.out.println("Previous Unpaid Amount: ₹" + unpaidAmount);
				}
				System.out.println("------------------------------------");
				System.out.println("Bill Amount: ₹" + totalAmount);
				System.out.println("------------------------------------");
				System.out.println("Bill Generated Successfully!...");

			} else {
				System.out.println("Consumer Not Found");
			}

			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void Pay_Bill() {
		try {
			String url = "jdbc:mysql://localhost:3306/EBMS";
			String uname = "root";
			String password = "8252";
			Connection con = DriverManager.getConnection(url, uname, password);
			Scanner sc = new Scanner(System.in);

			System.out.print("\nEnter Consumer No.: ");
			String consumer = sc.nextLine();

			// Get Consumer Info
			String q = "SELECT * FROM consumer WHERE Consumer_No=?";
			PreparedStatement psConsumer = con.prepareStatement(q);
			psConsumer.setString(1, consumer);
			ResultSet rs = psConsumer.executeQuery();

			if (rs.next()) {
				String consumerName = rs.getString("Full_Name");

				// Get latest bill for this consumer
				String billQuery = "SELECT * FROM bill_generate WHERE Consumer_No=? ORDER BY Bill_Id DESC LIMIT 1";
				PreparedStatement psBill = con.prepareStatement(billQuery);
				psBill.setString(1, consumer);
				ResultSet rsBill = psBill.executeQuery();

				if (rsBill.next()) {
					int billId = rsBill.getInt("Bill_Id");
					String month = rsBill.getString("Month");
					double totalAmount = rsBill.getDouble("Total_Amount");
					String dueDate = rsBill.getString("Date");
					String status = rsBill.getString("Status");

					System.out.println("\n---------- Bill Payment ----------");
					System.out.println("Consumer No.: " + consumer);
					System.out.println("Name: " + consumerName);
					System.out.println("Month: " + month);
					System.out.println("Total Bill: ₹" + totalAmount);
					System.out.println("Due Date: " + dueDate);
					System.out.println("Status: " + status);
					if (status.equalsIgnoreCase("Paid")) {
						System.out.println("Bill already paid!");
						return;
					}
					System.out.println("Do you want to pay now? (Y/N): ");
					String choice = sc.nextLine();

					if (choice.equalsIgnoreCase("Y")) {
						System.out.println("Select Payment Method:");
						System.out.println("=========================");
						System.out.println("1. Cash");
						System.out.println("2. UPI");
						System.out.println("3. Debit/Credit Card");
						System.out.println("4. Net Banking");
						System.out.println("=========================");
						System.out.print("Select Method: ");
						int select = sc.nextInt();
						sc.nextLine();

						String paymentMethod = "";
						if (select == 1) {
							paymentMethod = "Cash";
						} else if (select == 2) {
							System.out.print("Enter UPI ID: ");
							String upi = sc.nextLine();
							paymentMethod = "UPI (" + upi + ")";
						} else if (select == 3) {
							System.out.print("Enter Card Number (last 4 digits): ");
							String cardNo = sc.nextLine();
							paymentMethod = "Card (XXXX-XXXX-XXXX-" + cardNo + ")";
						} else if (select == 4) {
							System.out.print("Enter Bank Name: ");
							String bank = sc.nextLine();
							paymentMethod = "NetBanking (" + bank + ")";
						} else {
							System.out.println("Invalid method!");
							return;
						}

						System.out.println("\nProcessing Payment...");
						System.out.println("Payment Successful!");

						String updateQuery = "UPDATE bill_generate SET Status='Paid' WHERE Bill_Id=?";
						PreparedStatement psUpdate = con.prepareStatement(updateQuery);
						psUpdate.setInt(1, billId);
						psUpdate.executeUpdate();

						java.util.Date utilDate = new java.util.Date();
						SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
						String formattedDate = sdf.format(utilDate);

						String insert = "INSERT INTO payment(Txn_Id, Consumer_No, Method, Amount, Date, Status) VALUES(?,?,?,?,?,?)";
						PreparedStatement ps = con.prepareStatement(insert);
						Random rand = new Random();
						int randomNum = 1000 + rand.nextInt(9000);
						String txnId = "TXN" + randomNum;

						ps.setString(1, txnId);
						ps.setString(2, consumer);
						ps.setString(3, paymentMethod);
						ps.setDouble(4, totalAmount);
						ps.setString(5, formattedDate);
						ps.setString(6, "Paid");
						ps.executeUpdate();

						System.out.println("\n------- Payment Receipt -------");
						System.out.println("Txn Id     : " + txnId);
						System.out.println("Consumer No: " + consumer);
						System.out.println("Name       : " + consumerName);
						System.out.println("Method     : " + paymentMethod);
						System.out.println("Amount Paid: ₹" + totalAmount);
						System.out.println("Date       : " + formattedDate);
						System.out.println("Status     : Paid");
						System.out.println("-------------------------------");

					} else {
						System.out.println("Payment Cancelled!");
					}
				} else {
					System.out.println("No bill found for this consumer!");
				}
			} else {
				System.out.println("Consumer Not Found");
			}

			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void Login_Admin() {
		try {
			Scanner sc = new Scanner(System.in);
			System.out.print("\nEnter Admin ID: ");
			String AdminID = sc.nextLine();
			System.out.print("Enter Admin Password: ");
			int APassword = sc.nextInt();
			if (AdminID.equals("2030") && APassword == 1234) {
				System.out.println("Login Successfully!...");

				while (true) {
					System.out.println("\n------- Admin Dashboard -------");
					System.out.println(" 1. Add New Consumer");
					System.out.println(" 2. View All Consumer ");
					System.out.println(" 3. Add New Vendor");
					System.out.println(" 4. View Complains");
					System.out.println(" 5. Update Unit Cost");
					System.out.println(" 6. View All Vendor's ");
					System.out.println(" 7. View Bill Status");
					System.out.println(" 8. Logout");
					System.out.println("-------------------------------");
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
					} else if (choice == 5) {
						Update_Unit_Cost();
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

			String q = "insert into consumer(Consumer_No, Full_Name, Father_Name, DOB, Age, Gender, Phone, Aadhar, Address, House_No, City, State, Pincode, Area, Connection_Type, Load_Cap, Date) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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

			System.out.println("Select Area: ");
			System.out.println("===================");
			System.out.println(" 1. Rular");
			System.out.println(" 2. Urban ");
			System.out.println("===================");
			System.out.print("Enter your choice: ");
			String area = "";
			int choice = sc.nextInt();
			if (choice == 1) {

				area = "Rular";
				ps.setString(14, area);

			} else if (choice == 2) {

				area = "Urban";
				ps.setString(14, area);

			} else {
				System.out.println("Invalid choice, please try again.");
			}

			System.out.println("-----------Connection Details------------");
			System.out.println("Connection Type: ");
			System.out.println("==========================");
			System.out.println(" 1. Domestic");
			System.out.println(" 2. Commercial ");
			System.out.println("==========================");
			System.out.print("Enter your choice: ");
			String connectionType = "";
			choice = sc.nextInt();
			if (choice == 1) {

				connectionType = "Domestic";
				ps.setString(15, connectionType);

			} else if (choice == 2) {

				connectionType = "Commercial";
				ps.setString(15, connectionType);

			} else {
				System.out.println("Invalid choice, please try again.");
			}

			System.out.print("Enter Load Capacity (in kW): ");
			String loadCap = br.readLine();
			ps.setString(16, loadCap);

			java.util.Date utilDate = new java.util.Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String formattedDate = sdf.format(utilDate);

			ps.setString(17, formattedDate);

			ps.executeUpdate();
			System.out.println("Consumer Added Successfully!......");
			System.out.println("\n------------------ Receipt ------------------");
			System.out.println("Consumer No.: " + newConsumerNo);
			System.out.println("Full Name: " + fullName);
			System.out.println("Father Name: " + fatherName);
			System.out.println("DOB: " + dob);
			System.out.println("Age: " + age);
			System.out.println("Phone: " + phone);
			System.out.println("Aadhar: " + aadhar);
			System.out.println("Area: " + area);
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
					"\n----------------------------------------------------------- Complains List -----------------------------------------------------------");
			System.out.printf("%-5s %-12s %-18s %-30s %-40s %-12s %-12s%n", "No.", "Complain Id", "Consumer No",
					"Complain Type",
					"Description", "Status", "Date");
			System.out.println(
					"--------------------------------------------------------------------------------------------------------------------------------------");

			while (rs.next()) {
				String complainId = rs.getString("Complain_Id");
				String consumerNo = rs.getString("Consumer_No");
				String complainType = rs.getString("Complain_Type");
				String description = rs.getString("Description");
				String status = rs.getString("Status");
				String date = rs.getString("Date");

				System.out.printf("%-5d %-12s %-18s %-30s %-40s %-12s %-12s%n", complainCount, complainId, consumerNo,
						complainType, description, status, date);

				complainCount++;
			}
			System.out.println(
					"--------------------------------------------------------------------------------------------------------------------------------------");

			if (complainCount == 1) {
				System.out.println("No complains found.");
			}

			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void Update_Unit_Cost() {
		try {
			String url = "jdbc:mysql://localhost:3306/EBMS";
			String uname = "root";
			String password = "8252";
			Connection con = DriverManager.getConnection(url, uname, password);
			Scanner sc = new Scanner(System.in);

			System.out.println("\n----------- Update Unit Cost -----------");
			System.out.println("1. Domestic");
			System.out.println("2. Commercial");
			System.out.println("3. Back");
			System.out.print("Select option: ");
			int choice = sc.nextInt();

			String connectionType = "";
			if (choice == 1) {
				connectionType = "Domestic";
			} else if (choice == 2) {
				connectionType = "Commercial";
			} else if (choice == 3) {
				return;
			} else {
				System.out.println("Invalid option, try again!");
				return;
			}

			String q = "SELECT Unit_Price FROM Unit WHERE Connection_Type = ?";
			PreparedStatement ps = con.prepareStatement(q);
			ps.setString(1, connectionType);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				double currentCost = rs.getDouble("Unit_Price");
				System.out.println("\nCurrent Unit Cost: ₹" + currentCost + " / kWh");

				System.out.print("Enter New Unit Cost (₹/kWh): ");
				double newCost = sc.nextDouble();

				String updateQuery = "UPDATE Unit SET Unit_Price = ? WHERE Connection_Type = ?";
				PreparedStatement psUpdate = con.prepareStatement(updateQuery);
				psUpdate.setDouble(1, newCost);
				psUpdate.setString(2, connectionType);
				psUpdate.executeUpdate();

				System.out.println("\nUnit Cost Updated Successfully!...");
				System.out.println("Updated Unit Cost: ₹" + newCost + " / kWh");

			} else {
				System.out.println("No record found for " + connectionType);
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