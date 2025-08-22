
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Main {
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
			String q = "create database Testdb";
			st.execute(q);
			System.out.println("Data Base created");
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void create_table_user() {
		try {
			String url = "jdbc:mysql://localhost:3306/testdb";
			String uname = "root";
			String password = "8252";
			Connection con = DriverManager.getConnection(url, uname, password);
			Statement st = con.createStatement();
			String q = "create table SignUp_user(Name varchar(50) not null, Email_Id varchar(50) primary key not null, Password int not null,Phone int not null)";
			st.execute(q);
			System.out.println("Table Created");
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void main(String[] args) {
		Main ms = new Main();
		ms.DBConnection();
		// ms.CreateDB();
		// ms.create_table_user();
	}
}
