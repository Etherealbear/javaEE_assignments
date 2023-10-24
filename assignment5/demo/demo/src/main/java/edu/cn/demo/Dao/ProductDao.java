package edu.cn.demo.Dao;

import edu.cn.demo.entity.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.cn.demo.entity.Supplier;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lirongjun
 * @since 2023-10-25
 */
@Mapper
public interface ProductDao extends BaseMapper<Product> {

    //添加多表关系表插入方法
    @Insert("Insert into product_supplier(product_id, supplier_id) values (#{product_id},#{supplier_id})")
     void InsertProductandSupplier(long product_id, long supplier_id);



    //添加通过id查供应商方法
    @Select("SELECT supplier_id FROM product_supplier where product_id = {product_id}")
    List<Map<String, Object>> querySupplier(long product_id);
}
