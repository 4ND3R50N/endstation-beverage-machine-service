package com.endstation.beveragemachine.service.dataprovider.db.ingredients;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientsRepository extends JpaRepository<IngredientsEntity, Long> {
    boolean existsByName(String name);
}
