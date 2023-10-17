# SpringBoot
## Spring整合MyBatis

**MyBatis原始开发**

1.核心配置

![核心配置](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/核心配置.png)

2.JDBC连接数据

![JDBC连接数据](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/JDBC连接数据.png)

3.数据层操作

![数据层操作接口](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/数据层操作接口.png)

4.数据封装

![MyBatisAccount](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/MyBatisAccount.png)

5.APP测试
连接sql

![MyBatisApp](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/MyBatisApp.png)

**Spring整合MyBatis**

![程序核心对象分析](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/程序核心对象分析.png)

核心对象是 SqlSessionFactory

**目标：**
* 掌握基于SpringMVC获取请求参数与响应json数据操作
* 熟练应用基于REST风格的请求路径设置与参数传递
* 能够根据实际业务建立前后端开发通信协议并进行实现
* 基于SSM整合技术开发任意项目模块功能

## SpringMVC
* SpringMVC技术与servlet技术功能相同，均属于web层开发技术，但SpringMVC代码量较少。
* 是一种基于Java实现的MVC模型的轻量级web框架
---用于表现层开发，替代Servlet技术


### Create a SpringBoot Project
在网站 https://start.spring.io/ 下载生成代码。（生成后的pom.xml文件和下面入门案例有所区别）

### 入门案例学习

* 1.使用SpringMVC技术需要先导入SpringMVC坐标与Servlet坐标

![MVCpom](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/MVCpom.png)

* 2.创建SpringMVC控制器类
开发表现层的bean要用 **@Controller** 注解

**@ResquestMapping** ： 类型：方法注解，位置在SpringMVC控制器方法定义上方，作用是设置当前控制器方法请求访问路径 属性（默认）:请求访问路径

```
@Controller
public class UserController{
    @ResquestMapping("/save") //设置当前操作的访问路径(调用名称)
    //设置当前操作的返回值类型
    @ResponseBody
    public String save(){ //定义任意一个方法，返回值返回想要的类型，现在想要返回json文件所以定义为String
        System.out.printIn("user save...");
        return"{'info':'springmvc'}";
    }
}
```

* 3.初始化SpringMVC环境（同Spring环境），设定SpringMVC加载对应的bean。
![mvcconfig](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/mvcconfig.png)

* 4.初始化Servlet容器，加载SpringMVC环境，并设置SpringMVC技术处理的请求。
```
public class ServletContainersInitConfig extends AbstractDispatcherServletInitializer {
    //加载Spring配置
protected webApplicationContext createServletApplicationContext() {
AnnotationConfigwebApplicationContext ctx = new AnnotationConfigNebApplicationContext();ctx.register( SpringMvcConfig.class);
return ctx;}
//Tomcat接受的请求哪些归Spring处理？配置拦截所有请求：
protected string[ ] getServletMappings() {
return new String[ ]{"/"};
}
//加载Spring容器配置
protected webApplicationContext createRootApplicationcontext() {
return null;}
}

```
**总结**

![total](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/total.png)

![AbstractDispatcher](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/AbstractDispatcher.png)

![annaly](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/annaly.png)


### bean加载控制
**Controller加载控制与业务bean加载控制**

* SpringMVC只加载它对应的bean（表现层bean）通常是在controller包中的bean
* Spring 要加载其控制的bean
    * 业务层bean(Service)
    * 功能bean（DataSource等）

因为功能不同，如何避免Spring错误地加载到SpringMVC的bean？
> 加载spring控制的bean的时候排除掉SpringMVC控制的bean

![downloadcontroller](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/downloadcontroller.png)


### postman 
网页调试与发送网页http请求的chrome插件，常用于接口测试

## 请求与访问

> 请求映射路径
> 请求参数
> 日期类型参数传递
> 响应json数据

**请求映射路径**
@RequestMapping("/方法名") 该路径不能取名一样导致区分不了

**Get和Post的普通参数传递**
请求方式：
* Get请求
普通参数如何传递：

![GetTransfer](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/GetTransfer.png)


* Post请求

![Post](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/Post.png)

![PostTranfer](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/PostTranfer.png)

### 5种类型参数传递

![commonpra](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/commonpra.png)
![commonparadif](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/commonparadif.png)
![requestparam](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/requestparam.png)
![POJO](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/POJO.png)

### json格式传参
* json数组
* json对象（POJO）
* json数组（POJO）

1.先导入json坐标
![json1](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/json1.png)

2.设置postman发送json数据

![json2](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/json2.png)

3.
![json3](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/json3.png)

4.设置接受json数据（@RequestBody）

![json4](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/json4.png)
![requestbody](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/requestbody.png)


![rediffer](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/rediffer.png)


### 响应


## REST风格（Representational State Transfer）：表现形式状态转换
--访问网络资源的格式

> 传统风格：http://localhost/user/getByID?id=1   /  http://localhost/user/saveUser
> Rest风格：http://localhost/user/1     /  http://localhost/user

rest风格隐藏了访问行为，无法通过地址得知对资源进行何种操作；且书写简化。
根据rest风格对资源进行访问称为restful
![rest](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/rest.png)


![restful1](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/restful1.png)
有参数：路径要加参数占位，形参要加@PathVariable
![restful2](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/restful2.png)

**@RequestMapping**
要用method区分是get还是post等提交
![requestmapping](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/requestmapping.png)

**pathvariable**

![requestmapping](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/pathvariable.png)

![1](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/1.png)


### RESTful快速开发
简化重复写的东西

```
@RestController
@RequestMapping("todos")
public class TodoController {
    @Autowired
    TodoService todoService;

    //get API  :localhost:8080/todos/1
    @GetMapping("/{id}")
    public ResponseEntity<TodoItem> getTodo(@PathVariable long id){
        TodoItem result = todoService.getTodo(id);
        if (result == null){
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.ok(result);
        }
    }
    //get API2
    @GetMapping("")
    public  ResponseEntity<List<TodoItem>> findTodos(String name, Boolean complete){
        List<TodoItem> result = todoService.findTodos(name,complete);
        return ResponseEntity.ok(result);
    }

    //post API
    @PostMapping("")
    public ResponseEntity<TodoItem> addTodo(@RequestBody TodoItem todo){
        TodoItem result = todoService.addTodo(todo);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTodo(@PathVariable long id, @RequestBody TodoItem todo){
        todoService.updateTodo(id,todo);
        return ResponseEntity.ok().build();
    }

    public  ResponseEntity<Void> deleteTodo(@PathVariable long id){
        todoService.deleteTodo(id);
        return ResponseEntity.ok().build();
    }
}
```

![resquestcontroller](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/resquestcontroller.png)

![restful4](https://github.com/Etherealbear/javaEE_assignments/blob/main/pics/restful4.png)


### 测试文件的编写

先空着--