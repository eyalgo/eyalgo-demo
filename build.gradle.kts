import io.micronaut.testresources.buildtools.KnownModules.JDBC_POSTGRESQL

plugins {
    val kotlinVersion = "1.9.21"

    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
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

    // micronaut serialization
    val micronautSerdeVersion = "1.5.2"
    annotationProcessor("io.micronaut.serde:micronaut-serde-processor:$micronautSerdeVersion")
    implementation("io.micronaut.serde:micronaut-serde-jsonp:$micronautSerdeVersion")

    // database migration
//    implementation("io.micronaut.liquibase:micronaut-liquibase:5.7.0")
    implementation("io.micronaut.flyway:micronaut-flyway:5.5.0") {
        exclude(group = "io.micronaut", module = "micronaut-jackson-databind")
    }
    runtimeOnly("org.flywaydb:flyway-mysql:9.16.0")

    // DB
    implementation("io.micronaut.sql:micronaut-jdbc-hikari:4.8.0")
    runtimeOnly("com.h2database:h2:2.2.224")

    // validation
    annotationProcessor("io.micronaut:micronaut-http-validation:3.9.2")
    annotationProcessor("io.micronaut.validation:micronaut-validation-processor:3.9.2")
    implementation("io.micronaut.validation:micronaut-validation:3.8.7")
    implementation("jakarta.validation:jakarta.validation-api:3.0.2")

    // jwt security
    val micronautSecurityVersion = "3.10.0"
    annotationProcessor("io.micronaut.security:micronaut-security-annotations:$micronautSecurityVersion")
    implementation("io.micronaut.security:micronaut-security-jwt:$micronautSecurityVersion")
    aotPlugins("io.micronaut.security:micronaut-security-aot:$micronautSecurityVersion")
    aotPlugins(platform("io.micronaut.platform:micronaut-platform:4.3.2"))

    // reactive (for jwt)
    implementation("io.micronaut.reactor:micronaut-reactor:2.6.0")

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
    runtimeOnly("org.yaml:snakeyaml:2.0")

    // Testing
    testImplementation("io.micronaut:micronaut-http-client:3.8.7")
    testImplementation("io.micronaut.test:micronaut-test-rest-assured:3.9.2")

    // kotest and mockk
    testImplementation("io.kotest:kotest-assertions-core:5.8.0")
    testImplementation("io.kotest.extensions:kotest-assertions-arrow:1.4.0")
    testImplementation("io.mockk:mockk:1.13.9")

    // Test DB
    testRuntimeOnly("org.postgresql:postgresql:42.5.4")

    // Test Resources
    val extensionVersion = "2.3.3"
    testImplementation("io.micronaut.testresources:micronaut-test-resources-extensions-core:$extensionVersion")
    testImplementation("io.micronaut.testresources:micronaut-test-resources-extensions-junit-platform:$extensionVersion")
    val testContainersVersion = "1.17.6"
    testImplementation("org.testcontainers:testcontainers:$testContainersVersion")
    testImplementation("org.testcontainers:postgresql:$testContainersVersion")
}

application {
    mainClass.set("eyalgo.demo.Application")
}

java {
    sourceCompatibility = JavaVersion.toVersion("17")
}

tasks {
    task<Copy>("bootJar") {
        description = "Support pipelines, which are adjusted to SpringBoot."
        dependsOn.add(build)

        from("build/libs/${project.name}-${project.version}-all-optimized.jar")
        into("build/libs")
        rename{ fileName ->
            fileName.replace("${project.name}-${project.version}-all-optimized", project.name)
        }
    }
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
    testResources {
        enabled = false
        additionalModules.add(JDBC_POSTGRESQL)
    }
}
