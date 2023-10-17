package edu.cn.demo.service;

import edu.cn.demo.entity.Todoitem;

import java.util.*;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class TodoService {
    //创建一个map模拟Todo信息的存储
    private Map<Long, Todoitem> todos = Collections.synchronizedMap(new HashMap<Long,Todoitem>());

    //增加
    public Todoitem addTodo(Todoitem todo){
        todos.put(todo.getId(),todo);
        return todo;
    }

    //删除
    public  void deleteTodo(long id){
        todos.remove(id);
    }

    //修改
    public void updateTodo(long id, Todoitem todo){
        Todoitem updateTodo = todos.get(id);
        updateTodo.setName(todo.getName());
        updateTodo.setComplete(todo.isComplete());
        todos.put(id,updateTodo);
    }

    //查询
    public List<Todoitem> findTodos(String name, Boolean deleted) {  // 此处isDeleted的类型是Boolean而不是boolean
        List<Todoitem> result = new ArrayList<>();
        for (Todoitem todo: todos.values()) {
            if (name != null && !todo.getName().contains(name))  {
                continue;
            }
            if (deleted != null && !deleted.equals(todo.isComplete()))  {
                continue;
            }
            result.add(todo);
        }
        return result;
    }

    public Todoitem getTodo(long id){
        return todos.get(id);
    }
}
