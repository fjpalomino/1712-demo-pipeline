package com.revature.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.model.ERSStatus;
import com.revature.model.ERSTypes;
import com.revature.model.Request;
import com.revature.model.Requests;
import com.revature.model.UpdateUser;
import com.revature.model.User;

public class ERSDBDaoImpl implements ERSDBDao{
	
	private static String url = "jdbc:oracle:thin:@usfdbjava.cbanggyilq7p.us-east-2.rds.amazonaws.com:1521:orcl";
	private static String username = "ers";
	private static String password = "p4ssw0rd";
	
	static{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ERSTypes getErsTypes() {
		ERSTypes result = null;
		String[][] values = null;
		
		try(Connection conn = DriverManager.getConnection(url,username,password))
		{
			String sql = "SELECT COUNT(*) FROM ers_reimbursement_type";
			PreparedStatement pst = conn.prepareStatement(sql);
			//pst.setString(1, table);
			ResultSet rs = pst.executeQuery();
			if(rs.next())
			{
				int size = rs.getInt(1);				
				
				sql = "SELECT * FROM ers_reimbursement_type";
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery();
				
				values = new String[size][2];
				int i=0;
				while (rs.next())
				{
					values[i][0] = Integer.toString(rs.getInt(1));
					values[i][1] = rs.getString(2);
					i++;
				}
				result = new ERSTypes(values,size);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
			
		return result;
	}
	
	@Override
	public ERSStatus getErsStatus() {
		ERSStatus result = null;
		String[][] values = null;
		
		try(Connection conn = DriverManager.getConnection(url,username,password))
		{
			String sql = "SELECT COUNT(*) FROM ers_reimbursement_status";
			PreparedStatement pst = conn.prepareStatement(sql);
			//pst.setString(1, table);
			ResultSet rs = pst.executeQuery();
			if(rs.next())
			{
				int size = rs.getInt(1);				
				
				sql = "SELECT * FROM ers_reimbursement_status";
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery();
				
				values = new String[size][2];
				int i=0;
				while (rs.next())
				{
					values[i][0] = Integer.toString(rs.getInt(1));
					values[i][1] = rs.getString(2);
					i++;
				}
				result = new ERSStatus(values,size);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
			
		return result;
	}

	@Override
	public boolean updateUserById(int userId, UpdateUser newInfo) {
		
		try(Connection conn = DriverManager.getConnection(url,username,password))
		{
			String sql = "UPDATE ers_users SET u_username=?, u_firstname=?, u_lastname=?, u_email=? WHERE u_id = ?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, newInfo.getUsername());
			pst.setString(2, newInfo.getFirstName());
			pst.setString(3, newInfo.getLastName());
			pst.setString(4, newInfo.getEmail());
			pst.setInt(5, userId);
			
			int numOfRowsChanged = pst.executeUpdate();
			
			if(numOfRowsChanged==1){
				return true;
			}

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return false;		
	}

	@Override
	public User getUserById(int userId) {
		User userAccount = null;
		
		try(Connection conn = DriverManager.getConnection(url,username,password))
		{
			String sql = "SELECT * FROM ers_users WHERE u_id = ?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, userId);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
			{
				userAccount = new User(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getInt(7)
						);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return userAccount;
	}

	@Override
	public User getUserByUsername(String user) {

		User userAccount = null;
		
		try(Connection conn = DriverManager.getConnection(url,username,password))
		{
			String sql = "SELECT * FROM ers_users WHERE u_username = ?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, user);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
			{
				userAccount = new User(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getInt(7)
						);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return userAccount;
	}

	@Override
	public boolean selectByEmail(String email) {
		
		try(Connection conn = DriverManager.getConnection(url,username,password))
		{
			String sql = "SELECT * FROM ers_users WHERE u_email = ?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, email);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
			{
				return true;
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean insertRequest(Request userRequest) {
		
		try(Connection conn = DriverManager.getConnection(url,username,password);)
		{
			String sql = "{ call ers_reimbursement_p(?,?,?,?,?) }";
			CallableStatement cstm = conn.prepareCall(sql);
			
			cstm.setDouble(1,userRequest.getAmount());
			cstm.setString(2,userRequest.getDescription());
			cstm.setBlob(3,userRequest.getReceipt());
			cstm.setInt(4,userRequest.getId_author());
			cstm.setInt(5,userRequest.getRtType());
			
			int numOfRowsChanged = cstm.executeUpdate();
			
			if(numOfRowsChanged==1)
				return true;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public Requests getRequestsByUserId(int id) {
		Requests userRequests = null;
		ArrayList<Request> arr = new ArrayList<>();
		
		try(Connection conn = DriverManager.getConnection(url,username,password))
		{
			String sql = "SELECT * FROM ers_reimbursements WHERE u_id_author = ? ORDER BY r_id, rt_status ASC";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while (rs.next())
			{
				arr.add(new Request (
						rs.getInt(1), 
						rs.getDouble(2), 
						rs.getString(3), 
						rs.getBlob(4), 
						rs.getTimestamp(5), 
						rs.getTimestamp(6), 
						rs.getInt(7), 
						rs.getInt(8), 
						rs.getInt(9), 
						rs.getInt(10)));
			}
			userRequests = new Requests(arr);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return userRequests;
	}

	@Override
	public Requests getRequestsByStatus(int statusId) {
		Requests userRequests = null;
		ArrayList<Request> arr = new ArrayList<>();
		
		try(Connection conn = DriverManager.getConnection(url,username,password))
		{
			String sql = "SELECT * FROM ers_reimbursements WHERE rt_status = ? ORDER BY r_id ASC";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, statusId);
			ResultSet rs = pst.executeQuery();
			while (rs.next())
			{
				arr.add(new Request (
						rs.getInt(1), 
						rs.getDouble(2), 
						rs.getString(3), 
						rs.getBlob(4), 
						rs.getTimestamp(5), 
						rs.getTimestamp(6), 
						rs.getInt(7), 
						rs.getInt(8), 
						rs.getInt(9), 
						rs.getInt(10)));
			}
			userRequests = new Requests(arr);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return userRequests;
	}
	
	@Override
	public Requests getRequestsByResolved() {
		Requests userRequests = null;
		ArrayList<Request> arr = new ArrayList<>();
		
		try(Connection conn = DriverManager.getConnection(url,username,password))
		{
			String sql = "SELECT * FROM ers_reimbursements WHERE r_resolved IS NOT NULL ORDER BY r_id ASC";
			PreparedStatement pst = conn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next())
			{
				arr.add(new Request (
						rs.getInt(1), 
						rs.getDouble(2), 
						rs.getString(3), 
						rs.getBlob(4), 
						rs.getTimestamp(5), 
						rs.getTimestamp(6), 
						rs.getInt(7), 
						rs.getInt(8), 
						rs.getInt(9), 
						rs.getInt(10)));
			}
			userRequests = new Requests(arr);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return userRequests;
	}

	@Override
	public ArrayList<User> getEmployees() {

		ArrayList<User> arr = new ArrayList<>();
		
		try(Connection conn = DriverManager.getConnection(url,username,password))
		{
			String sql = "SELECT * FROM ers_users WHERE ur_id = 2";
			PreparedStatement pst = conn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next())
			{
				arr.add(new User (
						rs.getInt(1), 
						rs.getString(2), 
						rs.getString(3), 
						rs.getString(4), 
						rs.getString(5), 
						rs.getString(6), 
						rs.getInt(7)));
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return arr;
	}
	public ArrayList<User> getDBUsers() {

		ArrayList<User> arr = new ArrayList<>();
		
		try(Connection conn = DriverManager.getConnection(url,username,password))
		{
			String sql = "SELECT * FROM ers_users";
			PreparedStatement pst = conn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next())
			{
				arr.add(new User (
						rs.getInt(1), 
						rs.getString(2), 
						rs.getString(3), 
						rs.getString(4), 
						rs.getString(5), 
						rs.getString(6), 
						rs.getInt(7)));
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return arr;
	}

	@Override
	public Request getRequestById(int id) {
		Request userRequest = null;
		
		try(Connection conn = DriverManager.getConnection(url,username,password))
		{
			String sql = "SELECT * FROM ers_reimbursements WHERE r_id = ?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while (rs.next())
			{
				userRequest = new Request(
						rs.getInt(1), 
						rs.getDouble(2), 
						rs.getString(3), 
						rs.getBlob(4), 
						rs.getTimestamp(5), 
						rs.getTimestamp(6), 
						rs.getInt(7), 
						rs.getInt(8), 
						rs.getInt(9), 
						rs.getInt(10));
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return userRequest;
	}

}
