package cn.gzhu.test.testExample.controller;

import cn.gzhu.test.CustomSpringMVC.RequestMapping;
import cn.gzhu.test.customSpring.anno.MyAutoWired;
import cn.gzhu.test.customSpring.anno.MyController;
import cn.gzhu.test.testExample.service.HelloServiceImpl;

@MyController("helloController")
@RequestMapping("/hello")
public class HelloContorller {

    @MyAutoWired("helloServiceImpl")
    HelloServiceImpl helloService;


    @RequestMapping("/world")
    public void getHello() {
        helloService.getHello();
    }
}
