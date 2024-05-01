package com.quarkus.social.user.domain.dto.model

import io.quarkus.hibernate.orm.panache.PanacheEntity
import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@Entity(name = "users")
class UserEntity() : PanacheEntityBase(){

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false, unique = true, updatable = false)
    var uuid: UUID? = null

    @Column(nullable = false)
    lateinit var created_at: LocalDateTime

    @Column(nullable = false)
    lateinit var updated_at: LocalDateTime

    var deleted_at: LocalDateTime? = null

    @Column(nullable = false, length = 100)
    lateinit var name: String

    @Column(nullable = false, unique = true, length = 100)
    lateinit var email: String

    @Column(nullable = false)
    lateinit var password: String

    constructor(name: String, email: String, password: String) : this() {
        this.name = name
        this.email = email
        this.password = password
    }

    @PrePersist
    private fun onPrePersist() {
        val now = LocalDateTime.now()
        created_at = now
        updated_at = now

        if (uuid == null) {
            uuid = UUID.randomUUID()
        }
    }

    @PreUpdate
    private fun onPreUpdate() {
        updated_at = LocalDateTime.now()
    }



}
