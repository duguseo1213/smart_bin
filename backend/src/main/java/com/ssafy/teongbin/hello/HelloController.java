package com.ssafy.teongbin.hello;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/")
    public String getHello(){
        return "hello";
    }

    @GetMapping("/hello/{word}")
    @ResponseBody
    public String getVarHello(@PathVariable("word") String word){
        return word;
    }

}
