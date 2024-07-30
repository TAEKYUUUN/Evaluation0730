package quartz;

import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import dao.MemberDao;
import dto.MemberInfoDto;

public class PointUpdateJob implements Job {
	@Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            MemberDao memberDao = new MemberDao();
            List<MemberInfoDto> members = memberDao.getAllMembers();
            for (MemberInfoDto member : members) {
                member.setPoint(member.getPoint() + 1);
                memberDao.updateMember(member);
            }
            Date now = new Date();
            System.out.println("Job이 실행됨 : " + now + " / " + members.size() + "명에게 1포인트가 부여되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}