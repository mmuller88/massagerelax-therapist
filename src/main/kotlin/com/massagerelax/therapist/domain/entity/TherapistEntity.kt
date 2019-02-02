package com.massagerelax.therapist.domain.entity

import com.massagerelax.therapist.web.dto.*
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
        val name: String = "",

        @get: NotBlank
        val description: String? = "",

        @NotNull
        @Size(max = 100)
        @get: NotBlank
        val number: String = "",

        val mobile_table: Boolean = false,

        @ManyToMany
        @JoinTable(
                name = "therapist_massages",
                joinColumns = [JoinColumn(name = "therapist_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "massage_type_id", referencedColumnName = "id")])
        var massageTypes: MutableList<MassageTypeEntity> = mutableListOf(),

        // Working days bit coded
        // Mo=2^0 Tu=2^1 We=2^2 Th=2^3 Fr=2^4 Sa=2^5 Su=2^6
        @Size(min = 0, max = 127)
        var workingDays: Int = 0,

        // Working hours Monday bit coded
        // 0=2^0 1=2^1 2=2^2 3=2^3 ... 23=2^23
        @Size(min = 0, max = 16777215)
        var hoursMonday: Int = 0,

        // Working hours Tuesday bit coded
        // 0=2^0 1=2^1 2=2^2 3=2^3 ... 23=2^23
        @Size(min = 0, max = 16777215)
        var hoursTuesday: Int = 0,

        // Working hours Tuesday bit coded
        // 0=2^0 1=2^1 2=2^2 3=2^3 ... 23=2^23
        @Size(min = 0, max = 16777215)
        var hoursWednesday: Int = 0,

        // Working hours Tuesday bit coded
        // 0=2^0 1=2^1 2=2^2 3=2^3 ... 23=2^23
        @Size(min = 0, max = 16777215)
        var hoursThursday: Int = 0,

        // Working hours Tuesday bit coded
        // 0=2^0 1=2^1 2=2^2 3=2^3 ... 23=2^23
        @Size(min = 0, max = 16777215)
        var hoursFriday: Int = 0,

        // Working hours Tuesday bit coded
        // 0=2^0 1=2^1 2=2^2 3=2^3 ... 23=2^23
        @Size(min = 0, max = 16777215)
        var hoursSaturday: Int = 0,

        // Working hours Tuesday bit coded
        // 0=2^0 1=2^1 2=2^2 3=2^3 ... 23=2^23
        @Size(min = 0, max = 16777215)
        var hoursSunday: Int = 0
        )
{

        fun toDto() = TherapistDTO(
                id = this.id!!,
                name = this.name,
                description = this.description,
                number = this.number,
                mobileTable = this.mobile_table
        )

        companion object {

                fun fromDto(dto: TherapistDTO) = TherapistEntity(
                        id = dto.id,
                        name = dto.name,
                        description = dto.description,
                        number = dto.number,
                        mobile_table = dto.mobileTable)

                fun fromDto(dto: CreateTherapistDTO) = TherapistEntity(
                        name = dto.name,
                        description = dto.description,
                        number = dto.number,
                        mobile_table = dto.mobileTable)

                fun fromDto(dto: UpdateTherapistDTO) = TherapistEntity(
                        name = dto.name,
                        description = dto.description,
                        number = dto.number,
                        mobile_table = dto.mobileTable)
        }
}


