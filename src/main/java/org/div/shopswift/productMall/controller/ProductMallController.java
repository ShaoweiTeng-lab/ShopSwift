package org.div.shopswift.productMall.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductMallController {
    @RequestMapping("/welcome")
    public String welcome(){
        return  "Hello World";
    }
}
