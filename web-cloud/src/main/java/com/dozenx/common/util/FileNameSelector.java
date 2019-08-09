package com.dozenx.common.util;

import java.io.File;
import java.io.FilenameFilter;

public class FileNameSelector implements FilenameFilter {
	String extension = ".";
	public FileNameSelector(String fileExtensionNoDot) {
		extension += fileExtensionNoDot;
	}

	public boolean accept(File dir, String name) {
		return name.endsWith(extension);
	}
}
