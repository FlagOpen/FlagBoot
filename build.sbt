ThisBuild / scalaVersion := "2.12.8"

lazy val server = (project in file("."))
  .settings(
    name := "FlagJKB-Test",
    Compile/mainClass := Some("Process.IOServer"),
    assembly/mainClass := Some("Process.IOServer"),
    assembly/assemblyMergeStrategy := {
      case PathList("META-INF", xs @ _*) => MergeStrategy.discard
      case "application.conf" => MergeStrategy.concat
      case x => MergeStrategy.first
    },
    assemblyJarName in assembly := "Template.jar"
  )
lazy val doobieVersion = "0.13.4"
val circeVersion = "0.14.1"
lazy val akkaVersion = "2.6.18"
lazy val akkaHttpVersion = "10.2.4"
val http4sVersion = "0.22.14"
resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-literal",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-generic-extras",
  "io.circe" %% "circe-parser",
  "io.circe" %% "circe-shapes",
).map(_ % circeVersion)
libraryDependencies ++= Seq(
  "org.tpolecat" %% "doobie-core"     % doobieVersion,
  "org.tpolecat" %% "doobie-postgres" % doobieVersion,
  "org.tpolecat" %% "doobie-hikari"     % doobieVersion,
  "org.tpolecat" %% "doobie-specs2"   % doobieVersion
)
libraryDependencies ++= Seq(
  "com.rabbitmq" % "amqp-client" % "5.14.1",
  "org.apache.poi" % "poi-ooxml" % "3.17",
  "com.typesafe.slick" %% "slick" % "3.3.1",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.1",
  "org.postgresql" % "postgresql" % "42.2.5",//org.postgresql.ds.PGSimpleDataSource dependency
  "joda-time"%"joda-time"%"2.10.5",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4",
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster-typed"         % akkaVersion,
  "com.typesafe.akka" %% "akka-discovery" % akkaVersion,
  "com.typesafe.akka" %% "akka-pki" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test,
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
  "org.scalatest" %% "scalatest" % "3.1.0" % Test,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-serialization-jackson" % akkaVersion,
  "com.thesamet.scalapb" %% "scalapb-runtime" % "0.10.7",
  "io.netty" % "netty-tcnative-boringssl-static" % "2.0.22.Final",
  "com.sun.mail" % "javax.mail" % "1.6.2",
  "com.aliyun" % "aliyun-java-sdk-dysmsapi" % "2.1.0",
  "com.aliyun" % "aliyun-java-sdk-core" % "4.5.4",
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "ch.megard" %% "akka-http-cors" % "0.2.2",
  //"ch.qos.logback" % "logback-classic" % "1.2.10",
  "com.typesafe.akka" %% "akka-persistence-query" % akkaVersion,
  "com.typesafe.akka" %% "akka-persistence-typed" % akkaVersion,
  "com.lightbend.akka" %% "akka-persistence-jdbc" % "5.0.4",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.4",
  "io.monix" %% "monix" % "3.4.0",

  "com.github.takezoe" %% "runtime-scaladoc-reader" % "1.0.1",
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-circe" % http4sVersion,
  "org.http4s" %% "http4s-ember-server" % http4sVersion,
  "org.http4s" %% "http4s-ember-client" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % http4sVersion,//读注释生成文档，注意到下面的addComplilerPlugin(com.github.takezoe)也需要加
)
addCompilerPlugin("com.github.takezoe" %% "runtime-scaladoc-reader" % "1.0.1")
addCompilerPlugin(
  "org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full
)

scalacOptions += "-deprecation"
scalacOptions += "-Ypartial-unification"

fork in run := true
