import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.4.10"
}
group = "ru.shipa"
version = "1.0-SNAPSHOT"

application {
    mainClassName = "ru.shipa.big.data.processing.sample.App"
}

tasks.jar {
    manifest {
        attributes("Main-Class" to "ru.shipa.hadoop.sample.App")
    }

    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test-junit5"))
}
