## Need of Inheritance in java (继承)
### 多级继承
不能这样写：
```
class C extends A, B{

}

```
因为假如两个父类有一个同样的方法名的方法，子类调用该方法时不知道调用谁的。
>java中不支持多个实体
###方法重写
子类可以有和父类一样的方法，在子类对象调用的时候优先选择子类的该方法调用。
如下：
```
class A{
    public void show(){
            System.out.println("in A show");
    }
    public void config(){
        System.out.println("in A config");
    }
}
class B extends A{
    public void show(){
            System.out.println("in B show");
    }
}
public class Demo {
    public static void main(String args[]){
     B obj = new B();
     obj.show();

    }
    
}
```

#Package in java
两个文件不在一个包（文件夹）中，想使用其中的类的话必须引用（import 包名.类名）
如下：
```
import tools.Calc;
```
获取库的网站：
mvnrepository.com
如果想要共享自己的库的话，必须使这个包名独一无二，一般来说都使用反转域名的方式，如：com.Google

#访问修饰符
如果package内的字段不是public的时候，在包外无法访问，即使引用了这个包。

>**default** （即默认，什么也不写）: 在同一包内可见，不使用任何修饰符。使用对象：类、接口、变量、方法。
>**private** : 在同一类内可见。使用对象：变量、方法。 注意：不能修饰类（外部类）
>**public** : 对所有类可见。使用对象：类、接口、变量、方法
>**protected** : 对同一包内的类和所有子类可见。使用对象：变量、方法。 注意：不能修饰类（外部类）。

#多态 Polymorphism
相同对象或相同引用根据创建实例的方式具有不同
>多态有两种
> * 第一种： comply编译时多态 -->方法重载
> * 第二种： runtime多态  -->方法重写

##Dynamic Method Dispatch
```
class A{
    public void show(){
        System.out.println("in a show");
    }
}
class B extends A{
    
}
class C extends A{
    
}

public class test {
    public static void main(String args[]){
        B obj = new B();
        A obj1 = new B(); //可以创建超类的引用为子类的对象
    }
}

```
还可以将一个新对象分配给已创建好的引用变量(必须有*继承关系*)：
```
public class test {
    public static void main(String args[]){
        
        A obj1 = new B(); //可以创建超类的引用为子类的对象
        obj1.show();
        obj1 = new A();
        obj1.show();
        obj1 = new C();
        obj1.show();
    }}

```

# final keyword （常量）
## final --variable
```
public class test {
    public static void main(String args[]){
        final int num = 8; //将变量定义成常量了
        num = 9; // 常量不能修改
        System.out.println(num);
    }}

```
## final --method、class
不想让别人扩展/重写这个类/方法，加上final字符
#对象类 Object Class
如果你说打印一个对象，类似下面：
```
public class test {
    public static void main(String args[]){
         A obj = new A();
         System.out.println(obj);//打印对象
        
    }}

```
编译后会打印出如下：
>*A@7852e922*

其实，打印对象的时候是调用了对象类的一个方法： toString() 
该方法定义如下：
```
    return getClass().getName() + "@" + Integer.toHexString(hashCode()) //转换成十六进制哈希码
```
如果要说两个对象相同的话，他们不仅要拥有相同的值，还要拥有相同的哈希码。
如果想要自己定义 equals方法的话，可以右键选择生成哈希码，选择相应的要比较的所有变量。（Generate hashCode() and equals()）

##UPcasting and Downcasting in java（向上转型和向下转型）
父类不知道子类的存在，但是子类知道父类。父类不能调用子类的方法。
```
class A{
public void show1(){
        System.out.println("in a show");
    }
}
class B extends A{
    public void show2(){
System.out.println("in b show");
    }
}
public class test {
    public static void main(String args[]){
         A obj = new A();
        obj.show1();//此时obj只能调用show1()方法。
    }}

```

