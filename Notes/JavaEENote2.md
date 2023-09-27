#JavaEE第二次课程学习笔记
##Stream and IO

### FileReader and FileWriter
FileReader类从InputStreamReader类继承而来。该类按字符读取流中数据。
可以通过以下几种构造方法创建需要的对象。
给定从中读取数据的 File 的情况下创建一个新 FileReader。
>FileReader(File file)
在给定从中读取数据的 FileDescriptor 的情况下创建一个新 FileReader。
>FileReader(FileDescriptor fd) 
在给定从中读取数据的文件名的情况下创建一个新 FileReader。
>FileReader(String fileName) 

FileWriter 类从 OutputStreamWriter 类继承而来。该类按字符向流中写入数据。
可以通过以下几种构造方法创建需要的对象。
在给出 File 对象的情况下构造一个 FileWriter 对象。
>FileWriter(File file)
在给出 File 对象的情况下构造一个 FileWriter 对象。
>FileWriter(File file, boolean append)
测试代码
```
package edu.whu;

import org.omg.CORBA.portable.OutputStream;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileWAndR {
    public static void main(String[] args) throws IOException {
        File file = new File("FirstTest.txt");
        try(FileWriter writer = new FileWriter(file)){
            writer.write("This\n is\n an\n example\n");
            writer.flush();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        try(FileReader fr = new FileReader(file)){
            char[] a = new char[50];
            fr.read(a);
            for (char c :a){
                System.out.print(c);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
```
输出
<img src="..\other\photos\20230926121525.png style=" alt="Image text" style="zoom:50%;"/>

### FileInputStream and FileOutputStream

测试代码：
```
package edu.whu;

import java.io.*;

public class FileINAndOut {
    public static void main(String[] a)  {

        //以流的形式写入文件
        try(OutputStream os = new FileOutputStream("test.txt")){
            byte[] bWrite = { 96, 97, 93, 99,95};
            for (int x = 0 ; x< bWrite.length;x++){
                os.write(bWrite[x]);
            }
            os.flush(); //文件是异步写入的，flush将buffer中的数据全部写入文件中
        }
        catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 读取文件
        try (InputStream is = new FileInputStream("test.txt")){
            int size = is.available();  // available()：在读写操作前得知数据流里有多少个字节可读取
            for (int i = 0; i < size; i++) {
                System.out.print((char)is.read() + "  ");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

输出结果：


### Load files in Classpath
**Rescource files**
- Files(such as  properties files , images or txt files) in classpath
- The file can be placed in either an open folder or a jar file

在做Java项目开发过程中，涉及到一些数据库服务连接配置、缓存服务器连接配置等，通常情况下我们会将这些不太变动的配置信息存储在以 .properties 结尾的配置文件中。当对应的服务器地址或者账号密码信息有所变动时，我们只需要修改一下配置文件中的信息即可。同时为了让Java程序可以读取 .properties配置文件中的值，Java的JDK中提供了java.util.Properties类可以实现读取配置文件。

java读取properties文件
1. 从当前的类加载器的getResourcesAsStream来获取
在文件中的配置内容如下：
```
name = kevin
text = Hi
```
测试代码：
```
package edu.whu;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
    public static void main(String [] a)  {
        Properties pros = new Properties();
        try(InputStream input = PropertiesLoader.class.getResourceAsStream("/myapp.properties")){
            if(input == null){return;}
            pros.load(input);
            System.out.println(pros.getProperty("name"));
            System.out.println(pros.getProperty("text"));
        }
        catch (IOException e){
            System.out.println("Load proerties error!");
        }
    }
}
```

### 读File

```
public static void main(String[] a){
    String dirname = ".";
    File f1 = new File(dirname);
    if(!f1.exists()){return;}
    ListFiles(f1);
}


