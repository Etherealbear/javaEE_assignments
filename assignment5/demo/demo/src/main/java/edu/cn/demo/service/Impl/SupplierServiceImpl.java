package edu.cn.demo.service.Impl;

import edu.cn.demo.entity.Supplier;
import edu.cn.demo.Dao.SupplierDao;
import edu.cn.demo.service.ISupplierService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lirongjun
 * @since 2023-10-25
 */
@Service
public class SupplierServiceImpl extends ServiceImpl<SupplierDao, Supplier> implements ISupplierService {

}
