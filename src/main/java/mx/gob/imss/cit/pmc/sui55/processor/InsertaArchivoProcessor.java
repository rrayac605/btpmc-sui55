package mx.gob.imss.cit.pmc.sui55.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import mx.gob.imss.cit.pmc.common.InsertaArchivoInicialCommonProcessor;
import mx.gob.imss.cit.pmc.commons.dto.ArchivoDTO;
import mx.gob.imss.cit.pmc.commons.dto.CabeceraDTO;
import mx.gob.imss.cit.pmc.commons.dto.DetalleRegistroDTO;
import mx.gob.imss.cit.pmc.commons.dto.PatronDTO;
import mx.gob.imss.cit.pmc.commons.dto.RegistroDTO;
import mx.gob.imss.cit.pmc.commons.enums.IdentificadorArchivoEnum;
import mx.gob.imss.cit.pmc.commons.utils.Utils;

@Component
@StepScope
public class InsertaArchivoProcessor extends InsertaArchivoInicialCommonProcessor
		implements ItemProcessor<RegistroDTO, DetalleRegistroDTO> {
	@Override
	public DetalleRegistroDTO process(RegistroDTO item) throws Exception {
		String nombre = stepExecution.getJobParameters().getString("nombre");
		Integer indice = (Integer) stepExecution.getExecutionContext().get("indice");
		CabeceraDTO cabeceraDTO = (CabeceraDTO) stepExecution.getExecutionContext().get("Cabecera");
		ArchivoDTO archivoDTO = (ArchivoDTO) stepExecution.getExecutionContext().get("archivoDTO");
		@SuppressWarnings("unchecked")
		List<DetalleRegistroDTO> detalle = (List<DetalleRegistroDTO>) stepExecution.getExecutionContext()
				.get("detalleRegistroDTO");
		if (archivoDTO == null) {
			Optional<ArchivoDTO> archivoOptional = archivoRepository.findOneByName(nombre != null ? nombre
					: Utils.obtenerNombreArchivo(IdentificadorArchivoEnum.ARCHIVO_SUI55.getIdentificador()));
			archivoDTO = archivoOptional.get();
			detalle = new ArrayList<DetalleRegistroDTO>();
			indice = 0;
			stepExecution.getExecutionContext().put("indice", indice);
			stepExecution.getExecutionContext().put("archivoDTO", archivoOptional.get());
		}
		PatronDTO patronDTO = patronesService.obtenerPatronOracle(item.getRefRegistroPatronal());
		item.setCveDelegacionPatron(patronDTO.getCveDelegacionAux());

		complementarDatosBDTU(item);
		complementarLocal(item, IdentificadorArchivoEnum.ARCHIVO_SUI55);
		launcherSUI55.validaRegistro(item);
		DetalleRegistroDTO detalleRegistroDTO = new DetalleRegistroDTO();
		detalleRegistroDTO.setAseguradoDTO(procesarAseguradoDTO(item));
		detalleRegistroDTO.setIncapacidadDTO(procesarIncapacidadDTO(item));
		detalleRegistroDTO.setPatronDTO(procesarPatronDTO(patronDTO, item));
		detalleRegistroDTO.setBitacoraErroresDTO(item.getBitacoraErroresDTO());
		detalleRegistroDTO.setIdentificadorArchivo(new ObjectId(archivoDTO.getObjectIdArchivo()));
		detalleRegistroDTO.setCveOrigenArchivo(archivoDTO.getCveOrigenArchivo());
		detalleRegistroDTO.setAseguradoPasoAl(item.getAseguradoPasoAl());
		detalleRegistroDTO.getAseguradoDTO().setNumIndice((Integer) stepExecution.getExecutionContext().get("indice"));
		archivoDTO
				.setCifrasControlDTO(procesarCifrasControl(item.getCifrasControlDTO(), cabeceraDTO.getNumRegistros()));
		detalle.add(detalleRegistroDTO);
		indice++;
		stepExecution.getExecutionContext().put("indice", indice);
		stepExecution.getExecutionContext().put("archivoDTO", archivoDTO);
		stepExecution.getExecutionContext().put("detalleRegistroDTO", detalle);
		return detalleRegistroDTO;
	}
}