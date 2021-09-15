package com.xuandanh.sms.service;

import com.xuandanh.sms.domain.Product;
import com.xuandanh.sms.dto.CategoryDTO;
import com.xuandanh.sms.dto.ProductDTO;
import com.xuandanh.sms.dto.SupplierDTO;
import com.xuandanh.sms.mapper.ProductMapper;
import com.xuandanh.sms.repository.ProductRespository;
import com.xuandanh.sms.restapi.CategoryResources;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRespository productRespository;
    private final ProductMapper productMapper;
    private final SupplierService supplierService;
    private final CategoryService categoryService;
    private final Logger log = LoggerFactory.getLogger(ProductService.class);

    public Optional<ProductDTO>findOne(int suppliersId, String productId){
        Optional<SupplierDTO>supplierDTO = supplierService.findOne(suppliersId);
        if (supplierDTO.isPresent()){
            Optional<Product>product = productRespository.findById(productId);
            if (product.isEmpty()){
                log.error("product with id:"+productId+" not exist");
                return Optional.empty();
            }
            log.info("information of product with id:"+productMapper.productToProductDTO(product.get()));
            return Optional.ofNullable(productMapper.productToProductDTO(product.get()));
        }
        return Optional.empty();
    }

    public Optional<List<ProductDTO>>findAll(){
        Optional<List<ProductDTO>>productDTOList = Optional.ofNullable(productMapper.productToProductDTO(productRespository.findAll()));
        int sizeProduct = productDTOList.map(List::size).orElse(0);
        if (sizeProduct == 0){
            log.error("List product is empty");
            return Optional.empty();
        }
        return productDTOList;
    }

    public Optional<ProductDTO>createProduct(int suppliersId,int categoriesId,Product product){
        Optional<SupplierDTO>supplierDTO = supplierService.findOne(suppliersId);
        Optional<CategoryDTO>categoryDTO = categoryService.findOne(categoriesId);
        if (supplierDTO.isEmpty() || categoryDTO.isEmpty()){
            log.error("supplier with id:"+suppliersId+" not exist"+" || "+"category with id:"+categoriesId+" not exist");
            return Optional.empty();
        }
        return Optional.ofNullable(productMapper.productToProductDTO(productRespository.save(product)));
    }

    public Optional<ProductDTO>updateProduct(int suppliersId,int categoriesId,Product product){
        Optional<SupplierDTO>supplierDTO = supplierService.findOne(suppliersId);
        Optional<CategoryDTO>categoryDTO = categoryService.findOne(categoriesId);
        Optional<Product>product1 = productRespository.findById(product.getProductId());
        if (supplierDTO.isEmpty() || categoryDTO.isEmpty()){
            log.error("supplier with id:"+suppliersId+" not exist"+" || "+"category with id:"+categoriesId+" not exist");
            return Optional.empty();
        }
        return Optional.ofNullable(product1)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(product2 -> {
                    product2.setProductName(product.getProductName());
                    product2.setCategoriesId(product.getCategoriesId());
                    product2.setImageUrl(product.getImageUrl());
                    product2.setSuppliersId(product.getSuppliersId());
                    product2.setUnitPrice(product.getUnitPrice());
                    product2.setUnitsOnOrder(product.getUnitsOnOrder());
                    return productMapper.productToProductDTO(productRespository.save(product2));
                });
    }
}
