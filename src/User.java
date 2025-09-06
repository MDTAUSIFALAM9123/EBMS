package src;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.Scanner;

public class User {

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
                    System.out.println("Login Successfully!.... Welcome " + rs.getString("Name"));

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
                        System.out.println(" 9. Logout");
                        System.out.println("------------------------------");
                        System.out.print("Enter your choice: ");
                        int choice = sc.nextInt();
                        if (choice == 1) {
                            View_Bill();
                        } else if (choice == 2) {
                            Pay_Bill_User();
                        } else if (choice == 3) {
                            Payment_History();
                        } else if (choice == 4) {
                            View_Consumption();
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
                        } else if (choice == 7) {
                            Contact_Vendor();
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

    public void Pay_Bill_User() {
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
                String consumerName = rs.getString("Full_Name");

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
                        System.out.println("1. UPI");
                        System.out.println("2. Debit/Credit Card");
                        System.out.println("3. Net Banking");
                        System.out.println("=========================");
                        System.out.print("Select Method: ");
                        int select = sc.nextInt();
                        sc.nextLine();

                        String paymentMethod = "";
                        if (select == 1) {
                            System.out.print("Enter UPI ID: ");
                            String upi = sc.nextLine();
                            paymentMethod = "UPI (" + upi + ")";
                        } else if (select == 2) {
                            System.out.print("Enter Card Number (last 4 digits): ");
                            String cardNo = sc.nextLine();
                            paymentMethod = "Card (XXXX-XXXX-XXXX-" + cardNo + ")";
                        } else if (select == 3) {
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

                        String insert = "INSERT INTO payment(Txn_Id, Consumer_No, Method, Month, Amount, Date, Status) VALUES(?,?,?,?,?,?,?)";
                        PreparedStatement ps = con.prepareStatement(insert);
                        Random rand = new Random();
                        int randomNum = 1000 + rand.nextInt(9000);
                        String txnId = "TXN" + randomNum;

                        ps.setString(1, txnId);
                        ps.setString(2, consumer);
                        ps.setString(3, paymentMethod);
                        ps.setString(4, month);
                        ps.setDouble(5, totalAmount);
                        ps.setString(6, formattedDate);
                        ps.setString(7, "Paid");
                        ps.executeUpdate();

                        System.out.println("\n------- Payment Receipt -------");
                        System.out.println("Txn Id     : " + txnId);
                        System.out.println("Consumer No: " + consumer);
                        System.out.println("Name       : " + consumerName);
                        System.out.println("Method     : " + paymentMethod);
                        System.out.println("Month      : " + month);
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

    public void Payment_History() {
        try {
            String url = "jdbc:mysql://localhost:3306/EBMS";
            String uname = "root";
            String password = "8252";
            Connection con = DriverManager.getConnection(url, uname, password);
            Scanner sc = new Scanner(System.in);

            System.out.print("\nEnter Consumer No.: ");
            String consumer = sc.nextLine();

            String q = "SELECT * FROM payment WHERE Consumer_No=?";
            PreparedStatement ps = con.prepareStatement(q);
            ps.setString(1, consumer);
            ResultSet rs = ps.executeQuery();

            int paymentCount = 1;
            System.out.println("\n------------------------- Payment History -------------------------");
            System.out.printf("%-5s %-15s %-20s %-15s %-12s\n",
                    "No.", "TXN Id", "Consumer No", "Month", "Amount");
            System.out.println(
                    "-------------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-5d %-15s %-20s %-15s %-12s\n",
                        paymentCount,
                        rs.getString("Txn_Id"),
                        rs.getString("Consumer_No"),
                        rs.getString("Month"),
                        "₹" + rs.getString("Amount"));

                paymentCount++;
            }
            System.out.println("-------------------------------------------------------------------");

            if (paymentCount == 1) {
                System.out.println("Payments Not Found!");
            }

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void View_Consumption() {
        try {
            String url = "jdbc:mysql://localhost:3306/EBMS";
            String uname = "root";
            String password = "8252";
            Connection con = DriverManager.getConnection(url, uname, password);
            Scanner sc = new Scanner(System.in);

            System.out.print("\nEnter Consumer No.: ");
            String consumer = sc.nextLine();

            String q = "SELECT * FROM bill_generate WHERE Consumer_No=?";
            PreparedStatement ps = con.prepareStatement(q);
            ps.setString(1, consumer);
            ResultSet rs = ps.executeQuery();

            int consumption = 1;
            System.out.println("\n------------------------------- Consumption History -------------------------------");
            System.out.printf("%-5s %-15s %-15s %-18s %-15s %-12s\n",
                    "No.", "Month", "Reading", "Consumption Unit", "Unit Rate", "Amount");
            System.out.println(
                    "-----------------------------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-5d %-15s %-15s %-18s %-15s %-12s\n",
                        consumption,
                        rs.getString("Month"),
                        rs.getString("Current_Reading"),
                        rs.getString("Units_Consumed"),
                        rs.getString("Per_Unit_Price"),
                        "₹" + rs.getString("Total_Amount"));

                consumption++;
            }
            System.out.println("-----------------------------------------------------------------------------------");

            if (consumption == 1) {
                System.out.println("Payments Not Found!");
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

    public void Contact_Vendor() {
        try {
            String url = "jdbc:mysql://localhost:3306/EBMS";
            String uname = "root";
            String password = "8252";
            Connection con = DriverManager.getConnection(url, uname, password);
            String q = "select * from vendor";
            PreparedStatement ps = con.prepareStatement(q);
            ResultSet rs = ps.executeQuery();

            int vendorCount = 1;
            System.out.println(
                    "\n--------------------------------------------- Vendor Contact List ---------------------------------------------");
            System.out.printf("%-5s %-15s %-18s %-15s %-25s %-40s%n",
                    "No.", "Vendor Id", "Name", "Phone", "Email", "Address");
            System.out.println(
                    "---------------------------------------------------------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-5d %-15s %-18s %-15s %-25s %-40s%n",
                        vendorCount,
                        rs.getString("vendor_Id"),
                        rs.getString("Full_Name"),
                        rs.getString("Phone"),
                        rs.getString("Email"),
                        rs.getString("Address")
                                + ", " + rs.getString("State")
                                + "(" + rs.getString("Pincode") + ")");
                vendorCount++;
            }

            System.out.println(
                    "---------------------------------------------------------------------------------------------------------------");

            if (vendorCount == 1) {
                System.out.println("No consumer found.");
            }

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
