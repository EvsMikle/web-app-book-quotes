<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Book Quotes</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <style>   <%@include file="/WEB-INF/ressources/css/addQuote.css"%> </style>
</head>

<%



  String success =  (String)request.getParameter("success");
%>


<body>
<%@include file="navBar.jsp"%>
   
    <div class="container my-4">
        <h2 style ="position : relative ; top : 20px; left :150px;">Add a Quote</h2>
        <br>
        <%
        if (success != null) {
	 %>
           <script> 
                            Swal.fire({
                            icon: 'success',
                            title: 'Success...',
                            text: 'Quote bien saisie!',
                            confirmButtonText: 'OK',
                            allowOutsideClick: false // disable clicking outside of the alert to close it
                            }).then((result) => {
                          
                            });
                        </script>
                    
    <% 

        }    
  %>
 
		<div class="card mb-4">
			<div class="row no-gutters">
              <div class="col-md-8">
                <div class="card-body">
				        <form method="post" action="addQuote">
				            <div class="form-group">
				                <label for="bookName">Book Name:</label>
				                <input type="text" class="form-control" id="bookName" name="name_book">
				            </div>
				             <div class="form-group">
							    <label for="bookType">Type:</label>
								    <select class="form-control" name="book_type" id="bookType" style="width:350px;">
								        <option value=""></option>
								        <option value="Novel">Novel</option>
								        <option value="Romance">Romance</option>
								        <option value="Fiction">Fiction</option>
								        <option value="Short story">Short story</option>
								         <option value="Fantasy">Fantasy</option>
								        <option value="Horror">Horror</option>
								        <option value="Poetry">Poetry</option>
								    </select>
							</div>

				            <div class="form-group">
				                <label for="authorName">Author Name:</label>
				                <input type="text" class="form-control" id="authorName" name="name_author">
				            </div>
				            <div class="form-group">
				                <label for="quote">Quote:</label>
				                <textarea class="form-control resize-none" id="quote" rows="3" name="quote_text"></textarea>
				            </div>
				
				            <div class="d-flex justify-content-center">
				                <button type="submit" class="btn btn-primary mx-3">Add Quote</button>
				                <button type="reset" class="btn btn-secondary mx-3">Clear</button>
				            </div>
				        </form>
    </div>
     </div>
      </div>
       </div>
     </div>
     

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