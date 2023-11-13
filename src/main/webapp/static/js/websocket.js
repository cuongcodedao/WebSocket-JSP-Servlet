var ws;
var currentDate = new Date();
var hours = currentDate.getHours();
var minutes = currentDate.getMinutes();
var ampm = "AM";
if (hours >= 12) ampm = "PM";
var formattedMinutes = minutes.toString().padStart(2, '0');
var formattedHours = hours.toString().padStart(2, '0');
var username = null;
var receiver = null;
var chatBoxScroll = document.querySelector(".chat-history");
function connect() {
	username = document.querySelector(".username").value;
	var host = document.location.host;
	var pathname = document.location.pathname;
	ws = new WebSocket("ws://" + host + pathname + "/" + username);

	ws.onopen = function(event) {
		console.log("Kết nối WebSocket đã được thiết lập.");
		console.log(event);
		loadOnline();
		chatBoxScroll.scrollTop = chatBoxScroll.scrollHeight;
	};

	ws.onmessage = function(event) {
		var messageContent = JSON.parse(event.data);
		var conversation_wrap = document.querySelector('.conversation_wrap');
		if (atob(messageContent.content) == "add_friend") {
			var divWrapBtn = document.createElement('div');
			divWrapBtn.classList.add("btn_wrap_friend");
			divWrapBtn.innerHTML = `
				<button type="button" class="btn btn-primary" onclick="accept_friend()">Primary button</button>
				<button type="button" class="btn btn-secondary" onclick="disagree_friend()">Button</button>
			`;
			conversation_wrap.appendChild(divWrapBtn);
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
}

window.onload = function() {
	connect();
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
		"to": receiver
	});
	ws.send(json);
	document.querySelector(".content").value = "";
}

var loadOnline = function() {
	fetch("http://localhost:8080/api_user")
		.then(function(data) {
			data.json()
				.then(data => {
					var listUser = document.querySelector(".chat-list");
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

function loadMessage(element) {
	var id = element.id;
	receiver = id;
	loadUserChatting();
	var chatFrameCur = document.querySelector(".conversation_wrap");
	var xmlRequest = new XMLHttpRequest();
	xmlRequest.open("get", "http://localhost:8080/api_chat?sender=" + username + "&receiver=" + id);
	xmlRequest.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var messageAppend = "";
			var arrayMessage = JSON.parse(this.responseText);
			for (var message of arrayMessage) {
				var date = new Date(message.date_sent);
				var hh_mm = date.getHours() + ":" + date.getMinutes();
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
			chatFrameCur.innerHTML = messageAppend;
			chatBoxScroll.scrollTop = chatBoxScroll.scrollHeight;
		}
	};
	xmlRequest.send();

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

function addFriend() {
	var add_friend = document.querySelector(".add_friend");
	var content = btoa("add_friend");
	var json = JSON.stringify({
		"content": content,
		"from": username,
		"to": receiver
	});
	ws.send(json);
}






