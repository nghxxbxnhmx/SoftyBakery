package com.poly.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


import com.poly.models.SpecialOption;

public interface SpecialOptionDAO extends JpaRepository<SpecialOption,Integer> {

	@Override
    @Query(value = "SELECT s FROM SpecialOption s")
   
   List<SpecialOption> findAll();


// @Query("SELECT so.option FROM SpecialOption so WHERE so.product.id = :productId")
// List<String> findOptionsByProductId(Integer productId);

@Query("SELECT so.option FROM SpecialOption so WHERE so.product.id = :productId")
List<String> findOptionsByProductId(Integer productId);


}
