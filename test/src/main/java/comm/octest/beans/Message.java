package comm.octest.beans;

import java.util.ArrayList;
import java.util.List;

import comm.octest.dao.UserDAO;
import comm.octest.dao.user.UserDAOImp;

public class Message {
    public String from;
    public String to;
    public String msg;
    public String type;
    public String fromName;
    
    private UserDAO dao;

    public Message(String from, String to, String msg, String type) {
        this.from = from;
        this.to = to;
        this.msg = msg;
        this.type = type;
        dao = new UserDAOImp() ;
    }
    public Message(String from, String msg,String fromName) {
        this.from = from;
        this.msg = msg;
        this.fromName = fromName;
        dao = new UserDAOImp() ;
      
    }
    public Message() {
    	 dao = new UserDAOImp() ;
    } 
    
  
    
  
	public List<Message> notification(String email ) throws Exception{ 
    	List<Message> notifs = new ArrayList<>()  ; 
    	notifs = dao.notification(email) ;
    	return notifs;
    }
    public void insertMsg (String to, String from, String msg, String type) throws Exception { 
    	 dao.insertMsg( to, from,  msg,  type) ;
    }
    
    public  void removeMsg (String withClientId,String email) throws Exception { 
    	dao.removeMsg(withClientId, email);
    }
    
   public  ArrayList<Message> getMessagesWithUser (String withClientId,String email) throws Exception { 
		ArrayList<Message> msg = new ArrayList<>()  ;
	   msg = dao.getMessagesWithUser(withClientId, email) ; 
	   return msg ;
	   
   }
   public String getName(String email) throws Exception { 
	 String name =  dao.getName(email) ;
	   return name ; 
   }
   
   
   
   
   public String getFrom() {
 		return from;
 	}
 	public void setFrom(String from) {
 		this.from = from;
 	}
 	public String getMsg() {
 		return msg;
 	}
 	public void setMsg(String msg) {
 		this.msg = msg;
 	}

}