package com.baeldung.inprogress.hexagonal.util;

import com.baeldung.inprogress.hexagonal.core.domain.Package;

public class PackageUtils {
	public static Package generateDummyPackage(){
		return new Package("London Holiday Package", "LHP");
	}
}
