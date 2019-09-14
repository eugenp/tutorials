package com.baeldung.componentscan.filter.custom;

import java.io.IOException;

import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

public class ComponentScanCustomFilter implements TypeFilter {

	@Override
	public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
			throws IOException {
		ClassMetadata classMetadata = metadataReader.getClassMetadata();
		String superClass = classMetadata.getSuperClassName();
		if (Pet.class.getName().equalsIgnoreCase(superClass)) {
			return true;
		}
		return false;
	}
}
