package eyalgo.demo.adapters.rest

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class GuestDTO(val firstName: String, val lastName: String)
