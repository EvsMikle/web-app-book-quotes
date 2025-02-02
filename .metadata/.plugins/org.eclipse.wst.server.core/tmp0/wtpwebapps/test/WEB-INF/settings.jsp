<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.ArrayList"%>
   <%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>

<%@ page import="comm.octest.beans.User"%>
<%@ page import="java.time.LocalDate"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.time.LocalDateTime"%>

    
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Book Quotes</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/css/bootstrap.min.css">
    <script scr="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <style><%@include file="/WEB-INF/ressources/css/settings.css"%> </style>
</head>
<%

ArrayList<User> userInfo = new ArrayList<>();

 userInfo = (ArrayList<User>) request.getAttribute("userInfo") ;


String email = (String) session.getAttribute("email");
String error =  (String)request.getParameter("error") ; 






%>


<body>
   <%@include file="navBar.jsp"%>
   
    <div class="container rounded bg-white mt-5 mb-5">
        <div class="row">
                <%
for(User user : userInfo){
	String name = user.getName() ; 
	String country = user.getCountry() ; 
	String city = user.getCity() ;
    Timestamp created_at = user.getCreated_at() ; 
    int id_user = user.getId_user();
%>
  
            <div class="col-md-3 border-right">
                <div class="d-flex flex-column align-items-center text-center p-3 py-5">
                    <img class="rounded-circle mt-5" width="150px"
                        src="peopleProfilePicture?id_user=<%=id_user %>">
                    <span class="font-weight-bold"> <%=name %></span>
                    <span class="text-black-50"> <%=email%></span>
                    <span> </span>
                    <form action ="settings" method="post" enctype="multipart/form-data">
                    <div class="mt-3">
                        <input type="file" name="picture">
                        
                        
                    </div>
                    <br> <br> <br>
                       <button class="btn btn-primary profile-button" type="submit">Save Profile</button>
                    </form>
                </div>
            </div>
 


  
            <div class="col-md-9">
                <div class="p-3 py-5">
                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <h4 class="text-right">Profile Settings</h4>
                    </div>
                    <form action ="settings" method="post">
                    <div class="row">
                        <div class="col-md-12">
                            <label class="labels">Full Name</label>
                            <input type="text" class="form-control" placeholder=" <%=name %>" name="full_name">
                        </div>
                    </div>
                    <% 
                    if (error != null) {
	 %>
   <p> Email existant </p>
  <% 
}
%>
                    <div class="row mt-3">
                        <div class="col-md-12">
                            <label class="labels">Email</label>
                            <input type="text" class="form-control" placeholder=" <%=email %>" name="email">
                        </div>
                    </div>
                    <div class="row mt-3">
                        <div class="col-md-12">
                            <label class="labels">Password</label>
                            <input type="text" class="form-control" placeholder="*******" name="password">
                        </div>
                    </div>
                    <div class="row mt-3">
                        <div class="col-md-6">
                            <label class="labels">Country</label>
                            <input type="text" class="form-control" placeholder="<%=country %>" name="country">
                        </div>
                        <div class="col-md-6">
                            <label class="labels">State/Region</label>
                            <input type="text" class="form-control" placeholder=" <%=city%>" name="city">
                        </div>
                    </div>
                    <div class="mt-5 text-center">
                        <button class="btn btn-primary profile-button" type="submit">Save Profile</button>
                    </div>
                    </form>
                </div>
    
                                                  	<%
	}
	%>
            </div>
     
             
        </div>
         
       
         
    
      

    </div>




    <script src=" https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script>
        $('.favorite').on('click', function () {
            $(this).toggleClass('active');
        });
    </script>






</body>