package kerberos.kdc;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

class MiniKdcConfigBuilder {

	private String workDir;
	private String confDir;
	private String keytabName;
	private Collection<String> principals;

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
		this.principals = Arrays.asList(principals);
		return this;
	}

	String[] build() {

		Collection<String> miniKdcConfig = new ArrayList<>();

		miniKdcConfig.add(workDir);
		miniKdcConfig.add(confDir);
		miniKdcConfig.add(keytabName);
		miniKdcConfig.addAll(principals);

		return miniKdcConfig.toArray(new String[0]);
	}
}
