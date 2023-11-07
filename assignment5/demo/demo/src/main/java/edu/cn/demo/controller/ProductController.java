package edu.cn.demo.controller;



import com.baomidou.mybatisplus.core.metadata.IPage;

import com.baomidou.mybatisplus.extension.api.R;
import edu.cn.demo.entity.Product;
import edu.cn.demo.exception.ProductAdminException;
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


    @GetMapping("")

    public ResponseEntity<List<Product>> queryProducts(){
        List<Product> result = productService.getAllProducts();
        return ResponseEntity.ok(result);
    }

    @ApiOperation("添加一个商品")
    @PostMapping("")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) throws ProductAdminException {
        Product result = productService.InsertProduct(product);
        return ResponseEntity.ok(result);
    }



    @ApiOperation("删除商品byid")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable long id) throws ProductAdminException {
        productService.delectProduct(id);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("修改商品信息")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable long id,@RequestBody Product product) throws ProductAdminException {
        productService.UpdateProduct(id,product);
        return ResponseEntity.ok().build();
    }

}

