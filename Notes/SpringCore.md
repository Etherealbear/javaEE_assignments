# Spring框架学习笔记
## Spring Framework系统架构
* Data Access/Integration: 数据访问/集成(包容其他技术)
* web开发
* AOP： 面向切面编程（依赖核心容器）--编程思想：可以在不改动原始功能的基础上添加功能
* Aspects: AOP思想的实现
* Core Container: 核心容器 
* Test:单元测试与继承测试


### Spring Ecosphere
- Spring Framework: and it is the basis for all other technologies.
- SpringBoot: SpringBoot is to simplify Spring application developement.
- SpringCloud: microservice framework for distributed application.


### Spring IOC

使用原因：
代码书写现状；
> 耦合度偏高
解决方案：
> 使用对象时，在**程序中不要主动使用new产生对象，转换成外部提供对象**
# 什么是Spring IoC容器
spring技术对IOC思想进行了实现
> Spring 提供了一个容器，称为IOC容器，用来充当Ioc思想中的外部
> Spring IoC负责创建对象、管理对象（通过依赖注入（DI）、装配对象、配置对象，并且管理这些对象的整个生命周期。**被创建或者被管理的对象在IOC容器中统称为Bean**
## IOC（Inversion of Control）控制反转
>使用对象时，由主动new产生对象转换成为**外部**提供对象，此过程中对象控制权由程序转移到外部，此思想称为控制反转。
## DI（依赖注入）
>在容器中建立bean与bean之间的依赖关系的整个过程称为依赖注入

目标：充分解耦
* 使用IoC容器管理bean（IoC）
* 在IoC容器内将有依赖关系的bean进行关系绑定（DI）

**开闭原则**：写的代码可以扩展功能，但不能修改原来的代码
>假设我们在数据层实现了两个类，业务层创建了其中的类对象，假如该业务层已经打包，而数据层想要修改代码，业务层就没法修改。
>因此，需要进行修改。

解决实例如下:
- 1.双耦合编程（自己要写代码解决）
- 2.使用IOC（只用写配置文件，IOC会帮你初始化和创建对象）
通过配置文件进行依赖设置--不需要直接依赖。


**IoC入门案例思路分析**
1. 管理什么？（要造对象的那些类，放进来变成bean）
2. 如何将被管理的对象告知IOC容器？（配置文件）
3. 被管理的对象交给IOC容器，如何获取到IOC容器？（接口）
4. IOC容器得到后，如何从容器中获取bean?（接口方法）
5. 使用Spring导入哪些坐标？（pom.xml文件）

### 使用IOC：
![IocContainer](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/iocContatiner.png)


**1.要先导入spring 包：在pom.xml文件中导入**
```
<dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.2.10.RELEASE</version>
        </dependency>
</dependencies>

```
添加代码后，在右侧maven刷新一下

**2.在rescources下新建xml文件**
在该文件中配置bean

*配置bean格式*
```
<bean id = "随便取个名" class = "类所在的地址"/>
```

**3.获取IOC容器**
新建一个类文件，创建IOC容器
```
        //创建IoC容器
        
        //1.加载类路径下的配置文件
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        //2.从文件系统下加载配置文件
        ApplicationContext ctx = new FileSystemXmlApplicationContext("D:\\workspace\\spring_10_container\\src\\main\\resources\\applicationContext.xml");
```

获取bean
```
 //获取bean（根据bean配置id获取，要的哪个类就写对应的bean的id）
// 方法一：用id
BookDao bookDao = (BookDao) ctx.getBean("bookDao");

// 方法二：by Name(Generic method)
// 告诉类型，则不需要强制转换
BookDao bookDao = ctx.getBean("bookDao",BookDao.class);

//3. getBean by Type
BookDao bookDao = ctx.getBean(BookDao.class);
```
### DI入门案例

>1.基于IOC管理bean

>2. Service中使用new形式创建的Dao对象是否保留？（否）

>3. Service中需要的Dao对象如何进到Service中？(提供方法)

>4. Service和Dao间的关系怎么描述？（配置文件）

