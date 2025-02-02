package comm.octest.beans;

import java.sql.SQLException;

public class QuoteFactoryThreaded implements I_Quote {
    private QuoteFactory quoteFactory;
    private Flyweight quoteManager;

    public QuoteFactoryThreaded() {
        quoteFactory = new QuoteFactory(1);
    }

    @Override
    public Flyweight addQuote(String name_book, String quote_text, String author_name,int userId) throws SQLException {
        // create a new thread to handle the database insertion
        Thread insertionThread = new Thread(new QuoteFactoryRunnable(name_book, quote_text,author_name, userId, quoteFactory));

        // start the thread
        insertionThread.start();

        try {
            // wait for the thread to finish before continuing
            insertionThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return quoteManager;
        
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	@Override
	public void updateQuote(Flyweight quote) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addUserIds() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fetchQuotes(int user_id) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void putQuote(String key, Flyweight quote) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeQuote(String key, QuoteManager quote) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addObservers(Observer user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getObservers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyObservers(int id_quote) throws SQLException {
		// TODO Auto-generated method stub
		
	}
}
