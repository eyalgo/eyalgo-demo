import io.micronaut.testresources.buildtools.KnownModules.JDBC_MYSQL
import io.micronaut.testresources.buildtools.KnownModules.JDBC_POSTGRESQL

plugins {
    idea

    val kotlinVersion = "1.9.22"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.allopen") version kotlinVersion
    id("com.google.devtools.ksp") version "1.9.22-1.0.17"
    id("com.github.johnrengelman.shadow") version "8.1.1"

    id("io.micronaut.application") version "4.3.3"
    id("io.micronaut.aot") version "4.2.1"

    id("io.micronaut.test-resources") version "4.3.2"

    id("com.gorylenko.gradle-git-properties") version "2.4.1"
}

version = "0.1"
group = "eyalgo.demo"

repositories {
    mavenCentral()
}

application {
    mainClass.set("eyalgo.demo.Application")
}

java {
    sourceCompatibility = JavaVersion.toVersion("17")
    targetCompatibility = JavaVersion.toVersion("17")
}

idea {
    module {
        // Not using += due to https://github.com/gradle/gradle/issues/8749
        sourceDirs = sourceDirs + file("build/generated/ksp/main/kotlin") // or tasks["kspKotlin"].destination
        testSourceDirs = testSourceDirs + file("build/generated/ksp/test/kotlin")
        generatedSourceDirs = generatedSourceDirs + file("build/generated/ksp/main/kotlin") + file("build/generated/ksp/test/kotlin")
    }
}

rootProject.apply {
    from("gradle/exposed.gradle")
}

dependencies {
    val kotlinVersion: String by project
    // kotlin stuff
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")

    // micronaut
    implementation("io.micronaut:micronaut-management")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    annotationProcessor("io.micronaut:micronaut-inject-java")
    compileOnly("io.micronaut:micronaut-inject-java")

    // messaging
    implementation("io.micronaut.jms:micronaut-jms-activemq-classic")

    // micronaut serialization
    ksp("io.micronaut.serde:micronaut-serde-processor")
    annotationProcessor("io.micronaut.serde:micronaut-serde-processor")
    implementation("io.micronaut.serde:micronaut-serde-jackson")

    // database migration
    implementation("io.micronaut.flyway:micronaut-flyway")
    runtimeOnly("org.flywaydb:flyway-mysql") // for h2

    // DB
    implementation("io.micronaut.sql:micronaut-jdbc-hikari")

    // database drivers
    runtimeOnly("com.h2database:h2:2.2.224")
//    implementation("org.postgresql:postgresql")
    testRuntimeOnly("com.mysql:mysql-connector-j:8.3.0")

    // validation
    ksp("io.micronaut:micronaut-http-validation")
    annotationProcessor("io.micronaut:micronaut-http-validation")
    annotationProcessor("io.micronaut.validation:micronaut-validation-processor")
    implementation("io.micronaut.validation:micronaut-validation")
    implementation("jakarta.validation:jakarta.validation-api:3.0.2")

    // jwt security
    annotationProcessor("io.micronaut.security:micronaut-security-annotations")
    implementation("io.micronaut.security:micronaut-security-jwt")
    aotPlugins("io.micronaut.security:micronaut-security-aot")
    aotPlugins(platform("io.micronaut.platform:micronaut-platform"))

    // reactive (for jwt)
    implementation("io.micronaut.reactor:micronaut-reactor")

    // metrics
    implementation("io.micronaut.micrometer:micronaut-micrometer-annotation")
    implementation("io.micronaut.micrometer:micronaut-micrometer-registry-prometheus")
    implementation("io.micronaut:micronaut-management")

    // cache
    implementation("io.micronaut.cache:micronaut-cache-hazelcast")
    implementation("io.micronaut.cache:micronaut-cache-management")

    // Runtime stuff
    compileOnly("io.micronaut:micronaut-http-client")
    runtimeOnly("ch.qos.logback:logback-classic:1.4.14")
    runtimeOnly("org.yaml:snakeyaml:2.0")

    // Testing
    testImplementation("io.micronaut:micronaut-inject-java")
    testImplementation("io.micronaut:micronaut-http-client")

    testImplementation("io.github.hakky54:logcaptor:2.9.2")
    testImplementation("org.awaitility:awaitility-kotlin:4.2.0")

    // Rest Assured
    val restAssuredVersion = "5.4.0"
    testImplementation("io.rest-assured:kotlin-extensions:$restAssuredVersion")
    testImplementation("io.rest-assured:json-path:$restAssuredVersion")
    testRuntimeOnly("junit:junit:4.13.2")

    // kotest and mockk
    testImplementation("io.kotest:kotest-assertions-core:5.8.0")
    testImplementation("io.kotest.extensions:kotest-assertions-arrow:1.4.0")
    testImplementation("io.mockk:mockk:1.13.10")

    // Test Resources
    val extensionVersion = "2.3.3"
    testImplementation("io.micronaut.testresources:micronaut-test-resources-extensions-core:$extensionVersion")
    testImplementation("io.micronaut.testresources:micronaut-test-resources-extensions-junit-platform:$extensionVersion")
    val testContainersVersion = "1.19.4"
    testImplementation("org.testcontainers:testcontainers:$testContainersVersion")
    testImplementation("org.testcontainers:postgresql:$testContainersVersion")
    testImplementation("org.testcontainers:mysql:$testContainersVersion")
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
    ignoredAutomaticDependencies.add("io.micronaut.data:micronaut-data-processor")
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
        additionalModules.addAll(JDBC_POSTGRESQL, JDBC_MYSQL)
    }
}