// 递归函数
public static void listFiles(File file) {
    if (!file.isDirectory()) {  // 是文件，不是目录
        System.out.println(file.getPath() + "is a file");
        return;
    }
    System.out.println(file.getPath() + "is a directory");  // 是目录
    for (File sub: file.listFiles()) {  // 遍历该目录下所有File
        listFiles(sub);
    }
}
```

### Generics(泛型)
泛型的本质是参数化类型，也就是说所操作的数据类型被指定为一个参数。
假定我们有这样一个需求：写一个排序方法，能够对整型数组、字符串数组甚至其他任何类型的数组进行排序，该如何实现？
>答案是可以使用 Java 泛型。
>使用 Java 泛型的概念，我们可以写一个泛型方法来对一个对象数组排序。然后，调用该泛型方法来对整型数组、浮点数数组、字符串数组等进行排序

```
import java.util.ArrayList;
import java.util.Collection;

public class test {
    public static void main(String[] args) throws InterruptedException{   
    	Collection<Integer> nums = new ArrayList<Integer>();
        nums.add(6); //添加的数字其实不是int类型，而是对象类型
        nums.add(5);
        nums.add(7);
        for(Object n : nums){  //所以这里不能直接用int类型的变量接受
              int i = (Integer) n;
            System.out.println(i);
        }
  //Collection API适用于对象
    }
}

```
在用Collection API的时候，使用泛型：Type safety:  type errors can be checked by the compiler

>java 中泛型标记符：

>E - Element (在集合中使用，因为集合中存放的是元素)
>T - Type（Java 类）
>K - Key（键）
>V - Value（值）
>N - Number（数值类型）
>？ - 表示不确定的 java 类型

**泛型类**
代码示例：
```
package edu.whu;

public class Box <T>{  //type类型参数
    private T t;
    public  void  add(T t){
        this.t = t;
    }
    public  T get(){ //返回T类型值
        return t;
    }

    public static void main(String[] a){
        Box<Integer> integerBox = new Box<Integer>();  //等于是这个Box里面的对象是INT类型
        integerBox.add(10);
        System.out.printf("整型值为： %d\n\n", integerBox.get());
        //integerBox.add("Hello"); //会自动检查出类型错误

        Box<String> StringBox = new Box<String >();
        StringBox.add("This is an example");
        System.out.println(StringBox.get());

    }
}

```
**泛型方法**
代码示例：
```
// 泛型方法
package edu.whu.generics;

public class MaximumTest {
    /**
     * 比较三个值并返回最大值
     * 类型参数必需实现Comparable接口
     */
    public static <T extends Comparable<T>> T maximum(T x, T y, T z) {  // 在返回值类型前声明类型参数
        T max = x;
        if (y.compareTo(max) > 0) {
            max = y;
        }
        if (z.compareTo(max) > 0) {
            max = z;
        }
        return max;
    }

    public static void main(String args[]) {
        System.out.println("最大整数为:"
                + maximum(3, 4, 5));

        System.out.println("最大浮点数为:"
                +maximum(6.6, 8.8, 7.7));

        System.out.println("最大字符串为:"
                + maximum("pear", "apple", "orange"));
    }
}
```
### Java Reflection （反射）
- 1、反射机制有什么用？
通过java语言中的反射机制可以操作字节码文件（可以读和修改字节码文件。）
通过反射机制可以操作代码片段。（class文件。）
- 2、反射机制相关的重要的类有哪些？
类	含义
java.lang.Class	代表整个字节码。代表一个类型，代表整个类。
java.lang.reflect.Method	代表字节码中的方法字节码。代表类中的方法。
java.lang.reflect.Constructor	代表字节码中的构造方法字节码。代表类中的构造方法。
java.lang.reflect.Field	代表字节码中的属性字节码。代表类中的成员变量（静态变量+实例变量）。

**必须先获得Class才能获取Method、Constructor、Field。**

- 获取Class的三方式
要操作一个类的字节码，需要首先获取到这个类的字节码，怎么获取java.lang.Class实例？
如下：
Class.forName(“完整类名带包名”)	静态方法
```Class userClass = Class.forName(""com.xxl.model.User)``` //没有对象，知道类名和类的位置

对象.getClass()	
```Class userClass = User.class```
任何类型.class	

```
User user = new User();
Class userClass = user.gerClass(); //已经有了对象，获取该对象的类