以下情况也不行，因为引用是A的，但是A不知道show2是什么。
```
public class test {
    public static void main(String args[]){
        //A obj = new A();
        //obj.show1();//此时obj只能调用show1()方法。
        A obj = new B();
        obj.show1();
        //obj.show2(); //这样也不行 会报错
    }}
```
##向上转换（upcasting）
```
public class test {
    public static void main(String args[]){
        //A obj = new A();
        //obj.show1();//此时obj只能调用show1()方法。
        A obj =(A) new B(); //向上转换
        obj.show1();
        
    }}
```
##向下转换
提要：B继承A
```
        A obj = new B(); //向上转换
        obj.show1();
        B obj1 =obj; //这样就会报错，就像double 和 int 的类型转换一样

```
正确的写法是 ```B obj1 =(B) obj;  ```
>obj是父对象，B obj1 =(B) obj语句将其向下转换为**子引用**
>这时候obj1就可以调用show2()方法
>**这就是创建了子对象的父引用之后再转换成子引用的方法。**

# Wrapper Class in java 
# Abstract Keyword in java
在一个类中定义一个方法，但是不值当方法具体内容是什么的时候，添加abstract关键字，表示这只是一个抽象的概念。
> **抽象方法只能属于抽象类。**
> 抽象类可以没有抽象方法
> 如果你要扩展一个抽象类的话，你必须重写他的抽象方法。
> 不能创建抽象类的对象
```
abstract class Car{
    public abstract void drive();
    public void playmusic(){
        System.out.println("play the music");
        
    }
}
class WagonR extends Car{
    public void drive(){
        System.out.println("Driving...");
    }
}
public class test {
    public static void main(String args[]){
      Car obj = new WagonR();
      obj.drive();
      obj.playmusic();
    }}

```

# Inner class  （内置类） class of class
```
class A{
    int age;
    public void show(){
    System.out.println("in show");    
    }
    class B{
        public void config(){
            System.out.println("in config");
        }
    }
}
public class test {
    public static void main(String args[]){
      A obj = new A();
      obj.show();

     // B obj1 = new B(); //报错，B 属于A
     A.B obj1 = obj.new B();//创建内置类的对象
     //就像访问其他变量如age一样，因为不是静态的，所以必须用类的对象来访问，因为此时B类属于A的非静态成员。
    obj1.config();
    }}

```
如果B是静态的，那么可以通过A的类名来访问，如下：
```
class A{
    int age;
    public void show(){
    System.out.println("in show");    
    }
    static class B{ //B是静态类
        public void config(){
            System.out.println("in config");
        }
    }
}
public class test {
    public static void main(String args[]){
      A obj = new A();
      obj.show();

     A.B obj1 = new A.B(); //如果B是静态的，用 A.B 来访问
    obj1.config();
    }}
```
> **不能在非内置类的类前添加static修饰符**

## Anonymous Inner Class in java (匿名内置类)
> 匿名类： 没有名字的类
如果一个类的扩展类使用次数不多，不需要重新定义一个新类。
```

class A{
    public void show(){
        System.out.println(" in show");
    }
}


public class test {
    public static void main(String args[]){
     A obj = new A(){
        //匿名内置类
        public void show(){  //实现了该类的新方法，这其实是一个内置类
            System.out.println("in new show");
        }
     };
     obj.show();
    }}

```
## Abstract and Anonymous Inner Class
使用匿名内部类解决抽象类必须扩展类来创建对象的问题。
```

abstract class A{ //声明为抽象类
   abstract public void show();
}


public class test {
    public static void main(String args[]){
     A obj = new A(){
        //匿名内置类
        public void show(){  //实现了该类的新方法，这其实是一个内置类
            System.out.println("in new show");
        }
     };
     obj.show();
    }}
```

