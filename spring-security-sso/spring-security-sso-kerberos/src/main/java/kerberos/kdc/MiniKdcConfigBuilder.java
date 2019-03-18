package kerberos.kdc;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Objects;

class MiniKdcConfigBuilder {

	private static final int FIELDS_COUNT = 3;
	private String workDir;
	private String confDir;
	private String keytabName;
	private String[] principals = { "principal/localhost" };

	private MiniKdcConfigBuilder() {
		// desired
	}

	static MiniKdcConfigBuilder builder() {
		return new MiniKdcConfigBuilder();
	}

	MiniKdcConfigBuilder workDir(String workDir) {
		this.workDir = workDir;
		return this;
	}

	MiniKdcConfigBuilder confDir(String cfg) {
		try {
			URL resource = Thread.currentThread().getContextClassLoader().getResource(cfg);
			URI uri = Objects.requireNonNull(resource).toURI();
			this.confDir = Paths.get(uri).toString();
		} catch (URISyntaxException cause) {
			throw new IllegalStateException("Could not resolve path for: " + cfg, cause);
		}
		return this;
	}

	MiniKdcConfigBuilder keytabName(String keytabName) {
		this.keytabName = Paths.get(workDir).resolve(keytabName).toString();
		return this;
	}

	MiniKdcConfigBuilder principals(String... principals) {
		this.principals = principals;
		return this;
	}

	String[] build() {

		String[] args = new String[FIELDS_COUNT + principals.length];

		args[0] = workDir;
		args[1] = confDir;
		args[2] = keytabName;

		for (int i = 0, j = 3; i < principals.length; i++, j++) {
			args[j] = principals[i];
		}

		return args;
	}
}
