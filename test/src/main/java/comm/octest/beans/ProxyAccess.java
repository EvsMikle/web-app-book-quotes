package comm.octest.beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProxyAccess implements I_Access {

	
	   private static final List<String> registeredUsernames = new ArrayList<String>();
	   private final Access access= new Access() ;
	   
	   
	   

	    public void AddAccess(Observer user) {
	      
	    	String email = user.getEmail() ; 
	    	registeredUsernames.add(email) ;
	    }
	    
	    public void removeAccess(Observer user) { 
	    	String email = user.getEmail() ; 
	    	registeredUsernames.remove(email) ;
	    }
	    
	    
	    @Override
	    public void grantAccess(Observer user, String pageName,HttpServletResponse response,HttpServletRequest request) throws IOException, ServletException {
	        if (registeredUsernames.contains(user.getEmail())) {
	        	
	        	System.out.println("User " + user.getEmail() + " is registered. Access granted to page " + pageName);
	        	  String jspPath = "/WEB-INF/" + pageName + ".jsp";
	        	   
	        	   
	        	    request.getRequestDispatcher(jspPath).forward(request, response);

	           
	        }else { 
	        
	            access.grantAccess(user, pageName,response, request);
	        }
	   
	    }



	
		
}
