package com.example.demo;

import com.example.demo.domain.Employee;
import com.example.demo.jdbc.JdbcRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;

@Controller
public class DefaultController {

    @Autowired
    JdbcRequest jdbcRequest;

    @RequestMapping("/page")
    public String page() {
        return "page";
    }

    @RequestMapping("/params")
    public ModelAndView params(@RequestParam(defaultValue = "0") Long id) {
        return new ModelAndView("params",
                new HashMap<String, String>() {{
                    put("id", id.toString());
                }});
    }

    @RequestMapping(path = "/jdbc", method = RequestMethod.GET)
    public @ResponseBody List<Employee> jdbc() {
        return jdbcRequest.getJosh();
    }
}
