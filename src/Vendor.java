package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Vendor {
    Admin a1 = new Admin();

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
                        System.out.println(" 5. Logout");
                        System.out.println("----------------------------");
                        System.out.print("Enter your choice: ");
                        int choice = sc.nextInt();
                        if (choice == 1) {
                            Bill_Generate();
                        } else if (choice == 2) {
                            a1.Pay_Bill_Vendor();
                        } else if (choice == 3) {
                            System.out.println("\n------------- Profile -------------");
                            System.out.println("Name: " + rs.getString("Full_Name"));
                            System.out.println("Father Name: " + rs.getString("Father_Name"));
                            System.out.println("Date Of Birth: " + rs.getString("DOB"));
                            System.out.println("Email: " + rs.getString("Email"));
                            System.out.println("Phone: " + rs.getString("Phone"));
                            System.out.println("Address: " + rs.getString("Address")
                                    + " " + rs.getString("State")
                                    + "(" + rs.getString("Pincode") + ")");
                            System.out.println("-----------------------------------");
                        } else if (choice == 4) {
                            a1.View_complains();
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
            System.out.print("Enter Consumer No.: CN/SBPDCL/");
            String numPart = sc.nextLine();
            String consumer = "CN/SBPDCL/" + numPart;
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

}
