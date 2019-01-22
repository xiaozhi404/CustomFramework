package cn.gzhu.test.testExample.service;

import cn.gzhu.test.customSpring.anno.MyAutoWired;
import cn.gzhu.test.customSpring.anno.MyService;
import cn.gzhu.test.testExample.mapper.HelloDaoImpl;

@MyService("helloServiceImpl")
public class HelloServiceImpl {

    @MyAutoWired("helloDaoImpl")
    private HelloDaoImpl helloDao;

    public void getHello() {
        helloDao.getHello();
    }
}
