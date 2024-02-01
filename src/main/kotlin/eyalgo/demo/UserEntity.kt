package eyalgo.demo

import io.micronaut.core.annotation.NonNull
import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.Version

@MappedEntity(schema = "users")
data class UserEntity(
    @Id @GeneratedValue val id: Long,
    @Version val version: Long,
    @NonNull val firstName: String,
    @NonNull val lastName: String,
    @NonNull val birthYear: Int
)
