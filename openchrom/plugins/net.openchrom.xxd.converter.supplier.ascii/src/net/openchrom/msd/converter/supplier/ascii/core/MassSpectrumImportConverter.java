/*******************************************************************************
 * Copyright (c) 2016 Lablicate UG (haftungsbeschränkt).
 * 
 * All rights reserved.
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.ascii.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.chemclipse.converter.exceptions.FileIsEmptyException;
import org.eclipse.chemclipse.converter.exceptions.FileIsNotReadableException;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.msd.converter.io.IMassSpectraReader;
import org.eclipse.chemclipse.msd.converter.massspectrum.AbstractMassSpectrumImportConverter;
import org.eclipse.chemclipse.msd.converter.processing.massspectrum.IMassSpectrumImportConverterProcessingInfo;
import org.eclipse.chemclipse.msd.converter.processing.massspectrum.MassSpectrumImportConverterProcessingInfo;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.ascii.io.MassSpectraReader;
import net.openchrom.xxd.converter.supplier.ascii.internal.io.SpecificationValidator;

public class MassSpectrumImportConverter extends AbstractMassSpectrumImportConverter {

	private static final Logger logger = Logger.getLogger(MassSpectrumImportConverter.class);
	private static final String DESCRIPTION = "ASCII MS";

	@Override
	public IMassSpectrumImportConverterProcessingInfo convert(File file, IProgressMonitor monitor) {

		IMassSpectrumImportConverterProcessingInfo processingInfo = new MassSpectrumImportConverterProcessingInfo();
		/*
		 * Checks if the file is null or empty ...
		 */
		IProcessingInfo processingInfoValidate = super.validate(file);
		if(processingInfoValidate.hasErrorMessages()) {
			processingInfo.addMessages(processingInfoValidate);
		} else {
			try {
				file = SpecificationValidator.validateSpecification(file, "ascii");
				IMassSpectraReader massSpectraReader = new MassSpectraReader();
				IMassSpectra massSpectra = massSpectraReader.read(file, monitor);
				processingInfo.setMassSpectra(massSpectra);
			} catch(FileNotFoundException e) {
				logger.warn(e);
				processingInfo.addErrorMessage(DESCRIPTION, "The file couldn't be found: " + file.getAbsolutePath());
			} catch(FileIsNotReadableException e) {
				logger.warn(e);
				processingInfo.addErrorMessage(DESCRIPTION, "The file is not readable: " + file.getAbsolutePath());
			} catch(FileIsEmptyException e) {
				logger.warn(e);
				processingInfo.addErrorMessage(DESCRIPTION, "The file is empty: " + file.getAbsolutePath());
			} catch(IOException e) {
				logger.warn(e);
				processingInfo.addErrorMessage(DESCRIPTION, "Something has gone completely wrong: " + file.getAbsolutePath());
			}
		}
		return processingInfo;
	}
}
