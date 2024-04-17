import com.quarkus.social.domain.model.UserEntity
import com.quarkus.social.repository.UserRepository
import io.quarkus.hibernate.orm.panache.PanacheQuery
import io.quarkus.panache.common.Page
import user.dto.UserDTO
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import jakarta.ws.rs.*

import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/users")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class UserResource(

    private val userRepository: UserRepository

) {



    @POST
    @Transactional
    fun createUser(user: UserDTO): Response {
        userRepository.persist(UserEntity(user.name!!, user.email!!, user.password!!))
        return Response.status(Response.Status.CREATED).build()
    }



    @GET
    fun getUsers(@QueryParam("page") @DefaultValue("1") page: Int,
                 @QueryParam("size") @DefaultValue("10") size: Int): Response {
        if (page < 1 || size < 1) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("Page and size parameters must be greater than 0").build()
        }

        val pageObj = Page.of(page - 1, size)
        val panacheQuery = userRepository.findPaged(pageObj)
        val list = panacheQuery.list<UserEntity>()
        val totalUsers = panacheQuery.count()
        val totalPages = panacheQuery.pageCount()

        val result = mapOf(
            "users" to list,
            "currentPage" to page,
            "pageSize" to size,
            "totalUsers" to totalUsers,
            "totalPages" to totalPages,
        )

        return Response.ok(result).build()
    }
}