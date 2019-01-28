package cn.gzhu.test.testExample.service;

import cn.gzhu.test.customSpring.anno.MyAutoWired;
import cn.gzhu.test.customSpring.anno.MyService;
import cn.gzhu.test.testExample.mapper.CityMapper;
import cn.gzhu.test.testExample.pojo.City;

import java.util.List;

@MyService("cityServiceImpl")
public class CityServiceImpl {

    @MyAutoWired("cityMapper")
    private CityMapper cityMapper;

    public List<City> getCitys() {
        return cityMapper.getCitys();
    }
}
