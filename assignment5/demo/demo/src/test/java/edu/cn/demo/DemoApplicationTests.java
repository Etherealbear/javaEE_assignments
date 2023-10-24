package edu.cn.demo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.cn.demo.Dao.ProductDao;
import edu.cn.demo.entity.Product;
import edu.cn.demo.entity.query.ProductQuery;
import edu.cn.demo.service.Impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Wrapper;
import java.util.List;
import java.util.Map;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductServiceImpl productService;


	@Test
	void contextLoads() {
	}


/**
 * 条件查询
 */

@Test
void testGetAll(){
	//方式1.按条件查询
//	QueryWrapper wrapper =  new QueryWrapper();
//	//给wrapper加条件
//	wrapper.lt("price",20); //查询价格小于20的商品
//	List<Product> result =  productDao.selectList(wrapper)	;
//	System.out.println(result);

	//方式2.lambda格式按条件查询:防止字段写错
//	QueryWrapper<Product> wrapper =  new QueryWrapper();
//	//给wrapper加条件
//	wrapper.lambda().lt(Product::getPrice,20); //查询价格小于20的商品
//	List<Product> result =  productDao.selectList(wrapper)	;
//	System.out.println(result);

//	//模拟页面传递过来的查询数据
//	ProductQuery pq = new ProductQuery();
//	pq.setPrice(10f);
//	pq.setPrice2(100f);
	//方式三
//	LambdaQueryWrapper<Product> lqw = new LambdaQueryWrapper<Product>();
//	//给wrapper加条件
//	//and
//	//lqw.gt(Product::getPrice,10).lt(Product::getPrice,100); //查询价格小于30的商品
//	//or
//	lqw.gt(Product::getPrice,pq.getPrice()).or().
//			lt(Product::getPrice,pq.getPrice2()); //查询价格小于30的商品
//	List<Product> result =  productDao.selectList(lqw)	;
//	System.out.println(result);

//查询投影
//	LambdaQueryWrapper<Product> lqw = new LambdaQueryWrapper<Product>();
//	lqw.select(Product::getId,Product::getPrice,Product::getName);
//	List<Product> result =  productDao.selectList(lqw)	;
//	System.out.println(result);

//	QueryWrapper<Product> qw = new QueryWrapper<Product>();
//	qw.select("count(*) as newcount"); //投影并取别名
//	qw.groupBy("category");
//	List<Map<String, Object>> result =  productDao.selectMaps(qw)	;
//	System.out.println(result);



	//等匹配
//	LambdaQueryWrapper<Product> lqw = new LambdaQueryWrapper<Product>();
//	lqw.eq(Product::getName,"黑色保温杯");
//	List<Product> result = productDao.selectList(lqw);
//	System.out.println(result);

	//范围查询
//	LambdaQueryWrapper<Product> lqw = new LambdaQueryWrapper<Product>();
//	//范围查询：lt,le,ge,gt,eq,between
//	lqw.between(Product::getPrice,20,70);
//	List<Product> result = productDao.selectList(lqw);
//	System.out.println(result);

	//模糊匹配（like）
	LambdaQueryWrapper<Product> lqw = new LambdaQueryWrapper<Product>();
	lqw.like(Product::getName,"杯");
	List<Product> result = productDao.selectList(lqw);
	System.out.println(result);
}


//删除多个







/**
 * 普通查询
 */
	//	@Test
//	void testSave(){
//		Product product = new Product();
//		product.setName("静音鼠标");
//		product.setPrice(128f);
//		product.setQuantity(256f);
//		product.setProductType("鼠标");
//		product.setCategory("电子产品");
//		product.setDescription("这个鼠标不会发出很大的声音");
//		productDao.insert(product);
//	}

	/**
	 * 测试添加商品和供应商的关系
	 */
	@Test
	void testInsertProductandSupplier(){
		productService.InsertProductandSupplier(8,1);
	}


//	@Test
//	void testDeletebyid(){
//		productDao.deleteById(2);
//	}

//	@Test
//	void testUpdatebyID(){
//		Product product = new Product();
//		product.setId(3);
//		product.setPrice(110f);
//		productDao.updateById(product);
//	}

//	@Test
//	void testGetbyid(){
//		Product product = productDao.selectById(3);
//		System.out.println(product);
//	}
//
//	/**
//	 * 按页查询
//	 */
//	@Test
//	void testGetByPage(){
//		IPage page = new Page(1,2);//1为多少页，2为多少条记录
//		productDao.selectPage(page,null);
//		System.out.println("当前页码值: "+page.getCurrent());
//		System.out.println("每页显示数："+page.getSize());
//		System.out.println("一共多少页："+page.getPages());
//		System.out.println("一共多少条："+page.getTotal());
//		System.out.println("数据："+page.getRecords());
//	}
}
