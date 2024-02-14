package eyalgo.demo.adapters.data

import io.micronaut.core.annotation.NonNull
import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.Version

/**
 * Missing @NotBlank from import jakarta.validation.constraints.NotBlank;
 *
 * https://guides.micronaut.io/latest/micronaut-liquibase-maven-java.html
 */
@MappedEntity
data class Person(
    @Id
    @GeneratedValue
    val id: Long,
    @Version
    val version: Long,
    @NonNull
    val firstName: String,
    @NonNull
    val lastName: String,
)
