package user.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size


data class UserDTO(
    @field:NotBlank(message = "Name is required")
    var name: String? = null,

    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Email is not valid")
    var email: String? = null,

    @field:NotBlank(message = "Password is required")
    @field:Size(min = 6, message = "Password must be at least 6 characters")
    var password: String? = null
)