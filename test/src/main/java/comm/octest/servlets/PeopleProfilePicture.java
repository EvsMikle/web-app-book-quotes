package comm.octest.servlets;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "PeopleProfilePicture", value = "/peopleProfilePicture")
public class PeopleProfilePicture extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		

		int idPeople = Integer.parseInt(request.getParameter("id_user"));

		String filePath = "/WEB-INF/ressources/uploads/" + idPeople;

		// Create a File object with the file path
		File file = new File(getServletContext().getRealPath(filePath));
	

		if (file.exists()) {
		

			this.getServletContext().getRequestDispatcher(filePath).forward(request, response);

		} else {
			this.getServletContext().getRequestDispatcher(filePath).forward(request,
					response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}
}
