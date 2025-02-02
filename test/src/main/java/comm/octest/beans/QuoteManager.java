package comm.octest.beans;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import comm.octest.dao.quote.QuoteDAOImp;

//QUOTE == STACK GRABBER IMPLEMENTS SUBJECT == REMOVE ADD NOTIFY OBSERVERS 
public class QuoteManager implements Flyweight {
	private String name_book;
	private String quote_text;  
	private int user_id;  
	private Timestamp created_at;  
	private String author_name; 
	private String user_name;   
	private String like_color;  
	private QuoteDAOImp quoteDAO;  
	private int id_quote;     
	private String email;   
	private int author_id ; 
	private int book_id ; 
	private String type ;
	private int id_type;  
	private String book_img ; 
	private List<String> likedUsers = new ArrayList<>();
	

	public QuoteManager() {
		quoteDAO = new QuoteDAOImp();

	};

	// CONSTRUCTOR TO ADD QUOTE IN QUOTE FACTORY
	public QuoteManager(String name_book, String quoteText, String author_name) {
		this.name_book = name_book;
		this.quote_text = quoteText;
		//this.user_id = userId;
		 this.author_name = author_name;
		quoteDAO = new QuoteDAOImp();
	}

	public QuoteManager(String quote_text, int user_id) {
		this.user_id = user_id;
		this.quote_text = quote_text;

	}
	
	
	// FETCH THE QUOTES
	public QuoteManager(String name_book, String quoteText, String author_name, Timestamp created_at, String user_name,int id_quote, String like_color,String email,String book_img) {
		this.name_book = name_book;
		this.quote_text = quoteText;
		this.author_name = author_name;
		this.created_at = created_at;
		this.user_name = user_name;
		this.id_quote = id_quote;
		this.like_color = like_color;
		this.email = email ;
		this.book_img = book_img;
	}

	

	// FETCH MY QUOTES
	public QuoteManager(String name_book, String quoteText, String author_name, Timestamp created_at, String user_name,int id_quote,String email,int author_id,int book_id,String type,String book_img) {
		this.name_book = name_book;
		this.quote_text = quoteText;
		this.author_name = author_name;
		this.created_at = created_at;
		this.user_name = user_name;
		this.id_quote = id_quote;
		this.author_id = author_id; 
		this.book_id = book_id;
		this.email = email; 
		this.type = type ;
		this.book_img = book_img ;

	}

	// UPDATE THE QUOTE
	public QuoteManager(String name_book, String quoteText, String author_name, int id_quote,int id_user,int id_author,int book_id,String type) {
		this.name_book = name_book;
		this.quote_text = quoteText;
		this.author_name = author_name;
		this.id_quote = id_quote;
		this.user_id = id_user;
		this.author_id = id_author;
		this.book_id = book_id;
		this.type = type;
		quoteDAO = new QuoteDAOImp();
	
	}

	

	// FETCH ALL THE QUOTES
	public List<QuoteManager> fetchQuotes(int user_id) throws SQLException {
		List<QuoteManager> quotes = quoteDAO.fetchQuotes(user_id);
		return quotes;
	}
	// FETCH ALL THE QUOTES
	public List<QuoteManager> fetchUserQuotes(int user_id,int id_user_session) throws SQLException {
		List<QuoteManager> quotes = quoteDAO.fetchUserQuotes(user_id,id_user_session);
		return quotes;
	}
	// FETCH MY QUOTES
	public List<QuoteManager> fetchMyQuotes(String email_user) throws SQLException {
		List<QuoteManager> quotes = quoteDAO.fetchMyQuotes(email_user);
		return quotes;
	}

	// FETCH FAV QUOTES
	public List<QuoteManager> fetchFavQuotes(int user_id) throws SQLException {
		List<QuoteManager> favQuotes = quoteDAO.fetchFavQuotes(user_id);
		return favQuotes;
	}

	// FETCH Quote_AUTHORSHIP
	public List<QuoteManager> fetchQuoteAuthorship() throws SQLException {
		List<QuoteManager> user_quote = quoteDAO.fetchQuoteAuthorship();
		return user_quote;
	}

	// INSERT QUOTE
	public int insertQuote() throws SQLException {
		int id_quote = quoteDAO.insertQuote(this.name_book, this.quote_text);
		System.out.println("Book bien saisie ! " + name_book);
		return id_quote;
	}

	// INSERT QUOTE AUTHORSHIP
	public int insertQuoteAuthorship(String quote_text,int user_id) throws SQLException {
		int id_quote =  quoteDAO.insertQuoteAuthorship(quote_text, user_id);
		System.out.println("user_id : " + user_id + " SQUOTE ID " + quote_text);
return id_quote;
	
	}

	// UPDATE A QUOTE
	public void updateQuote(Flyweight quote) throws SQLException {
		quoteDAO.updateQuote(quote);

	}
	
	
	// GET NOTIFICATIONS :
	public List<Flyweight> getNotification(int id_user) throws SQLException {
		List<Flyweight> notifications = new ArrayList<>();
		notifications = quoteDAO.getNotification(id_user);
		return notifications;
	}

	// DELETE THE NOTIFICATION :
	public void removeNotification(int id_quote, int id_user) throws SQLException {
		quoteDAO.removeNotification(id_quote, id_user);
	}

	
	//REMOVE QUOTE 
	public void removeQuote(int id_quote) throws SQLException { 
		quoteDAO.removeQuote(id_quote);
	}
	public void removeQuoteAuthorship (int id_quote,int user_id) throws SQLException { 
		quoteDAO.removeQuoteAuthorship(id_quote, user_id);
	}
	public List<String> getLikedUsers() {
		return likedUsers;
	}

	public void addLikedUser(String userId, int id_quote, int id_user) {
		if (!likedUsers.contains(userId)) {
			likedUsers.add(userId);
		}
		notifyLikedUsers(id_quote, id_user);
	}

	
	

	public void notifyLikedUsers(int id_quote, int id_user) {
		// SQL SEQUEST TO ADD IN TH NOTIICATION TABLE
	}

	public void likeQuote(int user_id, int id_quote) {
		// TODO Auto-generated method stub

	}

	// GETTERS AND SETTERS
	public String getLike_color() {
		return like_color;
	}

	public void setLike_color(String like_color) {
		this.like_color = like_color;
	}

	public void setLikedUsers(List<String> likedUsers) {
		this.likedUsers = likedUsers;
	}

	public String getName_book() {
		return name_book;
	}

	public void setName_book(String name_book) {
		this.name_book = name_book;
	}

	public String getQuoteText() {
		return quote_text;
	}

	public void setQuoteText(String quoteText) {
		this.quote_text = quoteText;
	}

	public int getUserId() {
		return user_id;
	}

	public void setUserId(int userId) {
		this.user_id = userId;
	}

	public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}

	public String getAuthor_name() {
		return author_name;
	}

	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public int getId_quote() {
		return id_quote;
	}

	public void setId_quote(int id_quote) {
		this.id_quote = id_quote;
	}

	public void removeLikedUser(String userId) {
		// TODO Auto-generated method stub

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAuthor_id() {
		return author_id;
	}

	public void setAuthor_id(int author_id) {
		this.author_id = author_id;
	}
	public int getBook_id() {
		return book_id;
	}

	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public int getId_type() {
		return id_type;
	}

	public void setId_type(int id_type) {
		this.id_type = id_type;
	}

	public String getBook_img() {
		return book_img;
	}

	public void setBook_img(String book_img) {
		this.book_img = book_img;
	}


}
