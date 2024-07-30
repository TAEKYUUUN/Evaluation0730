<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dao.MemberDao" %>
<%@ page import="dto.MemberInfoDto" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원관리 - 수정 (관리자)</title>
</head>
<body>
<%
    String id = request.getParameter("id");
    MemberDao memberDao = new MemberDao();
    MemberInfoDto member = null;

    try {
        member = memberDao.getMemberById(id);
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
    <form action="UpdateMemberServlet" method="post">
        <h1>회원관리 - 수정 (관리자)</h1>
        <table>
            <tr>
                <td>ID :</td>
                <td><input type="text" id="id" name="id" value="<%= member.getId() %>" readonly/></td>
            </tr>
            <tr>
                <td>PW :</td>
                <td><input type="text" id="pw" name="pw" value="<%= member.getPassword() %>" required/></td>
            </tr>
            <tr>
                <td>Name :</td>
                <td><input type="text" id="name" name="name" value="<%= member.getName() %>" required/></td>
            </tr>
            <tr>
                <td>Point :</td>
                <td><input type="text" id="point" name="point" value="<%= member.getPoint() %>" required/></td>
            </tr>
            <tr>
                <td colspan="2"><button type="submit" style="width:100%;">제출</button></td>
            </tr>
        </table>
    </form>
</body>
</html>
