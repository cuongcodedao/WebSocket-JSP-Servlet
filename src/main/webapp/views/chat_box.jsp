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
<link rel="stylesheet"
	href="<c:url value="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" />">
<link rel="stylesheet" href="<c:url value="/static/css/style.css" />">
</head>
<body>
	<div class="container">
		<h1>Welcome ${sessionScope.user.getUsername()}</h1>
		<div class="row clearfix">
			<div class="col-lg-12">
				<div class="card chat-app">
					<div id="plist" class="people-list">
						<div class="group-icon-navbar">
							<div class="icon-navbar chat-couple active"
								onclick=changeTab(this)>
								<i class="fa fa-comment"></i>
							</div>
							<div class="icon-navbar chat-conversation"
								onclick=changeTab(this)>
								<i class="fa fa-comments"></i>
							</div>
						</div>
						<div class="input-group">
							<div class="input-group-prepend">
								<span class="input-group-text"><i class="fa fa-search"></i></span>
							</div>
							<input type="text" class="form-control" placeholder="Search...">
						</div>
						<ul class="list-unstyled chat-list mt-2 mb-0">

						</ul>
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
										<h6 class="m-b-0 name_user_chatting">Yourself in the
											future</h6>
										<small>Online</small>
									</div>
								</div>

								<div class="col-lg-6 hidden-sm text-right">
									<button class="btn btn-primary add_friend hiden" type="button"
										onclick="addFriend()">Add friend</button>
									<button class="btn btn-primary add_into_conversation hiden"
										type="button" onclick="addIntoConversation()"
										data-bs-toggle="modal" data-bs-target="#addMemberModal">Add
										Member</button>
									<a href="javascript:void(0);" class="btn btn-outline-secondary"><i
										class="fa fa-camera"></i></a> <a href="javascript:void(0)"
										onclick="triggerFileInput()" class="btn btn-outline-primary"><i
										class="fa fa-image"></i></a> <input type="file" multiple id="fileInput"
										style="display: none;" onchange="convertImage()">
									<a href="javascript:void(0);" class="btn btn-outline-info"><i
										class="fa fa-cogs"></i></a> <a href="javascript:void(0);"
										class="btn btn-outline-warning"><i class="fa fa-question"></i></a>
								</div>
							</div>
						</div>
	


						<div class="chat-history">
							<ul class="m-b-0 conversation_wrap ">
								<li class="clearfix">
									<div class="message-data">
										<img src="https://bootdey.com/img/Content/avatar/avatar7.png"
											alt="avatar"> <span class="message-data-time">12:00
											AM,Today</span>
									</div>
									<div class="message o-message">Just being yourself</div>
								</li>
								<li class="clearfix">
									<div class="message-data text-right">
										<span class="message-data-time">12:01 AM ,Today</span>
									</div>
									<div class="message m-message float-right">Yes sure, bro</div>
								</li>
							</ul>
						</div>
						<div class="chat-message clearfix text_sending active">
							<div class="input-group mb-0">
								<div class = "message_input">
									<input type="text" class="form-control content"
									placeholder="Enter text here...">
								</div>
								<div class="input-group-prepend send_message_btn">
									<span class="input-group-text">
										<button class="" onclick="send()">
											<i class="fa fa-send"></i>
										</button>
									</span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal modal-friend" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Modal title</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<p>Modal body text goes here.</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Close</button>
					<button type="button" onclick="accept_friend()"
						class="btn btn-primary">Add friend</button>
				</div>
			</div>
		</div>
	</div>
	<!-- Modal -->
	<div class="modal modal-createCvs" id="createGroupModal" tabindex="-1"
		role="dialog" aria-labelledby="createGroupModalLabel"
		aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="createGroupModalLabel">Create
						Group Chat</h5>
					<button type="button" class="close" data-dismiss="modal"
						onclick="closeModalCreateCVS()" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form id="createGroupForm">
						<div class="form-group">
							<label for="groupName">Group Name:</label> <input type="text"
								class="form-control" id="groupName" name="groupName" required>
						</div>
						<div class="form-group">
							<label for="avatar">Choose Avatar:</label> <input type="file"
								class="form-control-file" id="avatar" name="avatar" onchange = "selectAvatar()">
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="submit" onclick="createNewConversation()"
						class="btn btn-primary">Create Group</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal modal-add_member" id="addMemberModal" tabindex="-1"
		aria-labelledby="addMemberModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="addMemberModalLabel">Add Member to
						Chat</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close" onclick="closeModalAddMember()"></button>
				</div>
				<div class="modal-body">
					<ul class="list-unstyled add-member-list mt-2 mb-0">

					</ul>
				</div>
			</div>
		</div>
	</div>

	<input type="text" class="form-control username" placeholder="Username"
		aria-label="Recipient's username" aria-describedby="button-addon2"
		value="${sessionScope.user.getUsername()}" style="display: none">
	<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
	<script
		src="<c:url value="https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/js/bootstrap.bundle.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/static/js/websocket.js" />" charset="utf-8"></script>
</body>

</html>