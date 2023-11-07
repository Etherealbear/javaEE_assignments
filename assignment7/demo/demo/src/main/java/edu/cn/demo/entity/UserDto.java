package edu.cn.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDto extends User{
    private List<Role> roles = new java.util.ArrayList<>();
}