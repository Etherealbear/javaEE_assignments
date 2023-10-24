# OMR
ORM是对象关系映射的缩写，它可以让你用面向对象的方式来操作数据库。ORM可以把数据库中的表和字段映射成程序中的类和属性，这样你就可以像操作对象一样从数据库中获取或保存数据，而不用写复杂的SQL语句。ORM可以提高开发效率，简化代码，降低维护难度，但也会牺牲一些性能和灵活性。

# Mybatis
Mybatis 是一种流行的OMR框架。
Programers wirte SQL statements, the params and result can be mapped with entity objects. 

整合Mybatis-plus首先要在pom文件中加依赖：
![MybatisDependency](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/MybatisDependency.png)

定义好实体类后，在Dao层对应该类dao文件下写操作数据库的方法。要加（mapper）注解

![Mapper](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/Mapper.png)

yml配置文件中写连接的数据库：
![datasource](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/datasource.png)

版本过低报时区错误：挂上时区
![时区错误](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/时区错误.png)


# 使用MP：标准数据层开发
## 标准数据层CRUD功能

![CRUD](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/crud.png)

分页查询：selectPage
```

	/**
	 * 按页查询
	 */
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

```
但是直接执行不行，需要添加mp的拦截器
-->配置分页拦截器
```
@Configuration //配置成配置类
public class MpConfig {
    @Bean
    public MybatisPlusInterceptor mpInterceptor(){
        //1.定义mp的拦截器
        MybatisPlusInterceptor mpInterceptor = new MybatisPlusInterceptor();
        //2.添加具体的拦截器
        mpInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return mpInterceptor;
    }
}

```


## DQL编程控制（DQL：数据库查询语言）

### 条件查询方式

mybatisplus将书写复杂的SQL查询条件进行了封装，使用编程的形式完成条件查询的组合

```
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

	//方式三
	LambdaQueryWrapper<Product> lqw = new LambdaQueryWrapper<Product>();
	//给wrapper加条件
	//and
	//lqw.gt(Product::getPrice,10).lt(Product::getPrice,100); //查询价格小于30的商品
	//or
	lqw.gt(Product::getPrice,10).or().lt(Product::getPrice,100); //查询价格小于30的商品
	List<Product> result =  productDao.selectList(lqw)	;
	System.out.println(result);

}
```

条件查询--null值处理

lt（condition，，）在condition加null != xxx


### 查询投影

```
	QueryWrapper<Product> qw = new QueryWrapper<Product>();
	qw.select("count(*) as newcount"); //投影并取别名
	qw.groupBy("category");
	List<Map<String, Object>> result =  productDao.selectMaps(qw)	;
	System.out.println(result);

```

### DQL范围控制

**等匹配**
```
//	LambdaQueryWrapper<Product> lqw = new LambdaQueryWrapper<Product>();
//	lqw.eq(Product::getName,"黑色保温杯");
//	List<Product> result = productDao.selectList(lqw);
//	System.out.println(result);
```
**范围查询**
```
//范围查询
	LambdaQueryWrapper<Product> lqw = new LambdaQueryWrapper<Product>();
	//范围查询：lt,le,ge,gt,eq,between
	lqw.between(Product::getPrice,20,70);
	List<Product> result = productDao.selectList(lqw);
	System.out.println(result);
```
**模糊匹配**
```
	//模糊匹配（like）
	LambdaQueryWrapper<Product> lqw = new LambdaQueryWrapper<Product>();
	lqw.like(Product::getName,"杯");
	List<Product> result = productDao.selectList(lqw);
	System.out.println(result);

```

likeleft:左边是百分号还是右边是百分号


### 字段映射与表名映射
**case1：**
数据库表字段与编码属性不同，怎么办？
加上注解如：
```
@TableField(value = "pwd") //pwd是表对应字段的名称
private String password;
```

**case2:**
编码中添加了数据库中没有定义的属性.
某些数据（比如用户是否在线）不需要保存到数据库，怎么办？
添加注解：
```
@TableField(exist = false) 
private Integer online;

```

**case3**
采用默认查询开放了更多字段的查看权限
如密码，不希望默认能查出结果。
设定属性为不参与查询
```
@TableField(value = "pwd"，select = false) //pwd是表对应字段的名称
private String password;
```

***case4*
表名和编码对象名开发设计不同步
添加注解@TableName

```
@TableName("tb1_user)
```

## DML编程操作（DML：数据库操作语言，如增删改查）
### Insert

**id生成策略**
不同的表应该用不同的id生成策略
> 日志：自增id
> 购物订单：特殊规则
> 外卖单: 关联地区日期等信息
> 关系表：可省略id等等

在ID属性上添加注解改变id生成策略
```
    @TableId(type = IdType.AUTO)
```
简化：可以在配置类配置全局idtype



### Delect

**多记录操作，删多条**
API用哪个？

![多数据操作](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/多数据操作.png)

### 逻辑删除

问题：删除操作业务问题：业务数据从数据库中丢弃
>假如某个员工离职了，如果直接将与其相关的各项数据都删除，其营业额也被删除了，年末总结时，公司业务数据就不匹配。

**逻辑删除**：为数据设置是否可用状态字段，删除时设置字段为不可用状态，数据保留在数据库中

添加删除属性，添加注解
```
@TableLogic(value = "0",delval = "1")//value表示没删时的值，delval表示删了的值,要和数据库设计默认值的一致
private Integer deleted;
```

### 乐观锁

解决小型并发问题

>如抢购秒杀

步骤：
1. 要在数据库中添加一个字段表示当前正在操作该字段的用户是谁
>如 version字段  int  默认值1
2.实体类也要加该字段，并加Version注解
```
@Version
private Integer version
```
3.需要加拦截器

```
mpInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor();
)
```

### 代码生成器
采用代码或者Maven插件可快速生成mapper、model、service、controller层代码，支持模板引擎，代码生成器类只需修改表名，并输入项目包结构即可


![代码生成器1](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/代码生成器1.png)

数据库相关配置：读取数据库获取信息
开发者自定义配置：手工配置（如online等）
先导入坐标
1.创建代码生成器对象
2.执行代码生成器
```
//核心代码
        AutoGenerator autoGenerator = new AutoGenerator();

        //中间一堆配置
        autoGenerator.execute();
```

