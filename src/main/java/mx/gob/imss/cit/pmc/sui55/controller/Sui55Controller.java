package mx.gob.imss.cit.pmc.sui55.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mx.gob.imss.cit.pmc.commons.dto.ErrorResponse;
import mx.gob.imss.cit.pmc.commons.enums.EnumHttpStatus;

@RestController
@Api(value = "Ejecución batch PMC", tags = { "Ejecución batch PMC Rest" })
@RequestMapping("/msbatchsui55/v1")
public class Sui55Controller {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("jobLauncherController")
	private SimpleJobLauncher jobLauncherController;

	@Autowired
	@Qualifier("readSui55")
	private Job job;

	@Autowired
	@Qualifier("readSui55Inicial")
	private Job jobInicial;

	@RequestMapping("/health/ready")
	@ResponseStatus(HttpStatus.OK)
	public void ready() {
	}

	@RequestMapping("/health/live")
	@ResponseStatus(HttpStatus.OK)
	public void live() {
	}

	@Bean
	public SimpleJobLauncher jobLauncherController(JobRepository jobRepository) {
		SimpleJobLauncher launcher = new SimpleJobLauncher();
		launcher.setJobRepository(jobRepository);
		return launcher;
	}

	@ApiOperation(value = "Ejecución batch", nickname = "ejecutar", notes = "Ejecución batch", response = Object.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Respuesta exitosa", response = Object.class),
			@ApiResponse(code = 500, message = "Describe un error general del sistema", response = ErrorResponse.class) })
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(value = "/ejecutar", produces = MediaType.APPLICATION_JSON_VALUE)
	public Object ejecutar(@RequestParam(required = false) String nombre) {
		Object resultado = null;
		try {
			logger.info("ejecutarBatch");
			JobParameters param = new JobParametersBuilder()
					.addString("JobID", String.valueOf(System.currentTimeMillis())).addString("nombre", nombre)
					.toJobParameters();
			JobExecution execution = jobLauncherController.run(job, param);

			logger.debug("Job finished with status :" + execution.getStatus());
			resultado = new ResponseEntity<Object>("El resultado de la ejecución es: " + execution.getStatus(),
					HttpStatus.OK);
			logger.info("ejecutarBatch:returnOk");
		} catch (Exception be) {
			logger.info("ejecutarBatch:catch");
			ErrorResponse errorResponse = new ErrorResponse(EnumHttpStatus.SERVER_ERROR_INTERNAL, be.getMessage(),
					"Error de aplicaci\u00F3n");

			int numberHTTPDesired = Integer.parseInt(errorResponse.getCode());

			resultado = new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.valueOf(numberHTTPDesired));
			logger.info("ejecutarBatch:numberHTTPDesired");

		}

		logger.info("ejecutarBatch:FinalReturn");
		return resultado;
	}

	@ApiOperation(value = "Ejecución batch carga inicial", nickname = "ejecutarInicial", notes = "Ejecución batch carga inicial", response = Object.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Respuesta exitosa", response = Object.class),
			@ApiResponse(code = 500, message = "Describe un error general del sistema", response = ErrorResponse.class) })
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(value = "/ejecutarInicial", produces = MediaType.APPLICATION_JSON_VALUE)
	public Object ejecutarInicial(@RequestParam(required = false) String nombre) {
		Object resultado = null;
		try {
			logger.info("ejecutarBatch carga inicial");
			JobParameters param = new JobParametersBuilder()
					.addString("JobID", String.valueOf(System.currentTimeMillis())).addString("nombre", nombre)
					.toJobParameters();
			JobExecution execution = jobLauncherController.run(jobInicial, param);

			logger.debug("Job finished with status :" + execution.getStatus());
			resultado = new ResponseEntity<Object>("El resultado de la ejecución es: " + execution.getStatus(),
					HttpStatus.OK);
			logger.info("ejecutarBatch:returnOk");
		} catch (Exception be) {
			logger.info("ejecutarBatch:catch");
			ErrorResponse errorResponse = new ErrorResponse(EnumHttpStatus.SERVER_ERROR_INTERNAL, be.getMessage(),
					"Error de aplicaci\u00F3n");

			int numberHTTPDesired = Integer.parseInt(errorResponse.getCode());

			resultado = new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.valueOf(numberHTTPDesired));
			logger.info("ejecutarBatch:numberHTTPDesired");

		}

		logger.info("ejecutarBatch:FinalReturn");
		return resultado;
	}

}
