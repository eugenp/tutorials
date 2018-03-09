/**
 * 
 */
package com.juxtapose.example.ch07.flat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.batch.item.file.transform.ExtractorLineAggregator;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-9-20上午10:00:03
 */
public class CustomLineAggregator<T> extends ExtractorLineAggregator<T> {
	private String delimiter = ";";
	private String[] names;

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}
	
	public void setNames(String[] names) {
		Assert.notNull(names, "Names must be non-null");
		this.names = Arrays.asList(names).toArray(new String[names.length]);
	}

	@Override
	protected String doAggregate(Object[] fields) {
		List<String> fieldList = new ArrayList<String>();
		for (int i = 0; i < names.length; i++) {
			fieldList.add(names[i] + "=" + fields[i]);
		}
		return StringUtils
				.arrayToDelimitedString(
						fieldList.toArray(new String[fieldList.size()]),
						this.delimiter);
	}
}
