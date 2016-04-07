/*******************************************************************************
 * Copyright (c) 2016 Lablicate UG (haftungsbeschränkt).
 * 
 * All rights reserved.
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.converter.supplier.ascii.internal.io;

import java.io.File;

public class SpecificationValidator {

	/**
	 * Use only static methods.
	 */
	private SpecificationValidator() {
	}

	public static File validateSpecification(File file, String extension) {

		if(file == null) {
			return null;
		}
		/*
		 * Validate
		 */
		File validFile;
		String path = file.getAbsolutePath().toLowerCase();
		if(file.isDirectory()) {
			validFile = new File(file.getAbsolutePath() + File.separator + "LIB." + extension);
		} else {
			if(path.endsWith(".")) {
				validFile = new File(file.getAbsolutePath() + extension);
			} else if(!path.endsWith("." + extension)) {
				validFile = new File(file.getAbsolutePath() + "." + extension);
			} else {
				validFile = file;
			}
		}
		return validFile;
	}
}
