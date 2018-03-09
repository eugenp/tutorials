/**
 * 
 */
package com.juxtapose.example.ch06.flat;

import org.springframework.batch.item.file.separator.RecordSeparatorPolicy;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-4-5下午10:01:21
 */
public class MultiLineRecordSeparatorPolicy implements RecordSeparatorPolicy {
	private String delimiter = ",";
	private int count = 0;

	/* (non-Javadoc)
	 * @see org.springframework.batch.item.file.separator.RecordSeparatorPolicy#isEndOfRecord(java.lang.String)
	 */
	public boolean isEndOfRecord(String line) {
		return countDelimiter(line) == count;
	}

	/* (non-Javadoc)
	 * @see org.springframework.batch.item.file.separator.RecordSeparatorPolicy#postProcess(java.lang.String)
	 */
	public String postProcess(String record) {
		return record;
	}

	/* (non-Javadoc)
	 * @see org.springframework.batch.item.file.separator.RecordSeparatorPolicy#preProcess(java.lang.String)
	 */
	public String preProcess(String record) {
		return record;
	}
	
	private int countDelimiter(String s) {
		String tmp = s;
		int index = -1;
		int count = 0;
		while ((index=tmp.indexOf(","))!=-1) {
			tmp = tmp.substring(index+1);
			count++;
		}
		return count;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public int getCount() {
		return count;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
