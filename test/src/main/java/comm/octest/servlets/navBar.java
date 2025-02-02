package comm.octest.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import comm.octest.beans.Flyweight;
import comm.octest.beans.I_Quote;
import comm.octest.beans.Observer;
import comm.octest.beans.ProxyAccess;
import comm.octest.beans.QuoteFactorySingleton;
import comm.octest.beans.QuoteManager;
import comm.octest.beans.User;

@WebServlet(name = "NavBar", value = "/navBar")
public class navBar extends HttpServlet {
	private I_Quote quoteFactory = QuoteFactorySingleton.getInstance();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		int user_id = (int) session.getAttribute("user_id");
		

Flyweight quoteManager = new QuoteManager() ;


List<Flyweight> notifications;
try {
	notifications = quoteManager.getNotification(user_id);
	String json = new Gson().toJson(notifications); // convert the notifications data to JSON
	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	response.getWriter().write(json); // write the JSON response back to the client
	
	

} catch (Exception e) {
	
	e.printStackTrace();
} 



	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Observer user = new User() ;
		HttpSession session = request.getSession();
		String email2= (String) session.getAttribute("email");
		user.setEmail(email2);
		
		//REMOVING THE USER FROM THE PROXY ACCESS 
		
		ProxyAccess access = new ProxyAccess() ;
		access.removeAccess(user);
		response.sendRedirect("registration");
		
		//REMOVING THE USER FROM THE OBSERVER LIST : 
		quoteFactory.removeObserver( user);
		
		
		

	}

}








