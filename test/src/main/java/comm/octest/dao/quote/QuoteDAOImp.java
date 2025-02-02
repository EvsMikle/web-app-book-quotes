package comm.octest.dao.quote;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import comm.octest.beans.Flyweight;
import comm.octest.beans.QuoteManager;
import comm.octest.dao.QuoteDAO;

public class QuoteDAOImp implements QuoteDAO {


	
	Connection connexion = null;
	Statement statement = null;
	ResultSet resultat = null;
	
	public QuoteDAOImp() {}
	
	public void driver() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/goodQuotes", "root", "");
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	//FETCH ALL THE QUOTES && CHECK IF THE USER LIKED THE QUOTE OR NOT 
	public List<QuoteManager> fetchQuotes(int user_id) throws SQLException {
	    List<QuoteManager> quotes = new ArrayList<>();
	    String color;
	    driver();
	    Set<String> displayedQuotes = new HashSet<>(); // create a HashSet to keep track of displayed quote_text

	    PreparedStatement preparedStatement = connexion.prepareStatement("SELECT q.*, b.name AS book_name,b.book_img as book_img ,a.name AS author_name, uq.id_user,u.full_name AS user_name, u.email as email FROM quotes q INNER JOIN books b ON q.id_book = b.id_book INNER JOIN authors a ON b.id_author = a.id_author INNER JOIN user_quote uq ON q.id_quote = uq.id_quote INNER JOIN users u ON uq.id_user= u.id_user   ");
	    ResultSet resultat = preparedStatement.executeQuery();

	    while (resultat.next()) {
	        String quote_text = resultat.getString("quote_text");
	        if (displayedQuotes.contains(quote_text)) { // check if quote_text is already displayed
	            continue; // skip this iteration
	        }
	        displayedQuotes.add(quote_text); // add quote_text to the HashSet

	        String book_name = resultat.getString("book_name");
	        Timestamp created_at = resultat.getTimestamp("created_at");
	        String user_name = resultat.getString("user_name");
	        String author_name = resultat.getString("author_name");
	        int id_quote = resultat.getInt("id_quote");
	        String emailOfTheProfile = resultat.getString("email");
	        String book_img = resultat.getString("book_img");
	        System.out.println("email of the owner of the quote is " + emailOfTheProfile);

	        PreparedStatement preparedStatement2 = connexion
	                .prepareStatement("SELECT * from like_quote WHERE id_user =? AND id_quote=?");
	        preparedStatement2.setInt(1, user_id);
	        preparedStatement2.setInt(2, id_quote);
	        ResultSet resultat2 = preparedStatement2.executeQuery();
	        if (resultat2.next()) {
	            color = "red";

	        } else {
	            color = "blue";
	        }

	        QuoteManager quote = new QuoteManager(book_name, quote_text, author_name, created_at, user_name, id_quote,
	                color, emailOfTheProfile,book_img);
	        quotes.add(quote);
	    }
	    return quotes;
	}

		
		
		
		// FETCH THE QUOTES OF ANOTHER USER SO WE CAN DISPLAY IN HIS PROFILE
		public List<QuoteManager> fetchUserQuotes(int user_id,int id_user_session) throws SQLException {
			List<QuoteManager> quotes = new ArrayList<>();
			String color;
			driver();

			PreparedStatement preparedStatement = connexion.prepareStatement(
					"SELECT q.*, b.name AS book_name,b.book_img as book_img, a.name AS author_name, uq.id_user,u.full_name AS user_name, u.email as email FROM quotes q INNER JOIN books b ON q.id_book = b.id_book INNER JOIN authors a ON b.id_author = a.id_author INNER JOIN user_quote uq ON q.id_quote = uq.id_quote INNER JOIN users u ON uq.id_user= u.id_user  WHERE u.id_user =? ");
			preparedStatement.setInt(1, user_id);
			ResultSet resultat = preparedStatement.executeQuery();

			while (resultat.next()) {
				String quote_text = resultat.getString("quote_text");
				String book_name = resultat.getString("book_name");
				Timestamp created_at = resultat.getTimestamp("created_at");
				String user_name = resultat.getString("user_name");
				String author_name = resultat.getString("author_name");
				int id_quote = resultat.getInt("id_quote");
				String emailOfTheProfile = resultat.getString("email") ;
				  String book_img = resultat.getString("book_img");
			

				PreparedStatement preparedStatement2 = connexion
						.prepareStatement("SELECT * from like_quote WHERE id_user =? AND id_quote=?");
				preparedStatement2.setInt(1, id_user_session);
				preparedStatement2.setInt(2, id_quote);
				ResultSet resultat2 = preparedStatement2.executeQuery();
				if (resultat2.next()) {
					color = "red";

				} else {
					color = "blue";
				}

				QuoteManager quote = new QuoteManager(book_name, quote_text, author_name, created_at, user_name, id_quote,
						color,emailOfTheProfile,book_img);
				quotes.add(quote);
			}
			return quotes;

		}

		
		//FETCH THE QUOTES OF THE USER CONNECTED
		public List<QuoteManager> fetchMyQuotes(String email) throws SQLException {
			List<QuoteManager> quotes = new ArrayList<>();
			driver();

			System.out.println("Affichage des quote de user : " + email);
			PreparedStatement preparedStatement = connexion.prepareStatement("SELECT q.*, b.name AS book_name,b.book_img as book_img, b.id_book as id_book ,a.name AS author_name, a.id_author as id_author ,t.name as type ,uq.id_user,u.full_name AS user_name FROM quotes q INNER JOIN books b ON q.id_book = b.id_book INNER JOIN types t ON b.id_type = t.id_type  INNER JOIN authors a ON b.id_author = a.id_author INNER JOIN user_quote uq ON q.id_quote = uq.id_quote INNER JOIN users u ON uq.id_user= u.id_user WHERE u.email =? ");
			preparedStatement.setString(1, email);
			ResultSet resultat = preparedStatement.executeQuery();

			while (resultat.next()) {
				String quote_text = resultat.getString("quote_text");
				String book_name = resultat.getString("book_name");
				Timestamp created_at = resultat.getTimestamp("created_at");
				String user_name = resultat.getString("user_name");
				String author_name = resultat.getString("author_name");
				int id_quote = resultat.getInt("id_quote");
				int id_author =  resultat.getInt("id_author");
				int id_book = resultat.getInt("id_book");
				String type = resultat.getString("type");
			    String book_img = resultat.getString("book_img");

				QuoteManager quote = new QuoteManager(book_name, quote_text, author_name, created_at, user_name, id_quote,email,id_author,id_book,type,book_img);
				quotes.add(quote);
			}
			return quotes;
		}
	
		
		// FETCH USER FAVORITE QUOTES
		public List<QuoteManager> fetchFavQuotes(int user_id) throws SQLException {
			List<QuoteManager> favQuotes = new ArrayList<>();
			driver();
			
			PreparedStatement preparedStatement2 = connexion.prepareStatement("SELECT id_quote FROM like_quote WHERE id_user=?");
			preparedStatement2.setInt(1, user_id);
			ResultSet resultat2 = preparedStatement2.executeQuery();
			
			while (resultat2.next()) {
				int quote_id = resultat2.getInt("id_quote");
				System.out.print("next1: " + quote_id);
				PreparedStatement preparedStatement = connexion.prepareStatement("SELECT q.*, b.name AS book_name,b.book_img as book_img, a.name AS author_name, u.full_name AS user_name ,u.email as email FROM quotes q INNER JOIN books b ON q.id_book = b.id_book INNER JOIN authors a ON b.id_author = a.id_author INNER JOIN user_quote uq ON q.id_quote = uq.id_quote INNER JOIN users u ON uq.id_user= u.id_user WHERE q.id_quote=? LIMIT 1 ");
				preparedStatement.setInt(1, quote_id);
				ResultSet resultat = preparedStatement.executeQuery();

				while (resultat.next()) {
					System.out.println("next 2 ");
					String quote_text = resultat.getString("quote_text");
					String book_name = resultat.getString("book_name");
					Timestamp created_at = resultat.getTimestamp("created_at");
					String user_name = resultat.getString("user_name");
					String author_name = resultat.getString("author_name");
					String email = resultat.getString("email") ;
					String color = "red";
					  String book_img = resultat.getString("book_img");

					QuoteManager quote = new QuoteManager(book_name, quote_text, author_name, created_at, user_name,
							quote_id, color,email,book_img);
					favQuotes.add(quote);
				}
			}
			return favQuotes;
		}
		
