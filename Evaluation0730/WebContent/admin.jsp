<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="dao.MemberDao" %>
<%@ page import="dto.MemberInfoDto" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin</title>
<style>
   body {
    font-family: Arial, sans-serif;
    background-color: #fff;
    margin: 0;
    padding: 0;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
}

.container {
    width: 80%;
    max-width: 1200px;
    min-width: 800px;
    border: 1px solid #ccc;
    padding: 20px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-bottom: 1px solid #ccc;
    padding-bottom: 10px;
}

h1 {
    font-size: 2em;
    margin: 0;
}

.login-btn {
    padding: 5px 10px;
    border: 1px solid #ccc;
    background-color: #f0f0f0;
    cursor: pointer;
}

main {
    margin-top: 20px;
}

table {
    width: 100%;
    border-collapse: collapse;
    margin-bottom: 20px;
}

th, td {
    border: 1px solid #ccc;
    padding: 10px;
    text-align: center;
}

button {
    padding: 5px 10px;
    border: 1px solid #ccc;
    background-color: #f0f0f0;
    cursor: pointer;
}

.scheduler-buttons {
    display: flex;
    justify-content: center;
}

.scheduler-buttons button {
    margin: 5px;
    padding: 10px 20px;
    border: 1px solid #ccc;
    background-color: #f0f0f0;
    cursor: pointer;
}
</style>
<script>
function goToLogin() {
    window.location.href = 'login.jsp';
}

function updateMember(id) {
    window.location.href = 'memberUpdateAdmin.jsp?id=' + id;
}

function deleteMember(id) {
    if (confirm('정말로 삭제하시겠습니까?')) {
        var xhr = new XMLHttpRequest();
        xhr.open('POST', 'DeleteMemberServlet', true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4 && xhr.status == 200) {
                alert('회원이 삭제되었습니다.');
                window.location.reload();
            }
        };
        xhr.send('id=' + id);
    }
}

function startScheduler() {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', 'SchedulerControlServlet?action=start', true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            alert('스케줄러가 시작되었습니다.');
        }
    };
    xhr.send();
}

function stopScheduler() {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', 'SchedulerControlServlet?action=stop', true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            alert('스케줄러가 종료되었습니다.');
        }
    };
    xhr.send();
}
</script>
</head>
<body>
    <div class="container">
        <header>
            <h1>회원관리</h1>
            <button class="login-btn" onclick="goToLogin()">로그인</button>
        </header>
        <main>
            <%
                MemberDao memberDao = new MemberDao();
                List<MemberInfoDto> members = null;
                try {
                    members = memberDao.getAllMembers();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            %>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>PW</th>
                        <th>Name</th>
                        <th>Point</th>
                        <th>수정</th>
                        <th>삭제</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        if (members != null && !members.isEmpty()) {
                            for (MemberInfoDto member : members) {
                    %>
                    <tr>
                        <td><%= member.getId() %></td>
                        <td><%= member.getPassword() %></td>
                        <td><%= member.getName() %></td>
                        <td><%= member.getPoint() %></td>
                        <td><button onclick="updateMember('<%= member.getId() %>')">수정</button></td>
                        <td><button onclick="deleteMember('<%= member.getId() %>')">삭제</button></td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="6">회원 정보가 없습니다.</td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
            <h2>스케줄러관리</h2>
            <div class="scheduler-buttons">
                <button onclick="startScheduler()">스케줄러(20초마다 포인트 1 증가) 실행 시작</button>
                <button onclick="stopScheduler()">스케줄러 실행 종료</button>
            </div>
        </main>
    </div>
</body>
</html>
