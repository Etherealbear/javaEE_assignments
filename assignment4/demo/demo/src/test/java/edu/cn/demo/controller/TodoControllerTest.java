package edu.cn.demo.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import edu.cn.demo.entity.Todoitem;
import edu.cn.demo.service.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoService todoService;

    @Test
    public void testGet() throws Exception {
        //使用MockMvc执行get请求
        this.mockMvc.perform(get("/todos/{id}",1))
                .andDo(print()) //控制台打印返回信息
                .andExpect(status().isNoContent()); //验证响应代码是否为204

        todoService.addTodo(new Todoitem(1, "看书",true));

        this.mockMvc.perform(get("/todos/{id}",1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("看书"));  // 响应的JSON中包含一个"name"字段，且值为"薯条"
        //恢复测试环境
        todoService.deleteTodo(1);
    }


    @Test
    public void testPost() throws Exception {
        Todoitem todo = new Todoitem(1, "看书", false);
        String content = new ObjectMapper().writeValueAsString(todo);  // 使用ObjectMapper将该商品对象转换为JSON字符串
        // 使用MockMvc执行POST请求，请求路径是"/commodities"，请求的内容类型为JSON。请求的内容是前面创建的JSON字符串
        this.mockMvc.perform(post("/todos")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());  // 验证响应的JSON中是否包含一个名为"name"的字段
        todoService.deleteTodo(1);
    }
}