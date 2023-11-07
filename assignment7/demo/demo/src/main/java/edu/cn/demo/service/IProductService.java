package edu.cn.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import edu.cn.demo.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.cn.demo.entity.ProductDTO;
import edu.cn.demo.entity.Supplier;
import edu.cn.demo.exception.ProductAdminException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lirongjun
 * @since 2023-10-25
 */
public interface IProductService extends IService<Product> {

    IPage<ProductDTO> findProduct(Map<String, Object> condition, Integer pageNum, Integer pageSize);

    @Transactional
    Product addProduct(Product product) throws ProductAdminException;

    @Transactional(rollbackFor = ProductAdminException.class)
    void updateProduct(long id, Product product) throws ProductAdminException;

    @Transactional(rollbackFor = ProductAdminException.class)
    void updateProductSuppliers(long id, List<Supplier> suppliers) throws ProductAdminException;

    @Transactional(rollbackFor = ProductAdminException.class)
    void deleteProduct(long id) throws ProductAdminException;
}
