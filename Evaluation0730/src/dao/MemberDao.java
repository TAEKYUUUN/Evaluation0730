package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.MemberInfoDto;

public class MemberDao {
	// Connection getConnection() : Connection 객체를 리턴.
	Connection getConnection() throws Exception {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "user0416";
		String pw = "pass1234";

		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, id, pw);

		return conn;

	}

	// id, pw, name 입력 받아 회원가입
	public void registMember(String id, String pw, String name) throws Exception {
		Connection conn = getConnection();
		String sql = "INSERT INTO member(id, password, name) VALUES (?, ?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, id);
		pstmt.setString(2, pw);
		pstmt.setString(3, name);
		
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	
	
	// id, pw 입력 받아 로그인 체크
	public boolean loginCheck(String id, String pw) throws Exception {
		Connection conn = getConnection();
		String sql = "SELECT COUNT(*) FROM member WHERE id = ? AND password = ? ";
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, id);
		pstmt.setString(2, pw);

		ResultSet rs = pstmt.executeQuery();

		int result = 0;
		while (rs.next()) {
			result = rs.getInt(1);
		}
		rs.close();
		pstmt.close();
		conn.close();

		if (result == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	// id, pw 입력 받아 회원정보 가져오기
	public MemberInfoDto getMemberInfo(String id, String pw) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    MemberInfoDto memberInfo = null; // 메서드 범위 내에서 선언 및 초기화

	    try {
	        conn = getConnection();
	        String sql = "SELECT id, password, name, point FROM member WHERE id = ? and password = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, id);
	        pstmt.setString(2, pw);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            String memberId = rs.getString("id");
	            String memberPw = rs.getString("password");
	            String memberName = rs.getString("name");
	            int memberPoint = rs.getInt("point");

	            memberInfo = new MemberInfoDto(memberId, memberPw, memberName, memberPoint);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return memberInfo;
	}
	
	// 광고 시청 후 포인트 획득
	public void addPoints(String userId, int points) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;

	    try {
	        conn = getConnection();
	        String sql = "UPDATE member SET point = point + ? WHERE id = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, points);
	        pstmt.setString(2, userId);
	        pstmt.executeUpdate();
	    } finally {
	        if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
	        if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
	    }
	}
	
	// 포인트 가져오기
	public int getPoints(String userId) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    int points = 0;

	    try {
	        conn = getConnection();
	        String sql = "SELECT point FROM member WHERE id = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, userId);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            points = rs.getInt("point");
	        }
	    } finally {
	        if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
	        if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
	        if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
	    }

	    return points;
	}
	
	// 구매시 포인트 차감
	public boolean deductPoints(String userId, int points) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;

	    try {
	        conn = getConnection();
	        String checkSql = "SELECT point FROM member WHERE id = ?";
	        pstmt = conn.prepareStatement(checkSql);
	        pstmt.setString(1, userId);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            int currentPoints = rs.getInt("point");
	            if (currentPoints < points) {
	                return false;  // 포인트 부족
	            }
	        }

	        String updateSql = "UPDATE member SET point = point - ? WHERE id = ?";
	        pstmt = conn.prepareStatement(updateSql);
	        pstmt.setInt(1, points);
	        pstmt.setString(2, userId);
	        pstmt.executeUpdate();
	    } finally {
	        if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
	        if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
	    }

	    return true;
	}

	// 구매내역 추가
	public void addPurchase(String userId, String courseId, int price) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;

	    try {
	        conn = getConnection();
	        String sql = "INSERT INTO purchase (procedure, id, purchase_date) VALUES (?, ?, sysdate)";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, courseId);
	        pstmt.setString(2, userId);
	        pstmt.executeUpdate();
	    } finally {
	        if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
	        if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
	    }
	}
	
	// 모든 회원 정보를 가져오는 메서드
	public List<MemberInfoDto> getAllMembers() throws Exception {
        List<MemberInfoDto> members = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            String sql = "SELECT id, password, name, point FROM member";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");
                String password = rs.getString("password");
                String name = rs.getString("name");
                int point = rs.getInt("point");
                members.add(new MemberInfoDto(id, password, name, point));
            }
        } finally {
            if (rs != null) try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            if (pstmt != null) try { pstmt.close(); } catch (Exception e) { e.printStackTrace(); }
            if (conn != null) try { conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }

        return members;
    }
	
	// 멤버 삭제
	public void deleteMember(String id) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    
	    try {
	        conn = getConnection();
	        String sql = "DELETE FROM member WHERE id = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, id);
	        pstmt.executeUpdate();
	    } finally {
	        if (pstmt != null) try { pstmt.close(); } catch (Exception e) { e.printStackTrace(); }
	        if (conn != null) try { conn.close(); } catch (Exception e) { e.printStackTrace(); }
	    }
	}
	
	// id로 해당 멤버 정보 가져오기
	public MemberInfoDto getMemberById(String id) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    MemberInfoDto member = null;

	    try {
	        conn = getConnection();
	        String sql = "SELECT id, password, name, point FROM member WHERE id = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, id);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            String password = rs.getString("password");
	            String name = rs.getString("name");
	            int point = rs.getInt("point");
	            member = new MemberInfoDto(id, password, name, point);
	        }
	    } finally {
	        if (rs != null) try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
	        if (pstmt != null) try { pstmt.close(); } catch (Exception e) { e.printStackTrace(); }
	        if (conn != null) try { conn.close(); } catch (Exception e) { e.printStackTrace(); }
	    }

	    return member;
	}
	
	// 회원 정보 수정
	public void updateMember(MemberInfoDto member) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;

	    try {
	        conn = getConnection();
	        String sql = "UPDATE member SET password = ?, name = ?, point = ? WHERE id = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, member.getPassword());
	        pstmt.setString(2, member.getName());
	        pstmt.setInt(3, member.getPoint());
	        pstmt.setString(4, member.getId());
	        pstmt.executeUpdate();
	    } finally {
	        if (pstmt != null) try { pstmt.close(); } catch (Exception e) { e.printStackTrace(); }
	        if (conn != null) try { conn.close(); } catch (Exception e) { e.printStackTrace(); }
	    }
	}
}
