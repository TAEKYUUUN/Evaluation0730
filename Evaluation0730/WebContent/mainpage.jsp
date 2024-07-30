<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>메인 페이지</title>
	<style>
	   body {
	    font-family: Arial, sans-serif;
	    background-color: #fff;
	    margin: 0;
	    padding: 0;
	    display: flex;
	    flex-direction: column;
	    align-items: center;
	}
	
	header {
	    width: 100%;
	    padding: 20px;
	    display: flex;
	    justify-content: space-between;
	    align-items: center;
	    border-bottom: 1px solid #ccc;
	}
	
	h1 {
	    font-size: 2em;
	    margin: 0;
	}
	
	.user-info {
	    display: flex;
	    align-items: center;
	    margin-right : 10px;
	}
	
	.user-info span {
	    margin-right: 10px;
	}
	
	.user-info button {
	    padding: 5px 10px;
	    border: 1px solid #ccc;
	    background-color: #f0f0f0;
	    cursor: pointer;
	}
	
	main {
	    width: 100%;
	    max-width: 1200px;
	    text-align: center;
	    position: relative;
	}
	
	h2 {
	    margin-top: 20px;
	}
	
	.courses {
	    display: flex;
	    justify-content: space-between;
	    margin: 20px 0;
	}
	
	.course-card {
	    width: 30%;
	    border: 1px solid #ccc;
	    border-radius: 5px;
	    padding: 10px;
	    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	    text-align: left;
	}
	
	.course-image {
	    height: 150px;
	    display: flex;
	    align-items: center;
	    justify-content: center;
	    font-weight: bold;
	    color: white;
	}
	
	h3 {
	    margin: 10px 0;
	}
	
	.rating {
	    color: #f39c12;
	}
	
	.level {
	    color: #7f8c8d;
	}
	
	.estimated-time {
	    font-weight: bold;
	}
	
	.price {
	    margin-top: 10px;
	    font-weight: bold;
	    text-align: center;
	}
	
	.ad {
	    position: absolute;
	    right: 20px;
	}
	
	.ad p {
	    margin: 0;
	}
	
	.ad-image {
	    height: 120px;
	    width: 225px;
	    margin-top: 5px;
	    display: flex;
	    flex-direction: column;
	    color: white;
	    font-weight: bold;
	    border: 1px solid #ccc;
	    border-radius: 5px;
	}
	   
	</style>
	<script>
	    function logout() {
	        if (confirm("로그아웃 하시겠습니까?")) {
	            window.location.href = 'LogoutServlet';
	        }
	    }
	    
	    function addPoints() {
            var points = Math.floor(Math.random() * 1000) + 1;
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "AddPointsServlet", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    alert(points + "점이 적립되었습니다.");
                    window.location.href = "http://koreaisacademy.com";
                }
            };
            xhr.send("points=" + points);
        }
	    
	    function purchaseCourse(courseId, price) {
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "PurchaseServlet", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    var response = JSON.parse(xhr.responseText);
                    if (response.success) {
                        alert(response.message);
                        location.reload();  // 페이지 새로고침
                    } else {
                        alert(response.message);
                    }
                }
            };
            xhr.send("courseId=" + courseId + "&price=" + price);
        }
	</script>
</head>
<body>
<%
    HttpSession sessions = request.getSession();
    String userId = (String) sessions.getAttribute("userId");
    String userName = (String) sessions.getAttribute("userName");
    Integer userPoint = (Integer) sessions.getAttribute("userPoint");
%>
	<header>
        <h1 style="margin-left : 10px;">메인페이지</h1>
        <div class="user-info">
            <span><%= userName %>(<%= userId %>)님 안녕하세요.</span>
        	<span>포인트 : <%= userPoint %>점</span>
            <button onclick="logout()">로그아웃</button>
        </div>
    </header>
    <main>
        <h2 style="text-align:left;">구입할 컨텐츠를 선택하세요.</h2>
        <div class="courses">
            <div class="course-card" onclick="purchaseCourse('intro', 100000)">
                <img src="img/Intro_350_408.png">
                <p class="price">100,000포인트</p>
            </div>
            <div class="course-card" onclick="purchaseCourse('java', 500000)">
                <img src="img/Java_350_408.png">
                <p class="price">500,000포인트</p>
            </div>
            <div class="course-card" onclick="purchaseCourse('C++', 300000)">
                <img src="img/Cpp_350_408.png">
                <p class="price">300,000포인트</p>
            </div>
        </div>
        <div class="ad">
            <div class="ad-image" style="border:1px solid #eee;" onclick="addPoints()">
            	<p style="color:black; text-align: left;"> &lt;광고&gt; </p>
            	<img src="img/korea_it.png">
            </div>
        </div>
    </main>
</body>
</html>