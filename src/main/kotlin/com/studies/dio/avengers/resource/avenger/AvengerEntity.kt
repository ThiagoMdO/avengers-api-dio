package com.studies.dio.avengers.resource.avenger

import com.studies.dio.avengers.domain.avenger.Avenger
import jakarta.persistence.*
import org.springframework.data.jpa.domain.AbstractPersistable_.id

@Entity
data class AvengerEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Column(nullable = false)
    val nick: String,

    @Column(nullable = false)
    val person: String,

    val description: String?,
    val history: String?
) {
    fun toAvenger() = Avenger(
        id,
        nick,
        person,
        description,
        history
    )

    companion object {
        fun from(avenger: Avenger) =
            AvengerEntity(
                id = avenger.id,
                nick = avenger.nick,
                person = avenger.person,
                description = avenger.description,
                history = avenger.history
            )
    }
}
