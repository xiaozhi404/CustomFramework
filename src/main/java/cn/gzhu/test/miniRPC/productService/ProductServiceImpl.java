package cn.gzhu.test.miniRPC.productService;

import cn.gzhu.test.miniRPC.productService.api.Product;
import cn.gzhu.test.miniRPC.productService.api.ProductService;

public class ProductServiceImpl implements ProductService {
    @Override
    public Product getById(Long id) {
        Product product = new Product();
        product.setId(id);
        product.setName("商品");
        product.setPrice(13.5);
        return product;
    }
}
