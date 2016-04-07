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
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.chemclipse.converter.exceptions.FileIsNotWriteableException;
import org.eclipse.chemclipse.msd.converter.io.IMassSpectraWriter;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.core.runtime.IProgressMonitor;

public class MassSpectraWriter implements IMassSpectraWriter {

	@Override
	public void write(File file, IScanMSD massSpectrum, boolean append, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotWriteableException, IOException {

	}

	@Override
	public void write(File file, IMassSpectra massSpectra, boolean append, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotWriteableException, IOException {

	}

	@Override
	public void writeMassSpectrum(FileWriter fileWriter, IScanMSD massSpectrum, IProgressMonitor monitor) throws IOException {

	}
}
