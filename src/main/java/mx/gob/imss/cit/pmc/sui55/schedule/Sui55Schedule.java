package mx.gob.imss.cit.pmc.sui55.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Sui55Schedule {

	private final static Logger logger = LoggerFactory.getLogger(Sui55Schedule.class);

	@Autowired
	@Qualifier("jobLauncherScheduled")
	private SimpleJobLauncher jobLauncherScheduled;

	@Autowired
	@Qualifier("readSui55")
	private Job job;

	@Bean
	public SimpleJobLauncher jobLauncherScheduled(JobRepository jobRepository) {
		SimpleJobLauncher launcher = new SimpleJobLauncher();
		launcher.setJobRepository(jobRepository);
		return launcher;
	}

	//@Scheduled(cron = "${cron.expression.sui55}")
	public void startJob() {

		try {
			JobParameters param = new JobParametersBuilder()
					.addString("JobID", String.valueOf(System.currentTimeMillis())).toJobParameters();
			JobExecution execution = jobLauncherScheduled.run(job, param);

			logger.debug("Job finished with status :" + execution.getStatus());
		} catch (JobRestartException e) {
			logger.error(e.getMessage(), e);
		} catch (JobExecutionAlreadyRunningException e) {
			logger.error(e.getMessage(), e);
		} catch (JobInstanceAlreadyCompleteException e) {
			logger.error(e.getMessage(), e);
		} catch (JobParametersInvalidException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