# Interface 
例子：
假如一个程序员进入一个公司，他需要一台电脑才能写代码。这台电脑可以是笔记本，也可以是台式机，总之就是计算机。
一般来说我们可以像下面这样做：
```
import javafx.scene.control.Label;

class laptop
{
    public void code(){
        System.out.println("code, compile, run");
    }
}
class Desktop{
    public void code(){
        System.out.println("code, compile, run");
    }
}

class Developer{
    public void devApp(laptop lap){
        lap.code();
    }
}

public class test {
    public static void main(String args[]){
    laptop lap = new laptop();
     Developer navin = new Developer();
     navin.devApp(lap);
    }}

```
但事实上，我们只想要程序员只考虑computer，不管是什么类型，于是有如下抽象类：
```
import javafx.scene.control.Label;

abstract class Computer{
    public abstract void code();
    
}

class laptop extends Computer
{
    public void code(){
        System.out.println("code, compile, run ");
    }
}
class Desktop extends Computer{
    public void code(){
        System.out.println("code, compile, run : faster");
    }
}

class Developer{
    public void devApp(Computer lap){
        lap.code();
    }
}

public class test {
    public static void main(String args[]){
    Computer lap = new laptop();
     Developer navin = new Developer();
     Computer desk = new Desktop();
     navin.devApp(lap);
    }}

```
概括事物
我们不用抽象类，改成接口：
```
import javafx.scene.control.Label;

 interface Computer{ //定义成接口
    void code();
    
}

class laptop implements Computer  //接口应该用 implements
{
    public void code(){
        System.out.println("code, compile, run ");
    }
}
class Desktop implements Computer{
    public void code(){
        System.out.println("code, compile, run : faster");
    }
}

class Developer{
    public void devApp(Computer cp){
        cp.code();
    }
}

public class test {
    public static void main(String args[]){
    Computer lap = new laptop();
     Developer navin = new Developer();
     Computer desk = new Desktop();
     navin.devApp(lap);
    }}

```
接口与接口的扩展用 **extends**
类与类的扩展用 **extends**
类与接口的扩展用 **implements**
```

interface A{
    int age = 44;
    String area = "Mumb";

    void show();
    void config();

}
interface X{
    void run();
}
interface Y extends X { //接口与接口的扩展用extends
    
}

class B implements A, Y{ //一个类可以继承多个接口
    public void show(){
        System.out.println("in B show");
    }
    public void config(){
        System.out.println("in B config");
    }
    public void run(){
        System.out.println("running...");
    }
}
public class test {
    public static void main(String args[]){
    A obj = new B();
    obj.show();
    obj.config();
    X obj1 = new B(); //想调用run函数，必须创建X的引用
    obj1.run();
    }}

```
# Enum in java
枚举是我们创建的命名常量
```
enum Status{
    //命名常量
    Running, Failed, Pending, Success; //事实上，这些每一个都是status的一个对象
}

public class test {
    public static void main(String args[]){
        //int i=5;
        Status s = Status.Running;  //通过对象调用
        System.out.println(s);
    }}
```
如果有需要用到常量的场景，我们可以使用枚举
s.ordinal()
以上用来得到枚举常量的序号

## 获得并打印所有枚举的值：
```


enum Status{
    //命名常量
    Running, Failed, Pending, Success; //事实上，这些每一个都是status的一个对象
}

public class test {
    public static void main(String args[]){
        //int i=5;
        //Status s = Status.Running;
        //打印所有状态
        Status[] s1 = Status.values();
        for(Status s : s1){
            System.out.println(s);
        }
    }}
```
## Enum switch and if in java
### if的情况：
```
enum Status{
    //命名常量
    Running, Failed, Pending, Success; //事实上，这些每一个都是status的一个对象
}

public class test {
    public static void main(String args[]){
        
        Status s = Status.Pending;
        if(s == Status.Running){
            System.out.println("Good!");
        }
        else if (s == Status.Failed){
            System.out.println("Try again");
        }
        else if(s == Status.Pending){
            System.out.println("Please wait..");
        }
        else{
            System.out.println("done");
        }


    }}

```
### switch的情况

```
enum Status{
    //命名常量
    Running, Failed, Pending, Success; //事实上，这些每一个都是status的一个对象
}

public class test {
    public static void main(String args[]){
        
        Status s = Status.Running;

        switch (s){
            case Running:
                System.out.println("Good!");
                break;
            case Failed:
                System.out.println("Try again");
                break;
            case Pending:
                System.out.println("Please wait..");
                break;
            default:
                System.out.println("Done");
        }
    }}
```
## Enum class
enum is a class
but it couldn't extend another classes.
但是可以在enum中创建变量，构造函数和方法等。
如下：
```
enum Laptop{
    Macbook(2000), XPS(2200), Surface(1500), Thinkpad(1800);
    private int price;
    private Laptop(int price) { //构造函数没有返回值
        this.price = price;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
}

public class test {
    public static void main(String args[]){
        //Laptop lap = Laptop.Macbook;
       // System.out.println(lap + ":" + lap.getPrice());
        for(Laptop lap : Laptop.values()){
            System.out.println(lap + ":" + lap.getPrice());
        }
    }}
```

