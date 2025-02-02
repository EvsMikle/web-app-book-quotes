package comm.octest.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import comm.octest.beans.AuthorFactory;
import comm.octest.beans.BookFactory;
import comm.octest.beans.Flyweight;
import comm.octest.beans.QuoteFactory;
import comm.octest.beans.QuoteManager;

@WebServlet(name = "MyQuotes", value = "/myQuotes")
public class MyQuotes extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MyQuotes() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("email");

		if (email != null) {

			Flyweight quoteManager = new QuoteManager();
			try {
				List<QuoteManager> quotes = quoteManager.fetchMyQuotes(email);
				request.setAttribute("quotes", quotes);

			} catch (SQLException e) {

				e.printStackTrace();
			}
			this.getServletContext().getRequestDispatcher("/WEB-INF/myQuotes.jsp").forward(request, response);
		} else {
			response.sendRedirect("registration");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
		HttpSession session = request.getSession();
		String author_name = request.getParameter("author_name");
		String name_book = request.getParameter("book_name");
		int id_quote = Integer.parseInt(request.getParameter("id_quote"));
		String quote_text = request.getParameter("quote_text");
		int id_author = Integer.parseInt(request.getParameter("id_author"));
		int id_book= Integer.parseInt(request.getParameter("id_book"));
		int index= Integer.parseInt(request.getParameter("index"));
		int id_user = (Integer)session.getAttribute("user_id") ;
		String type= request.getParameter("type");
		String email = (String) session.getAttribute("email");

		Flyweight quoteManager2 = new QuoteManager(name_book, quote_text, author_name, id_quote,id_user,id_author,id_book,type);
		List<QuoteManager> quotes = quoteManager2.fetchMyQuotes(email); 
		
		
		
		
		
		try {
			if(quote_text !=null && !quote_text.isEmpty()) { 
			QuoteFactory quoteFactory = new QuoteFactory() ; 
		    quoteManager2.setName_book(quotes.get(index).getName_book()) ;
			quoteFactory.updateQuote(quoteManager2) ;
			
			}
		} catch (java.sql.SQLIntegrityConstraintViolationException ex) {
			System.out.print("************************** UPDATING THE SAME QUOTE ****************");	
			  
		}
		
	      
		 if(author_name !=null && !author_name.isEmpty()) { 
			  if(!author_name.equals(quotes.get(index).getAuthor_name() )) { 
			AuthorFactory authorFactory = new AuthorFactory() ; 
	        authorFactory.updateAuthor(quoteManager2);
			  }
		 }
		 
		 if(name_book !=null && !name_book.isEmpty() ) { 
             if(!name_book.equals(quotes.get(index).getName_book() )) { 
		        BookFactory bookFactory = new BookFactory() ; 
		        quoteManager2.setAuthor_name(quotes.get(index).getAuthor_name()) ;
	            bookFactory.updateBook(quoteManager2);
             }
		 }
		
		

		System.out.println("UPDATE INFOS => name of the author :  " + author_name + " name of the book : " + name_book
				+ "Id of the quote: " + id_quote +" QUOTE : " +quote_text);

		response.sendRedirect("myQuotes");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
