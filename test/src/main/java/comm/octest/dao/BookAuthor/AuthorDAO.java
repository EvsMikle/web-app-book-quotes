package comm.octest.dao.BookAuthor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import comm.octest.beans.Author;
import comm.octest.beans.I_author;
import comm.octest.dao.BookAuthorDAO;

public class AuthorDAO implements BookAuthorDAO<I_author> {
	Connection connexion = null;
	Statement statement = null;
	ResultSet resultat = null;

	public AuthorDAO() {}

	public void driver() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/goodQuotes", "root", "");
			System.out.println("Connexion reussite ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//FETCH ALL THE AUTHORS : 
		public List<I_author> fetch() throws SQLException {

			driver();
			List<I_author> authors = new ArrayList<>();

			PreparedStatement preparedStatement = connexion.prepareStatement("SELECT name FROM authors");
			ResultSet resultat = preparedStatement.executeQuery();

			while (resultat.next()) {
				String author_name = resultat.getString("name");
				Author author = new Author(author_name);
				authors.add(author);
			}

			return authors;
		}

	//INSERT THE AUTHOR (FLYWEIGHT) 
		public void insert(I_author author) throws SQLException {
			
			String author_name = author.getAuthor_name();
			driver();
			PreparedStatement preparedStatement = connexion.prepareStatement("INSERT INTO authors(name) VALUES (?)");
			preparedStatement.setString(1, author_name);
			preparedStatement.executeUpdate();

		}

	


}
