package edu.cn.demo.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author lirongjun
 * @since 2023-10-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "name")
    private String name;

    @TableField(value = "price")
    private float price;

    @TableField(value = "Quantity")
    private float Quantity;

    @TableField(value = "category")
    private String category;

    @TableField(value = "product_Type")
    private String product_Type;

    @TableField(value = "description")
    private String description;


}
