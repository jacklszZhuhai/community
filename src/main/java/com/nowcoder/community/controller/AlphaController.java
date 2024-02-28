package com.nowcoder.community.controller;

import com.nowcoder.community.service.AlphaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller // 类上有controller、service、repository、component，才会让bean被扫描
            //  分别对应： 处理请求组件、业务组件、数据库访问组件、通用组件
@RequestMapping("/alpha") // 给类取这个名字

public class AlphaController {

    @Autowired
    private AlphaService alphaService;

    @RequestMapping("/hello") // 给方法取名
    @ResponseBody   // 将返回字符串转换为页面
    public String sayHello(){
        return "Hello Spring Boot.";    // 返回普通字符串
    }
    @RequestMapping("/data") // 给方法取名
    @ResponseBody   // 将返回字符串转换为页面
    public String getData(){
        return alphaService.find();
    }

    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response){
        // 获得请求数据
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath());
        Enumeration<String> enumeration = request.getHeaderNames();
        while(enumeration.hasMoreElements()){
            String name = enumeration.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + ":" + value);
        }
        System.out.println(request.getParameter("code"));

        // 返回响应数据
        response.setContentType("text/html;charset=utf-8");
        try(
                PrintWriter writer = response.getWriter();
        ) {
            writer.write("<h1>牛客网</h1>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // GET请求 默认是GET

    /// students?current=1&limit=20
    @RequestMapping(path = "/students", method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(
            @RequestParam(name = "current", required = false, defaultValue = "1") int current,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit){
        System.out.println(current);
        System.out.println(limit);
        return "some students";
    }
    // /student/123
    @RequestMapping(path="/student/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id") int id) {
        System.out.println(id);
        return "a student";
    }

    @RequestMapping(path = "/teacher", method = RequestMethod.GET)
    public ModelAndView getTeacher(){   // 注意返回值是MAV类型，这个对象返回了model和view两份数据，由dispatch servlet调用这个方法，
        //将结果发送给模板引擎，模板引擎生成动态html
        ModelAndView mav = new ModelAndView();
        mav.addObject("name", "张三");
        mav.addObject("age", 30);
        mav.setViewName("/demo/view");
        return mav;
    }

    @RequestMapping(path = "/school", method = RequestMethod.GET)
    public String getSchool(Model model){   // dispatch servlet在调用方法时，发现没有mode对象，会自动为这个方法实例化一个model对象
        model.addAttribute("name", "北京大学");
        model.addAttribute("age", "80");
        return "/demo/view";    // 将视图的路径直接返回给dispatch servlet。
    }

    // 响应JSON数据（异步请求：局部验证是否成功）
    // Java对象 -> JSON字符串（跨语言） -> JS对象
    @RequestMapping(path = "/emp", method = RequestMethod.GET)
    @ResponseBody       // 不加ResonseBody会默认认为返回的是html
    // 自动将map类型转换成字符串！！
    public List<Map<String, Object>> getEmp() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "zhangsan");
        emp.put("age", 23);
        emp.put("salary", 8000.00);
        list.add(emp);
        emp = new HashMap<>();
        emp.put("name", "aaa");
        emp.put("age", 23);
        emp.put("salary", 9000.00);
        list.add(emp);
        emp = new HashMap<>();
        emp.put("name", "bbb");
        emp.put("age", 23);
        emp.put("salary", 10000.00);
        list.add(emp);
        return list;
    }


}
