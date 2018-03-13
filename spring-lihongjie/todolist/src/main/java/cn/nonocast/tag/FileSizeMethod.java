package cn.nonocast.tag;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.List;

// formatting - How to convert byte size into human readable format in java? - Stack Overflow
// http://stackoverflow.com/questions/3758606/how-to-convert-byte-size-into-human-readable-format-in-java
public class FileSizeMethod implements TemplateMethodModelEx {
	@Override
	public Object exec(List arguments) throws TemplateModelException {
		if (arguments.size() != 1) {
			throw new TemplateModelException("Wrong arguments");
		}
		if(arguments.get(0) == null) return "n/a";
		Long p = Long.valueOf(arguments.get(0).toString());
		return humanReadableByteCount(p, true);
	}

	public static String humanReadableByteCount(long bytes, boolean si) {
		int unit = si ? 1000 : 1024;
		if (bytes < unit) return bytes + " B";
		int exp = (int) (Math.log(bytes) / Math.log(unit));
		String pre = (si ? "KMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
		return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}
}