#  Annotation
## @override
```
class A{
    public void showTheDataWhichBelongsToThisClass(){
        System.out.println("in A show");
    }
}
class B extends A{
    @Override
    public void showTheDataWhichBelongToThisClass(){ //这里我们要重写A的函数，但是我们故意少打一个字母，加上override的注释后，我们知道我们写错了
        System.out.println("in B show");
    }
}
public class test {
    public static void main(String args[]){
       B obj = new B();
       obj.showTheDataWhichBelongsToThisClass();
    }}
```
## 接口类型
java8之前，在接口中我们只能声明方法。
三种类型的接口：
- Normal interface 
含有两个或以上的方法
- Function interface (SAM)
只有一个方法的接口
- Marker interface
没有方法的接口 ：to updata something to the complier. :如关闭游戏之后，保存游戏的数据，就要有一个文件保存数据在硬盘驱动器中。当重新加载游戏的时候，就从硬盘中load这些数据到对象的变量中。（反序列化）

### Funtional Interface
函数式接口是只有一种方法的接口。
```
@FunctionalInterface
interface A{
    void show(); // only a method here
}
public class test {
    public static void main(String args[]){
       
    }}
```
## 函数式接口为什么重要？
### Lambda Expression 
**lambda用于简化匿名内部类。**
只适用于函数式接口。
语法如下例：
```
@FunctionalInterface
interface A{
    void show(int i); // only a method here
}

public class test {
    public static void main(String args[]){
      /*  A obj = new A(){//这一段，可以改成lambda表达式
        public void show(){
            System.out.println("in new show");
        }
       };
       */
      A obj = (int i) ->  System.out.println("in new show" + i); // lambda 
      //还可以加参数
       obj.show(5);
    }}
```
并且他不会在编译以后出现匿名内部类的class文件，可以减少创建的文件数量。
### 有返回值的函数式接口
```

@FunctionalInterface
interface A{
    int  add(int i, int j); // only a method here
}

public class test {
    public static void main(String args[]){
      A obj = ( i , j) ->  i + j; //省略return
      int res =  obj.add(2,3);
      System.out.println(res);
    }}

```
# Exception
## Error type
- Compile time error
编译器在编译的时候就会说你写错了，没法编译
- Runtime error  （Exception）
编译没问题但是运行的时候出现了错误.--> 想要保证即使出现了运行时错误也不会自动停止应用程序。
我们不会直接停止应用，而是会检查运行条件 --handle runtime errors
- Logical error
没有编译时错误，没有运行时错误，但是运行结果错了  (bug)

万一出现了异常，必须处理它。
## Exception Handling Using try catch in java
异常例子：
```
public class test {
    public static void main(String args[]){
      int i = 0;
      int j = 19/i;  //这个语句就可能有异常出现,假如i是0的话
      System.out.println(j);
    }}
```

会报如下异常：
```
Exception in thread "main" java.lang.ArithmeticException: / by zero
        at test.main(test.java:8)
```
该异常是一个算术异常。

要解决这个问题，我们可以用 *try catch* 来解决：就是我们不是直接执行这个代码，我们是尝试执行这个代码，如果这段代码出现了问题，我们就catch这个异常，并处理他。
如下：
```
public class test {
    public static void main(String args[]){
      int i = 0;
      int j = 0;
      try
      {
        j = 18/i;  //将 critical 语句写在这
      }
      catch(Exception e){ //catch异常
        System.out.println("Something went wrong");
      }
      System.out.println(j);
      System.out.println("Bye");
    }}

```
输出将为：
```
Something went wrong
0
Bye
```
可以看出程序没有停在那里，而是向下继续执行了打印“bye”的语句。

