<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>로그인 페이지</title>
</head>
<body>
	<form action="LoginServlet" method="post">
        <h1>로그인</h1>
        <table>
            <tr>
                <td>ID :</td>
                <td><input type="text" id="id" name="id"/></td>
            </tr>
            <tr>
                <td>PW :</td>
                <td><input type="password" id="pw" name="pw"/></td>
            </tr>
            <tr>
                <td colspan="2"><button type="submit" style="width:100%;">로그인</button></td>
            </tr>
            <tr>
                <td colspan="2"><button type="button" onclick="window.location.href='regist.jsp'" style="width:100%;">회원가입</button></td>
            </tr>
        </table>
    </form>
</body>
</html>