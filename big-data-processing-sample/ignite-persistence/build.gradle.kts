import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm")
}

group = "ru.shipa"
version = "1.0-SNAPSHOT"

application {
    mainClassName = "ru.shipa.ignite.persistence.IgnitePersistenceApp"
}

tasks.jar {
    manifest {
        attributes("Main-Class" to "ru.shipa.ignite.persistence.IgnitePersistenceApp")
    }

    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("joda-time:joda-time:2.10.8")
    implementation("org.apache.ignite:ignite-core:2.9.0")
    implementation("org.apache.ignite:ignite-spring:2.9.0")

    implementation("org.apache.kafka:kafka-clients:2.6.0")
    implementation("org.slf4j:slf4j-log4j12:1.7.25")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.8.7")

    testImplementation(kotlin("test-junit5"))
}
