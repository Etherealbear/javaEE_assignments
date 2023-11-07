package edu.cn.demo.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductDTO extends Product{
    //保存供应商
    private List<Supplier> supplierList=new ArrayList<>();
}
