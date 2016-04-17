/*******************************************************************************
 * Copyright (c) 2016 Lablicate UG (haftungsbeschr채nkt).
 *
 * All rights reserved.
 *
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.ascii.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
import org.eclipse.chemclipse.msd.model.implementation.MassSpectra;
import org.eclipse.chemclipse.msd.model.implementation.ScanMSD;
import org.eclipse.core.runtime.IProgressMonitor;

public class MassSpectraReader extends AbstractMassSpectraReader implements IMassSpectraReader {

	private static final Logger logger = Logger.getLogger(MassSpectraReader.class);

	@Override
	public IMassSpectra read(File file, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

		IMassSpectra massSpectra = new MassSpectra();
		IScanMSD massSpectrum = new ScanMSD();
		/*
		 * TODO Trig
		 * Please implement a file reader here, e.g. using Regex Patterns.
		 * Have a look at the JCAMP-DX plugin:
		 * org.eclipse.chemclipse.xxd.converter.supplier.jcampdx
		 */
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = null;
		while((line = br.readLine()) != null) {
			line = line.trim();
			// skip blank lines or comment lines(begin with '#' or ';')
			if((line.length() == 0) || line.startsWith(";")) {
				continue;
			}
			if(line.toUpperCase().matches("^#.*TIME.*=.*")) {
				// The retention time is stored in seconds scale, must be changed to milliseconds.
				int retentionTime = (int)(getNumberValue(line) * 1000d);
				massSpectrum.setRetentionTime(retentionTime);
				continue;
			}
			if(line.toUpperCase().matches("^#.*INDEX.*=.*")) {
				float retentionIndex = getNumberValue(line);
				massSpectrum.setRetentionIndex(retentionIndex);
				continue;
			}
			// another lines begins with '#' will be treated as comment line
			if(line.startsWith("#")) {
				continue;
			}
			String[] temp = line.split("\\s+");
			if((temp != null) && (temp.length >= 2)) {
				addIon(massSpectrum, temp);
			}
		}
		br.close();
		//
		massSpectra.addMassSpectrum(massSpectrum);
		return massSpectra;
	}

	private void addIon(IScanMSD massSpectrum, String[] temp) {

		try {
			double mz = Double.parseDouble(temp[0]);
			float abundance = (float)Double.parseDouble(temp[1].replace(";", ""));
			massSpectrum.addIon(new Ion(mz, abundance));
		} catch(NumberFormatException e) {
			logger.warn(e);
		} catch(AbundanceLimitExceededException e) {
			logger.warn(e);
		} catch(IonLimitExceededException e) {
			logger.warn(e);
		}
	}

	private float getNumberValue(String line) {

		float number = 0.0f;
		String value = line.substring(line.indexOf("=") + 1);
		try {
			number = (float)Double.parseDouble(value);
		} catch(NumberFormatException e) {
			logger.warn(e);
		}
		return number;
	}
}
