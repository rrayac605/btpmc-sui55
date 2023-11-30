package mx.gob.imss.cit.pmc.sui55.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import mx.gob.imss.cit.pmc.commons.dto.ArchivoDTO;
import mx.gob.imss.cit.pmc.commons.dto.RegistroDTO;

@Component
@StepScope
public class ArchivoSUI55Processor implements ItemProcessor<RegistroDTO, ArchivoDTO> {

	private final static Logger logger = LoggerFactory.getLogger(ArchivoSUI55Processor.class);

	@Override
	public ArchivoDTO process(RegistroDTO item) throws Exception {
		logger.debug("Procesor: " + item.toString());

		return new ArchivoDTO();
	}

}
