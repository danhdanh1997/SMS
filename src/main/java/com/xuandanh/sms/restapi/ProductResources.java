package com.xuandanh.sms.restapi;

import com.xuandanh.sms.domain.Country;
import com.xuandanh.sms.domain.Product;
import com.xuandanh.sms.dto.CategoryDTO;
import com.xuandanh.sms.dto.CountryDTO;
import com.xuandanh.sms.dto.ProductDTO;
import com.xuandanh.sms.dto.SupplierDTO;
import com.xuandanh.sms.mapper.ProductMapper;
import com.xuandanh.sms.repository.ProductRespository;
import com.xuandanh.sms.service.CategoryService;
import com.xuandanh.sms.service.ProductService;
import com.xuandanh.sms.service.SupplierService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.util.*;

@SecurityRequirement(name = "Authorization")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductResources {
    private final ProductRespository productRespository;
    private final ProductService productService;
    private final SupplierService supplierService;
    private final CategoryService categoryService;
    private final ProductMapper productMapper;
    private final Logger log = LoggerFactory.getLogger(ProductResources.class);

    @GetMapping("/supplier/{suppliersId}/product/{productId}/fineOne")
    public ResponseEntity<?>getOne(@PathVariable(value = "suppliersId")int suppliersId,
                                   @PathVariable(value = "productId")String productId){
        Optional<ProductDTO>productDTO = productService.findOne(suppliersId,productId);
        Map<String,Boolean>response = new HashMap<>();
        if (productDTO.isEmpty()){
            response.put("supplier with id:"+suppliersId+" not exist"+" && "+ "product with id:"+productId+" not exist",Boolean.FALSE);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(productDTO);
    }

    @GetMapping("/product/findAll")
    public ResponseEntity<?>getAll(){
        Optional<List<ProductDTO>>productDTOList = productService.findAll();
        Map<String,Boolean>response = new HashMap<>();
        if (productDTOList.isEmpty()){
            response.put("List product is empty",Boolean.FALSE);
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(productDTOList);
    }

    @PostMapping("/supplier/{suppliersId}/category/{categoriesId}/product/create")
    public ResponseEntity<?>create(@PathVariable(value = "suppliersId")int suppliersId,
                                   @PathVariable(value = "categoriesId")int categoriesId,
                                   @Valid @RequestBody Product product){
        Optional<ProductDTO>productDTO = productService.createProduct(suppliersId,categoriesId,product);
        Map<String,Boolean>response = new HashMap<>();
        if (productDTO.isEmpty()){
            response.put("Object product invalid",Boolean.FALSE);
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(productDTO);
    }

    @PutMapping("/supplier/{suppliersId}/category/{categoriesId}/product/{productId}/update")
    public ResponseEntity<?>update(@PathVariable(value = "suppliersId")int suppliersId,
                                   @PathVariable(value = "categoriesId")int categoriesId,
                                   @PathVariable(value = "productId")String productId,
                                   @Valid @RequestBody Product product){
        Map<String,Boolean>response = new HashMap<>();
        Optional<ProductDTO>productDTO = productService.findOne(suppliersId,productId);
        if (productDTO.isEmpty()){
            log.error("product with id:"+productId+" not exist");
            response.put("product with id:"+productId+" not exist",Boolean.FALSE);
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
        log.info("product with id:"+productId+" was updated successfully");
        return ResponseEntity.ok(productService.updateProduct(suppliersId,categoriesId,product));
    }

    @DeleteMapping("/product/{productId}delete")
    public ResponseEntity<?>delete(@PathVariable(value = "productId")String productId){
        Optional<Product>product1 = productRespository.findById(productId);
        Map<String,Boolean>response = new HashMap<>();
        if (product1.isEmpty()){
            log.error(" product with id:"+productId+" not exist");
            response.put(" product with id:"+productId+" not exist",Boolean.FALSE);
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
        log.info("product with id:"+productId+" was delete successfully");
        response.put("product with id:"+productId+" was delete successfully",Boolean.TRUE);
        productRespository.delete(product1.get());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/product/getAllByProductName")
    public ResponseEntity<?>getAllByProductName(@RequestParam(value = "page")int page,
                                                @RequestParam(value = "size")int size,
                                                @RequestParam(required = false)String productName){
        try {
            List<ProductDTO>productDTOList = new ArrayList<ProductDTO>();
            Pageable pageable = PageRequest.of(page,size);
            Page<Product> productPage;
            if (productName == null)
                productPage = productRespository.findAll(pageable);
            else
                productPage = productRespository.findAllByProductName(pageable,productName);
            productDTOList = productMapper.productToProductDTO(productPage.getContent());
            Map<String, Object> response = new HashMap<>();
            response.put("productDTOList", productDTOList);
            response.put("currentPage", productPage.getNumber());
            response.put("totalItems", productPage.getTotalElements());
            response.put("totalPages", productPage.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
