var ws;
var currentDate = new Date();
var hours = currentDate.getHours();
var minutes = currentDate.getMinutes();
var ampm = "AM";
if (hours >= 12) ampm = "PM";
var formattedMinutes = minutes.toString().padStart(2, '0');
var formattedHours = hours.toString().padStart(2, '0');
var username = document.querySelector(".username").value;
var receiver = null;
var conversation = null;
var conversation_id = null;
var messages = null;
var isfriend = null;
var listFriend = null;
var typeMessage = "text";
var byteArray = null;
var chatBoxScroll = document.querySelector(".chat-history");
function connect(callback) {
	var host = document.location.host;
	var pathname = document.location.pathname;
	ws = new WebSocket("ws://" + host + pathname + "/" + username);
	ws.onopen = function(event) {
		console.log("Kết nối WebSocket đã được thiết lập.");
		console.log(event);
		loadUser();
		chatBoxScroll.scrollTop = chatBoxScroll.scrollHeight;
	};

	ws.onmessage = function(event) {
		var messageContent = JSON.parse(event.data);
		var conversation_wrap = document.querySelector('.conversation_wrap');
		if (messageContent.content == "add_friend" && receiver != null) {
			document.querySelector(".modal-friend").classList.add("active");
		}
		else {
			var message = document.createElement('li');
			message.classList.add('clearfix');
			message.innerHTML = `
	        		<li class="clearfix">
						<div class="message-data">
							<img src="https://bootdey.com/img/Content/avatar/avatar7.png" alt="avatar">
							<span class="message-data-time">${formattedHours}:${formattedMinutes} ${ampm}, Today</span>
						</div>
						<div class="message o-message">
							${messageContent.content}
						</div>
					</li>
	        	`;
			conversation_wrap.appendChild(message);
		}
		chatBoxScroll.scrollTop = chatBoxScroll.scrollHeight;
	};

	ws.onclose = function(event) {
		if (event.wasClean) {
			console.log("Kết nối WebSocket đã đóng sạch.");
		} else {
			console.error("Kết nối WebSocket đã bị đóng không sạch.");
		}
	};

	ws.onerror = function(error) {
		console.error("Lỗi kết nối WebSocket: " + error);
	};
	callback();
}

window.onload = function() {
	connect(getListFriend);
}

function send() {
	var content = document.querySelector(".content").value;
	var conversation_wrap = document.querySelector('.conversation_wrap');
	var message = document.createElement('li');
	message.classList.add('clearfix');
	message.innerHTML = `
        		<li class="clearfix">
				<div class="message-data text-right">
				<span class="message-data-time">${formattedHours}:${formattedMinutes} ${ampm}, Today</span>
				</div>
				<div class="message m-message float-right">
					${content}
				</div>
				</li>
        	`;
	conversation_wrap.appendChild(message);
	chatBoxScroll.scrollTop = chatBoxScroll.scrollHeight;
	var json = JSON.stringify({
		"content": content,
		"from": username,
		"to": receiver,
		"conversation_id": conversation_id,
		"type": typeMessage
	});
	if(typeMessage != "text") ws.send(byteArray);
	ws.send(json);
	document.querySelector(".content").value = "";
	typeMessage = "text";
}

var loadUser = function() {
	fetch("http://localhost:8080/api_user")
		.then(function(data) {
			data.json()
				.then(data => {
					var listUser = document.querySelector(".chat-list");
					listUser.innerHTML = ``;
					for (var user of data) {
						if (user.username != username) {
							var status = "offline";
							if (user.online == true) status = "online";
							var chatListItem = document.createElement("li");
							chatListItem.classList.add("clearfix");
							chatListItem.classList.add("active");
							chatListItem.setAttribute("id", user.username);
							chatListItem.setAttribute("onclick", "loadMessage(this)")
							chatListItem.innerHTML = `
								<img src="https://bootdey.com/img/Content/avatar/avatar2.png" alt="avatar">
								<div class="about">
									<div class="name">${user.username}</div>
									<div class="status">
										<i class="fa fa-circle ${status}"></i>
									</div>
								</div>
							`;
							listUser.appendChild(chatListItem);
						}
					}
				})
		})
		.catch(ex => {
			console.log(ex);
		});
}
function getMessage(sender, receiver) {
	var chatFrameCur = document.querySelector(".conversation_wrap");
	var xmlRequest = new XMLHttpRequest();
	xmlRequest.open("get", "http://localhost:8080/api_chat?sender=" + sender + "&receiver=" + receiver);
	xmlRequest.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var messageAppend = "";
			var arrayMessage = JSON.parse(this.responseText);
			messages = arrayMessage;
			for (var message of arrayMessage) {
				var date = new Date(message.date_sent);
				var hh_mm = date.getHours() + ":" + date.getMinutes();
				if (message.content != "add_friend") {
					if (message.from != username) {
						messageAppend += `
						   					<li class="clearfix">
						   						<div class="message-data">
													<img src="https://bootdey.com/img/Content/avatar/avatar7.png"
													alt="avatar"> <span class="message-data-time">${hh_mm} AM ,Today</span>
												</div>
												<div class="message o-message">${message.content}</div>
											</li>
										`;
					}
					else {
						messageAppend += `<li class="clearfix">
											<div class="message-data text-right">
												<span class="message-data-time">${hh_mm} AM ,Today</span>
											</div>
											<div class="message m-message float-right">${message.content}</div>
											</li>`
					}
				}
				else if (message.from != username) {
					showDialogFriend()
				}
			}
			chatFrameCur.innerHTML = messageAppend;
			chatBoxScroll.scrollTop = chatBoxScroll.scrollHeight;
		}
	};
	xmlRequest.send();
}

