package edu.cn.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author lirongjun
 * @since 2023-10-25
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Supplier implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id = 0L;

    private String email;

    private String name;

    private String phone;
}