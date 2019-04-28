package cn.gzhu.test.miniRPC.productService.api;

import lombok.Data;

import java.io.Serializable;

/**
 * 描述：商品
 */
@Data
public class Product implements Serializable {
    private Long id;
    private String name;
    private Double price;
}
