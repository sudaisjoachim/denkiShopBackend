package com.denkishop.user;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class HomeController {
	
	  @GetMapping("/")
	    public String home() {
		  return "<H1 style=\"color:red;text-align: center\" >Welcome to DenkiShop <br/> Under Testing Environment<H1/>";
	    }

}
