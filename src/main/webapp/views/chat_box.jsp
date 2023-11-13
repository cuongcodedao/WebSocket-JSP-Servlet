<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<title>Chat App - DVC.com</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"
	rel="stylesheet" />
<link rel="stylesheet" href="<c:url value="/static/css/style.css" />">
</head>
<body>
	<div class="container">
		<div class="row clearfix">
			<div class="col-lg-12">
				<div class="card chat-app">
					<div id="plist" class="people-list">
						<div class="input-group">
							<div class="input-group-prepend">
								<span class="input-group-text"><i class="fa fa-search"></i></span>
							</div>
							<input type="text" class="form-control" placeholder="Search...">
						</div>
						<ul class="list-unstyled chat-list mt-2 mb-0">
							
						</ul>
						<div class="input-group mb-3">
							<input type="text" class="form-control username"
								placeholder="Username" aria-label="Recipient's username"
								aria-describedby="button-addon2"
								value="${sessionScope.user.getUsername()}">
							<div class="input-group-append">
								<button class="btn btn-outline-secondary" type="button"
									id="button-addon2">Button</button>
							</div>
						</div>
					</div>
					<div class="chat">
						<div class="chat-header clearfix">
							<div class="row">
								<div class="col-lg-6">
									<a href="javascript:void(0);" data-toggle="modal"
										data-target="#view_info"> <img
										src="https://bootdey.com/img/Content/avatar/avatar2.png"
										alt="avatar">
									</a>
									<div class="chat-about">
										<h6 class="m-b-0 name_user_chatting">Aiden Chavez</h6>
										<small>Last seen: 2 hours ago</small>
									</div>
								</div>
			
								<div class="col-lg-6 hidden-sm text-right">
									<button class = "btn btn-primary add_friend" type = "button" onclick = "addFriend()">Add friend</button>
									<a href="javascript:void(0);" class="btn btn-outline-secondary"><i
										class="fa fa-camera"></i></a> <a href="javascript:void(0);"
										class="btn btn-outline-primary"><i class="fa fa-image"></i></a>
									<a href="javascript:void(0);" class="btn btn-outline-info"><i
										class="fa fa-cogs"></i></a> <a href="javascript:void(0);"
										class="btn btn-outline-warning"><i class="fa fa-question"></i></a>
								</div>
							</div>
						</div>
						<div class="chat-history">
							<ul class="m-b-0 conversation_wrap ">
								
							</ul>
						</div>
						<div class="chat-message clearfix">
							<div class="input-group mb-0">
								<div class="input-group-prepend">
									<span class="input-group-text">
										<button onclick="send()">
											<i class="fa fa-send"></i>
										</button>
									</span>
								</div>
								<input type="text" class="form-control content"
									placeholder="Enter text here...">
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/js/bootstrap.bundle.min.js"></script>
	<script type="text/javascript"
		src="<c:url value="/static/js/websocket.js" />" charset="utf-8"></script>
</body>

</html>