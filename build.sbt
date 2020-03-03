import sbt.ExclusionRule
import sbtassembly.MergeStrategy
name := "spark-on-google-cloud-app"

version := "0.1"

scalaVersion := "2.11.8"

val sparkVersion = "2.2.0"
val hadoopVersion = "2.7.3.2.6.4.0-91"
val hiveVersion = "1.2.1000.2.6.4.0-91"
val scalaVersion_ =  "2.11.8"
val scalaTestVersion    = "3.0.5"
val scalaCheckVersion   = "1.13.4"

libraryDependencies ++= Seq(
  "org.apache.spark"  %% "spark-core"      % sparkVersion,
  "org.apache.spark"  %% "spark-streaming" % sparkVersion,
  "org.apache.spark"  %% "spark-sql"       % sparkVersion,
  "org.apache.spark"  %% "spark-hive"      % sparkVersion,
  "org.apache.spark"  %% "spark-repl"      % sparkVersion,
  "com.holdenkarau" %% "spark-testing-base" % "2.2.0_0.8.0" % Test,
  "org.scalatest"     %% "scalatest"       % scalaTestVersion  % "test",
  "org.scalacheck"    %% "scalacheck"      % scalaCheckVersion % "test")

lazy val commonSettings = Seq(
  version := "0.1-SNAPSHOT",
  organization := "com.gcp.poc.spark.project",
  scalaVersion := s"$scalaVersion_",
  test in assembly := {}
)


lazy val common = (project in file("common")).
  settings(commonSettings: _*).
  settings(
    mainClass in assembly := Some("com.gcp.poc.spark.project.ExecutorMain"),
    // more settings here ...

  )

lazy val utils = (project in file("utils")).
  settings(commonSettings: _*).
  settings(
    assemblyJarName in assembly := "utils.jar",
    // more settings here ...
  ).dependsOn(common)


val defaultMergeStrategy: String => MergeStrategy = {
  case x if Assembly.isConfigFile(x) =>
    MergeStrategy.concat
  case PathList(ps @ _*) if Assembly.isReadme(ps.last) || Assembly.isLicenseFile(ps.last) =>
    MergeStrategy.rename
  case PathList("META-INF", xs @ _*) =>
    (xs map {_.toLowerCase}) match {
      case ("manifest.mf" :: Nil) | ("index.list" :: Nil) | ("dependencies" :: Nil) =>
        MergeStrategy.discard
      case ps @ (x :: xs) if ps.last.endsWith(".sf") || ps.last.endsWith(".dsa") =>
        MergeStrategy.discard
      case "plexus" :: xs =>
        MergeStrategy.discard
      case "services" :: xs =>
        MergeStrategy.filterDistinctLines
      case ("spring.schemas" :: Nil) | ("spring.handlers" :: Nil) =>
        MergeStrategy.filterDistinctLines
      case _ => MergeStrategy.deduplicate
    }
  case _ => MergeStrategy.deduplicate
}


