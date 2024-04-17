package com.quarkus.social.domain.model

import io.quarkus.hibernate.orm.panache.PanacheEntity
import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import jakarta.persistence.*
import java.time.LocalDate

@Entity(name = "users")
class UserEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null


    var name: String? = null
    var email: String? = null
    var password: String? = null

    constructor(name: String, email: String, password: String) : this() {
        this.name = name
        this.email = email
        this.password = password
    }


}