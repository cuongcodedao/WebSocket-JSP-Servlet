<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="<c:url value='/static/css/login.css' />">
<title>Login</title>
</head>
<body>
	<div class="wrapper">
		<div class="title-text">
			<div class="title login">Login Form</div>
			<div class="title signup">User Form</div>
		</div>
		<div class="form-container">

			<div class="form-inner">
				<form action="<c:url value="/login" />" class="login" method="post">
					<div class="field">
						<input type="text" name="username" placeholder="Username" required>
					</div>
					<div class="field">
						<input type="password" name="password" placeholder="Password"
							required>
					</div>
					<div class="signup-link">
						Not a member? <a href="">Signup now</a>
					</div>
				</form>
				<form action="<c:url value="/user/${pathInfo}" />" class="signup"
					method="post" enctype="multipart/form-data">
					<div class="field">
						<input type="text" name="username" placeholder="Username" required
							value="${user.getUsername()}">
					</div>
					<span>${warning}</span>
					<div class="field">
						<input type="password" name="password" placeholder="Password"
							required>
					</div>
					<div class="avatar">
						<c:if test="${not empty user.getAvatar()}">
							<img src="http://localhost:8080/files?userId=${user.getId()}"
								alt="Avatar" id="avatarImg" class="avatar-img">
						</c:if>
						<c:if test="${empty user.getAvatar()}">
							<img src="/static/images/user_avatar.png" alt="Avatar"
								id="avatarImg" class="avatar-img">
						</c:if>

						<input type="file" id="avatarInput" name="avatar" accept="image/*"
							onchange="loadAvatar(event)"> <label for="avatarInput"
							class="avatar-label">Choose Avatar</label>
					</div>
					<div class="field btn">
						<div class="btn-layer"></div>
						<input type="submit" value="Sign up">
					</div>
				</form>
			</div>
		</div>
	</div>

	<script type="text/javascript" charset="utf-8">
		const loginText = document.querySelector(".title-text .login");
		const loginForm = document.querySelector("form.login");
		const loginBtn = document.querySelector("label.login");
		const signupBtn = document.querySelector("label.signup");
		const signupLink = document.querySelector("form .signup-link a");
		loginForm.style.marginLeft = "-50%";
		loginText.style.marginLeft = "-50%";
		function loadAvatar(event) {
			var img = document.getElementById('avatarImg');
			img.src = URL.createObjectURL(event.target.files[0]);
		}
	</script>
</body>
</html>
