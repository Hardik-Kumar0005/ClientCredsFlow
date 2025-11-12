package com.hardik.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResController {

    @GetMapping("/resource")
    public String resource() {
        return "Hello Resource!";
    }
}
