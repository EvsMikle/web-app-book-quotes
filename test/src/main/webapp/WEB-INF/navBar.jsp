<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="comm.octest.beans.QuoteFlyweight" %>
<%@ page import="comm.octest.beans.Message" %>
<%@ page import="comm.octest.beans.QuoteManager" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%


int user_id = (int) session.getAttribute("user_id");
QuoteFlyweight quoteNotification = new QuoteManager();
List<QuoteFlyweight> notifications = quoteNotification.getNotification(user_id);  
int countNotif = notifications.size() ;
String emailNavBar= (String ) session.getAttribute("email") ;

Message message = new Message() ; 
List<Message> msgNotification = message.notification(emailNavBar) ;
int countMsgNotif = msgNotification.size() ;
%>
 <!--<nav class="navbar navbar-expand-lg navbar-dark bg-dark"> -->
  <nav class="navbar navbar-expand-lg">
        <a class="navbar-brand" href="index">Book Quotes</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
            aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item active">
                    <a class="nav-link" href="sharedQuotes">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="myQuotes">My Quotes</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                        data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Community
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item" href="people">People</a>
                        <a class="dropdown-item" href="discussion">Discussions</a>
                    </div>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="addQuote">Add Quotes</a>
                </li>
            </ul>
            <ul class="navbar-nav ml-auto">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="profileDropdown" role="button"
                        data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <i class="fas fa-user"></i>
                    </a>
                    <div class="dropdown-menu dropdown-menu-right" aria-labelledby="profileDropdown">
                        <a class="dropdown-item" href="profile">Profile</a>
                        <a class="dropdown-item" href="">Friends</a>
                        <a class="dropdown-item" href="discussion">Discussions</a>
                        <a class="dropdown-item" href="favQuotes">Favorite Quotes</a>
                        <a class="dropdown-item" href="addQuotes">Add Quote</a>
                        <a class="dropdown-item" href="settings">Account Settings</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="#">Sign Out</a>
                    </div>
                </li>
                 
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="notifDropdown" role="button" data-toggle="dropdown"
                        aria-haspopup="true" aria-expanded="false">
                        <i class="far fa-bell"></i>
                        <span class="badge badge-warning"><%=countNotif %></span>
                    </a>
                  
                   <div class="dropdown-menu dropdown-menu-right" aria-labelledby="notifDropdown">
                   
                    <%
                                       
                                       for (QuoteFlyweight notifs : notifications ) { 
                                                           	int id_quote = notifs.getId_quote() ;
                                       %>
                
               
                 <a class="dropdown-item" href="sharedQuotes?id_quote=<%=id_quote%>">New Quote added!</a>

                   
    <%} %>
    <div class="dropdown-divider"></div>
    <a class="dropdown-item" href="#">View All Notifications</a>
    
   </div>

    

</li>



                          <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="notifDropdown" role="button" data-toggle="dropdown"
                        aria-haspopup="true" aria-expanded="false">
                         <i class="far fa-envelope"></i>
                        <span class="badge badge-warning"><%=countMsgNotif %></span>
                    </a>
                  
                   <div class="dropdown-menu dropdown-menu-right" aria-labelledby="notifDropdown">
                   
                    <%for (Message msgNotif : msgNotification ) { 
                    	String id_from = msgNotif.getFrom() ; 
                    	Message msg = new Message() ;
                    	String  from_name = msg.getName(id_from) ; 
                    	String msgs= msgNotif.getMsg() ;
                     %>
                
               
                 <a class="dropdown-item" href="chat?withClientId=<%=id_from%>"> <%=from_name %> : "<%=msgs %>" </a>

                   
    <%} %>
    <div class="dropdown-divider"></div>
    <a class="dropdown-item" href="#">View All Messages</a>
    
   </div>

    

</li>

            </ul>
        </div>
    </nav>
</body>
</html>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $(document).ready(function() {
        setInterval(function() {
            $.ajax({
                url: "/notifications",
                type: "GET",
                dataType: "json",
                success: function(response) {
                	 var notifications = JSON.parse(json);
                     var notificationsHtml = '';
                     for (var i = 0; i < notifications.length; i++) {
                         notificationsHtml += '<a class="dropdown-item" href="#">' + notifications[i] + '</a>';
                     }
                     document.getElementById('notifications').innerHTML = notificationsHtml;
                }
            });
        }, 5000); // Récupérer les notifications toutes les 5 secondes
    });
</script>