package gunsWebApp.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import gunsWebApp.client.GreetingService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {
	
	@Override
	public String getGunsAmount() {
		try {
			
			Connection connection = null;
			connection = DriverManager.getConnection(
					   "jdbc:postgresql://localhost:5432/guns","filejunkie", "qwerty");
			
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM guns");
			resultSet.next();
			String count = Integer.toString(resultSet.getInt("count"));
			
			connection.close();
			return count;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "FAIL";
	}

	@Override
	public String getGun(String index, String property) {
		try {
			
			Connection connection = null;
			connection = DriverManager.getConnection(
					   "jdbc:postgresql://localhost:5432/guns","filejunkie", "qwerty");
			
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM guns");
			
			for(int i = 0; i < Integer.parseInt(index) + 1; i++){
				resultSet.next();
			}
			
			String result = escapeHtml(resultSet.getString(property));
	
			connection.close();
			
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "FAIL";
	}
	
	@Override
	public String getGunById(String id){
		try {
			
			Connection connection = null;
			connection = DriverManager.getConnection(
					   "jdbc:postgresql://localhost:5432/guns","filejunkie", "qwerty");
			
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM guns WHERE id=?");
			stmt.setInt(1, Integer.parseInt(id));
			ResultSet rs = stmt.executeQuery();
			rs.next();
					
			String result = rs.getString("name");
	
			connection.close();
			
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "FAIL";
	}
	
	@Override
	public String addUpdateGun(String id, String name){
		try {
			
			Connection connection = null;
			connection = DriverManager.getConnection(
					   "jdbc:postgresql://localhost:5432/guns","filejunkie", "qwerty");
			
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM guns WHERE id=?");
			stmt.setInt(1, Integer.parseInt(id));
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()){
				stmt = connection.prepareStatement("UPDATE guns SET name=? WHERE id=?");
				stmt.setString(1, name);
				stmt.setInt(2, Integer.parseInt(id));
			}
			else{
				stmt = connection.prepareStatement("INSERT INTO guns(name) VALUES(?)");
				stmt.setString(1, name);
			}
			stmt.execute();
			
			connection.close();
			
			return "OK";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "FAIL";		
	}
	
	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
}
