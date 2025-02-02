<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="comm.octest.beans.QuoteManager" %>
<%@ page import ="java.time.LocalDate" %> 
<%@ page import ="java.time.format.DateTimeFormatter" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.time.LocalDateTime" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Book Quotes</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
       <style>   <%@include file="/WEB-INF/ressources/css/myQuotes.css"%> </style>
</head>
<%



ArrayList<QuoteManager>  favQuotes = (ArrayList<QuoteManager>) request.getAttribute("favQuotes");
%>
<body>
  <%@include file="navBar.jsp"%>
   
        
    <div class="container my-4">
   	<h5 style="margin-left: 50px;">Favorite Quotes</h5> 
   	<% 
	for (QuoteManager q : favQuotes) {
		String author_name = q.getAuthor_name();
		String quote_text = q.getQuoteText();
		String book_name = q.getName_book();
		String user_name = q.getUser_name();
		Timestamp created_at = q.getCreated_at(); // get the Timestamp object
		LocalDateTime localDateTime = created_at.toLocalDateTime(); // convert to LocalDateTime
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy"); // create a formatter
		String formattedDate = localDateTime.format(formatter); // format the LocalDateTime object
		int quote_id = q.getId_quote() ;
    	String like_color = q.getLike_color();
    	String book_img = q.getBook_img() ;
     	String email = q.getEmail() ;
	%>
	
		<div class="card mb-4">
			<div class="row no-gutters">
				<div class="col-md-4">
					<img src="<%=book_img %>"
                        class="card-img" alt="<%=book_name%>">
				</div>
				<div class="col-md-8">
					<div class="card-body">
						 <form id="form_<%=quote_id%>" action="favQuotes" method="post" >
                    <h5 class="card-title"  id="quote">"<%=quote_text %>"</h5>
                    <br>
                       <p class="card-text"><small class="text-muted">Book name :   <%=book_name %></small></p>
                        <p class="card-text"><small class="text-muted">Published: <%=formattedDate %></small></p>
                        <p class="card-text"><small class="text-muted">Author : <%=author_name %></small></p>
                        <p class="card-text"><small class="text-muted">Added By: <a href="profile?email=<%=email %>"><%=user_name %></a></small></p>
                          <input type="hidden" name="quoteId" value="<%=quote_id%>">    
                          <br>                    
                        <a href="#" class="card-link favorite "  style="color:<%= like_color %>;" onclick="submitForm(<%=quote_id%>)"><i class="fas fa-heart"></i> Love</a>
                        </form>
					</div>
				</div>
			</div>
		</div>
	
	 <script>
    function submitForm(quoteId) {
        document.getElementById("form_" + quoteId).submit();
    }
</script>
	<%
	}
       
	%>
	<%
        if(favQuotes.isEmpty() ){ %>
        	<div class="alert alert-primary" role="alert">
        	  <p> You don't have any favorite quotes! </p>
        	  <a href="sharedQuotes">See quotes</a>
        	</div>	
       <%} %> 
	
        
       
      
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script>
        $('.favorite').on('click', function () {
            $(this).toggleClass('active');
        });
    </script>
</body>
</html>