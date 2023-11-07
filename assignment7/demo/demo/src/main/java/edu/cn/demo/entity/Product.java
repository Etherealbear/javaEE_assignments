package edu.cn.demo.entity;

import java.math.BigDecimal;
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
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String category;

    private String description;

    private String name;

    private Float price;

    @TableField(value = "product_type")
    private String productType;

    @TableField(value = "stock_quantity")
    private Float stockQuantity;
}
