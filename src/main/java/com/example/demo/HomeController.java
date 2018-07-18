package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    MessageRepository messageRepository;

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("messages", messageRepository.findAll());
        return "index";
    }
    @RequestMapping("/login")
    public String login(){
        return "login";
    }
    @RequestMapping("/secure")
    public String secure(){
        return "secure";
    }

    @GetMapping("/add")
    public String messageForm(Model model){
        model.addAttribute("message", new Message());
        return "secure";
    }

    @PostMapping("/process")
    public String processForm(@Valid Message message, BindingResult result){
        if(result.hasErrors()){
            return "secure";
        }

        messageRepository.save(message);
        return "redirect:/";
    }




    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "registration";

    }

        @RequestMapping(value = "/register", method = RequestMethod.POST)
        public String processRegistrationPage (
                @Valid @ModelAttribute("user") User user,
                BindingResult result,
                Model model){

            model.addAttribute("user", user);
            if (result.hasErrors()){
                return "registration";
            }else {
                userService.saveUser(user);
                model.addAttribute("message",
                        "User Account Successfully Created");
            }
            return "index";

        }
    @RequestMapping("/detail/{id}")
    public String showMsg(@PathVariable("id") long id, Model model) {
        model.addAttribute("message", messageRepository.findById(id).get());
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateMsg(@PathVariable("id") long id, Model model){
        model.addAttribute("message", messageRepository.findById(id).get());
        return "secure";
    }





}
