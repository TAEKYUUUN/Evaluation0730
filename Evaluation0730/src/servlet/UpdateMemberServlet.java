package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.MemberDao;
import dto.MemberInfoDto;

@WebServlet("/UpdateMemberServlet")
public class UpdateMemberServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String id = request.getParameter("id");
        String password = request.getParameter("pw");
        String name = request.getParameter("name");
        int point = Integer.parseInt(request.getParameter("point"));

        MemberInfoDto member = new MemberInfoDto(id, password, name, point);
        MemberDao memberDao = new MemberDao();

        try {
            memberDao.updateMember(member);
            response.getWriter().println("<script>alert('수정되었습니다.'); location.href='admin.jsp';</script>");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<script>alert('수정에 실패했습니다. 다시 시도해주세요.'); history.back();</script>");
        }
    }
}
