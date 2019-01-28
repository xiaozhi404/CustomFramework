package cn.gzhu.test.testExample.controller;

import cn.gzhu.test.CustomSpringMVC.RequestMapping;
import cn.gzhu.test.customSpring.anno.MyAutoWired;
import cn.gzhu.test.customSpring.anno.MyController;
import cn.gzhu.test.testExample.pojo.City;
import cn.gzhu.test.testExample.service.CityServiceImpl;

import java.util.List;

@MyController("cityController")
@RequestMapping("/city")
public class CityController {

    @MyAutoWired("cityServiceImpl")
    CityServiceImpl cityService;


    @RequestMapping("/getAll")
    public List<City> getCitys() {
        List<City> citys = cityService.getCitys();
        return citys;
    }
//
//    @RequestMapping("/getOne")
//    public List<City> getCity() {
//        return cityService;
//    }
}
