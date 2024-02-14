plugins {
    val kotlinVersion = "1.9.21"

    kotlin("jvm") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.allopen") version kotlinVersion
    id("com.google.devtools.ksp") version "1.9.21-1.0.16"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.micronaut.application") version "4.2.1"
    id("io.micronaut.aot") version "4.2.1"
    id("io.micronaut.test-resources") version "4.3.2"
}

version = "0.1"
group = "eyalgo.demo"

repositories {
    mavenCentral()
}

dependencies {
    val kotlinVersion: String by project
    // kotlin stuff
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")

    // micronaut
    ksp("io.micronaut:micronaut-http-validation:3.9.2")
    ksp("io.micronaut.serde:micronaut-serde-processor:1.5.2")
    implementation("io.micronaut:micronaut-management:3.8.7")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime:3.2.2")
    implementation("io.micronaut.serde:micronaut-serde-jackson:1.5.2")

    // micronaut data
    ksp("io.micronaut.data:micronaut-data-processor:3.9.6")
    implementation("io.micronaut.data:micronaut-data-jdbc:3.9.6")
    implementation("io.micronaut.liquibase:micronaut-liquibase:5.7.0")
    implementation("io.micronaut.sql:micronaut-jdbc-hikari:4.8.0")

    // exposed
    // https://github.com/JetBrains/Exposed
    val exposedVersion: String by project
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-crypt:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:$exposedVersion")

    implementation("org.jetbrains.exposed:exposed-json:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-money:$exposedVersion")

    // Runtime stuff
    compileOnly("io.micronaut:micronaut-http-client:3.8.7")
    runtimeOnly("ch.qos.logback:logback-classic:1.4.12")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.2")
    runtimeOnly("org.yaml:snakeyaml:2.0")

    // Testing
    testImplementation("io.micronaut:micronaut-http-client:3.8.7")

    // kotest and mockk
    testImplementation("io.kotest:kotest-assertions-core:5.8.0")
    testImplementation("io.kotest.extensions:kotest-assertions-arrow:1.4.0")
    testImplementation("io.mockk:mockk:1.13.9")

    testImplementation("io.projectreactor:reactor-core:3.6.2")

    // Testing DB
    testImplementation("com.h2database:h2:2.2.224")
}

application {
    mainClass.set("eyalgo.demo.ApplicationKt")
}

java {
    sourceCompatibility = JavaVersion.toVersion("17")
}

graalvmNative.toolchainDetection.set(false)
micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("eyalgo.demo.*")
    }
    aot {
    // Please review carefully the optimizations enabled below
    // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading.set(false)
        convertYamlToJava.set(false)
        precomputeOperations.set(true)
        cacheEnvironment.set(true)
        optimizeClassLoading.set(true)
        deduceEnvironment.set(true)
        optimizeNetty.set(true)
    }
}
