package edu.cn.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import edu.cn.demo.entity.Supplier;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lirongjun
 * @since 2023-10-25
 */
public interface ISupplierService extends IService<Supplier> {

    IPage<Supplier> findSuppliers(String name, Integer pageNum, Integer pageSize);
}
