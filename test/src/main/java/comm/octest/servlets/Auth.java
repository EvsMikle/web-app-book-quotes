package comm.octest.servlets;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import comm.octest.beans.I_Quote;
import comm.octest.beans.Observer;
import comm.octest.beans.QuoteFactorySingleton;
import comm.octest.beans.User;

@WebServlet(name = "Auth", value = "/auth")
public class Auth extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private I_Quote quoteFactory = QuoteFactorySingleton.getInstance();

	public Auth() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.getServletContext().getRequestDispatcher("/WEB-INF/registration.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// INITIALISE THE ARRAY LIST , AND CREATE A USER TO ADD HIM INTO THAT ARRAYLIST
		Observer user = new User();
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		

		
		
		
	
		
		try {
			user.getAllUsers();
			String hashPassword = user.hashPassword(password);
			user.setEmail(email);
			user.setPassword(hashPassword);
		} catch (NoSuchAlgorithmException e1) {
			
			e1.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	

		// AUTHENTIFICATION

		boolean auth = user.authentification();
		request.setAttribute("auth", auth);

		// SESSION :
		if (auth) {
			try {
				
				HttpSession session = request.getSession();
				session.setAttribute("email", email);
				int user_id = user.getIdUser(email);
				session.setAttribute("user_id", user_id);
				Observer user2 = new User(email, password, quoteFactory); // use the object to add this user to the //
																			// // arrayList of
				quoteFactory.getObservers();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			this.getServletContext().getRequestDispatcher("/WEB-INF/registration.jsp").forward(request, response);

		} else {
			this.getServletContext().getRequestDispatcher("/WEB-INF/registration.jsp?error=1").forward(request,
					response);
		}
	}

}
