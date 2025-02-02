package comm.octest.dao.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import comm.octest.beans.Message;
import comm.octest.beans.Observer;
import comm.octest.beans.User;
import comm.octest.dao.UserDAO;

public class UserDAOImp  implements UserDAO{
	
	Connection connexion = null;
	Statement statement = null;
	ResultSet resultat = null;
	
	public UserDAOImp() {}
	public void driver() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/goodQuotes", "root", "");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// CHECK UNICITE OF THE EMAIL
		public boolean validerInput(String name, String email, String password, String passwordc) throws SQLException, ClassNotFoundException {
			driver();
			PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM users WHERE email = ?");
			preparedStatement.setString(1, email);
			ResultSet resultat = preparedStatement.executeQuery();

			if (resultat.next()) {
				System.out.println("email exists");
				return false;
			} else {
				if (password.equals(passwordc)) {
					System.out.println("Password checked");
					return true;
				} else {
					System.out.println("The password dont match ");
					return false;
				}
			}
		}
		
		// check if the email exists :
		public boolean validEmail(String email, String currentEmail) throws SQLException {
			driver();
			PreparedStatement preparedStatement = connexion
					.prepareStatement("SELECT COUNT(*) AS count FROM users WHERE email = ? AND email != ?");
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, currentEmail);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int count = resultSet.getInt("count");
				if (count > 0) {
					return false; // email already exists for another user
				} else {
					return true; // email does not exist for any other user
				}
			}
			return false; // something went wrong
		}
	
	// REGISTRATION
	public void registration(Observer user) {

		String email = user.getEmail();
		String name = user.getName();
		String password = user.getPassword();
		String city = user.getCity();
		System.out.println(user.getEmail());

		try {
			driver();
			PreparedStatement preparedStatement = connexion
					.prepareStatement("INSERT INTO users(full_name,email,city, password) VALUES (?,?,?,?)");
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, email);
			preparedStatement.setString(3, city);
			preparedStatement.setString(4, password);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


	     //AUTHENTIFICATION 
		public boolean authentification(Observer user) {
			String email = user.getEmail();
			String password = user.getPassword();
			System.out.println("authentification :" + email);
			boolean auth = false;
			try {
				driver();
				PreparedStatement preparedStatement = connexion
						.prepareStatement("SELECT password FROM users WHERE email = ?");
				preparedStatement.setString(1, email);
				ResultSet resultat = preparedStatement.executeQuery();

				if (resultat.next()) {
					String passwordBb = resultat.getString("password");
					if (password.equals(passwordBb)) {
						auth = true;
					}
				} else {
					auth = false;
				}
			} catch (SQLException e) {
				e.printStackTrace();

			}
			return auth;
		}
		
		
		// GET INFORMATION OF A USER
		public List<User> getUser(String email, int idUserConnected) throws SQLException {
			List<User> userInfo = new ArrayList<>();
			boolean isFriends;
			int nbreFriends;

			driver();
			PreparedStatement preparedStatement3 = connexion.prepareStatement(
					"SELECT u.*, COUNT(CASE WHEN uq.id_user IS NOT NULL THEN 1 ELSE 0 END) AS nbreQuotes, COUNT(CASE WHEN lk.id_quote IS NOT NULL THEN 1 ELSE 0 END)  as nbreOfLikes FROM users u LEFT JOIN user_quote uq ON u.id_user = uq.id_user LEFT JOIN like_quote lk ON uq.id_quote = lk.id_quote  WHERE email =? GROUP BY u.id_user");
			preparedStatement3.setString(1, email);

			ResultSet resultat3 = preparedStatement3.executeQuery();

			if (resultat3.next()) {
				String full_name = resultat3.getString("full_name");
				String country = resultat3.getString("country");
				String city = resultat3.getString("city");
				String emailProfile = resultat3.getString("email");
				String password = resultat3.getString("password");
				Timestamp created_at = resultat3.getTimestamp("created_at");
				int nbreQuotes = resultat3.getInt("nbreQuotes");
				int id_user = resultat3.getInt("id_user");
				int nbre_likes = resultat3.getInt("nbreOfLikes");

				PreparedStatement preparedStatement4 = connexion.prepareStatement(
						"SELECT COUNT(*) as nbreOfFriends FROM friendships WHERE id_user1 =? or id_user2=? ");

				preparedStatement4.setInt(1, id_user);
				preparedStatement4.setInt(2, id_user);

				ResultSet resultat4 = preparedStatement4.executeQuery();

				PreparedStatement preparedStatement5 = connexion.prepareStatement(
						"SELECT * FROM friendships WHERE id_user1 =?  AND id_user2 =? or id_user2=? AND id_user1=?");
				preparedStatement5.setInt(1, idUserConnected);
				preparedStatement5.setInt(2, id_user);
				preparedStatement5.setInt(3, idUserConnected);
				preparedStatement5.setInt(4, id_user);

				ResultSet resultat5 = preparedStatement5.executeQuery();

				if (resultat4.next()) {
					nbreFriends = resultat4.getInt("nbreOfFriends");

				} else {

					nbreFriends = 0;

				}

				if (resultat5.next()) {
					isFriends = true;

				} else {
					isFriends = false;

				}
				User user = new User(full_name, country, city, password, created_at, emailProfile, nbreQuotes, id_user,
						nbreFriends, nbre_likes, isFriends);
				userInfo.add(user);
			}
			return userInfo;
		}
		
		
		// GET THE ID OF THE USER BASED ON HIS EMAIL
		public int getId(String email) throws SQLException {

			driver();
			PreparedStatement preparedStatement = connexion.prepareStatement("SELECT id_user FROM users WHERE email = ?");
			preparedStatement.setString(1, email);
			ResultSet resultat = preparedStatement.executeQuery();
			if (resultat.next()) {
				int id_user = resultat.getInt("id_user");
				return id_user;
			}
			return -1;
		}
		
		
		// UPDATE USER INFO
public void updateUserInfo(Observer user) throws SQLException {
				String full_name = user.getName();
				String email = user.getEmail();
				String country = user.getCountry();
				String city = user.getCity();
				String password = user.getPassword();
				int id_user = user.getId_user();
	
				driver();
				PreparedStatement preparedStatement = connexion
						.prepareStatement("UPDATE users SET full_name = ?,email=?,country=?,city=?,password=? WHERE id_user=?");
				preparedStatement.setString(1, full_name);
				preparedStatement.setString(2, email);
				preparedStatement.setString(3, country);
				preparedStatement.setString(4, city);
				preparedStatement.setString(5, password);
				preparedStatement.setInt(6, id_user);
				preparedStatement.executeUpdate();
		}



//ADD LIKED QUOTE
	public void addLikedQuote(Observer user) throws SQLException {
		int user_id = user.getId_user();
		int quote_id = user.getId_quote();
		
		driver();
		PreparedStatement preparedStatement2 = connexion
				.prepareStatement("SELECT * FROM like_quote WHERE id_user=? AND id_quote=?");
		preparedStatement2.setInt(1, user_id);
		preparedStatement2.setInt(2, quote_id);
		ResultSet resultat2 = preparedStatement2.executeQuery();
		if (resultat2.next()) {

			PreparedStatement preparedStatement3 = connexion
					.prepareStatement("DELETE FROM like_quote WHERE id_user=? AND id_quote=?");
			preparedStatement3.setInt(1, user_id);
			preparedStatement3.setInt(2, quote_id);
			preparedStatement3.executeUpdate();

		} else {

			PreparedStatement preparedStatement = connexion
					.prepareStatement("INSERT INTO like_quote(id_user,id_quote) VALUES (?,?)");
			preparedStatement.setInt(1, user_id);
			preparedStatement.setInt(2, quote_id);
			preparedStatement.executeUpdate();
		}

	}
	
	// REMOVE LIKED QUOTES
	public void removeLikedQuote(Observer user) throws SQLException {
		int user_id = user.getId_user();
		int quote_id = user.getId_quote();
		driver();
		PreparedStatement preparedStatement3 = connexion
				.prepareStatement("DELETE FROM like_quote WHERE id_user=? AND id_quote=?");
		preparedStatement3.setInt(1, user_id);
		preparedStatement3.setInt(2, quote_id);
		preparedStatement3.executeUpdate();

	}
  
	
	 // INSERT NOTIFICATION FOR A NEW QUOTE
		public void insertNotification(int id_quote, int id_user) throws SQLException {
			driver();

			PreparedStatement preparedStatement2 = connexion
					.prepareStatement("SELECT id_user FROM user_quote WHERE id_quote=?");
			preparedStatement2.setInt(1, id_quote);

			ResultSet resultat2 = preparedStatement2.executeQuery();
			while (resultat2.next()) {
				int idOwnerOfTheQuote = resultat2.getInt("id_user");

				if (idOwnerOfTheQuote != id_user) {

					PreparedStatement preparedStatement5 = connexion
							.prepareStatement("INSERT INTO notification_quotes(id_quote,id_user) VALUES (?,?) ");
					preparedStatement5.setInt(1, id_quote);
					preparedStatement5.setInt(2, id_user);
					System.out.println(id_quote);
					System.out.println(id_user);
					preparedStatement5.executeUpdate();
				}
			}
		}
		
		// FETCH USERS TO DISPLAY THEM :
		public List<Observer> getUsers(int idUserConnected) throws SQLException {
			List<Observer> users = new ArrayList<>();
			driver();
			PreparedStatement preparedStatement2 = connexion.prepareStatement(
					"SELECT u.*, COUNT(CASE WHEN uq.id_user IS NOT NULL THEN 1 ELSE 0 END) AS nbreQuotes FROM users u LEFT JOIN user_quote uq ON u.id_user = uq.id_user GROUP BY u.id_user ORDER BY nbreQuotes DESC");

			ResultSet resultat2 = preparedStatement2.executeQuery();
			while (resultat2.next()) {
				String full_name = resultat2.getString("full_name");
				String email = resultat2.getString("email");
				String country = resultat2.getString("country");
				String city = resultat2.getString("city");
				int nbreQuotes = resultat2.getInt("nbreQuotes");
				int id_user = resultat2.getInt("id_user");

				PreparedStatement preparedStatement3 = connexion.prepareStatement(
						"SELECT * FROM friendships WHERE id_user1 =?  AND id_user2 =? or id_user2=? AND id_user1=?");
				preparedStatement3.setInt(1, idUserConnected);
				preparedStatement3.setInt(2, id_user);
				preparedStatement3.setInt(3, idUserConnected);
				preparedStatement3.setInt(4, id_user);

				ResultSet resultat3 = preparedStatement3.executeQuery();
				Observer user = new User();

				user.setCity(city);
				user.setCountry(country);
				user.setEmail(email);
				user.setName(full_name);
				user.setId_user(id_user);
				user.setNbreQuoteAdded(nbreQuotes);
				if (resultat3.next()) {

					boolean isFriends = true;
					user.setFriends(isFriends);

				} else {
					boolean isFriends = false;
					user.setFriends(isFriends);
				}
				PreparedStatement preparedStatement4 = connexion.prepareStatement(
						"SELECT COUNT(*) as nbreOfFriends FROM friendships WHERE id_user1 =? or id_user2=? ");

				preparedStatement4.setInt(1, id_user);
				preparedStatement4.setInt(2, idUserConnected);
				ResultSet resultat4 = preparedStatement4.executeQuery();
				if (resultat4.next()) {
					int nbreFriends = resultat4.getInt("nbreOfFriends");
					user.setNbreFriends(nbreFriends);

				} else {
					int nbreFriends = 0;
					user.setNbreFriends(nbreFriends);
				}
				users.add(user);

			}

			return users;
		}
		
		
		
		// ADD A FRIENDSHIP && CHECK IF THEY ARE FRIENDS :
		public void addFriend(int idUser1, int idUser2) throws SQLException {
			driver();
			PreparedStatement preparedStatement5 = connexion
					.prepareStatement("INSERT INTO friendships(id_user1,id_user2) VALUES (?,?)");
			preparedStatement5.setInt(1, idUser1);
			preparedStatement5.setInt(2, idUser2);
			preparedStatement5.executeUpdate();

		}
		
		//GET MESSAGE WITH USER :

		public ArrayList<Message> getMessagesWithUser (String withClientId,String email) throws Exception{
			ArrayList<Message> messages = new ArrayList<Message>();
		      driver() ;
				PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM messages  WHERE (id_sender=? AND id_receiver=?) OR (id_sender=? AND id_receiver=?) ");
				preparedStatement.setString(1, email);
				preparedStatement.setString(2, withClientId);
				preparedStatement.setString(3, withClientId);
				preparedStatement.setString(4, email);
				ResultSet res = preparedStatement.executeQuery();
				while (res.next()) {
					Message msg = new Message(res.getString("id_sender"),res.getString("id_receiver"),res.getString("message"),res.getString("type_message"));
					messages.add(msg);
				}

			
				
			return messages;
		}
		
	
		
		
		public void removeMsg (String withClientId,String email) throws Exception{
                driver() ;
			
			
				PreparedStatement preparedStatement = connexion.prepareStatement("DELETE FROM messages WHERE id_receiver=? AND id_sender=?");
				preparedStatement.setString(1, email);
				preparedStatement.setString(2, withClientId);
				preparedStatement.executeUpdate();

			}
		

		public void insertMsg (String to, String from, String msg, String type) throws Exception{

			    driver() ;
				PreparedStatement preparedStatement = connexion.prepareStatement("INSERT INTO messages (id_sender,id_receiver,message,type_message) values(?,?,?,?)");
				preparedStatement.setString(1, from);
				preparedStatement.setString(2, to);
				if(type.equals("text")) {
					preparedStatement.setString(3, msg);
				}
				else {
					preparedStatement.setString(3, msg);
				}
				
					preparedStatement.setString(4, type);
					preparedStatement.executeUpdate();

			}
		
		public List<Message> notification(String email) throws Exception { 
			       List<Message> notifs = new ArrayList<>() ;
		           driver() ;
		           
			
					PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM messages WHERE id_receiver = ?");
					preparedStatement.setString(1, email);
					ResultSet res = preparedStatement.executeQuery();
					while (res.next()) {
						String id_sender = res.getString("id_sender");  
						String message = res.getString("message") ;
						
						

						PreparedStatement preparedStatement2 = connexion.prepareStatement("SELECT full_name FROM users WHERE email= ?");
						preparedStatement2.setString(1, id_sender);
						ResultSet res2 = preparedStatement2.executeQuery();
						
						if(res2.next()) { 
							String full_name = res2.getString("full_name");
							Message msg = new Message(id_sender ,message,full_name) ; 
							
							notifs.add(msg) ;
						}
						
						
						
						;
						 						
						}
						
					return notifs ; 
					
			
		}
		
public String getName(String email) throws Exception{ 
	  driver() ;
		
		PreparedStatement preparedStatement = connexion.prepareStatement("SELECT full_name FROM users WHERE email = ?");
		preparedStatement.setString(1, email);
		ResultSet res = preparedStatement.executeQuery();
		if(res.next()) {
			 String full_name = res.getString("full_name");
			
			return full_name ;
		}
		 
		 


return "null";
}

// FETCH USERS TO DISPLAY THEM :
public List<Observer> getAllUsers() throws SQLException {
	List<Observer> users = new ArrayList<>();
	driver();
	PreparedStatement preparedStatement2 = connexion.prepareStatement("SELECT email FROM users");

	ResultSet resultat2 = preparedStatement2.executeQuery();
	while (resultat2.next()) {
	
		String email = resultat2.getString("email");


	
		
		Observer user = new User();

	
		user.setEmail(email);
		users.add(user);

	}

	return users;
}
}

		

