package edu.cn.demo.service.Impl;

import edu.cn.demo.entity.Product;
import edu.cn.demo.Dao.ProductDao;
import edu.cn.demo.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lirongjun
 * @since 2023-10-25
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductDao, Product> implements IProductService {
    /**
     * Service的实现类
     */
    @Autowired
    ProductDao productDao;

    //添加一个商品
    public Product InsertProduct(Product product){
            productDao.insert(product);
            return productDao.selectById(product.getId());
    }

    //添加商品和供应商对应关系

    public void InsertProductandSupplier(long product_id, long supplier_id){
        productDao.InsertProductandSupplier(product_id,supplier_id);
    }

    //查询所有商品
    public List<Product> getAllProducts(){
        return  productDao.selectList(null);
    }

    //按id 查询商品
    public Product getProductByID(long id){
        return  productDao.selectById(id);
    }

    // 根据商品id查询所有供应商
    public List<Map<String, Object>> querySuppliersById (Integer id) {
        return productDao.querySupplier(id);
    }



}
