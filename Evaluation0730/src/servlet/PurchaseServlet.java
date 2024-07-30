package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemberDao;

@WebServlet("/PurchaseServlet")
public class PurchaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        String courseId = request.getParameter("courseId");
        int price = Integer.parseInt(request.getParameter("price"));

        MemberDao memberDao = new MemberDao();

        try {
            if (memberDao.deductPoints(userId, price)) {
                memberDao.addPurchase(userId, courseId, price);
                int updatedPoints = memberDao.getPoints(userId);
                session.setAttribute("userPoint", updatedPoints);

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"success\":true, \"message\":\"컨텐츠(" + courseId + ")를 구입하였습니다.\"}");
            } else {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"success\":false, \"message\":\"포인트가 부족합니다. 광고를 클릭하세요.\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"success\":false, \"message\":\"구매 처리 중 오류가 발생했습니다.\"}");
        }
	}

}
