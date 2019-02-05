# TransferApp - Core

## Technicalities

Some great stuff used in this app:
* Gradle 5
* Java 11
* Ratpack
* jOOQ
* H2
* Spock Framework
* for more details, please take a look at the `build.gradle` file :)

## How to run it?

1. Generate jOOQ's schema (yep, it could be attached to the main build Gradle task, but for this case it's easier to do it in this way):
   ```bash
   ./gradlew :transfer-app-core:generateTransferappJooqSchemaSource
   ```
   If there will be need to regenerate the schema by each build, comment out or remove line `project.tasks.getByName("compileJava").dependsOn -= "generateTransferappJooqSchemaSource"` from the `jooq.gradle` file located in the root of this subproject.

2. Run the app using Ratpack Gradle plugin:
   ```bash
   ./gradlew :transfer-app-core:run
   ```
   or to run in the [reload mode][1] (not working well under Java 9+):
   ```bash
   ./gradlew :transfer-app-core:run -t
   ```

## How to create a ready-to-go fat-jar?

1. Create an artifact using the [Shadow plugin][2] (after creating jOOQ schema!):
   ```bash
   ./gradlew :transfer-app-core:shadowJar
   ```
2. Run the app:
   ```bash
   java -jar transfer-app-core-${projectVersion}.jar
   ```
   By default it will be located in the following directory: `${repo_root}/transfer-app-core/build/libs`
3. As the in-memory DB is used, there won't be any problems with configuring the database at all, at least in this moment.

## Possible problems running the app on Java 9+

1. As [Ratpack][3] uses [Guice][4] and [Netty][5], they can complain a lot complain due to some Java 9+ limitations and to bypass them, there is a need to use the following JVM parameters:
   ```text
   --add-opens java.base/java.lang=ALL-UNNAMED
   --add-exports java.base/jdk.internal.misc=ALL-UNNAMED
   --illegal-access=warn   
   -Dio.netty.tryReflectionSetAccessible=true
   ```
   Running the app via Gradle plugin should not require using these parameters explicit because they are already configured in the `build.gradle` configuration. 

[1]: https://ratpack.io/manual/current/gradle.html#development_time_reloading
[2]: https://github.com/johnrengelman/shadow
[3]: https://ratpack.io/manual/current/java9-support.html
[4]: https://www.github.com/google/guice/issues/1133
[5]: https://www.github.com/ratpack/ratpack/issues/1410