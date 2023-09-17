# java 学习笔记
##使用markdown预览时出现报错
    Error loading webview: Error: Could not register service worker: InvalidStateError: Failed to register a ServiceWorker: The document is in an invalid state..
解决办法：
+  关闭vscode
+  打开cmd，终端输入命令 code --no-sandbox
+  重启vscode

## JDK JRE JVM IN JAVA 
>JDK
>>java development kit

>JVM
>>Java Virtual Machine : the place code running

>JRE
>>Java Runtime Envirement 含有Java的各种内置类，它会检查你的字节码并验证你的字节码。

  jvm is part of jre.  
  jdk consists of jre and jvm.

## Methods in java

方法定义在类中
创建对象和调用方法的示例：
```
class Computer{
    public void playmusic()
    {
        System.out.println("music playing");
    }
    public String getMeAPen(int cost)
    {
        return "Pen";
    }
}
public class hello{
public static void main(String args[])
{
    //想要使用一个类，必须创建对象
    Computer obj = new Computer();
    obj.playmusic();
    obj.getMeAPen(10);
}
}
```
### Method Overloading in java
*重写方法，方法名一样，参数不一样。*
*返回值不一样也不行，必须参数不一样*
示例：
```
class calculator{
    public int add(int n1, int n2){
        
        return n1 + n2;
    }
    public int add(int n1, int n2, int n3){
        return n1 + n2 + n3;
    }
}

public class hello{
public static void main(String args[])
{
    calculator c1 = new calculator();
    c1.add(1, 0);
    c1.add(1, 2, 3);
}
}
```

### Stack And Heap in java
>jvm里面有什么？
>从 内存级别来看：创建变量的时候，变量存储在内存中（或者jvm内存中）什么位置？
>   两部分：1.**stack**：一个存储数据的区域，并且是**后进先出** 
>          2.**heap**：open space
讨论如下的变量：
```
class calculator{
    int num;
    public int add(int n1, int n2){
        
        return n1 + n2;
    }
    public int add(int n1, int n2, int n3){
        return n1 + n2 + n3;
    }
}
public class hello{
public static void main(String args[])
{
    int data = 10;
    calculator c1 = new calculator();
    c1.add(1, 0);
    c1.add(1, 2, 3);
}
}
```
> 1.**n1、n2是局部变量**，保存在add方法的stack中
> 2.**num是实例变量**，保存在heap中
> 3.**obj是一个引用变量**，当使用new 创建对象时，它将在heap中创建对象。该对象有两个部分，第一部分包含所有实例变量（不是局部变量）局部变量保存在stack中；第二部分包含了该对象的所有方法。该对象也有其对应的地址。
>>在main方法的堆栈中，obj引用变量的值部分保存的是该对象在堆中的起始地址。

> **每个方法都有自己的堆栈**, 堆栈有key 和 value。

## 多维数组
```
public class Demo {
    public static void main(String args[])
    {
        int nums[][] = new int[3][]; //该二维数组中有三个数组，每个数组中的元素不确定
        int nums1[][] = new int[3][4];//该二维数组中有三个数组，每个数组中有4歌元素
    }
}
```

**用for循环和增强for循环打印二维数组**
```
public class Demo {
    public static void main(String args[]){
        //int nums[][] = new int[3][]; 
        int nums1[][] = new int[3][4];

           for(int i =0 ;i<3;i++){
            for(int j=0;j<4;j++){
                nums1[i][j] = (int)(Math.random() *100);
            }
        }

        for(int i =0 ;i<3;i++){
            for(int j=0;j<4;j++){
                System.out.print(nums1[i][j]+" ");
            }
            System.out.println();
        }
        //增强for循环
        for(int n[] : nums1){
            for(int m:n){
                System.out.print(m+" ");
            }
            System.out.println();
        }
    }
}

```

### jagged and 3D Array in java
>jagged array: 
>>```int nums[][] = new int[3][];```

