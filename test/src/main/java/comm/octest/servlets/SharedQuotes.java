package comm.octest.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import comm.octest.beans.Flyweight;
import comm.octest.beans.Observer;
import comm.octest.beans.QuoteManager;
import comm.octest.beans.User;

@WebServlet(name = "SharedQuotes", value = "/sharedQuotes")
public class SharedQuotes extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SharedQuotes() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			String email = (String) session.getAttribute("email");

			if (email != null) {
				int user_id = (int) session.getAttribute("user_id");
				String id_quote = request.getParameter("id_quote");

				if (id_quote != null) {

					int idQuote = Integer.parseInt(id_quote);
					Flyweight quoteManager = new QuoteManager();
					List<QuoteManager> quotes = quoteManager.fetchQuotes(user_id);
					request.setAttribute("quotes", quotes);
					
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/sharedQuotes.jsp");
					dispatcher.forward(request, response);
					response.getWriter().append("#form_" + idQuote);

					quoteManager.removeNotification(idQuote, user_id);

				} else {

					Flyweight quoteManager = new QuoteManager();
					List<QuoteManager> quotes = quoteManager.fetchQuotes(user_id);
					request.setAttribute("quotes", quotes);

					this.getServletContext().getRequestDispatcher("/WEB-INF/sharedQuotes.jsp").forward(request,
							response);
				}
			} else {
				response.sendRedirect("registration");
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String quoteId = request.getParameter("quoteId");
			int quote_id = Integer.parseInt(quoteId);
			HttpSession session = request.getSession();
			int user_id = (int) session.getAttribute("user_id");

			Observer user = new User();
			user.setId_quote(quote_id);
 			user.setId_user(user_id);
			user.likedQuote(user);

		} catch (SQLException e) {

			e.printStackTrace();
		}
		response.sendRedirect("sharedQuotes");

	}

}