## Try with Multiple Catch in java

数组越界取值也会出现异常：
```
public class test {
    public static void main(String args[]){
        int i = 2;
        int j = 0;
        int nums[] = new int[5];

      try
      {
        j = 18/i;
        System.out.println(nums[1]);
        System.out.println(nums[5]); //该数组大小只到下标为4的
      }
      catch(Exception e){
        System.out.println("Something went wrong. " + e);
      }
      System.out.println(j);
      System.out.println("Bye");
    }}
```
此时运行就会报错：
```
0
Something went wrong. java.lang.ArrayIndexOutOfBoundsException: 5     
9
Bye
```
为了确定异常的类型并打印对应的错误，可以在catch异常后先判断异常的类型。也可以用多个catch块处理不同的异常。
同时为了保证还有除这两个异常之外的异常出现，我们还会多catch一个exception的异常，保证能处理全部异常。
```
public class test {
    public static void main(String args[]){
        int i = 2;
        int j = 0;
        int nums[] = new int[5];
        String str = null;
      try
      {
        j = 18/i; //可能会出现i=0的情况
        System.out.println(str.length());//可能会出现该字符串是空的情况
        System.out.println(nums[1]);
        System.out.println(nums[5]); //该数组大小只到下标为4的
      }
      catch(ArithmeticException e){
        System.out.println("Cannot divide by zero. ");
      }
      catch(ArrayIndexOutOfBoundsException e){
        System.out.println("Stay in your limit.");
      }
      catch(Exception e){
        ystem.out.println("Something went wrong ");
      }
      System.out.println(j);
      System.out.println("Bye");
    }}

```
exception 类必须放在catch最后，因为他可以处理所有异常。
这样确实可以处理不同的异常。但是有一个问题：
如果有两个异常同时出现，比如上述如果i等于0，那么最后只catch了一个异常，而数组越界的异常却没有打印出来。

### Exception Hierarchy in java
异常（Exception）是一个超类，他有很多子类。
哪些异常需要处理，哪些不需要？
>最顶层是object类。 因为java中的每个对象都扩展这个类
>下面有一个throwable的类
>throwable下分支为error和exception
>>error是你不能处理的问题：如ThreadDeath或者I/O Error或者还有一些Virtual Machine Error(比如 out of memory )
>
>Exception下分为RuntimeException（Unchecked exception）、[SQLException和I/O Exception](checked exception)等
>>RuntimeException 下有很多子类异常
>> unchecked的异常是你可以选择处理或者不处理他们。但是checked异常，编译器一定会强制你处理异常。

## Exception throw keyword in java
### throw
假如我们的程序没有异常，但是输出结果我们有限制，如果不是那样，我们就想抛出一个异常并处理它，使用*throw*关键字：
```
public class test {
    public static void main(String args[]){
        int i = 20;
        int j = 0;
        
      try
      {
        j = 18/i; //可能会出现i=0的情况
        if(j == 0){ //当i不是0，是大于18的数的时候，没有异常，但我们不想要答案等于0，我们想进入异常怎么办？
            //于是我们在这里进行判断，并抛出一个异常，这个异常会被catch到。
            throw new ArithmeticException();
        }
      }
      catch(ArithmeticException e){
        //System.out.println("Cannot divide by zero. ");
        j = 18/1;
        System.out.println("that's default output");
    }
      catch(Exception e){
        System.out.println("Something went wrong " + e);
      }
      System.out.println(j);
      System.out.println("Bye");
    }}
```
**Custom Exception in java**
我们不想抛出已经定义过的异常，我们想抛出自己的异常。
```
class NavinException extends Exception{
    public NavinException (String s){ //该自定义异常必须扩展Exception类或者RuntimeException
        super(s); 
    }
}

class test {
    public static void main(String args[]){
        int i = 20;
        int j = 0;
      try
      {
        j = 18/i; //可能会出现i=0的情况
        if(j == 0){ //当i不是0，是大于18的数的时候，没有异常，但我们不想要答案等于0，我们想进入异常怎么办？
            //于是我们在这里进行判断，并抛出一个异常，这个异常会被catch到。
            throw new NavinException("I dont want to print zero");
        }
      }
      catch(NavinException e){
        j = 18/1;
        System.out.println("that's default output." + e);
      }
    
      catch(Exception e){
        System.out.println("Something went wrong " + e);
      }
      System.out.println(j);
      System.out.println("Bye");
    }}

```

