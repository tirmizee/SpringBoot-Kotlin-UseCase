package com.tirmizee.repositories.custom

import com.tirmizee.entities.UserEntity
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.core.update
import org.springframework.data.relational.core.query.Criteria.where
import org.springframework.data.relational.core.query.Query.query
import org.springframework.data.relational.core.query.Update
import org.springframework.stereotype.Repository

@Repository
class CustomUserRepositoryImpl(val r2dbcEntityTemplate: R2dbcEntityTemplate): CustomUserRepository {

    override suspend fun getVersion() = "1"

    override suspend fun updatePasswordByUsername(username: String, password: String): Int? =
        r2dbcEntityTemplate.runCatching {
            this.update(UserEntity::class.java)
                .matching(query(where("username").`is`(username)))
                .apply(
                    Update.update("password", password)
//                        .set("column_name","value")
//                        .set("column_name","value")
//                        .set("column_name","value")
//                        .set("column_name","value")
//                        .set("column_name","value")
//                        .set("column_name","value")
//                        .set("column_name","value")
//                        .set("column_name","value")
//                        .set("column_name","value")
                )
                .awaitSingle()
        }
        .onSuccess { updated -> updated }
        .onFailure { exception ->  println(exception) }
        .getOrNull()

}