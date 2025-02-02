 	package comm.octest.dao.BookAuthor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import comm.octest.beans.Book;
import comm.octest.beans.BookFlyweight;
import comm.octest.dao.BookAuthorDAO;

public class BookDAOImp implements BookAuthorDAO<BookFlyweight>{

	Connection connexion = null;
	Statement statement = null;
	ResultSet resultat = null;
	
	public BookDAOImp() {}
	
	
	public void driver() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/goodQuotes", "root", "");
			System.out.println("Connexion reussite ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	    //FETCH BOOKS
		public List<BookFlyweight> fetch() throws SQLException {
			List<BookFlyweight> books = new ArrayList<>();

			driver();
			PreparedStatement preparedStatement = connexion.prepareStatement("SELECT b.*,t.name AS type_name, a.name AS author_name FROM books b INNER JOIN types t ON b.id_type = t.id_type INNER JOIN authors a ON b.id_author = a.id_author");
			ResultSet resultat = preparedStatement.executeQuery();

			while (resultat.next()) {
				String book_name = resultat.getString("name");
				String type_name = resultat.getString("type_name");
				String author_name = resultat.getString("author_name");
				String img_book = resultat.getString("book_img");
				Book book = new Book(book_name, type_name, author_name,img_book);
				
				books.add(book);
			}
			return books;
		}

	    //INSERT BOOK (FLYWEIGHT) 
		public void insert(BookFlyweight book) throws SQLException {
			String book_name = book.getName_book() ; 
			String type_name = book.getType();
			String author_name = book.getAuthor();
			String img_book = book.getBook_img() ;

			driver();
			
			PreparedStatement preparedStatement2 = connexion.prepareStatement("SELECT id_type FROM types WHERE name =?");
			preparedStatement2.setString(1, type_name);
			ResultSet resultat2 = preparedStatement2.executeQuery();

			if (resultat2.next()) {
				int id_type = resultat2.getInt("id_type");
				PreparedStatement preparedStatement3 = connexion.prepareStatement("SELECT id_author FROM authors WHERE name =?");
				preparedStatement3.setString(1, author_name);
				ResultSet resultat3 = preparedStatement3.executeQuery();

				if (resultat3.next()) {
					int id_author = resultat3.getInt("id_author");
					PreparedStatement preparedStatement = connexion.prepareStatement("INSERT INTO books(name,book_img,id_type,id_author) VALUES (?,?,?,?)");
					preparedStatement.setString(1, book_name);
					preparedStatement.setString(2, img_book);
					preparedStatement.setInt(3, id_type);
					
					preparedStatement.setInt(4, id_author);
					preparedStatement.executeUpdate();
					System.out.println("book bien saisie ! " + book_name);
				}
				
			}
		}

		
	    //UPDATE AUTHOR(FLYWEIGHT)
		public void update(BookFlyweight book) throws SQLException { 
			    String book_name = book.getName_book();
			    int id_book = book.getId_book() ;
			
			    driver();
				PreparedStatement preparedStatement2 = connexion.prepareStatement("UPDATE books SET name =?  WHERE id_book=? ");
				preparedStatement2.setString(1, book_name);
				preparedStatement2.setInt(2, id_book);
				preparedStatement2.executeUpdate();		
				
			}
				
		
		
		//REMOVE BOOK 
		public void remove(BookFlyweight book) throws SQLException {
			int id_book = book.getId_book();
			driver();
			System.out.println("DELETE BOOK") ;
			PreparedStatement preparedStatement5 = connexion.prepareStatement("DELETE FROM books WHERE id_book=? ");
			
			preparedStatement5.setInt(1, id_book);
			 preparedStatement5.executeUpdate();
		}
		
		
		//UPDATE ID BOOK IN QUOTES TABLE WHEN UPDATE (FLYWEIGHT)
		public void updateId(BookFlyweight book) throws SQLException {
			int id_quote = book.getId_quote();
			int id_book = book.getId_book() ;
			 
			driver();
			PreparedStatement preparedStatement4 = connexion.prepareStatement("UPDATE quotes SET id_book = ?  WHERE id_quote=?");
			preparedStatement4.setInt(1, id_book);
			preparedStatement4.setInt(2, id_quote);
			preparedStatement4.executeUpdate();
	
		}
		
		
		

	
	


	
}
