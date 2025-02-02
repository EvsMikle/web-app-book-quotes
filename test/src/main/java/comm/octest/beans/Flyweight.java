package comm.octest.beans;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public interface Flyweight {

	    
	    public List<QuoteManager> fetchQuotes(int user_id) throws SQLException;
	    
	    public List<QuoteManager> fetchUserQuotes(int user_id,int id_user_session) throws SQLException;
	    
	    public List<QuoteManager> fetchMyQuotes(String email_user) throws SQLException;
	    
	    public List<QuoteManager> fetchFavQuotes(int user_id) throws SQLException;
	    
	    public List<QuoteManager> fetchQuoteAuthorship() throws SQLException;
	    
	    public int insertQuote() throws SQLException;
	    
	    public int insertQuoteAuthorship(String quote_text,int user_id) throws SQLException;
	    
	    public void updateQuote(Flyweight quote) throws SQLException;
	    
	    public List<Flyweight> getNotification(int id_user) throws SQLException;
	    
	    public void removeNotification(int id_quote, int id_user) throws SQLException;
	    
	    public void removeQuote(int id_quote) throws SQLException;
	    
	    public void removeQuoteAuthorship (int id_quote,int user_id) throws SQLException;
	    
	    public List<String> getLikedUsers();
	    
	    public void addLikedUser(String userId, int id_quote, int id_user);
	    
	    public void notifyLikedUsers(int id_quote, int id_user);
	    
	    public void likeQuote(int user_id, int id_quote);
	    
	    public String getLike_color();
	    
	    public void setLike_color(String like_color);
	    
	    public void setLikedUsers(List<String> likedUsers);
	    
	    public String getName_book();
	    
	    public void setName_book(String name_book);
	    
	    public String getQuoteText();
	    
	    public void setQuoteText(String quoteText);
	    
	    public int getUserId();
	    
	    public void setUserId(int userId);
	    
	    public Timestamp getCreated_at();
	    
	    public void setCreated_at(Timestamp created_at);
	    
	    public String getAuthor_name();
	    
	    public void setAuthor_name(String author_name);
	    
	    public String getUser_name();
	    
	    public void setUser_name(String user_name);
	    
	    public int getId_quote();
	    
	    public void setId_quote(int id_quote);
	    
	    public void removeLikedUser(String userId);
	    
	    public String getEmail();
	    
	    public void setEmail(String email);
	    
	    public int getAuthor_id();
	    
	    public void setAuthor_id(int author_id);
	    
	    public int getBook_id();
	    
	    public void setBook_id(int book_id);
	    
	    public String getType();
	    
	    public void setType(String type);
	    
	    public int getId_type();
	    
	    public void setId_type(int id_type);
		public String getBook_img()  ;

		public void setBook_img(String book_img)  ;
	    
	
}
