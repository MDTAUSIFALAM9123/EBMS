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

public class Admin {

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
                    } else if (choice == 7) {
                        Pay_Bill_Vendor();
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
                    "\n------------------------------------------------------------ Consumer List ------------------------------------------------------------");
            System.out.printf("%-5s %-20s %-20s %-20s %-12s %-15s %-40s%n",
                    "No.", "Consumer No", "Full Name", "Father's Name", "DOB", "Phone", "Address");
            System.out.println(
                    "---------------------------------------------------------------------------------------------------------------------------------------");

            while (rs.next()) {

                System.out.printf("%-5d %-20s %-20s %-20s %-12s %-15s %-40s%n",
                        consumerCount,
                        rs.getString("Consumer_No"),
                        rs.getString("Full_Name"),
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
                    "---------------------------------------------------------------------------------------------------------------------------------------");

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

            int vendorCount = 1;
            System.out.println("\n--------------------- Vendor's List ---------------------");

            while (rs.next()) {
                System.out.println("\nNo.           : " + vendorCount);
                System.out.println("Vendor ID     : " + rs.getString("vendor_Id"));
                System.out.println("Full Name     : " + rs.getString("Full_Name"));
                System.out.println("Father's Name : " + rs.getString("Father_Name"));
                System.out.println("Service       : " + rs.getString("Service_Type"));
                System.out.println("Experience    : " + rs.getString("Experince"));
                System.out.println("Phone         : " + rs.getString("Phone"));
                System.out.println("Email         : " + rs.getString("Email"));
                System.out.println("Address       : " + rs.getString("Address") + ", "
                        + rs.getString("City") + ", "
                        + rs.getString("State") + "("
                        + rs.getString("Pincode") + ")");
                System.out.println("---------------------------------------------------------");

                vendorCount++;
            }

            if (vendorCount == 1) {
                System.out.println("No vendor found.");
            }

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void Pay_Bill_Vendor() {
        try {
            String url = "jdbc:mysql://localhost:3306/EBMS";
            String uname = "root";
            String password = "8252";
            Connection con = DriverManager.getConnection(url, uname, password);
            Scanner sc = new Scanner(System.in);

            System.out.print("Enter Consumer No.: CN/SBPDCL/");
            String numPart = sc.nextLine();
            String consumer = "CN/SBPDCL/" + numPart;

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

}