在接口类中 new对象操作取消，提供依赖对象的set方法
```
public class BookServiceImpl implements BookService {
    //5.删除业务层中使用new的方式创建的dao对象
    private BookDao bookDao; //即这里不new对象了

    @Override
    public void save() {
        System.out.println("book service save ...");
        bookDao.save();
    }
    //6.提供对应的set方法
    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }
}
```
Service和Dao间的关系，写进配置文件中。
```
    <bean id="bookDao" class="com.itheima.dao.impl.BookDaoImpl"/>

    <bean id="bookService" class="com.itheima.service.impl.BookServiceImpl">
        <!--7.配置server与dao的关系-->
        <!--property标签表示配置当前bean的属性
        name属性表示配置哪一个具体的属性,在这里指的就是要配置上面BookService类中的bookDao属性；
        ref属性表示参照哪一个bean，在这里指的是bookDao类，也就是上面bean的id为bookDao的那个类
        -->
        <property name="bookDao" ref="bookDao"/>
    </bean>
```

### bean配置
- **bean基础配置**

![beanConfig](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/beanConfig.png)

- **bean别名配置**
![beanConfig](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/beanOtherName.png)

```
<!--name:为bean指定别名，别名可以有多个，使用逗号，分号，空格进行分隔-->
    <bean id="bookService" name="service service4 bookEbi" class="com.itheima.service.impl.BookServiceImpl">
        <property name="bookDao" ref="bookDao"/>
    </bean>
```
- **bean作用范围配置**
bean是一个对象还是多个对象（单例还是多例）
默认创建的是单例对象，要多例的话需要配置scope
```
 <!--scope：为bean设置作用范围，可选值为单例singloton，非单例prototype-->
    <bean id="bookDao" name="dao" class="com.itheima.dao.impl.BookDaoImpl" scope="prototype"/>

```
适合交给容器进行管理的bean：
> - 表现层对象
> - 业务层对象
> - 数据层对象
> - 工具对象
不适合交给容器管理的bean：
> - 封装实体的域对象（已经记录了一些成员变量）

### bean实例化
**bean本质是对象，创建bean使用构造方法完成**

spring创造bean调用的是无参数的构造方法，且是通过反射进行调用。

- **法一：构造方法**

1.提供可访问的构造方法
```
public class BookDaoImpl implements BookDao {
    public BookDaoImpl(){
        System.out.println("book constructor is running...");
    }//该构造方法写和不写都可以
    @Override
    public void save() {
        System.out.println("book dao save ...");
    }
}
```
2.配置
```
<!--方式一：构造方法实例化bean-->
    <bean id="bookDao" class="com.itheima.dao.impl.BookDaoImpl"/>
```
*注意：如果无参数构造方法不存在，将抛出异常BeanCreationException*

- **法二：静态工厂**

1.写一个工厂类，有静态方法：
```
public class OrderDaoFactory {
    public OrderDaoFactory() {
    }

    public static OrderDao getOrderDao() { //静态方法
        System.out.println("factory setup....");
        return new OrderDaoImpl();
    }
}
```
2.配置文件格式：
<!--方式二：使用静态工厂实例化bean-->
    <bean id="orderDao" class="com.itheima.factory.OrderDaoFactory" factory-method="getOrderDao"/> //要写出工厂方法名称

- **法三：实例工厂初始化bean**

工厂类中的方法不是静态的，需要先创建工厂对象，才能用工厂对象调用方法。
1.实例工厂：
```
public class UserDaoFactory {
    public UserDaoFactory() {
    }

    public UserDao getUserDao() { //非静态方法创建对象
        return new UserDaoImpl();
    }
}

```
2.配置
```
<!--方式三：使用实例工厂实例化bean-->
    <bean id="userFactory" class="com.itheima.factory.UserDaoFactory"/>
    <bean id="userDao" factory-method="getUserDao" factory-bean="userFactory"/> 这里的factory-bean要写用的是哪个工厂的实例，在本例中就是上一行的userFactory，且不需要写class了
```

