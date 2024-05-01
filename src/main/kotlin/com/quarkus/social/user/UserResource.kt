import com.quarkus.social.user.domain.dto.ErrorResponse
import com.quarkus.social.user.domain.dto.model.UserEntity
import com.quarkus.social.user.repository.UserRepository
import io.quarkus.panache.common.Page
import user.dto.UserDTO
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import jakarta.ws.rs.*

import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import java.time.LocalDateTime
import javax.xml.validation.Validator

@Path("/users")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class UserResource(

    private val userRepository: UserRepository


) {

    @POST
    @Transactional
    fun createUser(@Valid user: UserDTO): Response {
        val isEmailAlreadyRegistered = userRepository.find("email", user.email!!).firstResultOptional<UserEntity>().isPresent
        if (isEmailAlreadyRegistered) {
            val error = ErrorResponse("Email already registered")
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build()
        }
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

    @PUT
    @Path("{id}")
    @Transactional
    fun updateUser(@PathParam("id") id: Long, user: UserDTO): Response {
        val userEntity = userRepository.findActiveById(id) ?: return Response.status(Response.Status.BAD_REQUEST).build()
        userEntity.name = user.name!!
        userEntity.email = user.email!!
        userEntity.password = user.password!!
        return Response.ok().build()
    }

    @DELETE
    @Path("{id}")
    @Transactional
    fun deleteUser(@PathParam("id") id: Long): Response {
        val userEntity = userRepository.findActiveById(id) ?: return Response.status(Response.Status.BAD_REQUEST).build()
        userEntity.deleted_at = LocalDateTime.now()
        userRepository.persist(userEntity)
        return Response.ok().build()
    }


}