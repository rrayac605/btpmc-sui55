package mx.gob.imss.cit.pmc.sui55.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.imss.cit.pmc.commons.mail.EmailTasklet;
import mx.gob.imss.cit.pmc.services.EmailParametrosService;
import mx.gob.imss.cit.pmc.services.EmailService;
import mx.gob.imss.cit.pmc.services.enums.CasoValidadoEnum;
import mx.gob.imss.cit.pmc.services.enums.PlantillaEmailEnum;
import mx.gob.imss.cit.pmc.services.enums.SistemaOrigenEnum;
import mx.gob.imss.cit.pmc.services.exception.EmailException;

@Component
@StepScope
public class EmailRegistrosIncorrectosTasklet extends EmailTasklet implements Tasklet {

	@Autowired
	private EmailService emailService;

	@Autowired
	private EmailParametrosService emailParametrosService;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		try {
			plantilla = emailParametrosService.obtenerParametrosEmail(SistemaOrigenEnum.SUI55,
					CasoValidadoEnum.ERROR_NUM_REG);
			llenaParametros();
			emailService.sendEmail(mail, PlantillaEmailEnum.ARCHIVO_CON_ERROR);

		} catch (Exception e) {
			throw new EmailException(e);
		}
		return RepeatStatus.FINISHED;
	}
}
