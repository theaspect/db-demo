package com.example.demo;

import com.example.demo.domain.Employee;
import com.example.demo.jdbc.JdbcRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {

    @Autowired
    JdbcRequest jdbcRequest;

    @RequestMapping("/")
    public String index() {
        return "Hello Docker World";
    }

    @RequestMapping(path = "/jdbc", method = RequestMethod.GET)
    public @ResponseBody List<Employee> jdbc() {
        return jdbcRequest.getJosh();
    }
}
