import io.micronaut.testresources.buildtools.KnownModules.JDBC_MYSQL
import io.micronaut.testresources.buildtools.KnownModules.JDBC_POSTGRESQL

plugins {
    idea

    val kotlinVersion = "1.9.21"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.allopen") version kotlinVersion
    id("com.google.devtools.ksp") version "1.9.21-1.0.16"
    id("com.github.johnrengelman.shadow") version "8.1.1"

    id("io.micronaut.application") version "4.2.1"
    id("io.micronaut.aot") version "4.2.1"

    id("io.micronaut.test-resources") version "4.3.2"

    id("com.gorylenko.gradle-git-properties") version "2.4.1"
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
    val micronautVersion = "3.8.7"
    ksp("io.micronaut.serde:micronaut-serde-processor:1.5.2")
    implementation("io.micronaut:micronaut-management:$micronautVersion")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime:3.2.2")

    // micronaut serialization
    val micronautSerdeVersion = "1.5.2"
    annotationProcessor("io.micronaut.serde:micronaut-serde-processor:$micronautSerdeVersion")
    implementation("io.micronaut.serde:micronaut-serde-jsonp:$micronautSerdeVersion")

    // database migration
    implementation("io.micronaut.flyway:micronaut-flyway:5.5.0") {
        exclude(group = "io.micronaut", module = "micronaut-jackson-databind")
    }
    runtimeOnly("org.flywaydb:flyway-mysql:9.16.0")

    // DB
    implementation("io.micronaut.sql:micronaut-jdbc-hikari:4.8.0")
    runtimeOnly("com.h2database:h2:2.2.224")

    // validation
    val micronautValidationVersion = "3.9.2"
    ksp("io.micronaut:micronaut-http-validation:$micronautValidationVersion")
    annotationProcessor("io.micronaut:micronaut-http-validation:$micronautValidationVersion")
    annotationProcessor("io.micronaut.validation:micronaut-validation-processor:$micronautValidationVersion")
    implementation("io.micronaut.validation:micronaut-validation:$micronautVersion")
    implementation("jakarta.validation:jakarta.validation-api:3.0.2")

    // jwt security
    val micronautSecurityVersion = "3.10.0"
    annotationProcessor("io.micronaut.security:micronaut-security-annotations:$micronautSecurityVersion")
    implementation("io.micronaut.security:micronaut-security-jwt:$micronautSecurityVersion")
    aotPlugins("io.micronaut.security:micronaut-security-aot:$micronautSecurityVersion")
    aotPlugins(platform("io.micronaut.platform:micronaut-platform:4.3.2"))

    // reactive (for jwt)
    implementation("io.micronaut.reactor:micronaut-reactor:2.6.0")

    // metrics
    val micronautMicrometerVersion = "4.8.2"
    implementation("io.micronaut.micrometer:micronaut-micrometer-annotation:$micronautMicrometerVersion")
    implementation("io.micronaut.micrometer:micronaut-micrometer-registry-prometheus:$micronautMicrometerVersion")
    implementation("io.micronaut:micronaut-management:$micronautVersion")

    // exposed
    // https://github.com/JetBrains/Exposed
    val exposedVersion = "0.47.0"
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-crypt:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-json:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-money:$exposedVersion")

    // Runtime stuff
    compileOnly("io.micronaut:micronaut-http-client:$micronautVersion")
    runtimeOnly("ch.qos.logback:logback-classic:1.4.12")
    runtimeOnly("org.yaml:snakeyaml:2.0")

    // Testing
    testImplementation("io.micronaut:micronaut-http-client:$micronautVersion")
    testImplementation("io.micronaut.test:micronaut-test-rest-assured:3.9.2")

    // kotest and mockk
    testImplementation("io.kotest:kotest-assertions-core:5.8.0")
    testImplementation("io.kotest.extensions:kotest-assertions-arrow:1.4.0")
    testImplementation("io.mockk:mockk:1.13.9")

    // Test database drivers
    testRuntimeOnly("org.postgresql:postgresql:42.5.4")
    testRuntimeOnly("com.mysql:mysql-connector-j:8.2.0")

    // Test Resources
    val extensionVersion = "2.3.3"
    testImplementation("io.micronaut.testresources:micronaut-test-resources-extensions-core:$extensionVersion")
    testImplementation("io.micronaut.testresources:micronaut-test-resources-extensions-junit-platform:$extensionVersion")
    val testContainersVersion = "1.19.3"
    testImplementation("org.testcontainers:testcontainers:$testContainersVersion")
    testImplementation("org.testcontainers:postgresql:$testContainersVersion")
    testImplementation("org.testcontainers:mysql:$testContainersVersion")
}

application {
    mainClass.set("eyalgo.demo.Application")
}

java {
    sourceCompatibility = JavaVersion.toVersion("17")
}

idea {
    module {
        // Not using += due to https://github.com/gradle/gradle/issues/8749
        sourceDirs = sourceDirs + file("build/generated/ksp/main/kotlin") // or tasks["kspKotlin"].destination
        testSourceDirs = testSourceDirs + file("build/generated/ksp/test/kotlin")
        generatedSourceDirs = generatedSourceDirs + file("build/generated/ksp/main/kotlin") + file("build/generated/ksp/test/kotlin")
    }
}

//kotlin {
//    sourceSets.main {
//        kotlin.srcDir("build/generated/ksp/main/kotlin")
//    }
//    sourceSets.test {
//        kotlin.srcDir("build/generated/ksp/test/kotlin")
//    }
//}

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
        additionalModules.addAll(JDBC_POSTGRESQL, JDBC_MYSQL)
    }
}
