package comm.octest.servlets;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import comm.octest.beans.Observer;
import comm.octest.beans.User;

@WebServlet(name = "Settings", value = "/settings")
@MultipartConfig
public class Settings extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Settings() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("email");
		

		if (email != null) {
			Observer user = new User();
			int id_user = (Integer) session.getAttribute("user_id");

			try {

				List<User> userInfo = user.getInfo(email, id_user);
				request.setAttribute("userInfo", userInfo);

			} catch (SQLException e) {

				e.printStackTrace();
			}
			this.getServletContext().getRequestDispatcher("/WEB-INF/settings.jsp").forward(request, response);
		} else {
			response.sendRedirect("registration");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String email_session = (String) session.getAttribute("email");
		int id_user2 = (Integer) session.getAttribute("user_id");
		
		String full_name = request.getParameter("full_name");
		String email = request.getParameter("email");
		String city = request.getParameter("city");
		String country = request.getParameter("country");
		String password = request.getParameter("password");
		int idUserConnected = (Integer) session.getAttribute("user_id");
		
	Part filePart = request.getPart("picture");// "profile_picture" is the name of the input file element in the HTML form
		
	try {
	if(filePart != null) {
			System.out.println("££££" +filePart) ;
			
		//InputStream fileContent = filePart.getInputStream();
		
		
		String fileName = filePart.getSubmittedFileName(); // get the filename from the submitted file part
		String extension = "";

	

	    String uploadPath = "C:/Users/Simofatt/workspace3/test/src/main/webapp/WEB-INF/ressources/uploads/"+id_user2;
		 
		//String filePath = request.getServletContext().getRealPath("/") + "WEB-INF\\ressources\\uploads\\" + fileName;
// construct the file path using the web app root path and the "uploads" folder
//String filePath = "/test/src/main/webapp/WEB-INF/ressources/uploads"+fileName ;
		//System.out.println("££££" +filePath) ;
		//Files.copy(fileContent, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING); // save the file to disk
	    
	    FileOutputStream fos = new FileOutputStream(uploadPath);
	    InputStream is = filePart.getInputStream() ;
	    byte[] data = new byte[is.available()] ;
	    is.read(data);
	    fos.write(data);
	    fos.close();
	    
	}
	}catch (Exception e) { 
		e.printStackTrace() ;
	}


//String filePath = request.getServletContext().getRealPath("/") + "uploads/" + fileName; 
		try {

			Observer user = new User();
			List<User> userInfo = user.getInfo(email_session, idUserConnected);
			int id_user = user.getIdUser(email_session);
			user.setId_user(id_user);
			
			
			if (full_name != null && !full_name.isEmpty()) {
				user.setName(full_name);
			} else {
				user.setName(userInfo.get(0).getName()); // set the current value of name in the database
			}
			if (email != null && !email.isEmpty()) {
				user.setEmail(email);
				boolean valide = user.validEmail(email, email_session);
				if (valide) {
					session.setAttribute("email", email);
				}

			} else {
				user.setEmail(email_session);
			}
			if (city != null && !city.isEmpty()) {
				user.setCity(city);
			} else {
				user.setCity(userInfo.get(0).getCity());
			}
			if (country != null && !country.isEmpty()) {
				user.setCountry(country);
			} else {
				user.setCountry(userInfo.get(0).getCountry());
			}
			if (password != null && !password.isEmpty()) {
				
				try {
					String hashPassword = user.hashPassword(password);
					user.setPassword(hashPassword);
				} catch (NoSuchAlgorithmException e) {
				  	e.printStackTrace();
				}
			
			} else {
				user.setPassword(userInfo.get(0).getPassword());
			}

			boolean emailValide = user.updateUserInfo(user, email_session);
			System.out.println(emailValide);
			System.out.println(userInfo.get(0).getPassword());

			if (emailValide) {
				response.sendRedirect("settings");
			} else {
				request.setAttribute("userInfo", userInfo);
				this.getServletContext().getRequestDispatcher("/WEB-INF/settings.jsp?error=1").forward(request,
						response);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

}