打印jagged array:
```

public class Demo {
    public static void main(String args[]){
        int nums[][] = new int[3][]; 
        nums[0] = new int[3];
        nums[1] = new int[4];
        nums[2] = new int[2];

        for(int i = 0; i<nums.length;i++){
            for(int j = 0; j<nums[i].length;j++){
                nums[i][j] = (int)(Math.random()*100);
            }
        }
        //普通for循环，需要知道每行大小
        for(int i = 0; i<nums.length;i++){
            for(int j = 0; j<nums[i].length;j++){
                System.out.print(nums[i][j]+" " );
            }
            System.out.println();
        }
        //增强for循环，更好，不需要知道大小
        for(int n[]:nums){
            for(int m:n){
                System.out.print(m+" ");
            }
            System.out.println();
        }
    }
}

```
### Array of Objects in java
采用数组保存对象更好处理数据：
```

class Student{
    int rollno;
    String name;
    int marks;
}
public class Demo {
    public static void main(String args[]){
        Student s1 = new Student();
        s1.rollno = 1;
        s1.name = "Navin";
        s1.marks = 89;

        Student s2 = new Student();
        s2.rollno = 2;
        s2.name = "ken";
        s2.marks = 84;

        Student s3 = new Student();
        s3.rollno = 3;
        s3.name = "candy";
        s3.marks = 75;

        Student students[] = new Student[3];
        students[0] = s1;
        students[1] = s2;
        students[2] = s3;

        for(int i=0; i<students.length;i++){
            System.out.println(students[i].name+": "+students[i].marks);
        }
    }
}

```
采用enchanced loop 打印对象数组：
```
 for(Student stu :students){
            System.out.println(stu.name + ": "+stu.marks);
        }
```
#String
string是一个类，但是我们却可以这样使用它：*String name = "wendy";*
但是也可以像创建对象那样使用： *String name = new String()*
##Mutable vs Immutable String in Java
###可变字符串类
```
public class StringExercise{
    public static void main(){
        String s1 = "candy";
        String s2 = "candy";
        
    }
}
```
事实上，在该段代码中，s1和s2指向同一个对象
在jvm中有一个string constant pool,我们在java中创建的每个字符串，实际上是一个常量，我们不能改变它。
所以当我们使用String类时，他是一个不可变的字符串。
###不可变字符串类：StringBuffer and StringBuilder in Java
+ 使用StringBuffer:
    ```
    public class StringExercise{
    public static void main(String args[]){
        StringBuffer sb = new StringBuffer("navin");
        sb.append(" reddy");
        System.out.println(sb);

    }
}
``
    一个不添加任何字符串的缓冲区大小为16
    不能从StringBuffer类型转换成String类型，需要用如下方法：
    String str = sb.toString()
    
+ 使用StringBuilder
    和上面差不多

*二者的区别是，StringBuffer**线程安全***
#Static
##Static Variable in java
如果将一个类中的实例变量声明为static，那么该变量将被所有该类的对象公用，如果某个对象修改了该值，也会改变其他对象的该值。
所以如果访问该静态变量的时候，应该用静态方法(类名)访问，如下：
```

class Student{
    int rollno;
    String name;
    int marks;
}
class Mobile{
    String brand;
    int price;
    static String name;
}
public class Demo {
    public static void main(String args[]){
       Mobile m1 = new Mobile();
       m1.brand = "Apple";
       m1.price= 1500;
       Mobile.name ="smartphone"; //采用静态访问
    }
}

```
**static意味着你将某成员定义成类成员而不是对象成员**

##Static Method in java 静态方法
代码示例：
```

class Student{
    int rollno;
    String name;
    int marks;
}
class Mobile{
    String brand;
    int price;
    static String name;

    public void show(){
        System.out.println(brand + ":" + price + ": " +name);
    }
    public static void show1(){
        System.out.println("in static method");
    }
}
public class Demo {
    public static void main(String args[]){
       Mobile m1 = new Mobile();
       m1.brand = "Apple";
       m1.price= 1500;
       Mobile.name ="smartphone"; //采用静态访问

       Mobile.show1();
       Mobile.show(); //这样就会报错说不能对非静态方法进行静态引用
    }
    
}
```
**可以在静态方法中使用静态变量，但不能使用非静态变量**
>因为非静态变量是属于对象成员，每个对象成员的该变量值都不一样，而静态变量属于类成员变量，静态方法也属于类成员方法，每个对象的该值都是一样的，所以上句成立。

如果想要在静态方法中使用非静态变量，必须通过对象名调用。
如下：
```
class Mobile{
    String brand;
    int price;
    static String name;

