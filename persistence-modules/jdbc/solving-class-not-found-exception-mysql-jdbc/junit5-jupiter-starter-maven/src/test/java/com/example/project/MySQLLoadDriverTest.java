/*
 * Copyright 2015-2024 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v2.0 which
 * accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v20.html
 */

package com.example.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class MySQLLoadDriverTest {

	@Test
	void loadDriver() {
		MySQLLoadDriver connection = new MySQLLoadDriver();
		assertEquals(1,connection.loadDriver());
	}


}