- **法四:FactoryBean**
用实例工厂创建对象的方法每次都要先创建一个工厂的bean，而且方法名不固定每次都需要配置，很麻烦，于是有下面改进：
在工厂包里创建一个新的类，xxxFactoryBean，继承FactoryBean接口，该接口是一个泛型接口，<>里要用该类造什么类的对象就写什么类
```
public class UserDaoFactoryBean implements FactoryBean<UserDao> {
    public UserDaoFactoryBean() {
    }
//代替原始实例工厂中创建对象的方法
//固定的创建对象的方法名getObject
    public UserDao getObject() throws Exception {
        return new UserDaoImpl();
    }
//返回的是字节码文件，得到bean的类型
    public Class<?> getObjectType() {
        return UserDao.class;
    }
//该方法表示造的对象是单例还是非单例
    public boolean isSingleton(){
        return true; //true表示单例，false表示非单例
    }
}
```
配置：
```
<!--方式四：使用FactoryBean实例化bean-->
    <bean id="userDao2" class="com.itheima.factory.UserDaoFactoryBean"/>

```
### bean生命周期

- 生命周期：从创建到消亡的完整过程
- bean生命周期： bean从创建到销毁的整体过程
> - 初始化容器
>  创建对象（内存分配）
>  执行构造方法
>  执行属性注入（set操作）
>  执行bean初始化方法
> - 使用bean
>  执行业务操作
> - 关闭/销毁容器
>  执行bean销毁方法 

- bean生命周期控制：在bean创建后到销毁前做一些事情

1.提供bean生命周期控制方法：在类中定义init函数和destory函数：
```
public class BookDaoImpl implements BookDao {

    @Override
    public void save() {
        System.out.println("book dao save ...");
    }
    //表示bean初始化对应的操作
    public void init(){
        System.out.println("init...");
    }
    //表示bean销毁前对应的操作
    public void destory(){
        System.out.println("destory...");
    }

}
```
2.配置：
```
<!--init-method：设置bean初始化生命周期回调函数-->
    <!--destroy-method：设置bean销毁生命周期回调函数，仅适用于单例对象-->
    <bean id="bookDao" class="com.itheima.dao.impl.BookDaoImpl" init-method="init" destroy-method="destory"/>

```
输出后发现并没有进行销毁，因为虚拟机在销毁bean之前就关闭了，这时候有两种方法：
```
public class AppForLifeCycle {
    public static void main( String[] args ) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        //这里不能用ApplicationContext类，要用ClassPathXmlApplicationContext 类，因为该类中定义了close方法而前面哪个类没有实现
        BookDao bookDao = (BookDao) ctx.getBean("bookDao");
        bookDao.save();
        /*-------------- 关闭bean的两个方法 -----------------------*/
        //法1
        //注册关闭钩子函数，在虚拟机退出之前回调此函数，关闭容器
        //ctx.registerShutdownHook(); //注册关闭钩子

        //法2
        //关闭容器
        ctx.close();//该方法比较暴力

        //真正写程序不用这两个方法，到web阶段来解答
    }
}
```

- 用Spring提供的接口，在配置中就不必写init-method和destory-method了：
- 用Spring提供的抽象接口:**InitializingBean**和**DisposableBean**，并实现他们的抽象方法。
```

public class BookServiceImpl implements BookService, InitializingBean, DisposableBean {
    private BookDao bookDao;

    public void setBookDao(BookDao bookDao) {
        System.out.println("set .....");
        this.bookDao = bookDao;
    }
    @Override
    public void save() {
        System.out.println("book service save ...");
        bookDao.save();
    }
    @Override
    public void destroy() throws Exception {
        System.out.println("service destroy");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("service init");
    }
}
```
配置文件：

```
 <bean id="bookService" class="com.itheima.service.impl.BookServiceImpl">
        <property name="bookDao" ref="bookDao"/>
    </bean>
```

## 依赖注入方式
向一个类中传递数据的方式有几种？
- 普通方法（set方法）
- 构造方法

依赖注入描述了在容器中建立bean与bean之间关系依赖的过程，如果bean运行需要的是字符串或者数字而不是引用类型呢？

