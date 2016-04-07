/*******************************************************************************
 * Copyright (c) 2016 Lablicate UG (haftungsbeschränkt).
 * 
 * All rights reserved.
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.ascii.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.chemclipse.converter.exceptions.FileIsEmptyException;
import org.eclipse.chemclipse.converter.exceptions.FileIsNotReadableException;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.exceptions.AbundanceLimitExceededException;
import org.eclipse.chemclipse.msd.converter.io.AbstractMassSpectraReader;
import org.eclipse.chemclipse.msd.converter.io.IMassSpectraReader;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.exceptions.IonLimitExceededException;
import org.eclipse.chemclipse.msd.model.implementation.Ion;
import org.eclipse.chemclipse.msd.model.implementation.ScanMSD;
import org.eclipse.core.runtime.IProgressMonitor;

public class MassSpectraReader extends AbstractMassSpectraReader implements IMassSpectraReader {

	private static final Logger logger = Logger.getLogger(MassSpectraReader.class);

	@Override
	public IMassSpectra read(File file, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

		IMassSpectra massSpectra = null;
		/*
		 * TODO Trig
		 * Please implement a file reader here, e.g. using Regex Patterns.
		 * Have a look at the JCAMP-DX plugin:
		 * org.eclipse.chemclipse.xxd.converter.supplier.jcampdx
		 */
		IScanMSD massSpectrum = new ScanMSD();
		massSpectrum.setRetentionTime(500);
		massSpectrum.setRetentionIndex(1.72f);
		try {
			massSpectrum.addIon(new Ion(18.1d, 5002.5f));
			massSpectrum.addIon(new Ion(28.0d, 8222.5f));
		} catch(AbundanceLimitExceededException e) {
			logger.warn(e);
		} catch(IonLimitExceededException e) {
			logger.warn(e);
		}
		//
		return massSpectra;
	}
}
