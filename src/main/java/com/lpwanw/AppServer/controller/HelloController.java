package com.lpwanw.AppServer.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {
	@RequestMapping(value = {"/hello"})
	public String sayHello(Model model) {
		String name = "Tây";
		model.addAttribute("msg",name);
		return "hello";
	}
	//IoC
}
