package edu.cn.demo.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.QueryResult;
import edu.cn.demo.entity.Product;
import edu.cn.demo.Dao.ProductDao;
import edu.cn.demo.entity.ProductDTO;
import edu.cn.demo.entity.Supplier;
import edu.cn.demo.exception.ProductAdminException;
import edu.cn.demo.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    //添加一个商品
    public Product InsertProduct(Product product) throws ProductAdminException {
        try {
            this.baseMapper.insert(product);
            return productDao.selectById(product.getId());
        }
        catch (Exception e){
            throw new ProductAdminException("添加商品失败"+e.getMessage());
        }

    }

    /**
     *
     * @param id
     */
    @Transactional(rollbackFor = ProductAdminException.class)
    public void delectProduct(long id) throws ProductAdminException{
        try {
            //先删product_supplier
            productDao.deleteSuppliersOfProduct(id);
            productDao.deleteById(id);
        }
        catch (Exception e){
            throw new ProductAdminException("删除商品失败"+e.getMessage());
        }
    }


    /**
     * 更新商品，商品有supplier信息
     */
    @Transactional(rollbackFor = ProductAdminException.class)
    public void UpdateProduct(long id,Product product) throws ProductAdminException{
        //先检查商品存不存在
        if(productDao.selectById(id) == null){
           throw new ProductAdminException("商品不存在");
        }
        try {
            productDao.updateById(product);
        }catch (Exception e){
            throw new ProductAdminException("修改产品失败：" + e.getMessage());
        }
    }
    //更新商品和供应商关系
    @Transactional(rollbackFor = ProductAdminException.class)
    public void UpdateProductSupplier(long id, List<Supplier> suppliers) throws ProductAdminException{
        //先判断商品存不存在
        if(productDao.selectById(id) == null){
            throw new ProductAdminException("商品不存在");
        }
        try {
            productDao.deleteSuppliersOfProduct(id);
            for(Supplier supplier :suppliers){
                productDao.InsertProductandSupplier(id,supplier.getId());
            }
        }catch (Exception e){
            throw new ProductAdminException("商品供应商关系表添加失败 "+e.getMessage());
        }

    }

    /**
     * 查询所有商品
     */
 public IPage<ProductDTO> findProducts(Map<String, Object> condition, Integer pageNum, Integer pageSize) throws  ProductAdminException{
     //先new一个页面用于调用
     IPage<ProductDTO> page = new Page<>(pageNum,pageSize);
     //new一个查询语句
     QueryWrapper<ProductDTO> qw = new QueryWrapper<>();
     //添加查询条件
     qw.like(condition.containsKey("name"), "p.name", condition.get("name"));
     qw.le(condition.containsKey("price"), "p.price", condition.get("price"));
     qw.ge(condition.containsKey("Quantity"), "p.Quantity", condition.get("Quantity"));
     qw.eq(condition.containsKey("category"), "p.category", condition.get("category"));
     if(condition.containsKey("supplierName")){
     //先实现多表查询试试
     qw.eq(condition.containsKey("supplierName"),"s.name", condition.get("supplierName"));
         try {
             productDao.findProductsHaveSupplier(page,qw);
         }
         catch (Exception e){
             throw new ProductAdminException("找不到该类商品");
         }
     }
     else {
         try {
             productDao.findProducts(page,qw);
         }catch (Exception e){
             throw new ProductAdminException("找不到该类商品");
         }
     }

     return page;
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
