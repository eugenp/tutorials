import java.nio.file.{Path, Paths}

object IDEPathHelper {
    val gatlingConfUrl: Path = Paths.get(getClass.getClassLoader.getResource("gatling.conf").toURI)

    val projectRootDir: Path = gatlingConfUrl.getParent.getParent.getParent

    val mavenSourcesDirectory: Path = projectRootDir.resolve("src").resolve("test").resolve("scala")
    val mavenResourcesDirectory: Path = projectRootDir.resolve("src").resolve("test").resolve("resources")
    val mavenTargetDirectory: Path = projectRootDir.resolve("target")
    val mavenBinariesDirectory: Path = mavenTargetDirectory.resolve("test-classes")
    val resourcesDirectory: Path = mavenResourcesDirectory
    val recorderSimulationsDirectory: Path = mavenSourcesDirectory
    val resultsDirectory: Path = mavenTargetDirectory.resolve("gatling")
    val recorderConfigFile: Path = mavenResourcesDirectory.resolve("recorder.conf")
}
