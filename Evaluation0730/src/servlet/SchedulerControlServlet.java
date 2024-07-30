package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import quartz.PointUpdateJob;

@WebServlet("/SchedulerControlServlet")
public class SchedulerControlServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Scheduler scheduler;

    @Override
    public void init() throws ServletException {
        try {
            scheduler = new StdSchedulerFactory().getScheduler();
            // JobDetail 생성
            JobDetail jobDetail = JobBuilder.newJob(PointUpdateJob.class)
                    .withIdentity("pointUpdateJob", "group1")
                    .build();
            // CronTrigger 생성 - 20초마다 실행
            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity("cronTrigger", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?"))
                    .forJob(jobDetail)
                    .build();
            // JobDetail과 Trigger를 Scheduler에 설정
            scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (SchedulerException e) {
            throw new ServletException(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("start".equals(action)) {
                startScheduler();
                response.getWriter().println("스케줄러가 시작되었습니다.");
            } else if ("stop".equals(action)) {
                stopScheduler();
                response.getWriter().println("스케줄러가 종료되었습니다.");
            }
        } catch (SchedulerException e) {
            throw new ServletException(e);
        }
    }

    private void startScheduler() throws SchedulerException {
        if (!scheduler.isStarted()) {
            scheduler.start();
            System.out.println("스케줄러가 시작되었습니다.");
        }
    }

    private void stopScheduler() throws SchedulerException {
        if (!scheduler.isShutdown()) {
            scheduler.shutdown();
            System.out.println("스케줄러가 종료되었습니다.");
        }
    }
}