注入数据类型分类：
- 引用类型
- 简单类型（基本数据类型和字符串类型）

**依赖注入的两种方式**
- setter注入
> * 简单类型
> * 引用类型

- 构造器注入
> * 简单类型
> * 引用类型

**setter注入--引用类型**

* 在bean中定义引用类型属性并提供可访问的set方法
```
public class BookServiceImpl implements BookService {
    private BookDao bookDao; 
    //提供对应的set方法
    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }
}
```
* 配置中使用*property*标签和*ref*属性注入引用类型对象
```
 <bean id="bookDao" class="com.itheima.dao.impl.BookDaoImpl"/>

    <bean id="bookService" class="com.itheima.service.impl.BookServiceImpl">
        <property name="bookDao" ref="bookDao"/>
    </bean>
```

**setter注入--简单类型**

- 在bean中定义简单数据类型的属性并提供set方法：
```
public class BookDaoImpl implements BookDao {

    private String databaseName;
    private int connectionNum;
    //setter注入需要提供要注入对象的set方法
    public void setConnectionNum(int connectionNum) {
        this.connectionNum = connectionNum;
    }
    //setter注入需要提供要注入对象的set方法
    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    @Override
    public void save() {
        System.out.println("book dao save ..."+databaseName+","+connectionNum);
    }
}
```
- 配置中使用*property*和*value*标签注入简单类型数据
value会自动转换数据类型
```
 <!--注入简单类型-->
    <bean id="bookDao" class="com.itheima.dao.impl.BookDaoImpl">
        <!--property标签：设置注入属性-->
        <!--name属性：设置注入的属性名，实际是set方法对应的名称-->
        <!--value属性：设置注入简单类型数据值-->
        <property name="connectionNum" value="100"/>
        <property name="databaseName" value="mysql"/>
    </bean>
```

**构造器注入--引用类型**
不用setter方法，用构造方法：
```
public class BookServiceImpl implements BookService{
    private BookDao bookDao;
    private UserDao userDao;

    public BookServiceImpl(BookDao bookDao, UserDao userDao) {
        this.bookDao = bookDao;
        this.userDao = userDao;
    }

    @Override
    public void save() {
        System.out.println("book service save ...");
        bookDao.save();
        userDao.save();
    }
}
```
配置文件
用**constructor-arg**标签，后面接name和ref,name写的是构造函数的形参名
```
 <bean id="bookService" class="com.itheima.service.impl.BookServiceImpl">
        <constructor-arg name="userDao" ref="userDao"/>
        <constructor-arg name="bookDao" ref="bookDao"/>
    </bean>
```

**构造器注入--简单类型**

```
public class BookDaoImpl implements BookDao {
    private String databaseName;
    private int connectionNum;

    public BookDaoImpl(String databaseName, int connectionNum) {
        this.databaseName = databaseName;
        this.connectionNum = connectionNum;
    }

    @Override
    public void save() {
        System.out.println("book dao save ..."+databaseName+","+connectionNum);
    }
}
```
配置：

```
   <bean id="bookDao" class="com.itheima.dao.impl.BookDaoImpl">
        <!--根据构造方法参数名称注入-->
        <constructor-arg name="connectionNum" value="10"/>
        <constructor-arg name="databaseName" value="mysql"/>
    </bean>
```

但是因为name写的是形参名，耦合度很高

```

    解决形参名称的问题，与形参名不耦合
    <bean id="bookDao" class="com.itheima.dao.impl.BookDaoImpl">
        根据构造方法参数类型注入
        <constructor-arg type="int" value="10"/>
        <constructor-arg type="java.lang.String" value="mysql"/>
    </bean>
    <bean id="userDao" class="com.itheima.dao.impl.UserDaoImpl"/>

    <bean id="bookService" class="com.itheima.service.impl.BookServiceImpl">
        <constructor-arg name="userDao" ref="userDao"/>
        <constructor-arg name="bookDao" ref="bookDao"/>
    </bean>

```
但是如果有多个同类型的形参的话，这样就无法解决了。
一种解决办法是用index来标注参数位置。但是我感觉有点暴力...
```
<bean id="bookDao" class="com.itheima.dao.impl.BookDaoImpl">
        <!--根据构造方法参数位置注入-->
        <constructor-arg index="0" value="mysql"/>
        <constructor-arg index="1" value="100"/>
    </bean>
    <bean id="userDao" class="com.itheima.dao.impl.UserDaoImpl"/>

```

