package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Table {
	public void DBConnection() {
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

	public void CreateDB() {
		try {
			String url = "jdbc:mysql://localhost:3306/";
			String uname = "root";
			String password = "8252";
			Connection con = DriverManager.getConnection(url, uname, password);
			Statement st = con.createStatement();
			String q = "create database EBMS";
			st.execute(q);
			System.out.println("Data Base created");
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void create_register_table_user() {
		try {
			String url = "jdbc:mysql://localhost:3306/EBMS";
			String uname = "root";
			String password = "8252";
			Connection con = DriverManager.getConnection(url, uname, password);
			Statement st = con.createStatement();
			String q = "create table register_user(Id int not null auto_increment primary key, Name varchar(50) not null, Email varchar(50) unique not null, Password varchar(30) not null, Phone bigint not null, Consumer_No varchar(20) not null)";
			st.execute(q);
			System.out.println("Table Created");
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void add_consumer() {
		try {
			String url = "jdbc:mysql://localhost:3306/EBMS";
			String uname = "root";
			String password = "8252";
			Connection con = DriverManager.getConnection(url, uname, password);
			Statement st = con.createStatement();
			String q = "create table consumer(Consumer_No varchar(20) not null primary key, Full_Name varchar(50) not null, Father_Name varchar(50) not null, DOB varchar(20) not null, Age int not null, Gender varchar(10) not null, Phone bigint not null, Aadhar bigint not null, Address varchar(100) not null, House_No int not null, City varchar(30) not null, State varchar(30) not null, Pincode int not null, Connection_Type varchar(30) not null, Load_Cap varchar(20) not null)";
			st.execute(q);
			System.out.println("Table Created");
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void add_vendor() {
		try {
			String url = "jdbc:mysql://localhost:3306/EBMS";
			String uname = "root";
			String password = "8252";
			Connection con = DriverManager.getConnection(url, uname, password);
			Statement st = con.createStatement();
			String q = "create table vendor(vendor_Id varchar(30) not null primary key, Full_Name varchar(50) not null, Father_Name varchar(50) not null, DOB varchar(20) not null, Age int not null, Gender varchar(10) not null, Phone bigint not null, Aadhar bigint not null, Email varchar(30) unique not null, Qualification varchar(100) not null, Skills varchar(100) not null, Certificates varchar(100) not null, Service_Type varchar(50) not null, Experince varchar(30) not null, Address varchar(100) not null, House_No int not null, City varchar(30) not null, State varchar(30) not null, Pincode int not null)";
			st.execute(q);
			System.out.println("Table Created");
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void main(String[] args) {
		Table t1 = new Table();
		t1.DBConnection();
		// t1.CreateDB();
		// t1.create_register_table_user();
		// t1.add_consumer();
		t1.add_vendor();
	}

}
