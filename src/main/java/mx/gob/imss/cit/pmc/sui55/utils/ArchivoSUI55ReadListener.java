package mx.gob.imss.cit.pmc.sui55.utils;

import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.stereotype.Component;

import mx.gob.imss.cit.pmc.commons.dto.RegistroDTO;

@Component
@StepScope
public class ArchivoSUI55ReadListener implements ItemReadListener<RegistroDTO> {

	@Override
	public void beforeRead() {

	}

	@Override
	public void afterRead(RegistroDTO item) {

	}

	@Override
	public void onReadError(Exception ex) {

	}

}
