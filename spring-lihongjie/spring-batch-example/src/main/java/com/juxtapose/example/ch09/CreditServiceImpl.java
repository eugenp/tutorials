/**
 * 
 */
package com.juxtapose.example.ch09;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-10-7上午10:27:37
 */
public class CreditServiceImpl implements CreditService {
	ResourceLoader loader = new DefaultResourceLoader();

	/* (non-Javadoc)
	 * @see com.juxtapose.example.ch09.CreditService#decompress(java.lang.String, java.lang.String)
	 */
	@Override
	public void decompress(String inputFile, String outputDirectory) {
		Resource inputFileResource = loader.getResource(inputFile);
		Resource outputDirectoryResource = loader.getResource(outputDirectory);
		try {
			ZipUtils.decompressZip(inputFileResource.getFile().getAbsolutePath(), 
					outputDirectoryResource.getFile().getAbsolutePath());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.juxtapose.example.ch09.CreditService#verify(java.lang.String,java.lang.String)
	 */
	@Override
	public String verify(String outputDirectory, String readFileName) {
		try {
			Resource outputDirectoryResource = loader.getResource(outputDirectory + readFileName);
			if(!outputDirectoryResource.exists()){
				return "FAILED";
			}
			if(!outputDirectoryResource.getFile().canRead()){
				return "SKIPTOCLEAN";
			}else {
				return "COMPLETED";
			}
		} catch (IOException e) {
			return "FAILED";
		}
	}

	/* (non-Javadoc)
	 * @see com.juxtapose.example.ch09.CreditService#clean(java.lang.String)
	 */
	@Override
	public void clean(String outputDirectory) {
		Resource outputDirectoryResource = loader.getResource(outputDirectory);
		try {
			FileUtils.cleanDirectory(outputDirectoryResource.getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.juxtapose.example.ch09.CreditService#exists(java.lang.String)
	 */
	@Override
	public boolean exists(String file) {
		Resource fileResource = loader.getResource(file);
		return fileResource.exists();
	}

}