function loadMessage(element) {
	var id = element.id;
	receiver = id;
	var sender = username;
	conversation_id = null;
	conversation = null;
	loadUserChatting();
	showAddFriendBtn();
	getMessage(sender, receiver);
}

function loadMessageOfConversation(element) {
	var id = element.id;
	conversation = id;
	var sender = "anyone";
	receiver = null;
	loadConversationChatting(id);
	showAddFriendBtn();
	getMessage(sender, conversation);

}
function loadUserChatting() {
	var nameChat = document.querySelector(".name_user_chatting");
	var xmlRequest = new XMLHttpRequest();
	xmlRequest.open("get", "http://localhost:8080/api_user?username=" + receiver);
	xmlRequest.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var user = JSON.parse(this.responseText);
			nameChat.innerHTML = user.username;
		}
	}
	xmlRequest.send();

}
function loadConversationChatting() {
	var nameChat = document.querySelector(".name_user_chatting");
	var xmlRequest = new XMLHttpRequest();
	xmlRequest.open("get", "http://localhost:8080/api_conversation?name=" + conversation);
	xmlRequest.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var conversation = JSON.parse(this.responseText);
			nameChat.innerHTML = conversation.name;
			conversation_id = conversation.id;
		}
	}
	xmlRequest.send();
}

function addFriend() {
	var json = JSON.stringify({
		"content": 'add_friend',
		"from": username,
		"to": receiver
	});
	ws.send(json);
}

function accept_friend() {
	isfriend = true;
	const message = {
		"id_user1": username,
		"id_user2": receiver
	}
	const messageJson = JSON.stringify(message);
	const options = {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: messageJson
	};
	fetch("http://localhost:8080/api_friend", options)
		.then(function(response) {
			return response.json();
		})
		.then(function(data) {
			if (data.status == "success") {
				var btn_friend = document.querySelector(".modal-friend");
				btn_friend.classList.remove("active");
				var add_friend = document.querySelector(".add_friend");
				add_friend.classList.add("hiden");
			}
		})
		.catch(function(error) {
			console.error('Error:', error);
		});
	location.reload();
}

function showAddFriendBtn() {
	var add_friend = document.querySelector(".add_friend");
	var text_sending = document.querySelector(".send_message_btn");
	var add_member = document.querySelector(".add_into_conversation");
	if (conversation != null) {
		add_friend.classList.add("hiden");
		text_sending.classList.add("active");
		if (add_member.classList.contains("hiden")) {
			add_member.classList.remove("hiden");
		}
		return;
	}
	fetch("http://localhost:8080/api_friend?user1=" + username + "&user2=" + receiver)
		.then(function(data) {
			data.json()
				.then(data => {
					if (data.status == "YES") {
						add_member.classList.add("hiden");
						add_friend.classList.add("hiden");
						text_sending.classList.add("active");
						isfriend = true;
					}
					else {
						isfriend = false;
						add_member.classList.add("hiden");
						if (add_friend.classList.contains("hiden")) {
							add_friend.classList.remove("hiden");
						}
						if (text_sending.classList.contains("active")) {
							text_sending.classList.remove("active");
						}
					}
				})
		})
		.catch(ex => {
			console.log(ex);
		});
}
function getListFriend() {
	fetch("http://localhost:8080/api_friend?username=" + username)
		.then(function(data) {
			data.json()
				.then(data => {
					listFriend = data;
				})
		})
		.catch(ex => {
			console.log(ex);
		});
}

function showDialogFriend() {
	if (!listFriend.find(user => user.username === receiver)) {
		document.querySelector(".modal-friend").classList.add("active");
		console.log("Failed");
		console.log(listFriend);
	}
	else {
		console.log("Success");
	}
}

