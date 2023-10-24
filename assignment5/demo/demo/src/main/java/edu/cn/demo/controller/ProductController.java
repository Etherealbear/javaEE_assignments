package edu.cn.demo.controller;



import com.baomidou.mybatisplus.core.metadata.IPage;

import edu.cn.demo.entity.Product;
import edu.cn.demo.service.Impl.ProductServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lirongjun
 * @since 2023-10-25
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    /**
     * 前端控制
     */
    @Autowired
    ProductServiceImpl productService;


    @ApiOperation("添加一个商品")
    @PostMapping("")
    public ResponseEntity<Product> addProduct(@RequestBody Product product){
        Product result = productService.InsertProduct(product);
        return ResponseEntity.ok(result);
    }

    @ApiOperation("添加商品与供应商的关系")
    @PostMapping("/{product_id}/{supplier_id}")
    public ResponseEntity<Void> addProductSupplier (@PathVariable int product_id, @PathVariable int supplier_id) {
        productService.InsertProductandSupplier(product_id, supplier_id);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("删除商品byid")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable int id){
        productService.removeById(id);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("修改商品信息")
    @PutMapping("")
    public ResponseEntity<Void> updateProduct(@RequestBody Product product){
        productService.updateById(product);
        return ResponseEntity.ok().build();
    }

}

