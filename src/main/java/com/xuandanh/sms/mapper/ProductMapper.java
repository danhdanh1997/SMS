package com.xuandanh.sms.mapper;

import com.xuandanh.sms.domain.Product;
import com.xuandanh.sms.domain.Store;
import com.xuandanh.sms.dto.ProductDTO;
import com.xuandanh.sms.dto.StoreDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ProductMapper {
    public List<ProductDTO> productToProductDTO(List<Product> productList) {
        return productList.stream().filter(Objects::nonNull).map(this::productToProductDTO).collect(Collectors.toList());
    }

    public ProductDTO productToProductDTO(Product product){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(product,ProductDTO.class);
    }

    public List<Product> productDTOToProduct(List<ProductDTO> productDTOList) {
        return productDTOList.stream().filter(Objects::nonNull).map(this::productDTOToProduct).collect(Collectors.toList());
    }

    public Product productDTOToProduct(ProductDTO productDTO){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(productDTO , Product.class);
    }
}