function changeTab(element) {
	if (!element.classList.contains("active")) {
		document.querySelector(".icon-navbar.active").classList.remove("active");
		element.classList.add("active");
		if (element.classList.contains("chat-couple")) {
			loadUser();
		}
		else {
			document.querySelector(".chat-list").innerHTML = `
			<li class="icon-navbar create-conversation"
								onclick=showModalCreateConversation()>
								<i class="fa fa-plus"></i>
			</li>
			`;
			loadConversation();
		}
	}
}
function showModalCreateConversation() {
	document.querySelector(".modal-createCvs").classList.add("active");
}

function createNewConversation() {
	var group_name = document.getElementById("groupName").value;
	if(group_name==null) return;
	const data = {
		usernameCreater: username,
		name: group_name
	};
	document.getElementById("groupName").value = "";
	fetch("http://localhost:8080/api_conversation", {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json;charset=UTF-8'
		},

		body: JSON.stringify(data)
	})
		.then(response => {
			if (!response.ok) {
				throw new Error('Network response was not ok');
			}
			return response.json();
		})
		.then(data => {
			console.log(data);
		})
		.catch(error => {
			console.error('There was an error with the fetch operation:', error);
		});
	document.querySelector(".modal-createCvs.active").classList.remove("active");
}

function loadConversation() {
	fetch("http://localhost:8080/api_conversation?username=" + username)
		.then(function(data) {
			data.json()
				.then(data => {
					var listUser = document.querySelector(".chat-list");
					for (var conversation of data) {
						var chatListItem = document.createElement("li");
						chatListItem.classList.add("clearfix");
						chatListItem.classList.add("active");
						chatListItem.setAttribute("id", conversation.name);
						chatListItem.setAttribute("onclick", "loadMessageOfConversation(this)")
						chatListItem.innerHTML = `
								<img src="https://bootdey.com/img/Content/avatar/avatar2.png" alt="avatar">
								<div class="about">
									<div class="name">${conversation.name}</div>
									<div class="status">
										<i class="fa fa-circle ${status}"></i>
									</div>
								</div>
							`;
						listUser.appendChild(chatListItem);
					}
				})
		})
		.catch(ex => {
			console.log(ex);
		});
}

function addIntoConversation() {
	var add_member_modal = document.querySelector(".modal-add_member");
	add_member_modal.classList.add("active");
	var friendAddMember = document.querySelector(".add-member-list");
	friendAddMember.innerHTML = ``;
	for (var friend of listFriend) {
		var chatListItem = document.createElement("li");
		chatListItem.classList.add("clearfix");
		chatListItem.classList.add("active");
		chatListItem.setAttribute("id", friend.username);
		chatListItem.setAttribute("onclick", "addThisMember(this)")
		chatListItem.innerHTML = `
								<img src="https://bootdey.com/img/Content/avatar/avatar2.png" alt="avatar">
								<div class="about">
									<div class="name">${friend.username}</div>
									<div class="status">
										<i class="fa fa-circle online"></i>
									</div>
								</div>
							`;
		friendAddMember.appendChild(chatListItem);
	}
}
function addThisMember(element){
	fetch("http://localhost:8080/api_conversation?friend_name=" + element.id + "&conversation_name="+conversation, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json;charset=UTF-8'
		},
	})
		.then(response => {
			if (!response.ok) {
				throw new Error('Network response was not ok');
			}
			return response.json();
		})
		.then(data => {
			console.log(data);
		})
		.catch(error => {
			console.error('There was an error with the fetch operation:', error);
		});
	document.querySelector(".modal-add_member.active").classList.remove("active");
}
function closeModalCreateCVS(){
	var modelclose = document.querySelector(".modal-createCvs");
	modelclose.classList.remove("active");  
}
function closeModalAddMember(){
	document.querySelector(".modal-add_member").classList.remove("active");
}


function triggerFileInput() {
  document.getElementById('fileInput').click();
}

function convertImage() {
  const fileInput = document.getElementById('fileInput');
  const file = fileInput.files[0];
  const reader = new FileReader();

  reader.onload = function(event) {
	typeMessage = file.type;
    const binaryString = event.target.result;
    byteArray = new Uint8Array(new ArrayBuffer(binaryString.length));
    for (let i = 0; i < binaryString.length; i++) {
      byteArray[i] = binaryString.charCodeAt(i);
    }

    const base64String = btoa(String.fromCharCode.apply(null, byteArray));
    
    // Set giá trị của input tin nhắn thành base64 của ảnh
    const messageInput = document.querySelector('.message_input');
    var divWrapImage = document.createElement("div");
    divWrapImage.innerHTML = `<img src="data:image/jpeg;base64,${base64String}" alt="Selected Image" />`;
    messageInput.appendChild(divWrapImage);
  }
  reader.readAsBinaryString(file);
}











