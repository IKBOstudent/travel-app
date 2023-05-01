package com.travelapp.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/hotels")
@CrossOrigin(origins = "http://localhost:8081")
public class HotelController {

}