## Ducking Exception using throws in java
### Throws
在某个方法中我们不想处理异常，想让调用这个方法的处理，于是我们可以在该方法中抛出异常，使用 **throws** 关键字。
在调用该方法的方法中，需要try catch异常进行处理。
该方法如果不处理，也可以向上（调用该方法的另一个方法）抛出异常，由另一个方法try catch... 以此类推。
> **尽量不要在main函数抛出异常，因为它会交给JVM处理，会导致程序停止运行。**

# User Input using BufferReader and Scanner in java
println方法属于PrintStream类
也就是说，你想要调用该方法的话，你就需要创建该类的对象，但是我们一般如下这样写就行了：
```System.out.println("  ");```

原因是该对象已经被创建过了。就是上面的out对象。
因为out是定义在System类中的static对象，所以我们可以通过静态方法（类名）调用它。
## 如何接受用户输入？
**1.使用 System.in.read()**
一次只能读一个字符，想要读取多个字符需要循环，很不方便。
```
import java.io.IOException;

class test {
       
    public static void main(String args[]) throws IOException{
        System.out.println("Ebter a number:  ");
        int num = System.in.read(); //这样得到的是ASCII码，并且一次只能读一个字符。
        System.out.println(num - 48);
        
    }}

```
**2. BufferedReader**
BufferedReader is a class which works with IO . It belongs to the java.io package
```
class test {
       
    public static void main(String args[]) throws IOException{
        System.out.println("Ebter a number:  ");
        InputStreamReader in = new InputStreamReader(System.in);
        BufferedReader bf = new BufferedReader(in);
        int num =Integer.parseInt(bf.readLine()) ;
        System.out.println(num);

        bf.close(); //因为 BufferedReader 实际上属于一种资源，每次使用之后需要手动关闭。
    }}

```
其中，Integer.parseInt方法介绍：

>parseInt() 方法用于将字符串参数作为有符号的十进制整数进行解析。

>如果方法有两个参数， 使用第二个参数指定的基数，将字符串参数解析为有符号的整数。

**3.Scanner**
```
class test {
       
    public static void main(String args[]) throws IOException{
        System.out.println("Enter a number:  ");
       Scanner sc =new Scanner(System.in);
       int num = sc.nextInt();
        System.out.println(num);
    }}
```

## Try with rescources in java
* try ... finally
```
class test {
       
    public static void main(String args[]) {
        
            int i = 0;
            int j = 0;

            try{
                j = 18/i;
            }
            catch(Exception e){
                System.out.println("something was wrong");
            }
            finally{ //无论有没有异常都会执行finally 里的语句
                System.out.println("bye");
            }
    }}

```
所以使用finally的意义是什么呢？
-->资源的使用与关闭
一般来说使用资源的时候都会写在try中，因为很容易出现异常，而资源使用后需要手动关闭，那么就使用finally。
```
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class test {
       
    public static void main(String args[]) throws NumberFormatException, IOException {
        
            int num = 0;
            BufferedReader br = null;
            try{
                InputStreamReader in = new InputStreamReader(System.in);
                br = new BufferedReader(in) ;
                num = Integer.parseInt(br.readLine());
                System.out.println(num);
            }
            finally{ //无论有没有异常都会执行finally 里的句子
                br.close();
            }
    }}

```

但是我们可以直接在try后面加一个括号，在括号中创建资源对象，这样资源就会自动关闭，如下：
class test {
       
    public static void main(String args[]) throws NumberFormatException, IOException {
        
            int num = 0;
            
            try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) //在try中创建资源对象
            {
                num = Integer.parseInt(br.readLine());
                System.out.println(num);
            }
         
    }}

