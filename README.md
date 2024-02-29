# Micronaut Kotlin Demo

An example project using Micronaut and Kotlin

## Design Concepts

## Micronaut 4.2.4 Documentation

- [User Guide](https://docs.micronaut.io/4.2.4/guide/index.html)
- [API Reference](https://docs.micronaut.io/4.2.4/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/4.2.4/guide/configurationreference.html) (_application.yml_)
- [Micronaut Guides](https://guides.micronaut.io/index.html)

---

- [Micronaut Gradle Plugin documentation](https://micronaut-projects.github.io/micronaut-gradle-plugin/latest/)
- [GraalVM Gradle Plugin documentation](https://graalvm.github.io/native-build-tools/latest/gradle-plugin.html)
- [Shadow Gradle Plugin](https://plugins.gradle.org/plugin/com.github.johnrengelman.shadow)

## Some Pointers 

- [Micronaut Launch](https://micronaut.io/launch)
- [Micronaut Test](https://micronaut-projects.github.io/micronaut-test/latest/guide/)
- [Micronaut for Spring](https://micronaut-projects.github.io/micronaut-spring/latest/guide/)
- [Micronaut Scope Types](https://guides.micronaut.io/latest/micronaut-scope-types-gradle-java.html)
- [Configuration](https://guides.micronaut.io/latest/micronaut-configuration-gradle-kotlin.html) (@Configuration and @ConfigurationBuilder)
- [Annotations](https://www.baeldung.com/kotlin/annotations)
- [Management](https://docs.micronaut.io/latest/guide/#management)
- [Test Resources](https://micronaut-projects.github.io/micronaut-test-resources/latest/guide/#modules-testcontainers)
  - [Good tutorial](https://guides.micronaut.io/latest/replace-h2-with-real-database-for-testing.html)
- [REST-assured](https://github.com/rest-assured/rest-assured/wiki/)
- [Baeldung](https://www.baeldung.com/micronaut)

## Feature micronaut-aot documentation

- [Micronaut AOT documentation](https://micronaut-projects.github.io/micronaut-aot/latest/guide/)

## Feature serialization documentation

- [Micronaut Serialization Core documentation](https://micronaut-projects.github.io/micronaut-serialization/latest/guide/)

## Feature ksp documentation

- [Micronaut Kotlin Symbol Processing (KSP) documentation](https://docs.micronaut.io/latest/guide/#kotlin)
- [https://kotlinlang.org/docs/ksp-overview.html](https://kotlinlang.org/docs/ksp-overview.html)

## Gradle

- [gradle plugin](https://micronaut-projects.github.io/micronaut-gradle-plugin/latest/index.html)

# TODO

- Setup project ✅
- DB ✅
  - JOOQ ❓
  - exposed ❓ (✅)
    - [baeldung](https://www.baeldung.com/kotlin/exposed-persistence)
  - liquibase ✅
  - Flyway ✅
- environments (profiles) ✅
- Testcontainers ✅
  - Need to optimise startup and more ✅
- How to split tests
- jar and run the application ✅
  - CI/CD

## Application

- monitoring and health check
  - [Micronaut Micrometer](https://micronaut-projects.github.io/micronaut-micrometer/latest/guide/) ✅
- Security, roles, and permissions ✅
  - [JWT](https://guides.micronaut.io/latest/micronaut-security-jwt-gradle-kotlin.html) ✅
  - [Micronaut Security](https://micronaut-projects.github.io/micronaut-security/latest/guide/index.html#custom) ✅

---

- Check [Micronaut Control Panel](https://micronaut-projects.github.io/micronaut-control-panel/latest/guide/)
- Check [TOML](https://toml.io/en/) - nope
- Check using [GraalVM](https://guides.micronaut.io/latest/creating-your-first-micronaut-app-maven-kotlin.html)
- Check reactive mode
- [Inversion of Control](https://docs.micronaut.io/latest/guide/#beans)
- [Configuration](https://docs.micronaut.io/latest/guide/#configuration)

---

- logging ✅
- Either
- Messaging
  - [Example that worked](https://github.com/ricall/micronaut-jms-application/)
- Scheduling ✅
- Cache
- Gradle plugin
