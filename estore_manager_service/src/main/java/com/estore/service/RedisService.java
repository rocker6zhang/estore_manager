package com.estore.service;

import java.util.List;


import com.estore.bean.Product;
import com.estore.utils.JsonMsg;


public interface RedisService {


	JsonMsg updateIndexElementAll();


	JsonMsg updateProductByKey(String updateKey);


	JsonMsg updateProductCategory(String updateKey);


	JsonMsg updateProductAll();


	JsonMsg updateProductCategoryAll();

}