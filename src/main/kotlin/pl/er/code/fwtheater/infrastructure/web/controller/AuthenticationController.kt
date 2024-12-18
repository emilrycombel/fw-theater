package pl.er.code.fwtheater.infrastructure.web.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.er.code.fwtheater.infrastructure.web.security.jwt.JwtTokenUtil


@RestController
@RequestMapping("/api/auth")
class AuthenticationController(private val jwtTokenUtil: JwtTokenUtil, private val passwordEncoder: PasswordEncoder) {

    @PostMapping("/login")
    fun login(@RequestBody loginData: Map<String?, String?>): ResponseEntity<*> {
        val username = loginData["username"]
        val password = loginData["password"]

        if ("cinemaadmin" == username && passwordEncoder.matches(password, passwordEncoder.encode("password"))) {
            val token = jwtTokenUtil.generateToken(username)
            return ResponseEntity.ok(java.util.Map.of("token", token))
        } else {
            return ResponseEntity.status(401).body("Invalid credentials")
        }
    }
}
