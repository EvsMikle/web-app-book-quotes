package comm.octest.dao;

import java.sql.SQLException;
import java.util.List;

import comm.octest.beans.Flyweight;
import comm.octest.beans.QuoteManager;

public interface QuoteDAO{
	
	void driver();
	List<QuoteManager> fetchQuotes(int user_id) throws SQLException;
	List<QuoteManager> fetchUserQuotes(int user_id,int id_user_session) throws SQLException;
	List<QuoteManager> fetchMyQuotes(String email) throws SQLException;
	List<QuoteManager> fetchFavQuotes(int user_id) throws SQLException;
	List<QuoteManager>fetchQuoteAuthorship() throws SQLException;
	int insertQuoteAuthorship(String quote_text, int user_id) throws SQLException ;
	int insertQuote(String book_name, String quote_text) throws SQLException;
	void updateQuote(Flyweight quote) throws SQLException;
	List<Flyweight> getNotification(int id_user) throws SQLException ;
	void removeNotification(int id_quote, int id_user) throws SQLException;

}
