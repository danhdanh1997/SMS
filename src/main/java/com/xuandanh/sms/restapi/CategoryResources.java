package com.xuandanh.sms.restapi;

import com.xuandanh.sms.domain.Category;
import com.xuandanh.sms.dto.CategoryDTO;
import com.xuandanh.sms.exception.ResourceNotFoundException;
import com.xuandanh.sms.mapper.CategoryMapper;
import com.xuandanh.sms.repository.CategoryRepository;
import com.xuandanh.sms.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@SecurityRequirement(name = "xuandanh")
public class CategoryResources {
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final Logger log = LoggerFactory.getLogger(CategoryResources.class);

    @GetMapping("/category/findOne/{categoriesId}")
    public ResponseEntity<?>getOne(@PathVariable(value = "categoriesId")int categoriesId){
        return ResponseEntity.ok(categoryService.findOne(categoriesId));
    }

    @GetMapping("/category/findAll")
    public ResponseEntity<?>getAll(){
        Map<String,Boolean>response = new HashMap<>();
        Optional<List<CategoryDTO>>categoryDTOList = categoryService.findAll();
        int size = 0;
        size = categoryDTOList.map(List::size).orElse(0);
        if (size == 0){
            log.error("List category is empty");
            response.put("List category is empty",Boolean.FALSE);
            return ResponseEntity.ok(response);
        }
        log.info("Show list of category +:"+categoryDTOList);
        return ResponseEntity.ok(categoryDTOList);
    }

    @PostMapping("/category/create")
    public ResponseEntity<?>create(@Valid @RequestBody Category category){
        return ResponseEntity.ok(categoryService.createCategory(category));
    }

    @PutMapping("/category/update/{categoriesId}")
    public ResponseEntity<?>update(@PathVariable(value = "categoriesId")int categoriesId,@Valid @RequestBody Category category){
        Map<String,Boolean>response = new HashMap<>();
        Optional<CategoryDTO>categoryDTO = categoryService.findOne(categoriesId);
        if (categoryDTO.stream().findFirst().isEmpty()){
            log.error("category with id:"+categoriesId+" not exist");
            response.put("category with id:"+categoriesId+" not exist",Boolean.FALSE);
            return ResponseEntity.ok(response);
        }
        log.info("update info category with id:"+categoriesId+" was successfully");
        return ResponseEntity.ok(categoryService.updateCategory(category));
    }

    @DeleteMapping("/category/delete/{categoriesId}")
    public ResponseEntity<?>delete(@PathVariable(value = "categoriesId")int categoriesId){
        Map<String,Boolean>response = new HashMap<>();
        return categoryRepository.findById(categoriesId)
                .map(category -> {
                    log.info("deleted category with id:"+categoriesId+" was successfully");
                    response.put("deleted category with id:"+categoriesId+" was successfully",Boolean.TRUE);
                    categoryRepository.delete(category);
                    return ResponseEntity.ok(response);
                }).orElseThrow(()-> new ResourceNotFoundException("category with id:"+categoriesId+" not exist"));
    }

    @GetMapping("/category/findCategoryByCategoriesName")
    public ResponseEntity<?>getCategoryByCategoriesName(@RequestParam(value = "page")int page,
                                                      @RequestParam(value = "size")int size,
                                                      @RequestParam(required = false)String categoriesName){
        try {
                List<CategoryDTO>categoryDTOList = new ArrayList<CategoryDTO>();
                Pageable pageable = PageRequest.of(page,size);
                Page<Category>categoryPage;
                if (categoriesName == null)
                    categoryPage = categoryRepository.findAll(pageable);
                else
                    categoryPage = categoryRepository.findCategoryByCategoriesName(pageable,categoriesName);
                categoryDTOList = categoryMapper.categoryToCategoryDTO(categoryPage.getContent());
                Map<String, Object> response = new HashMap<>();
                response.put("categoryDTOList", categoryDTOList);
                response.put("currentPage", categoryPage.getNumber());
                response.put("totalItems", categoryPage.getTotalElements());
                response.put("totalPages", categoryPage.getTotalPages());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