```
**以上三种方式返回值都是Class类型。**

**JDBC重点(Class.forName导致类加载)**
如果你只是希望一个类的静态代码块执行，其它代码一律不执行，可以使用：

Class.forName("完整类名");
这个方法的执行会导致类加载，类加载时，静态代码块执行。

## 反射Filed【反射/反编译一个类的属性】

### Class类方法
方法名	备注
public T newInstance()	创建对象
public String getName()	返回完整类名带包名
public String getSimpleName()	返回类名
public Field[] getFields()	返回类中public修饰的属性
public Field[] getDeclaredFields()	返回类中所有的属性
public Field getDeclaredField(String name)	根据属性名name获取指定的属性
public native int getModifiers()	获取属性的修饰符列表,返回的修饰符是一个数字，每个数字是修饰符的代号【一般配合Modifier类的toString(int x)方法使用】
public Method[] getDeclaredMethods()	返回类中所有的实例方法
public Method getDeclaredMethod(String name, Class<?>… parameterTypes)	根据方法名name和方法形参获取指定方法
public Constructor<?>[] getDeclaredConstructors()	返回类中所有的构造方法
public Constructor getDeclaredConstructor(Class<?>… parameterTypes)	根据方法形参获取指定的构造方法
----	----
public native Class<? super T> getSuperclass()	返回调用类的父类
public Class<?>[] getInterfaces()	返回调用类实现的接口集合

### Field类方法
方法名	备注
public String getName()	返回属性名
public int getModifiers()	获取属性的修饰符列表,返回的修饰符是一个数字，每个数字是修饰符的代号【一般配合Modifier类的toString(int x)方法使用】
public Class<?> getType()	以Class类型，返回属性类型【一般配合Class类的getSimpleName()方法使用】
public void set(Object obj, Object value)	设置属性值
public Object get(Object obj)	读取属性值

**以下都是建立在已经用反射加Get类的情况下**
```
// 利用反射获取类属性
Class userClass = Class.forName("com.xxl.model.User");
Field[] fields = userClass.getFields(); //公有属性
for (Field field : fields) {
    System.out.println(field);
}

Class userClass = Class.forName("com.xxl.model.User");
Field[] fields2 = userClass.getDeclaredFields(); //全部属性
for (Field field : fields2) {
    System.out.println(field);
}

Field nameField = userClass.getDeclaredField("name");
System.out.println(nameField);

```

**Get/Set the field value of an object**
set()可以访问私有属性嘛？
不可以，需要打破封装，才可以。
如下实例:
```
Field nameField = userClass.getDeclaredField("name");
System.out.println(nameField);
nameField.setAccessible(true); //允许私有属性读写

User user = new User();
System.out.println(nameField.get(user));  //get the field value of an object
nameField.set(user,"zhang wuji");  //set the field value of an object
System.out.println(nameField.get(user));

```

**Get Method objects**

```
System.out.println("userClass.getMethods(): "); //获取公有属性
    for(Method method :userClass.getMethods()){
    System.out.println(" " + method);
}
System.out.println("userClass.getDeclaredMethods():");
    for (Method method : userClass.getDeclaredMethods()) {
            System.out.println("  "+method);
        }
System.out.println("getMethod by name and types:");//根据方法名和方法形参获取指定方法
        Method method = userClass.getMethod("setName", String.class);
        System.out.println("  "+method);

```

### 通过类反射实例化对象
 >对象.newInstance()
 *注：newInstance()方法内部实际上调用了无参数构造方法，必须保证无参构造存在才可以。*
*否则会抛出java.lang.InstantiationException异常。*

例子:
```
class ReflectTest02{
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        // 下面这段代码是以反射机制的方式创建对象。

        // 通过反射机制，获取Class，通过Class来实例化对象
        Class c = Class.forName("javase.reflectBean.User");
        // newInstance() 这个方法会调用User这个类的无参数构造方法，完成对象的创建。
        // 重点是：newInstance()调用的是无参构造，必须保证无参构造是存在的！
        Object obj = c.newInstance();
        System.out.println(obj);
    }
}

