package edu.cn.demo.entity.query;

import edu.cn.demo.entity.Product;
import lombok.Data;

@Data
public class ProductQuery extends Product {
    //Product查询模型(查询上限）
    private float price2;
    private float QuQuantity2;
}
