<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>회원가입 페이지</title>
	<style>
		body {
			margin : auto;
			padding : auto;
		}
	</style>
</head>
<body>
	<form action="RegistServlet" method="post">
        <h1>회원가입</h1>
        <table>
            <tr>
                <td>ID :</td>
                <td><input type="text" id="id" name="id" required/></td>
            </tr>
            <tr>
                <td>PW :</td>
                <td><input type="password" id="pw" name="pw" required/></td>
            </tr>
            <tr>
                <td>Name :</td>
                <td><input type="text" id="name" name="name" required/></td>
            </tr>
            <tr>
                <td colspan="2"><button type="submit" style="width:100%;">작성완료</button></td>
            </tr>
        </table>
    </form>
</body>
</html>