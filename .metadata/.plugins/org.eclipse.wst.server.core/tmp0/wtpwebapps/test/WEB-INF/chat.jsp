<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import ="java.util.List" %>
<%@ page import="comm.octest.beans.ChatRoom" %>
<%@ page import="comm.octest.dao.user.UserDAOImp" %>
<%@ page import="java.lang.reflect.Array" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="comm.octest.beans.Message" %>


   
    
      

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Insert title here</title>
         <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        
            <%
                    

                                        
                                         
                                                    String email =  (String) session.getAttribute("email");
                                                    String withClientId = (String) request.getParameter("withClientId");
                                              
                                                    
                                                    UserDAOImp user = new UserDAOImp();
                                                    
                                                      ArrayList<Message> my_messages = new ArrayList<Message>();
                                                      my_messages = (ArrayList<Message>) request.getAttribute("my_messages") ; 
                                                      String  withClientName = (String) request.getAttribute("withClientName") ;
                                                      int idUser =  user.getId(withClientId) ; 
                                                     
                                             
                                                      
                                                      try {
                                                          user.removeMsg(withClientId,email);
                                                      } catch (Exception e) {

                                                      }
                    %>
           <style><%@include file="/WEB-INF/ressources/css/chat.css"%> </style>
           
   <script type="text/javascript">

 
      window.addEventListener("load", function (event) {
    	  let pseudo = "<%=(email)%>";
          let withClientId = "<%=(withClientId)%>";
        let ws = new WebSocket("ws://localhost:8085/test/chatroom/" + pseudo);
        let txtHistory = document.getElementById("discussion");
        let txtMessage = document.getElementById("txtMessage");
        let showInputFile = document.getElementById("showInputFile");

        txtMessage.focus();

        ws.addEventListener("open", function (evt) {
            console.log("Connection established");
        });
        
        let btnSend = document.getElementById("btnSend");
        let fileInput = document.getElementById("fileInput");

        showInputFile.addEventListener("click", function () {
            if (fileInput.style.display=="none"){
                fileInput.style.display="block";
                txtMessage.style.display="none";
            }
            else {
                fileInput.style.display="none";
                txtMessage.style.display="block";
            }
            console.log("show");
        })

        btnSend.addEventListener("click", function (clickEvent) {
        	
            let message = {
                to: withClientId,
                from : pseudo,
                message: txtMessage.value
            };

            if (fileInput.files.length > 0) {
                let file = fileInput.files[0];
                let reader = new FileReader();
                reader.onload = function (loadEvent) {
                    let fileData = loadEvent.target.result.split(",")[1];
                    message.file = {
                        name: file.name,
                        type: file.type,
                        data: fileData
                    };
                    ws.send(JSON.stringify(message));
                };
                reader.readAsDataURL(file);
                txtHistory.innerHTML += '<div class="chat outgoing"> <div class="details"> <p>'+ 'Vous avez envoyé un fichier' + '</p></div> </div>';
                txtMessage.value = "";
                txtMessage.focus();
            } else {
                ws.send(JSON.stringify(message));
                txtHistory.innerHTML += '<div class="chat outgoing"> <div class="details"> <p>'+ txtMessage.value + '</p></div> </div>';
                txtMessage.value = "";
                txtMessage.focus();
            }


        });
        ws.addEventListener("message", function (evt) {
            let message_infos = evt.data;
            let infos_arr = message_infos.split(",");
            if(infos_arr[0] == withClientId){
                let msgData = infos_arr[2].split(":");
                let typeMsg = msgData[0];
                if (typeMsg == "Msg"){
                    console.log("Receive new message: " + message_infos);
                    txtHistory.innerHTML += '<div class="chat incoming"> <div class="details"> <p>'+ msgData[1]+ '</p></div> </div>';
                }
                else if (typeMsg == "File"){
                    console.log("Receive new message: " + msgData[1]);
                    let fileInfos = msgData[1].split("|");
                    txtHistory.innerHTML += '<div class="chat incoming"> <div class="details"> <a href="http://localhost/php/chatApp-ressources//'+fileInfos[0]+'_'+fileInfos[1] + '"> Telecharger Piéce jointe </a></div> </div>';
                }

            }
        });

      

    });


</script>
  

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css"/>

    </head>
    <body>
      <%@include file="navBar.jsp"%>

    <div class="wrapper">
        <section class="chat-area">
            <header>
                <a href="people" class="back-icon"><i class="fas fa-arrow-left"></i></a>
                <img src="peopleProfilePicture?id_user=<%=idUser %>" alt="">
                <div class="details">
                    <span><%=withClientName%></span>
                    <p>status</p>
                </div>
            </header>
            <div id="discussion" class="chat-box">
  <% if (my_messages != null ) {
  for (Message msg: my_messages) {
                        String position = "";
                        if (msg.from.equals(email)){
                            position = "outgoing";
                        }
                        else {
                            position = "incoming";
                        }
                        if (msg.type.equals("text")){ %>
                    <div class="chat <%=position%>"> <div class="details"> <p> <%=msg.msg%> </p></div> </div>
                    <% }
                    else { %>
                    <div class="chat <%=position%>"> <div class="details"> <a href="http://localhost/php/chatApp-ressources/<%=msg.msg%>"> Telecharger Piéce jointe </a></div> </div>
                    <% }
                        }
  
                    } %>
            </div>
              <div class="typing-area">
                    <input type="text" class="incoming_id" name="incoming_id" value="<?php echo $user_id; ?>" hidden>
                    <input type="text" name="message" id="txtMessage" class="input-field" placeholder="Type a message here..." autocomplete="off">
                    <input type="file" id="fileInput">
                    <button id="showInputFile">File</button>
                    <button id="btnSend"><i class="fab fa-telegram-plane"></i></button>
                </div>
        </section>
    </div>
</script>
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
	<script>
		$('.favorite').on('click', function() {
			$(this).toggleClass('active');
		});
		// Get a reference to the edit button
		// Add a click event listener to the "Edit" button
	</script>
    </body>
   
</html>
