plugins {
    application
    kotlin("jvm") version "1.4.10"
}

application {
    mainClassName = "ru.shipa.hadoop.sample.AppKt"
}

repositories {
    jcenter()
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

dependencies {
    val hadoopVersion = "2.10.0"
    val junit5Version = "5.0.2"

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.apache.hadoop:hadoop-common:$hadoopVersion")
    implementation("org.apache.hadoop:hadoop-hdfs:$hadoopVersion")
    implementation("org.apache.hadoop:hadoop-yarn-common:$hadoopVersion")
    implementation("org.apache.hadoop:hadoop-minicluster:$hadoopVersion")
    implementation("org.apache.hadoop:hadoop-mapreduce-client-core:$hadoopVersion")
    implementation("org.apache.hadoop:hadoop-mapreduce-client-jobclient:$hadoopVersion")
    implementation("org.apache.hadoop:hadoop-mapreduce-client-app:$hadoopVersion")
    implementation("org.apache.hadoop:hadoop-mapreduce-client-shuffle:$hadoopVersion")
    implementation("org.apache.hadoop:hadoop-mapreduce-client-common:$hadoopVersion")
    implementation("org.apache.hadoop:hadoop-client:$hadoopVersion")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junit5Version")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junit5Version")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junit5Version")
    testImplementation("org.apache.mrunit:mrunit:1.1.0:hadoop2")
}