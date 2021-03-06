package com.xuandanh.sms.repository;

import com.xuandanh.sms.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
    @Query("select cate from Category cate where cate.categoriesName like %:categoriesName%")
    Page<Category>findCategoryByCategoriesName(Pageable pageable,String categoriesName);
}
