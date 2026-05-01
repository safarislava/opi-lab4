plugins {
    java
    war
    alias(libs.plugins.shadow)
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(libs.bundles.jakarta)
    implementation(
        libs.primefaces.get().group,
        libs.primefaces.get().name,
        libs.primefaces.get().version,
        classifier = "jakarta"
    )
    implementation(libs.bundles.hibernate)
    implementation(libs.gson)
    testImplementation(libs.junit)
    testImplementation(libs.h2database)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(libs.versions.java.get())
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.jar {
    enabled = false
}

tasks.war {
    from("src/webapp") {
        into("")
    }

    archiveFileName = "ROOT.war"
}

tasks.register<Copy>("copyDependencies") {
    from(configurations.compileClasspath)
    into(layout.projectDirectory.dir("libs"))

    from(configurations.runtimeClasspath)
    into(layout.projectDirectory.dir("libs"))

    from(configurations.testCompileClasspath)
    into(layout.projectDirectory.dir("libs"))

    from(configurations.testRuntimeClasspath)
    into(layout.projectDirectory.dir("libs"))
}

tasks.test {
    useJUnit()
    testLogging {
        events("failed", "skipped")
        showStandardStreams = true
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        showCauses = true
        showExceptions = true
        showStackTraces = true
    }
    maxParallelForks = Runtime.getRuntime().availableProcessors()
}