# Threads in java
## 创建线程
**1.逐个线程地对类进行线程版本控制**
想让两个show方法并行进行.
1.类需要扩展Thread 类
2.用start方法启动一个新线程
3. 每个线程必须有一个run方法,所以将show方法改为run
```
class A extends Thread
{
    public void run(){ //改成run方法
        for(int i = 0; i<100; i++){
             System.out.println("Hi");
        }
    }
}
class B extends Thread
{
    public void run(){
        for(int i = 0; i<100; i++){
             System.out.println("Hello");
        }
    }
}
class test {
       
    public static void main(String args[])  {
        A obj1 = new A();
        B obj2 = new B();
       obj1.start(); //用start方法启动一个新线程
       obj2.start(); //当你调用start之后，他会调用run方法。
    }}
```
有一个调度程序会进行线程的调度。
## Thread Priority and Sleep in java
想要实现 hi 和 hello 交替一个一个出现怎么办？
-->线程优先级
优先级的范围从1到10
可以设置优先级，得到优先级
可以设置线程休眠时间 --sleep  让线程进入等待阶段
```
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
class A extends Thread
{
    public void run(){ 
        for(int i = 0; i<100; i++){
             System.out.println("Hi");
             try {
                Thread.sleep(2); // 让该线程等待一段时间
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
class B extends Thread
{
    public void run(){
        for(int i = 0; i<100; i++){
             System.out.println("Hello");
              try {
                Thread.sleep(2); //如果是10的话，会出现打印两个一样的句子的情况，为了优化可以减少等待时间。
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
class test {
       
    public static void main(String args[])  {
        A obj1 = new A();
        B obj2 = new B();
       obj1.start(); //用start方法启动一个新线程
       obj2.start(); //当你调用start之后，他会调用run方法。
    }}

```

这些都只是给调度程序建议，并不能直接控制调度程序选择那一个线程先执行。

## Runnable and Thread in java
**更多创建线程的方法**
假如A类继承了线程类，但是A 类又想继承别的类怎么办？因为java是不允许多重继承的，如何解决这个问题呢？
事实上，查看线程Thread的源码：
```class Thread implements Runnable ```
发现线程是扩展了一个叫 Runnable的接口的。
查看该接口源码：
```
public interface Runnable {
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see     java.lang.Thread#run()
     */
    public abstract void run();
}
```
发现该接口中只有一个抽象方法run，这也是为什么我们的线程必须有run方法的原因。

```
class test {
       
    public static void main(String args[])  {
        //用匿名内部类并转换成lambda表达式
        Runnable obj1 = ()->
        {for(int i = 0; i<5; i++){
             System.out.println("Hello");
              try {
                Thread.sleep(10); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        };//可以创建一个接口的引用
        Runnable obj2 = () ->
        {for(int i = 0; i<5; i++){
             System.out.println("Hi");
              try {
                Thread.sleep(10); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }};
        Thread t1 = new Thread(obj1); //Thread的一个构造函数中参数可以是Runnable 对象
        Thread t2 = new Thread(obj2); //传递对象之后该线程才知道要启动哪个对象的run函数
       t1.start(); //用start方法启动一个新线程
       t2.start(); //当你调用start之后，他会调用run方法。
    }
}
```
## Race Condtion in java (竞争条件)
*Java的可变性（Java Mutation）*
某些变量可以被大家修改，如你定义了一个 int i,i可以被加，可以减。这回引起安全问题
If you do mutation with lots of threads, what will be work?
假如现在有两个线程T1和T2，都要操作变量i，会导致-->线程安全问题
-->同步性：
同一时间只有一个线程能访问某资源
```
class Counter
{
	int count;
	//public void increment()
	public synchronized void increment()
	{
		count++;
	}
}

public class test {
    public static void main(String[] args) throws InterruptedException{   
    	
    	Counter c=new Counter();
    	
    	Runnable obj1=()->
    	{
//    		for(int i=1;i<=1000;i++)
    		for(int i=1;i<=100;i++)
    		{
    			c.increment();
    		}
    	};
    	
    	Runnable obj2=()->
    	{
//    		for(int i=1;i<=1000;i++)
    		for(int i=1;i<=100;i++)
    		{
    			c.increment();
    		}
    	};
    	
    	Thread t1=new Thread(obj1);
    	Thread t2=new Thread(obj2);
    	 	
    	t1.start();
    	t2.start();
    	
    	t1.join();
    	t2.join();
    	
    	System.out.println(c.count);
    }
}

```
join()方法允许主线程等待其他线程回来并加入。
假如线程t1和t2同时想要调用资源，他们同时迭代，那么count自增就只发生一次。
两个线程访问共享变量就有可能出现这样的问题。
如何保证同一时间只能有一个线程可以处理共享资源呢？
> 用**synchronized** keyword