		//FETCH ALL THE QUOTE AUTHORSHIP
		public List<QuoteManager>fetchQuoteAuthorship() throws SQLException {
			driver();
			List<QuoteManager> user_quote = new ArrayList<>();

			PreparedStatement preparedStatement = connexion.prepareStatement("SELECT uq.*,quote_text FROM user_quote uq INNER JOIN quotes q ON uq.id_quote = q.id_quote ");
			ResultSet resultat = preparedStatement.executeQuery();
			while (resultat.next()) {
				String quote_text = resultat.getString("quote_text");
				int id_user = resultat.getInt("id_user");

				QuoteManager quote = new QuoteManager(quote_text, id_user);
				user_quote.add(quote);
			}
			return user_quote;
		}
		
		
		//INSERT QUOTE AUTHORSHIP
		public int insertQuoteAuthorship(String quote_text, int user_id) throws SQLException {
			driver();
			

			PreparedStatement preparedStatement3 = connexion.prepareStatement("SELECT id_quote FROM quotes WHERE quote_text =?");
			preparedStatement3.setString(1, quote_text);
			ResultSet resultat3 = preparedStatement3.executeQuery();

			if (resultat3.next()) {
				int id_quote = resultat3.getInt("id_quote");
				
				PreparedStatement preparedStatement = connexion.prepareStatement("INSERT INTO user_quote(id_user,id_quote) VALUES (?,?)");
				preparedStatement.setInt(1, user_id);
				preparedStatement.setInt(2, id_quote);
				System.out.println("user_id : " + user_id + " SQUOTE ID " + id_quote);
				preparedStatement.executeUpdate();
				return id_quote;
			}
			return -1;
		}
		
		
		//INSERT QUOTE 
		public int insertQuote(String book_name, String quote_text) throws SQLException {
			driver();

			PreparedStatement preparedStatement3 = connexion.prepareStatement("SELECT id_book FROM books WHERE name =?");
			preparedStatement3.setString(1, book_name);
			ResultSet resultat3 = preparedStatement3.executeQuery();

			if (resultat3.next()) {
				int id_book = resultat3.getInt("id_book");
				PreparedStatement preparedStatement = connexion.prepareStatement("INSERT INTO quotes(id_book,quote_text) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setInt(1, id_book);
				preparedStatement.setString(2, quote_text);
				preparedStatement.executeUpdate();

				System.out.println("Quote bien inserer :" + quote_text);
				ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
				if (generatedKeys.next()) {
					int id_quote = generatedKeys.getInt(1);
					System.out.println("The ID of the inserted quote is: " + id_quote);
					return id_quote;
				}
			}
			return -1;
		}
	
		
		
	
		
		public void updateQuote(Flyweight quote) throws SQLException {
			String quote_text = quote.getQuoteText();
			int id_quote = quote.getId_quote();
		
			driver();
			PreparedStatement preparedStatement4 = connexion.prepareStatement("UPDATE quotes SET quote_text = ?  WHERE id_quote =?");
			preparedStatement4.setString(1, quote_text);
			preparedStatement4.setInt(2, id_quote);
			preparedStatement4.executeUpdate();

			
		}
		//REMOVE QUOTE AUTHORSHIP
		
		public void removeQuoteAuthorship(int id_quote,int user_id) throws SQLException {
			driver();
			PreparedStatement preparedStatement5 = connexion.prepareStatement("DELETE FROM user_quote WHERE id_user =? AND id_quote=? ");
			preparedStatement5.setInt(1, user_id);
			preparedStatement5.setInt(2, id_quote);
			 preparedStatement5.executeUpdate();
		}
		
		//REMOVE A QUOTE
		public void removeQuote(int id_quote) throws SQLException {
			 driver();
			 PreparedStatement preparedStatement5 = connexion.prepareStatement("DELETE FROM quotes WHERE id_quote=? ");
			 preparedStatement5.setInt(1, id_quote);
			 preparedStatement5.executeUpdate();
		}
		
	
		
		// TO GET NOTIFICATIONS:
		public List<Flyweight> getNotification(int id_user) throws SQLException {
			List<Flyweight> notifications = new ArrayList<>();
			
			driver();
			PreparedStatement preparedStatement2 = connexion.prepareStatement("SELECT nq.id_quote FROM notification_quotes nq WHERE nq.id_user=?");
			preparedStatement2.setInt(1, id_user);
			ResultSet resultat2 = preparedStatement2.executeQuery();
			while (resultat2.next()) {
				int id_quote = resultat2.getInt("id_quote");
				Flyweight quoteNotification = new QuoteManager();
				quoteNotification.setId_quote(id_quote);
				System.out.print(id_quote);
				notifications.add(quoteNotification);
			}
			return notifications;
		}
		
		
		// DELETE THE NOTIFS
		public void removeNotification(int id_quote, int id_user) throws SQLException {
			driver();
			
			PreparedStatement preparedStatement5 = connexion.prepareStatement("DELETE FROM notification_quotes WHERE id_quote =? AND id_user=?");
			preparedStatement5.setInt(1, id_quote);
			preparedStatement5.setInt(2, id_user);
			preparedStatement5.executeUpdate();
		
		}

}
