package cn.gzhu.test.testExample.mapper;

import cn.gzhu.test.customSpring.anno.MyRepository;

@MyRepository("helloDaoImpl")
public class HelloDaoImpl {

    public void getHello() {
        System.out.println("*****  insert hello  ******");
    }
}
