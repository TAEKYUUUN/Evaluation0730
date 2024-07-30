package quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class ExQuartz {
    public static void main(String[] args) throws Exception {
        // Scheduler 인스턴스 생성
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
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
        // Scheduler 시작
        scheduler.start();
    }
}
