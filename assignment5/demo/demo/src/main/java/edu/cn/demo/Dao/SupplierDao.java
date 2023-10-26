package edu.cn.demo.Dao;

import edu.cn.demo.entity.Supplier;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lirongjun
 * @since 2023-10-25
 */
@Mapper
public interface SupplierDao extends BaseMapper<Supplier> {

    //根据product_id查找供应商
    @Select("select supplier.* from supplier, product_supplier " +
            "where supplier.id = product_supplier.supplier_id " +
            "and product_supplier.product_id = #{product_id}")
    public List<Supplier> findSuppliersByProductId(long product_id);
}
