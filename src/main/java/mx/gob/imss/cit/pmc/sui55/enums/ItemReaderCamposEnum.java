package mx.gob.imss.cit.pmc.sui55.enums;

import lombok.Getter;
import lombok.Setter;

public enum ItemReaderCamposEnum {

	CAMPOS_SUI55(new String[] { "identificador", "refFolioOriginal", "cveDelegacionNss", "cveSubdelNss",
			"cveUmfAdscripcion", "cveDelegacionAtencion", "cveSubDelAtencion", "cveUmfExp", "cveUmfPagadora",
			"refRegistroPatronal", "numNss", "refCurp", "nomAsegurado", "cveTipoRiesgo", "cveConsecuencia", "fecInicio",
			"fecAtencion", "fecAccidente", "fecInicioPension", "fecAltaRegistro", "fecExpedicionDictamen", "fecFin",
			"numDiasSubsidiados", "porPorcentajeIncapacidad", "numSalarioDiario", "numLaudo", "numCodigoDiagnostico",
			"numMatriculaTratante", "numMatriculaAutoriza", "cveCausaExterna", "cveNaturaleza", "cveRiesgoFisico",
			"cveActoInseguro", "cveOcupacion", "cveTipoIncapacidad" }),

	CAMPOS_SUI55_CARGA_INICIAL(new String[] { "identificador", "refFolioOriginal", "cveDelegacionNss", "cveSubdelNss",
			"cveUmfAdscripcion", "cveDelegacionAtencion", "cveSubDelAtencion", "cveUmfExp", "cveUmfPagadora",
			"refRegistroPatronal", "numNss", "refCurp", "nomAsegurado", "cveTipoRiesgo", "cveConsecuencia", "fecInicio",
			"fecAtencion", "fecAccidente", "fecInicioPension", "fecAltaRegistro", "fecExpedicionDictamen", "fecFin",
			"numDiasSubsidiados", "porPorcentajeIncapacidad", "numSalarioDiario", "numLaudo", "numCodigoDiagnostico",
			"numMatriculaTratante", "numMatriculaAutoriza", "cveCausaExterna", "cveNaturaleza", "cveRiesgoFisico",
			"cveActoInseguro", "cveOcupacion", "cveTipoIncapacidad", "desRazonSocial", "desRfc", "cveDelRegPatronal",
			"cveSubDelRegPatronal", "cveClase", "cveFraccion", "desFraccion", "numPrima" });

	@Setter
	@Getter
	private String[] valores;

	private ItemReaderCamposEnum(String[] valores) {
		this.valores = valores;
	}

}