**依赖注入方式选择：**
- 强制依赖 用构造器进行，因为使用setter注入有可能会不进行注入导致null对象的出现
> 强制依赖：这个bean运行有必须要依赖的东西
- 可选依赖使用setter注入进行，灵活性强（setter可以执行可以不执行）
- Spring框架倡导使用构造器，第三方框架内部大多采用构造器注入的形式进行数据初始化，相对严谨
- 如果有必要可以两者同时使用
- 实际开发过程中还要根据实际情况分析，如果受控对象没有提供setter方法就必须使用构造器注入
- 自己开发的模块推荐使用setter注入

### 依赖自动装配（autoware）
IOC容器根据bean所依赖的资源在容器中自动查找并注入到bean中的过程为自动装配
**自动装配方式**

- 按类型 (常用)
- 按名称
- 按构造方法
- 不启动自动装配

**自动装配用于引用类型注入，不能对简单类型进行操作**
**使用按类型装配时（byType）必须保障容器中相同类型的bean唯一**
**使用按名称装配是必须保证容器中有指定名称的bean，因变量名与配置耦合，不推荐使用**
**自动装配优先级低于前两个注入方式，同时出现时自动装配失效**

在配置文件中使用**bean**标签和***autoware**属性设置自动装配的类型
```
<bean id="bookDao" class="com.itheima.dao.impl.BookDaoImpl"/>
<!--autowire属性：开启自动装配，通常使用按类型装配-->
    <bean id="bookService" class="com.itheima.service.impl.BookServiceImpl" autowire="byType"/>

```
假如有两个同样类型的bean，需要用byname装配：
要保证在有一个bean中的id和在类中定义的属性名一样
```
    <bean id="bookDao" class="com.itheima.dao.impl.BookDaoImpl"/>
    <bean id="bookDao2" class="com.itheima.dao.impl.BookDaoImpl2"/> //该名称在类中没有定义，所以找不到
    <!--autowire属性：开启自动装配，通常使用按类型装配-->
    <bean id="bookService" class="com.itheima.service.impl.BookServiceImpl" autowire="byName"/>

```

### 集合注入

```
public class BookDaoImpl implements BookDao {
    private int[] array;
    private List<String> list;
    private Set<String> set;
    private Map<String, String> map;
    private Properties properties;

    public BookDaoImpl() {
    }

    public void setArray(int[] array) {
        this.array = array;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public void setSet(Set<String> set) {
        this.set = set;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public void save() {
        System.out.println("book dao save ...");
        System.out.println("遍历数组:" + Arrays.toString(this.array));
        System.out.println("遍历List" + this.list);
        System.out.println("遍历Set" + this.set);
        System.out.println("遍历Map" + this.map);
        System.out.println("遍历Properties" + this.properties);
    }
}

```
配置文件写法：
**name写的是变量名**
```
<bean id="bookDao" class="com.itheima.dao.impl.BookDaoImpl">
        <!--数组注入-->
        <property name="array">
            <array>
                <value>100</value>
                <value>200</value>
            </array>
        </property>
        <!--list集合注入-->
        <property name="list">
            <list>
                <value>itcast</value>
                <value>boxuegu</value>
            </list>
        </property>
        <!--set集合注入-->
        <property name="set">
            <set>
                <value>itcast</value>
                <value>itheima</value>
            </set>
        </property>
        <!--map集合注入-->
        <property name="map">
            <map>
                <entry key="country" value="china"/>
                <entry key="province" value="henan"/>
                <entry key="city" value="kaifeng"/>
            </map>
        </property>
        <!--Properties注入-->
        <property name="properties">
            <props>
                <prop key="country">china</prop>
                <prop key="province">henan</prop>
                <prop key="city">kaifeng</prop>
            </props>
        </property>
    </bean>


```

