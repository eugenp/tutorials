/**
 * 
 */
package com.juxtapose.example.ch09;


/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-10-7上午10:06:37
 */
public interface CreditService {
	
	/**
	 * 解压缩文件
	 * @param inputFile
	 * @param outputDirectory
	 */
	void decompress(String inputFile,String outputDirectory);
	
	/**
	 * 校验文件
	 * @param outputDirectory
	 */
	String verify(String outputDirectory, String readFileName);
	
	/**
	 * 清空临时作业空间
	 * @param outputDirectory
	 */
	void clean(String outputDirectory);
	
	
	/**
	 * 检查文件是否存在
	 * @param file
	 * @return
	 */
	boolean exists(String file);
}
