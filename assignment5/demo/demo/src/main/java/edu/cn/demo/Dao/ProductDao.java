package edu.cn.demo.Dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import edu.cn.demo.entity.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.cn.demo.entity.ProductDTO;
import edu.cn.demo.entity.Supplier;
import org.apache.ibatis.annotations.*;
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

    @Delete("delete from product_supplier where product_id = #{id}")
    public void deleteSuppliersOfProduct(long id);


    /**
     * 多表查询
     * @param page
     * @param wrapper
     * @return
     */
    @Select("select distinct p.* from product p "+
            " left join product_supplier ps on p.id=ps.product_id " +
            " left join supplier s on s.id=ps.supplier_id " +
            " ${ew.customSqlSegment}") //由QueryWrapper转换
    @Results({@Result(property = "id", column = "id",id = true),
             // @Result(property = "name", column = "name"),
              @Result(property = "supplierList", column = "id",
                      many = @Many(select = "edu.en.demo.Dao.SupplierDao.findSuppliersByProductId"))})
    public IPage<ProductDao> findProductsHaveSupplier(IPage<ProductDTO> page,@Param(Constants.WRAPPER) QueryWrapper<ProductDTO> wrapper);


    /**
     * 查询条件只有product信息时，进行单表查询
     * @param page
     * @param wrapper
     * @return
     */
    @Select("select p.* from product p ${ew.customSqlSegment}")
//    @Results({@Result(id = true, property = "id", column = "id"),
//            @Result(property = "supplierList", column = "id",
//                    many = @Many(select = "edu.en.demo.dao.SupplierDao.findSuppliersByProductId"))})
    public IPage<ProductDTO> findProducts(IPage<ProductDTO> page,
                                          @Param(Constants.WRAPPER) QueryWrapper<ProductDTO> wrapper);





    //添加通过id查供应商方法
    @Select("SELECT supplier_id FROM product_supplier where product_id = {product_id}")
    List<Map<String, Object>> querySupplier(long product_id);


}
