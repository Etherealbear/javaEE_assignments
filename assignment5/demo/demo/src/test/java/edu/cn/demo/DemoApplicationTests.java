package edu.cn.demo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.cn.demo.Dao.ProductDao;
import edu.cn.demo.Dao.SupplierDao;
import edu.cn.demo.entity.Product;
import edu.cn.demo.entity.ProductDTO;
import edu.cn.demo.entity.Supplier;
import edu.cn.demo.entity.query.ProductQuery;
import edu.cn.demo.exception.ProductAdminException;
import edu.cn.demo.service.Impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductServiceImpl productService;
	@Autowired
	private SupplierDao supplierRes;

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
		@Test
	void testSave() throws ProductAdminException {
		Product product = new Product();
		product.setName("花生");
		product.setPrice(15f);
		product.setQuantity(66f);
		product.setProduct_Type("坚果");
		product.setCategory("食品");
		product.setDescription("很好吃的脆脆的果子");
		productService.InsertProduct(product);
	}



	/**
	 * 测试删除服务
	 */
	@Test
	void testDeleteProduct() throws ProductAdminException{
		productService.delectProduct(1);
	}


	//测试更新商品
	@Test
	void testUpdateProduct() throws ProductAdminException{
				Product product = new Product();
		product.setId(3L);
		product.setPrice(130f);
		product.setQuantity(100f);
		productService.UpdateProduct(3L,product);
	}



	//测试更新商品供应商关系表
	@Test
	void testUpdateProductSupplier() throws ProductAdminException{
		List<Supplier> suppliers = new ArrayList<>();
		Supplier supplier = new Supplier();
		supplier.setId(8L);
		supplier.setSupplier_name("A公司");
		supplierRes.insert(supplier);
		suppliers.add(supplier);

		Product product = new Product();
		product.setName("橙汁");
		product.setPrice(6f);
		product.setQuantity(155f);
		product.setProduct_Type("果汁");
		product.setCategory("食品");
		product.setDescription("新鲜的榨出的果汁");
		productService.InsertProduct(product);

		productService.UpdateProductSupplier(product.getId(),suppliers);

	}


	/**
	 * 测试通过商品id查找供应商
	 */

	@Test
	void testFindSuppliersByProduct(){
		List<Supplier> result = supplierRes.findSuppliersByProductId(2);
		System.out.println(result);
	}


	/**
	 * 测试多表查询
	 */

	@Test
	void testFindProducts() throws ProductAdminException {
		//查询条件为空
		Map<String,Object> condition=new HashMap<>();

	//	assertEquals(11,result.getRecords().size());

		//使用跨表属性查询(supplierName在supplier表中)
		condition.put("name","鼠标");
		IPage<ProductDTO> result = productService.findProducts(condition, 0, 11);
		System.out.println(result);

//		condition.put("supplierName","A公司");
//		 result = productService.findProducts(condition, 0, 11);
//		System.out.println(result);
	//	assertEquals(1,result.getTotal());

	}

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
	@Test
	void testGetByPage(){
		IPage page = new Page(1,2);//1为多少页，2为多少条记录
		productDao.selectPage(page,null);
		System.out.println("当前页码值: "+page.getCurrent());
		System.out.println("每页显示数："+page.getSize());
		System.out.println("一共多少页："+page.getPages());
		System.out.println("一共多少条："+page.getTotal());
		System.out.println("数据："+page.getRecords());
	}
}
