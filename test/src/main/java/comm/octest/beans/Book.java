package comm.octest.beans;

import java.sql.SQLException;
import java.util.List;

import comm.octest.dao.BookAuthorDAO;
import comm.octest.dao.BookAuthor.BookDAOImp;

public class Book implements BookFlyweight {
	private int id_book;
	private String name_book;
	private String book_img;
	//private String author; // 
	private AuthorFlyweight author;   // association  avec linterface author
	private String type;
	private Flyweight quote;     // composition avec linterface quote
	//private int id_quote;
	private BookAuthorDAO<BookFlyweight>  bookDAO;

	
	public Book(String name_book, String type, String authorName,String book_img) {
		quote = new QuoteManager() ;
		author = new Author() ;
		bookDAO = new BookDAOImp() ;
		this.name_book = name_book;
		this.type = type;
		this.book_img = book_img;
		setAuthor(authorName);
	
	}

	
	public Book() {
		
		bookDAO = new BookDAOImp() ;
	}

	
	//GET BOOKS
	public List<BookFlyweight> fetchBooks() throws SQLException {
		
		List<BookFlyweight> books = bookDAO.fetch();
		return books;
	}

	//INSERT BOOK
	public void save(BookFlyweight book) throws SQLException {
	
		bookDAO.insert(book);
		System.out.println("Book bien saisie ! " + name_book);
	}
	
	//UPDATE BOOK 
	public void updateBook(BookFlyweight book2) throws SQLException { 
		bookDAO.update(book2);
	}

	
	//REMOVE BOOK 
	public void removeBook(BookFlyweight book) throws SQLException { 
		bookDAO.remove(book);
	}
	
	//UPDATE ID_BOOK IN THE QUOTE TABLE
	public void updateId(BookFlyweight book) throws SQLException { 
		bookDAO.updateId(book);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// GETTERS AND SETTERS
	public int getId_book() {
		return id_book;
	}

	public void setId_book(int id_book) {
		this.id_book = id_book;
	}

	public String getName_book() {
		return name_book;
	}

	public void setName_book(String name_book) {
		this.name_book = name_book;
	}

	public String getBook_img() {
		return book_img;
	}

	public void setBook_img(String book_img) {
		this.book_img = book_img;
	}

	public String getAuthor() {
		return author.getAuthor_name();
	}

	public void setAuthor(String authorName) {
		author.setAuthor_name(authorName) ;
		//this.author = author;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public int getId_quote() {
		return quote.getAuthor_id();
	}

	public void setId_quote(int id_quote) {
		quote.setAuthor_id(id_quote);
	}


	

}