```

```
Class userClass = Class.forName("edu.whu.model.User");
Object user = userClass.newInstance();    //无参数构造函数

Constructor constructor = userClass.getConstructor(int.class, String.class);
Object user2= constructor.newInstance(1,"Li"); // 有参数构造函数

Method method = userClass.getMethod("setName", String.class); 
method.invoke(user2, “zhang”); //调用method类代表的方法
```

# Annotation (注解)
>Java 注解（Annotation）又称 Java 标注，是 JDK5.0 引入的一种注释机制。Java 语言中的类、方法、变量、参数和包等都可以被标注。**和 Javadoc 不同，Java 标注可以通过反射获取标注内容**。在编译器生成类文件时，标注可以被嵌入到字节码中。Java 虚拟机可以保留标注内容，在运行时可以获取到标注内容 。 当然它也支持自定义 Java 标注。

特性:
* can be considered a special kind of comment, but it can be compiled into bytecode, and loaded into Heap 
* Compiler scans some annotations during the compilation, and then does some processing based on annotations. This only applies build-in annotations.

## 自定义注解

>Java语言使用@interface语法来定义注解,所有的注解会自动继承java.lang.Annotation接口,且不能再继承别的类或是接口。
>注解的成员参数只能用public或默认(default) 访问权修饰来进行修饰。
>成员参数只能使用八种基本类型（byte、short、char、int、long、float、double、boolean）和String、Enum、Class、annotations等数据类型，及其数组。
>获取类方法和字段的注解信息，只能通过Java的反射技术来获取 Annotation 对象
>注解可以没有定义成员，只做标识。

**元注解**
元注解是专门用来注解其他注解的注解，听起来有些绕口，实际上就是专门为自定义注解提供的注解。java.lang.annotation提供了四种元注解：

- @Documented – 注解是否将包含在JavaDoc中
- @Retention – 什么时候使用该注解
* @Target – 注解用于什么地方
- @Inherited – 是否允许子类继承该注解
- @Repeatable - 是否可重复注解，jdk1.8引入

**注解的生命周期**
通过@Retention定义注解的生命周期，格式如下：

```@Retention(RetentionPolicy.SOURCE)```

其中RetentionPolicy的不同策略对应的生命周期如下：

RetentionPolicy.SOURCE : 仅存在于源代码中，编译阶段会被丢弃，不会包含于class字节码文件中。@Override, @SuppressWarnings都属于这类注解。
RetentionPolicy.CLASS : 默认策略，在class字节码文件中存在，在类加载的时被丢弃，运行时无法获取到。
RetentionPolicy.RUNTIME : 始终不会丢弃，可以使用反射获得该注解的信息。自定义的注解最常用的使用方式。

**注解的作用目标**
通过@Target定义注解作用的目标，比如作用于类、属性、或方法等，默认可用于任何地方。格式如下：

@Target(ElementType.TYPE)
对应ElementType参数值适用范围如下：

ElementType.TYPE: 类、接口、注解、enum
ElementType.CONSTRUCTOR: 构造函数
ElementType.FIELD: 成员变量、对象、属性、枚举的常量
ElementType.LOCAL_VARIABLE: 局部变量
ElementType.METHOD: 方法
ElementType.PACKAGE: 包
ElementType.PARAMETER: 参数
ElementType.ANNOTATION_TYPE: 注解
ElementType.TYPE_PARAMETER：类型参数，表示这个注解可以用在 Type的声明式前,jdk1.8引入。

**Inherited**
@Inherited，定义该注解和子类的关系，使用此注解声明出来的自定义注解，在使用在类上面时，子类会自动继承此注解，否则，子类不会继承此注解。注意，使用Inherited声明出来的注解，只有在类上使用时才会有效，对方法，属性等其他无效。

```
package edu.whu;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
public @interface InitSex {
    enum SEX_TYPE {MAN, WOMAN}
    SEX_TYPE sex() default  SEX_TYPE.WOMAN;

}

```
