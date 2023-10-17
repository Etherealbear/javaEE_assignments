package edu.cn.demo.controller;


import edu.cn.demo.entity.Todoitem;
import edu.cn.demo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("todos")
public class TodoController {

    @Autowired
    TodoService todoService;

    @GetMapping("/{id}")
    public ResponseEntity<Todoitem> getTodo(@PathVariable long id){
        Todoitem result = todoService.getTodo(id);
        if (result ==null){
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.ok(result);
        }
    };

    @GetMapping("")
    public ResponseEntity<List<Todoitem>> findTodos(String name, Boolean complete){
        List<Todoitem> result = todoService.findTodos(name,complete);
        return ResponseEntity.ok(result);
    }

    @PostMapping("")
    public ResponseEntity<Todoitem> addTodo(@RequestBody Todoitem todo){
        Todoitem result = todoService.addTodo(todo);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTodo(@PathVariable long id, @RequestBody Todoitem todo){
        todoService.updateTodo(id,todo);
        return  ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable long id){
        todoService.deleteTodo(id);
        return  ResponseEntity.ok().build();
    }
}

