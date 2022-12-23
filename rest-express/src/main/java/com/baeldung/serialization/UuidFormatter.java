package com.baeldung.serialization;

import java.util.UUID;

import com.strategicgains.hyperexpress.annotation.TokenFormatter;
import com.strategicgains.repoexpress.util.UuidConverter;

public class UuidFormatter
implements TokenFormatter
{
	@Override
	public String format(Object field)
	{
		return UuidConverter.format((UUID) field);
	}
}
