package test;

import java.util.List;

import dao.MemberDao;
import dto.MemberInfoDto;

public class DaoTest {
	public static void main(String[] args) throws Exception {
		MemberDao member = new MemberDao();
		
		member.registMember("WTK", "1234", "태균짱");
		
		member.loginCheck("WTK", "1234");
		
		MemberInfoDto memberDto = member.getMemberInfo("WTK", "1234");
		
		member.addPoints("WTK", 123456);
		
		member.getPoints("WTK");
		
		member.deductPoints("WTK", 500);
		
		member.addPurchase("WTK", "intro", 100000);
		
		List<MemberInfoDto> members = member.getAllMembers();
		
		member.deleteMember("WTK");
		
		MemberInfoDto memberDto2 = member.getMemberById("abcd");
		
	}
}
