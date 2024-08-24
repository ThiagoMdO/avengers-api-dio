package com.studies.dio.avengers.application.web.resource.request

import com.studies.dio.avengers.domain.avenger.Avenger
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import org.jetbrains.annotations.NotNull

data class AvengerRequest(
    @NotNull
    @NotBlank
    @NotEmpty
    val nick: String,

    @NotNull
    @NotBlank
    @NotEmpty
    val person: String,
    val description: String? = null,
    val history: String? = null
) {

    fun toAvenger() = Avenger(
        nick = nick,
        person = person,
        description = description,
        history = history
    )

    companion object {
        fun to(id: Long?, request: AvengerRequest) = Avenger(
            id = id,
            nick = request.nick,
            person = request.person,
            description = request.description,
            history = request.history
        )

    }
}
