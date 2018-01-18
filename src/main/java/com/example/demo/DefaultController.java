package com.example.demo;

import com.example.demo.domain.Department;
import com.example.demo.domain.Employee;
import com.example.demo.request.JdbcRequest;
import com.example.demo.request.JpaRequest;
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

    @Autowired
    JpaRequest jpaRequest;

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

    @RequestMapping(path = "/repository", method = RequestMethod.GET)
    public @ResponseBody
    List<Department> repository() {
        return jpaRequest.getDepartments();
    }

    @RequestMapping(path = "/hql", method = RequestMethod.GET)
    public @ResponseBody
    List<Employee> hql() {
        return jpaRequest.getEmployees();
    }

    @RequestMapping(path = "/criteria", method = RequestMethod.GET)
    public @ResponseBody
    List<Department> criteria(@RequestParam(defaultValue = "0") Long id) {
        return jpaRequest.getDepartmentsByManager(id);
    }

    @RequestMapping(path = "/nsql", method = RequestMethod.GET)
    public @ResponseBody
    Employee nsql(@RequestParam(defaultValue = "0") Long id) {
        return jpaRequest.getManagerByDepartment(id);
    }
}