    public void show(){
        System.out.println(brand + ":" + price + ": " +name);
    }
    public static void show1(Mobile obj){
        System.out.println(obj.brand + ":" + obj.price + ": " +name);
    }
}
public class Demo {
    public static void main(String args[]){
       Mobile m1 = new Mobile();
       m1.brand = "Apple";
       m1.price= 1500;
       Mobile.name ="smartphone"; //采用静态访问

       Mobile.show1(m1);
      
    }
    
}
```
所以main方法必须是静态的原因是：
如果它是非静态的，那么要调用该方法就必须建立一个Demo对象（示例为上面的代码），但是main方法是程序的起点，如果程序都没开始运行，怎么建立新对象呢？所以main必须为静态的。

##Static Block in java
构造函数在每次创建新对象之前都会初始化对象一次。
但是静态成员变量只需要初始化一次就行了，怎么办呢？
想要一个静态的block来初始化静态成员，如上面的name ：
```
class Mobile{
    String brand;
    int price;
    static String name;

    static{
        name = "phone";
    }
    public Mobile(){
        //构造函数
        brand = "";
        price = 200;
      //  name = "phone";
    }
    public void show(){
        System.out.println(brand + ":" + price + ": " +name);
    }
}
```
其中
```
static{
        name = "phone";
    }
```
只会被调用一次。
且static block在构造函数之前被调用。
如果不创建对象，那么类也不会被加载。
如果想要只加载类怎么办？
用如下方法：
```Class.forName("Mobile");
```

#Encapsulation in java  (封装)
我们不想完全共享我们的数据
如果有人想要知道我们的某些事（数据），他们需要询问我们。
将某变量设为private意味着：这个特定的变量，只能在同一个类中访问
如果其他创建的对象想要访问的话，需要通过间接的办法：
**类将会提供一些方法给外人分享自己的私有数据**
如下：
```
class Human{
    private int age = 11;
    private String name = "Candy";
    public int getAge(){
        return age;
    }
    public String getName(){
        return name;
    }
}
public class Demo {
    public static void main(String args[]){
     Human obj = new Human();
     System.out.println(obj.getAge());
     System.out.println(obj.getName());
     
    }
}

```
但是我们不事先分配值的话，我们怎么设置值呢？
需要提供设置值的方法：
```
  public void setAge(int a){
        age = a;
    }
    public void setName(String s){
        name = s;
    }
```

我们在某个地方封装了数据和方法，这就是封装。
## This keyword in java
```
class Human{
    private int age = 11;
    private String name = "Candy";
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
   
}
```
在该段代码中，有一个新关键字 -->this。
如果你的局部变量名和类的实例变量名一样的话，要用this表明该名称是类的实例变量名。
**this表示当前对象。**

##构造函数
>当我们创建对象的时候不想要空值而想要一些默认的值时，需要构造函数。
###构造函数重载
当我想要实现：每次我创建一个新对象的时候，他不是初始化为构造函数中的值，而是想要初始化为我们自定义的值怎么办？
>每次我创建对象，我想要可以选择传递我想要初始化的值，这时候就需要重载构造函数。
```
class Human{
    private int age = 11;
    private String name = "Candy";

    public Human(){ //default constructor
        System.out.println(" in constructor");
    }
    public Human(int a, String n){ // Parameterized constructor
        age = a;
        name = n;
    }
```
## This and Super Method in java
>每一个构造函数都有一个方法叫super();即使你没有明确的写上去，它也存在，当一个类继承另一个类时，构造该类的对象，会先调用该类的构造函数，构造函数中的super()方法，即先调用父类的默认构造函数。
如果你想调用父类的带参数的构造函数的话，就在super()中加相对应的参数
java中的每个类都extends Object类
```
class A{
    public A(){
        System.out.println("in A");
    }
    public A(int n){
        System.out.println("in A int");
    }
}
class B extends A{
    public B(){
        System.out.println("in B");
    }
    public B(int n ){
        this(); //这将调用同一个类的构造函数
        System.out.println("in B int");
    }
}
public class Demo {
    public static void main(String args[]){
     B objB = new B(5);
    }
    
}

```









