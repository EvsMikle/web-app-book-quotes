package comm.octest.servlets;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import comm.octest.beans.Observer;
import comm.octest.beans.ProxyAccess;
import comm.octest.beans.User;

@WebServlet(name = "Registration", value = "/registration")
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Registration() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.getServletContext().getRequestDispatcher("/WEB-INF/registration.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Observer user = new User();
		String name = request.getParameter("full_name");
		String email = request.getParameter("email");
		String city = request.getParameter("city");
		String password = request.getParameter("password");
		String passwordc = request.getParameter("passwordc");
		
		
		try {
			String hashPassword = user.hashPassword(password);

			user.setEmail(email);
			user.setPassword(hashPassword);
			user.setCity(city);
			user.setName(name);
			
			ProxyAccess access = new ProxyAccess() ;
			access.AddAccess(user) ;

		} catch (NoSuchAlgorithmException e1) {
			
			e1.printStackTrace();
		}
		

	

		boolean uniq = false;
		try {
		
		
			uniq = user.validerInput(name, email, password, passwordc);
			
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

		if (uniq) {
			 try {
			user.registration(user);
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
			response.sendRedirect("auth");
		} else {
			response.sendRedirect("registration");
		}

	}

}
