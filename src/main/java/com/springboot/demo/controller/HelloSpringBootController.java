package com.springboot.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by yg.jang on 2017. 8. 14..
 */
@Controller
public class HelloSpringBootController {

    @RequestMapping(value="/hello")
    public String helloSpringBoot() {
        return "hello";
    }
}