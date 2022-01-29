package gruppoBrewDay2.brewday;

import java.sql.*;
import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class ControllerAccessoSito {
	
	public Map<String, String> parseBody(String str) {
		Map<String, String> body = new HashMap<>();
		  String[] values = str.split("&");
		  for (int i = 0; i < values.length; ++i)
		  {
			  String[] coppia = values[i].split("=");
			    if (coppia.length != 2) {
			    	continue;
			    }
			    else
			    {
			      body.put(coppia[0], coppia[1]);
			    }
		  }
		  return body;
	}
	
	@GetMapping("/")
    public String firstPage() {
        return "firstPage";
    }
	
	@GetMapping("/signin")
    public String registerPage() {
        return "registerPage";
    }
	
	@GetMapping("/homePage")
    public String homePage() {
        return "homePage";
    }
	
	@PostMapping("/login")
	public String login(@RequestBody String input) {
		input=input.replace("%40", "@");
		Map<String, String> body = parseBody(input);
		String nick = body.get("nickname");
		String pwd = body.get("pwd");
		String jdbcURL = "jdbc:mysql://localhost:3306/progetto_brewday";
		String usernameDB = "root";
		String passwordDB = "admin";
		
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			Connection connection = DriverManager.getConnection(jdbcURL,usernameDB,passwordDB);
		
			String sql = "SELECT * FROM progetto_brewday.users WHERE nickname=\""+nick+"\" AND password=\""+pwd+"\";";
		
			Statement statement = connection.createStatement();
			
			ResultSet result= statement.executeQuery(sql);
			
			
			if(result.next())
				{
					connection.close();
					return "redirect:/homePage";
				}
			else 
				{	connection.close();
					return "firstPageAccessFailed";
				}
			
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return "error";
    }
	
	@PostMapping("/signin")
	public String signin(@RequestBody String input) {
		input=input.replace("%40", "@");
		Map<String, String> body = parseBody(input);
		String nick = body.get("nickname");
		String email = body.get("email");
		String pwd = body.get("pwd");
		
		String jdbcURL = "jdbc:mysql://localhost:3306/progetto_brewday";
		String usernameDB = "root";
		String passwordDB = "admin";
		
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			Connection connection = DriverManager.getConnection(jdbcURL,usernameDB,passwordDB);
		
			String sql = "insert into users (nickname, email, password) values (\""+nick+"\",\""+ email +"\",\""+pwd+"\");";
			
			Statement statement = connection.createStatement();
			
			int rows = statement.executeUpdate(sql);
			
			
			if(rows > 0)
			{connection.close();
			return "redirect:/homePage";}
			
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return "error";
    }

}
