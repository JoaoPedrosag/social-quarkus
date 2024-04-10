import user.dto.UserDTO
import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.Consumes

import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces

import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/users")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class UserResource {

    @POST
    fun createUser(user: UserDTO): Response {
        print("User created: $user")
        return Response.ok().build()
    }

}