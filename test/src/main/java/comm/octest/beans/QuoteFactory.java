package comm.octest.beans;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuoteFactory implements I_Quote {
	private static   Map<String, QuoteFlyweight> quotes = new HashMap<>();
	private Map<String, Integer> user_quotes = new HashMap<>();
	private QuoteFlyweight quoteManager;
	private List<Observer> observers;

	public QuoteFactory() {
		quoteManager = new QuoteManager();
		observers = new ArrayList<>();
	}

	public QuoteFactory(int nothing) {
		quoteManager = new QuoteManager();
	}

	public QuoteFlyweight addQuote(String name_book, String quote_text, int userId) throws SQLException {
		// CREATE THE KEYS
		String key = name_book + quote_text;
		String key2 = quote_text + userId;

		// REMPLISSAGE DES MAPS AVEC LE CONTENUE DE LA BASE DE DONNEE
		fetchQuotes(userId);
		addUserIds();
		
		// SEARCHING FOR THE QUOTE IF IT EXISTS IN THE QUOTES MAP
		//quoteManager = (QuoteManager) quotes.get(key);
		// IF NOT WE ADD IT AND WE INSERT IT IN THE DB
		if (!quotes.containsKey(key)) {
			quoteManager = new QuoteManager(name_book, quote_text, userId);
			//putQuote(key, quoteManager);
			int id_quote = quoteManager.insertQuote();
			quoteManager.insertQuoteAuthorship();
			QuoteFactorySingleton.getInstance().notifyObservers(id_quote);
			
		}

		// IN THE QUOTE AUTHORSHIP MAP IF DOSNT EXISTS WE ADD IT IN THE MAP AND WE
		// INSERT IT IN THE DB
		if (!user_quotes.containsKey(key2)) {
			user_quotes.put(key2, userId);
			// JUST TO CREATE THE OBJECT QUOTE ANYWAY AND TO NOTIFY THE OBSERVERS OF ANY
			// CHANGES
			if (quotes.containsKey(key)) {
				quoteManager = new QuoteManager(name_book, quote_text, userId);
				int id_quote = quoteManager.insertQuoteAuthorship();
				getObservers();
				QuoteFactorySingleton.getInstance().notifyObservers(id_quote);
			} else if (!quotes.containsKey(key)) {
				//quoteManager.insertQuoteAuthorship();
			}

		}

		return quoteManager;
	}
	
	public void updateQuote(QuoteFlyweight quote) throws SQLException{
		
		String name_book = quote.getName_book();
		String quote_text = quote.getQuoteText();
		int id_quote = quote.getId_quote();
		int userId = quote.getUserId() ; 
		
	    String key = name_book + quote_text;
	    String key2 = quote_text + userId;
		
		fetchQuotes(userId);
		addUserIds();
		System.out.println("*********** KEY    :  "+key);
				
        if (!quotes.containsKey(key)) {
					quoteManager = new QuoteManager(name_book, quote_text, userId);
					putQuote(key, quoteManager);
					quoteManager.updateQuote(quote) ;
					//int id_quote = quoteManager.insertQuote();
					//QuoteFactorySingleton.getInstance().notifyObservers(id_quote);
					
		 }else  { 
					if (!user_quotes.containsKey(key2)) {
						user_quotes.put(key2, userId);
							 quoteManager = new QuoteManager(name_book, quote_text, userId);
							 quoteManager.insertQuoteAuthorship();
							 quoteManager.removeQuote(id_quote) ; 
							 quoteManager.removeQuoteAuthorship(id_quote,userId) ;
							//QuoteFactorySingleton.getInstance().notifyObservers(id_quote);
						} 
				}							
	}

	public void addUserIds() throws SQLException {
		List<QuoteManager> userQuoteList = quoteManager.fetchQuoteAuthorship();
		for (QuoteManager userQuote : userQuoteList) {
			String quote_text = userQuote.getQuoteText();
			int user_id = userQuote.getUserId();
			String key = quote_text + user_id;
			user_quotes.put(key, user_id);
		}

	}

	public void fetchQuotes(int user_id) throws SQLException {
		List<QuoteManager> fetchedQuotes = quoteManager.fetchQuotes(user_id);
	
		for (QuoteManager q : fetchedQuotes) {
	
			String name_book = q.getName_book();
			String quote_text = q.getQuoteText();
			String key = name_book + quote_text;
			
		    putQuote(key, quoteManager);
		    System.out.println("*********** :  "+q.getName_book()+q.getQuoteText()) ;
		}
	}

	
	// ADD A QUOTE FROM THE MAP
	public  void putQuote(String key, QuoteFlyweight quote) {
		if (!quotes.containsKey(key)) {
		quotes.put(key, quote);
	}
	}

	// REMOVE A QUOTE FROM THE MAP
	public void removeQuote(String key, QuoteManager quote) {
		quotes.remove(key);
	}

	// ADD OBSERVERS
	public void addObservers(Observer user) {

		if (!observers.contains(user)) {

			observers.add(user);
		}

	}

	// REMOVE OBSERVERS
	public void removeObserver(Observer user) {
		observers.remove(user);
	}

	// GET OBSERVERS
	public void getObservers() {
		for (Observer ob : observers) {
			System.out.println("observers please : " + ob.getEmail());
		}
	}

	// NOTIFY THE OBSERVER THAT THE THERE HAS BEEN A CHANGE
	public void notifyObservers(int id_quote) throws SQLException {

		for (Observer ob : observers) {
			System.out.println("observers please : " + ob.getEmail());
			ob.update(id_quote);
		}
	}
}
