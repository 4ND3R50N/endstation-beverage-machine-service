package com.endstation.beveragemachine.service.core.usecase.drinks;

import com.endstation.beveragemachine.service.dataprovider.db.drinks.DrinkEntity;
import com.endstation.beveragemachine.service.dataprovider.db.drinks.DrinkIngredientConceptionEntity;
import com.endstation.beveragemachine.service.dataprovider.db.ingredients.IngredientEntity;
import com.endstation.beveragemachine.service.model.DrinkData;
import com.endstation.beveragemachine.service.model.DrinkIngredient;
import lombok.RequiredArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DrinkMapper {

    @PersistenceContext
    private final EntityManager entityManager;

    public DrinkEntity map(DrinkData drinkData) {

        DrinkEntity entity = new DrinkEntity(drinkData.getVisitorId(), drinkData.getName(), drinkData.getIsBasicDrink());

        drinkData.getIngredients()
                .forEach(drinkIngredient -> entity.addConception(DrinkIngredientConceptionEntity.builder()
                        .ingredient(entityManager.find(IngredientEntity.class, drinkIngredient.getIngredientId()))
                        .amount(drinkIngredient.getAmount().intValue())
                        .quantityType(drinkIngredient.getQuantityType())
                        .build()));

        return entity;
    }

    public DrinkEntity map(Long drinkId, DrinkData drinkData) {

        return new DrinkEntity(drinkId, drinkData.getVisitorId(), drinkData.getIngredients().stream()
                .map(drinkIngredient -> DrinkIngredientConceptionEntity.builder()
                        .ingredient(entityManager.getReference(IngredientEntity.class, drinkIngredient.getIngredientId()))
                        .amount(drinkIngredient.getAmount().intValue())
                        .quantityType(drinkIngredient.getQuantityType())
                        .build())
                .collect(Collectors.toList()), drinkData.getName(), drinkData.getIsBasicDrink());
    }

    public DrinkData map(DrinkEntity drinkEntity) {
        return DrinkData.builder()
                .drinkId(JsonNullable.of(drinkEntity.getDrinkId()))
                .name(drinkEntity.getName())
                .isBasicDrink(drinkEntity.isBasicDrink())
                .visitorId(drinkEntity.getVisitorId())
                .ingredients(drinkEntity.getIngredientConceptions().stream()
                        .map(drinkIngredientConception -> DrinkIngredient.builder()
                                .ingredientId(drinkIngredientConception.getIngredient().getIngredientId())
                                .quantityType(drinkIngredientConception.getQuantityType())
                                .amount(BigDecimal.valueOf(drinkIngredientConception.getAmount()))
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
