package com.quarkus.social.user.repository

import com.quarkus.social.user.domain.dto.model.UserEntity
import io.quarkus.hibernate.orm.panache.PanacheQuery
import io.quarkus.hibernate.orm.panache.PanacheRepository
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase
import io.quarkus.panache.common.Page
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserRepository: PanacheRepositoryBase<UserEntity, Long> {

    fun findPaged(page: Page): PanacheQuery<UserEntity> {
        return findAll().page(page)
    }

    fun findActiveById(id: Long): UserEntity? {
        return find("id = ?1 and deleted_at is null", id).firstResult()
    }

}