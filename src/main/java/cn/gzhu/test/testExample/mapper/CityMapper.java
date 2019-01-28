package cn.gzhu.test.testExample.mapper;

import cn.gzhu.test.customSpring.anno.MyMapper;
import cn.gzhu.test.testExample.pojo.City;

import java.util.List;

@MyMapper("cityMapper")
public interface CityMapper {

    List<City> getCitys();

    City selectById(Integer id);
}
