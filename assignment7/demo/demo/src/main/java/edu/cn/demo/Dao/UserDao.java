package edu.cn.demo.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.cn.demo.entity.User;
import edu.cn.demo.entity.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao extends BaseMapper<User> {
    /**
     * 查询用户及其角色
     * @return
     */
    @Select("select * from user where id =  #{id}")
    @Results({@Result(id = true, property = "id", column = "id")})
    public UserDto getUser(String id);
}
