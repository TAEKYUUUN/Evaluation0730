package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemberDao;
import dto.MemberInfoDto;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        String pw = request.getParameter("pw");

        MemberDao memberDao = new MemberDao();

        try {
            boolean loginSuccessful = memberDao.loginCheck(id, pw);
            MemberInfoDto memberInfo = memberDao.getMemberInfo(id, pw);
            if (loginSuccessful) {
            	
            	// 로그인 성공 시 세션에 사용자 정보 저장
                HttpSession session = request.getSession();
                session.setAttribute("userId", memberInfo.getId());
                session.setAttribute("userPw", memberInfo.getPassword());
                session.setAttribute("userName", memberInfo.getName());
                session.setAttribute("userPoint", memberInfo.getPoint());
            	
            	if ("admin".equals(id)) {
                    response.sendRedirect("admin.jsp");
                } else {
                    response.sendRedirect("mainpage.jsp");
                }
            } else {
                response.setContentType("text/html; charset=UTF-8");
                response.getWriter().println("<script>alert('아이디/비밀번호를 다시 확인하세요.'); location.href='login.jsp';</script>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().println("<script>alert('로그인 중 오류가 발생했습니다. 다시 시도해주세요.'); location.href='login.jsp';</script>");
        }
	}

}
