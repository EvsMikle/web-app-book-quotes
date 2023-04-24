package comm.octest.beans;

import java.sql.SQLException;
import java.util.List;

public interface Flyweight {

	String getAuthor_name();

	String getName_book();

	String getQuoteText();

	int getId_quote();

	List<QuoteManager> fetchQuoteAuthorship() throws SQLException;

	int insertQuote() throws SQLException;

	int insertQuoteAuthorship() throws SQLException;

	List<QuoteManager> fetchQuotes(int user_id) throws SQLException;

	void updateQuote(Flyweight quote) throws SQLException;

	void setName_book(String name_book);

	void setQuoteText(String quote_text);

	List<QuoteManager> fetchFavQuotes(int user_id) throws SQLException;

	List<Flyweight> getNotification(int id_user) throws SQLException;

	void setId_quote(int quote_id);

	void setUserId(int user_id);

	

	List<QuoteManager> fetchMyQuotes(String email) throws SQLException;

	int getUserId();

	void removeNotification(int id_quote, int id_user) throws SQLException;
	
	 String getEmail() ;
	 void setEmail(String email);
	 List<QuoteManager> fetchUserQuotes(int user_id , int id_user_session) throws SQLException ;
	
}