## 注解开发

### 注解开发定义bean
- 用Component注解代替配置文件中的bean，（）中指定名称
```
@Component("bookDao")
public class BookDaoImpl implements BookDao{

}
@Component
public class BookServiceImpl{

}

```
如果不写名称的话，就不能用名称访问bean，需要用类型，如：

```BookService bookService = ctx.getBean(BookService.class);
```

- 然后在配置文件中用context-scan 标签扫描加载bean;base-package写该组件所在的类的位置
```<context：component-scan base-package = "xxx.xx.xxx"/>```
![Component](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/component.png)

**Spring提供@Component注解的三个衍生注解**
- @Controller :用于表现层bean定义
- @Service    :用于业务层bean定义
- @Repository :用于数据层bean定义


### 纯注解开发（不写配置文件了，用java类替代核心配置文件）
定义一个新类叫SpringConfig来代替配置文件。

该类需要加一个注解 **@Configuration**
该注解只代表配置文件的基本内容，之前写的context标签还没有加上，没有扫描组件，把扫描组件换成了
**@ComponentScan("包名")**

![SpringConfig](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/SpringConfig.png)

不需要加载配置文件了，而要加载配置类：

![SpringConfig](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/AnnotationConfigApplicationContext.png)

*注意： @Configuration注解用于设定当前类为配置类*
*@ComponentScan("包名")注解用于设定扫描路径，此注解只能添加一次，多个数据要用数组格式*

### 注解开发bean管理
![SpringConfig](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/beanControl.png)


### 注解开发的依赖注入---自动装配

![SpringConfig](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/AnnoAutoware.png)

* **注入引用类型**
使用 **@Autowared** 注解开启自动装配模式（按类型）：引用类型

**注意：**自动装配基于反射设计创建对象并暴力反射对应属性为私有属性初始化数据，因此不需要提供setter方法
**注意：**自动装配建议使用无参数构造方法创建对象（默认），如果不提供对应构造方法，请提供唯一的构造方法。

使用 **@Qualifier** 注解开启指定名称装配
> **该注解无法单独使用，必须配合@Autowared使用**
```
@Service
public class BookServiceImpl implements BookService{
    @Autowared
    @Qualifier("bookDao")
    private BookDao bookDao;
}
```

* **注入简单类型**
用 **@value（值）** 注解
```
@Repository("bookDao")
    public class BookDaoImpl implements BookDao{
        @value("ins666) //注解传值
        private String name; 
        public void save(){
            System.out.println("book dao save ...."+ name);
        }
    }

```

### 注解开发的properties文件注入

使用 **@PropertySource** 注解加载properties文件

![SpringConfig](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/propertiesInjection.png)

**注意：路径仅支持单一文件配置，多文件使用数组格式配置，不允许使用通配符‘*’**

### 第三方bean管理
**@Bean**
pom.xml文件导入druid坐标
SpringConfig类中不要 **@ComponentScan("包名")** 注解了
使用手工编程
在该类中：
1.先提供一个方法获取对应的bean
2.将该方法的返回值定义成一个bean

![beanMethod](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/beanMethod.png)

使用：

![beanMethodTest](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/beanMethodTest.png)

**导配置类：**

一般，我们可以将这些对应的配置写在单独的配置文件中，如新的一个jdbcConfig.java文件中。
然后需要如下操作：

* 方法一：
给jdbcConfig类添加 **@Configuration** 注解
给SpringConfig类添加 **@ComponentScan("包名")** 注解，包名写上面那个类

* 方法二：（推荐）
在SpringConfig类上import，如果有多个就写数组

![SpringImport](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/SpringImport.png)

### 注解开发为第三方类注入资源

* 1.简单类型注入（成员变量）

![SimpleInjection](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/SimpleInjection.png)


* 2.引用类型注入（方法形参）
要扫描包
![Dao](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/Dao.png)

自动装配（按类型）

![RefInjection](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/RefInjection.png)


### XML和注解配置对比

![Compare](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/Compare.png)
