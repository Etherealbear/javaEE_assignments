package edu.cn.demo.service;

import edu.cn.demo.aspect.StopWatchAspect;
import edu.cn.demo.entity.Todoitem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class TodoServiceTest {
    private TodoService todoService;

    private StopWatchAspect stopWatchAspect = new StopWatchAspect();

    @BeforeEach
    public void setup(){
        todoService = new TodoService();
    }

    @Test
    public void testAddTodo(){
        Todoitem todo = new Todoitem(1,"刷代码题", true);
        Todoitem result = todoService.addTodo(todo);
        assertEquals(todo,result);
    }

    @Test
    public void testDeleteTodo(){
        Todoitem todo = new Todoitem(2,"跑步", false);
        todoService.addTodo(todo);

        todoService.deleteTodo(2);
        Todoitem result = todoService.getTodo(2);
        assertNull(result);
    }

    @Test
    public void testUpdateTodo(){
        Todoitem todo = new Todoitem(2, "跑步",false);
        todoService.addTodo(todo);

        //更新操作
        Todoitem update = new Todoitem(2, "学习",true);
        todoService.updateTodo(2,update);

        //测试结果
        Todoitem result = todoService.getTodo(2);
        assertEquals(update,result);

    }

    @Test
    public void testFindTodo_postive(){
        Todoitem todo = new Todoitem(2, "跑步",false);
        Todoitem todo1 = new Todoitem(3, "学习",true);
        todoService.addTodo(todo);
        todoService.addTodo(todo1);
        //查找结果

       List<Todoitem>  result = todoService.findTodos("跑步",false);
        assertEquals(1,result.size());
        assertEquals(todo,result.get(0));
    }

    @Test
    public void testFindTodo_negative(){
        Todoitem todo = new Todoitem(2, "学习",false);
        Todoitem todo1 = new Todoitem(3, "跑步",true);
        todoService.addTodo(todo);
        todoService.addTodo(todo1);
        //查找结果

        List<Todoitem>  result = todoService.findTodos("跑步",false);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetTodo_positive(){
        Todoitem todo = new Todoitem(4, "洗衣服",true);
        todoService.addTodo(todo);

        Todoitem result = todoService.getTodo(4);
        assertEquals(todo,result);
        System.out.println(result);
    }

    @Test
    public void testGetTodo_negative(){

        Todoitem result = todoService.getTodo(4);
        assertNull(result);
    }

    /**
     * 测试Spring AOP编写API监控功能
     */
    @Test
    public void testAspect(){
        Todoitem todo = new Todoitem(6, "做饭",true);
        todoService.addTodo(todo);
        todoService.getTodo(1);
        todoService.getTodo(2);
        todoService.addTodo(todo);
        todoService.deleteTodo(6);
        stopWatchAspect.getMetric().forEach((k,v)-> {System.out.println(k  +"= "+v);});

    }


}
