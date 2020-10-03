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
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    compile("org.apache.hadoop:hadoop-common:2.7.3")
    compile("org.apache.hadoop:hadoop-hdfs:2.7.3")
    compile("org.apache.hadoop:hadoop-yarn-common:2.7.3")
    compile("org.apache.hadoop:hadoop-minicluster:2.7.3")
    compile("org.apache.hadoop:hadoop-mapreduce-client-core:2.7.3")
    compile("org.apache.hadoop:hadoop-mapreduce-client-jobclient:2.7.3")
    compile("org.apache.hadoop:hadoop-mapreduce-client-app:2.7.3")
    compile("org.apache.hadoop:hadoop-mapreduce-client-shuffle:2.7.3")
    compile("org.apache.hadoop:hadoop-mapreduce-client-common:2.7.3")
    compile("org.apache.hadoop:hadoop-client:2.7.3")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testCompile("junit:junit:4.11")
}