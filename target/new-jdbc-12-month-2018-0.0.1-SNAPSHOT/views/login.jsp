<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Đăng nhập</title>
</head>
<body>
	<div class="container">
		<!-- <h1 class="form-heading">login Form</h1> -->
		<div class="login-form">
			<div class="main-div">
				<c:if test="${not empty message}"><!-- chỉ khi thằng mess không empty mới dùng cái dưới https://www.youtube.com/watch?v=MYrx9NiJ_QI&list=PLW1k06REn7HsHn6D6e27gtFm-HXrZDLPX&index=38 22:10-->
					<div class="alert alert-${alert}">
							${message}
					</div>
				</c:if>
				<!--
				<div class="alert alert-${alert}">
					<p>a</p>
					${message}
				</div>
-->
				<form action="<c:url value='/dang-nhap'/>" id="formLogin" method="post"><!-- gửi url từ action khi đăng nhập thành công-->
					<div class="form-group">
						<input type="text" class="form-control" id="userName" name="userName"
							placeholder="Tên đăng nhập">
					</div>

					<div class="form-group">
						<input type="password" class="form-control" id="password" name="password"
							placeholder="Mật khẩu">
					</div>
					<input type="hidden" value="login" name="action"/>
					<button type="submit" class="btn btn-primary" >Đăng nhập</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>