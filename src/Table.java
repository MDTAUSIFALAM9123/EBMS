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
			String q = "create table register_user(Name varchar(50) not null, Email varchar(50) primary key not null, Password varchar(30) not null, Phone bigint not null, Consumer_No int not null)";
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
		t1.create_register_table_user();
	}

}
