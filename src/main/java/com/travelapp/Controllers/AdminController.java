package com.travelapp.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
//@CrossOrigin(origins = "http://localhost:8081")
public class AdminController {

    @PostMapping("api/admin/login")
    public ResponseEntity<String> login() {
        System.out.println("sign in");
        return ResponseEntity.ok().body("successfully signed in");
    }

}
