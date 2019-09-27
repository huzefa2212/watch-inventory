package jdbc;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class watchDemo {
	 	Scanner sc = new Scanner(System.in);
	public static void main(String[] args) {
		
		// Object creation
		watchDemo wd = new watchDemo();
		
		Scanner sc = new Scanner(System.in);
		
		int ch;
		do {
			System.out.println("Enter Your Choice :");
			System.out.println("1. Insert The Data :");
			System.out.println("2. Display The Records :");
			System.out.println("3. Update the records :");
			System.out.println("4. Delete The record :");
			System.out.println("5. Exit");
			
			ch = sc.nextInt();
			
			switch(ch) {
			case 1:
				wd.insertData();
				break;
				
			case 2:
				wd.displayData();
				break;
			case 3:
				wd.updateData();
				break;
			case 4:
				wd.deleteData();
				break;
			case 5:
				System.out.println("Thank you for visiting us..!");
				break;
				
			default:
				break;
			}
			
		}while(ch!= 5);
		
	}
	
	public Connection getConnect() throws SQLException {
		try {
		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/watches?useSSL=false","admin","admin");
		return con;
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
		
	}

	private void deleteData() {
		try {
			int id;
			System.out.println("Enter the record no. to delete :");
			id = sc.nextInt();
			watch w = new watch();
			w.setWatch_id(id);
			
			Connection con = getConnect();
			String query = "delete from watch where watch_id=?";
			PreparedStatement pst = (PreparedStatement) con.prepareStatement(query);
			pst.setInt(1, id);
			
			int x=pst.executeUpdate();
			if(x==1) {
				System.out.println("Record deleted.");
			}else {
				System.out.println("Record not found. ");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void updateData() {
		
		watch w = new watch();
		
		  System.out.println("Enter the watch id / no :"); 
		  int i = sc.nextInt();
		  w.setWatch_id(i);
		 
		
		System.out.println("Enter the Watch name :");
		String wname = sc.next();
		w.setWatch_name(wname);
		
		System.out.println("Enter the Watch Brand :");
		String wbrand = sc.next();
		w.setBrand(wbrand);
		
		System.out.println("Enter the Watch Price :");
		Double wprice = sc.nextDouble();
		w.setPrice(wprice);
		
		
		try {
			
			Connection con = getConnect();
			String query = "update watch set watch_name=?, watch_brand=?, watch_price=? where watch_id=?";
			PreparedStatement pst = (PreparedStatement) con.prepareStatement(query);
			pst.setString(1, w.getWatch_name());
			pst.setString(2, w.getBrand());
			pst.setDouble(3, w.getPrice());
			pst.setInt(4, w.getWatch_id());
			
			int x = pst.executeUpdate();
			pst.close();
			con.close();
			
			if(x==1) {
				System.out.println("Successfully updated ." );
			}else {
				System.out.println("Record not Found.");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void displayData() {
		try {
			Connection con = getConnect();
			String query = "select * from watch";
			PreparedStatement pst = (PreparedStatement) con.prepareStatement(query);
			
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getDouble(4));
				
			}rs.close();
			pst.close();
			con.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	private void insertData() {
		// TODO Auto-generated method stub
		watch w = new watch();
		
		System.out.println("Enter watch Number / watch id : ");
		int wid = sc.nextInt();
		w.setWatch_id(wid);
		
		System.out.println("Enter watch name : ");
		String wname = sc.next();
		w.setWatch_name(wname);
		
		System.out.println("Enter watch Brand : ");
		String wbrand = sc.next();
		w.setBrand(wbrand);
		
		System.out.println("Enter watch Price : ");
		Double wprice = sc.nextDouble();
		w.setPrice(wprice);
	
		
		try {
			Connection con = getConnect();
			String query = "insert into watch (watch_id,watch_name,watch_brand,watch_price)values(?,?,?,?)";
			PreparedStatement pst = (PreparedStatement) con.prepareStatement(query);
			pst.setInt(1, w.getWatch_id());
			pst.setString(2, w.getWatch_name());
			pst.setString(3, w.getBrand());
			pst.setDouble(4, w.getPrice());
			
			int x = pst.executeUpdate();
			pst.close();
			con.close();
			
			if(x==1) {
				System.out.println("Record Inserted Successfully.");
			}else {
				System.out.println("Something Error while Inserting Record");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
