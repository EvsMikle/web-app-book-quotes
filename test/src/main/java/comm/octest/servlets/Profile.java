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

import comm.octest.beans.Flyweight;
import comm.octest.beans.Observer;
import comm.octest.beans.QuoteManager;
import comm.octest.beans.User;

@WebServlet(name = "Profile", value = "/profile")
public class Profile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Profile() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
		HttpSession session = request.getSession();
		
		
		/**
		 * 2 EMAIL POSSIBLE EMAIL = of the user connected. 
		 * emailVisitPofile = of the profile that is being shown  WE SEND IT BY GET IN THE URL
		 * 
		 */

		String email = (String) session.getAttribute("email");
		String emailVisitProfile = request.getParameter("email");

		if (email != null) {
			int id_user_session = (Integer) session.getAttribute("user_id");
			Observer user = new User();
			Flyweight quoteManager = new QuoteManager();
			
          //IF IT EXISTS WE FETCH THE QUOTE OF THE EMAIL == THE USER THAT VISIT HIS PROFILE
				if (emailVisitProfile == null) {

					List<QuoteManager> quotes = quoteManager.fetchMyQuotes(email);
					request.setAttribute("quotes", quotes);
					List<User> userInfo = user.getInfo(email, id_user_session);
					request.setAttribute("userInfo", userInfo);
		//OR WE FETCH THE QUOTE OF emailVisitProfile  == THE USER VISIT ANOTHER PROFILE
				} else {
					int user_id= user.getIdUser(emailVisitProfile);
					List<QuoteManager> quotes = quoteManager.fetchUserQuotes(user_id,id_user_session);
					request.setAttribute("quotes", quotes);
					List<User> userInfo = user.getInfo(emailVisitProfile, id_user_session);
					request.setAttribute("userInfo", userInfo);
				}

			
			this.getServletContext().getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
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
			HttpSession session = request.getSession();

			 String addFriendForm = request.getParameter("addFriend");
			 String quoteId = request.getParameter("quoteId");
			
			 if(addFriendForm !=null) { 
				 
					int idUser1 = Integer.parseInt(request.getParameter("addFriend"));
					int idUser2 = (Integer) session.getAttribute("user_id");
					String emailFriend = request.getParameter("emailFriend");
					System.out.println(idUser1);
		
					Observer user = new User();
					user.addFriend(idUser1, idUser2);
					response.sendRedirect("profile?email=" + emailFriend);
					 }
			
			
			 if(quoteId !=null) { 
				 
					
					int quote_id = Integer.parseInt(quoteId);
					int idUser2 = (Integer) session.getAttribute("user_id");
				
					Observer user2 = new User();
					user2.setId_quote(quote_id);
					user2.setId_user(idUser2);
					user2.likedQuote(user2);
					
					String emailFriend = request.getParameter("emailFriend");
		
					response.sendRedirect("profile?email=" + emailFriend);

					
					

					
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

}
