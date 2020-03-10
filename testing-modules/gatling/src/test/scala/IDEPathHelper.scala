import java.nio.file.Path

import io.gatling.commons.util.PathHelper._

object IDEPathHelper {

  val gatlingConfUrl: Path = getClass.getClassLoader.getResource("gatling.conf")
  val projectRootDir = gatlingConfUrl.ancestor(3)

  val mavenSourcesDirectory = projectRootDir / "src" / "test" / "scala"
  val mavenResourcesDirectory = projectRootDir / "src" / "test" / "resources"
  val mavenTargetDirectory = projectRootDir / "target"
  val mavenBinariesDirectory = mavenTargetDirectory / "test-classes"

  val resourcesDirectory = mavenResourcesDirectory
  val recorderSimulationsDirectory = mavenSourcesDirectory
  val resultsDirectory = mavenTargetDirectory / "gatling"
  val recorderConfigFile = mavenResourcesDirectory / "recorder.conf"
}
