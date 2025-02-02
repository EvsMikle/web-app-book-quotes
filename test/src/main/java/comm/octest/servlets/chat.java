package comm.octest.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import comm.octest.beans.Message;

@WebServlet(name = "chat", value = "/chat")
public class chat extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public chat() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		 HttpSession session = request.getSession();
		  Message msg = new Message() ;
	
		  String email =  (String) session.getAttribute("email");
          String withClientId = (String) request.getParameter("withClientId");
     
          
           
            ArrayList<Message> my_messages = new ArrayList<Message>();
            try {
            	
                my_messages = msg.getMessagesWithUser(withClientId,email);
                String withClientName = msg.getName(withClientId) ; 
                System.out.println("************" +withClientName);
                request.setAttribute("withClientName" ,withClientName);
                
                request.setAttribute("my_messages", my_messages); 
                } catch (Exception e) {
                	System.out.println(e) ;
            }
        
            
            

		this.getServletContext().getRequestDispatcher("/WEB-INF/chat.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nom = request.getParameter("nom");
		request.setAttribute("nom", nom);
		this.getServletContext().getRequestDispatcher("/WEB-INF/chat.jsp").forward(request, response);
	
	}

}
