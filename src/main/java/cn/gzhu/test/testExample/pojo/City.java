package cn.gzhu.test.testExample.pojo;

import lombok.Data;

import java.util.Date;

/**
 * 市实体类
 */
@Data
public class City {

    private Integer id;

    private Integer provinceId;

    private String name;

    private Date createdAt;

    private Date updatedAt;

}
