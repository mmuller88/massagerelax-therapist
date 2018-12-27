package com.massagerelax.therapist.domain.entity

import com.massagerelax.therapist.web.dto.CreateTherapistDTO
import com.massagerelax.therapist.web.dto.TherapistDTO
import com.massagerelax.therapist.web.dto.UpdateTherapistDTO
import org.hibernate.annotations.NaturalId
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "therapist")
data class TherapistEntity (
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

        @NotNull
        @Email
        @Size(max = 100)
        @Column(unique = true)
        @get: NotBlank
        val number: String = "",

        val mobile_table: Boolean = false,

        @ManyToMany
        @JoinTable(
                name = "therapist_massages",
                joinColumns = [JoinColumn(name = "therapist_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "massage_type_id", referencedColumnName = "id")])
        var massageTypes: List<MassageTypeEntity> = mutableListOf())
{

        fun toDto(): TherapistDTO = TherapistDTO(
                id = this.id!!,
                name = this.name,
                description = this.description,
                number = this.number,
                mobile_table = this.mobile_table
        )

        companion object {

                fun fromDto(dto: TherapistDTO) = TherapistEntity(
                        id = dto.id,
                        name = dto.name,
                        description = dto.description,
                        number = dto.number,
                        mobile_table = dto.mobile_table)

                fun fromDto(dto: CreateTherapistDTO) = TherapistEntity(
                        name = dto.name,
                        description = dto.description,
                        number = dto.number,
                        mobile_table = dto.mobile_table)

                fun fromDto(dto: UpdateTherapistDTO) = TherapistEntity(
                        name = dto.name,
                        description = dto.description,
                        number = dto.number,
                        mobile_table = dto.mobile_table)
        }
}


