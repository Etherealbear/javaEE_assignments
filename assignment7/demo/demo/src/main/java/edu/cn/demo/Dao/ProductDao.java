package edu.cn.demo.Dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    @Insert("insert into product_supplier (product_id,supplier_id) values(#{productId},#{supplierId})")
    public void insertProductSupplier(long productId, long supplierId);

    @Delete("delete from product_supplier where product_id = #{productId}")
    public void deleteSuppliersOfProduct(long productId);

    /**
     * 查询条件包括supplier信息时，需要多表联合查询
     *
     * @param page
     * @param wrapper
     * @return
     */
    @Select("select distinct p.* from product p" +
            " left join product_supplier ps on p.id=ps.product_id " +
            " left join supplier s on s.id=ps.supplier_id " +
            " ${ew.customSqlSegment}") //由QueryWrapper转换
    @Results({@Result(id = true, property = "id", column = "id"),
            @Result(property = "supplierList", column = "id",
                    many = @Many(select = "edu.en.demo.Dao.SupplierDao.findSuppliersByProduct"))})
    public IPage<ProductDTO> findProductsBySupplier(IPage<ProductDTO> page,
                                                    @Param(Constants.WRAPPER) QueryWrapper<ProductDTO> wrapper);


    /**
     * 查询条件只有product信息时，进行单表查询
     *
     * @param page
     * @param wrapper
     * @return
     */
    @Select("select p.* from product p ${ew.customSqlSegment}")
    @Results({@Result(id = true, property = "id", column = "id"),
            @Result(property = "supplierList", column = "id",
                    many = @Many(select = "edu.en.demo.Dao.SupplierDao.findSuppliersByProduct"))})
    public IPage<ProductDTO> findProducts(IPage<ProductDTO> page,
                                          @Param(Constants.WRAPPER) QueryWrapper<ProductDTO> wrapper);
}