# Collection API in java
 Collection API 
 Collection   ：接口
 Collections  : 集合
 在该API中可以使用定义好的数据结构。
 ## ArrayList in java

 ```
 import java.util.ArrayList;
import java.util.Collection;

public class test {
    public static void main(String[] args) throws InterruptedException{   
    	Collection nums = new ArrayList<>();
        nums.add(6); //添加的数字其实不是int类型，而是对象类型
        nums.add(5);
        nums.add(7);
        for(int n : nums){  //所以这里不能直接用int类型的变量接受
              System.out.println(nums);
        }
  //Collection API适用于对象
    }
}

 ```

 正确写法：
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

 ### Set in java
 Set 不会给元素排序，也不支持index（Hashset）
 Set 是一个不包含重复元素的集合
TreeSet继承了一个排序集合，所以他会对元素进行排序
TreeSet继承了iterator迭代器。
```

//用泛型的方法
public class test {
    public static void main(String[] args) throws InterruptedException{   
    	Collection <Integer> nums = new TreeSet<Integer>();
        nums.add(54); 
        nums.add(52);
        nums.add(73);
      Iterator<Integer> values = nums.iterator();
      while(values.hasNext()){
          System.out.println(values.next());
      }
    }
}

```
### map in java
```
public class test {
    public static void main(String[] args) throws InterruptedException{   
      Map<String, Integer> studMap = new HashMap<>();
      studMap.put("Herry", 16);
      studMap.put("mary", 25);
      studMap.put("march", 38);
      studMap.put("boody", 54);

      //循环打印map中的值  keyset（）:得到key集合
      for(String name : studMap.keySet()){
        System.out.println(name + ":" + studMap.get(name));
      }
    }

}

```
### Comparator vs Comparable
如果我们想按照自己的逻辑对集合等进行排序，我们要用到comparator
```
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;


//class Student implements Comparable<Student>
class Student
{
	int age;
	String name;
	
	public Student(int age, String name)
	{
		this.age=age;
		this.name=name;
	}
	
	public String toString() {
		return "Student [age=" + age + ", name=" +name +"]";
	}
	
//	public int CompareTo(Student that)
//	{
////		return 0;
//		if(this.age >that.age)
//			return 1;
//		else
//			return -1;
//	}
}
	

public class Demo {
    public static void main(String[] args){   
    	
//    	Comparator<Integer> com=new Comparator<Integer>()
//    	{
//    		public int compare(Integer i,Integer j)
//    		{
//    			if(i%10 >j%10)
//    				return 1;
//    			else
//    				return -1;
//    		}
//    	};	
    	
//    	List<Integer> nums= new ArrayList<>();
//    	nums.add(43);
//    	nums.add(31);
//    	nums.add(72);
//    	nums.add(29);
    	
//    	Comparator<Student> com=new Comparator<Student>()
//    	{
//    		public int compare(Student i,Student j)
//    		{
//    			if(i.age >j.age)
//    				return 1;
//    			else
//    				return -1;
//    		}
//    	};	
    	
    	Comparator<Student> com=(i,j) -> i.age > j.age?1:-1;
     	
    	List<Student> studs= new ArrayList<>();
    	studs.add(new Student(21,"Navin"));
    	studs.add(new Student(12,"John"));
    	studs.add(new Student(18,"Parul"));
    	studs.add(new Student(20,"Kiran"));
    	
//    	Collections.sort(nums);
//    	System.out.println(nums);
    	
    	for(Student s:studs)
    		System.out.println();
    	
    	Collections.sort(studs);
    	for(Student s: studs)
    		System.out.println(s);
    }
}


```



