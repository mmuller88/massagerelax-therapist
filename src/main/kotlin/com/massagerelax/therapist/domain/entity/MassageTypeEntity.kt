package com.massagerelax.therapist.domain.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.massagerelax.therapist.web.dto.*
import org.hibernate.annotations.NaturalId
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "massage_type")
data class MassageTypeEntity (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

        @NotNull
        @get: NotBlank
        @Size(max = 100)
        @NaturalId
        val name: String = "",

        @get: NotBlank
        val description: String? = "",

        @ManyToMany(
                mappedBy = "massageTypes")
        @JsonIgnoreProperties("massageTypes")
        var therapists: List<TherapistEntity> = mutableListOf()
        ) {

        fun toDto(): MassageTypeDTO = MassageTypeDTO(
                id = this.id!!,
                name = this.name,
                description = this.description
        )

        companion object {

                fun fromDto(dto: MassageTypeDTO) = MassageTypeEntity(
                        id = dto.id,
                        name = dto.name,
                        description = dto.description)

                fun fromDto(dto: CreateMassageTypeDTO) = MassageTypeEntity(
                        name = dto.name,
                        description = dto.description)

                fun fromDto(dto: UpdateMassageTypeDTO) = MassageTypeEntity(
                        name = dto.name,
                        description = dto.description)
        }
}


