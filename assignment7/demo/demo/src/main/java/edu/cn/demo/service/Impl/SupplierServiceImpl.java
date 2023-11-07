package edu.cn.demo.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    @Override
    public IPage<Supplier> findSuppliers(String name, Integer pageNum, Integer pageSize) {
        Page<Supplier> page=new Page<>(pageNum,pageSize);
        QueryWrapper<Supplier> qw = new QueryWrapper<>();
        qw.like(name!=null,"name",name);
        this.baseMapper.selectPage(page,qw);
        return page;
    }
}
