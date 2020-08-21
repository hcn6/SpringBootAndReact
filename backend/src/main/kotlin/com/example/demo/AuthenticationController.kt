package com.example.demo

import com.example.dataMongoDB.AuthService
import com.example.dataMongoDB.Authentication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin(origins = ["http://localhost:3000", "http://localhost:4200" ])
@RestController
@RequestMapping("/hieu")
class AuthenticationController {
    @Autowired
    lateinit var authService: AuthService

    @GetMapping("/")
    fun getUsername() : ResponseEntity<String>{
        return ResponseEntity("Hello", HttpStatus.OK)

    }

    @GetMapping("/all")
    fun getAll() : List<Authentication>{
        return authService.findAllUser()
    }

    @PostMapping("/signin")
    fun getID(@RequestBody auth: Authentication) : String{
        return if(authService.authenticate(auth.username!!, auth.password!!)){
            authService.findByUsername(auth.username).id
        }
        else return "Wrong Username or Password"
    }

    @PostMapping("/signup")
    fun pass(@RequestBody auth: Authentication) : String{
        var userName = authService.existByUsername(auth.username!!)
        var passWord = auth.password!!
        return if(userName) "Username already existed"
        else {
            authService.saveOrUpdateAuthentication(Authentication(auth.username, auth.password))
            return "Registered successfully"
        }
    }

    @PostMapping("/check")
    fun checkForUser(@RequestBody auth: Authentication) : String {
        var userName = authService.existByUsername(auth.username!!)
        return if(userName) ("Username already existed")
        else {
            return "OK"
        }
    }
}