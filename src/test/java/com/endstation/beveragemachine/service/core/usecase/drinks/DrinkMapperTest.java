package com.endstation.beveragemachine.service.core.usecase.drinks;

import com.endstation.beveragemachine.service.dataprovider.db.drinks.DrinkEntity;
import com.endstation.beveragemachine.service.dataprovider.db.drinks.DrinkIngredientConceptionEntity;
import com.endstation.beveragemachine.service.dataprovider.db.ingredients.IngredientEntity;
import com.endstation.beveragemachine.service.model.DrinkData;
import com.endstation.beveragemachine.service.model.DrinkIngredient;
import com.endstation.beveragemachine.service.model.DrinkIngredient.QuantityTypeEnum;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DrinkMapperTest {

    private static final long INGREDIENT_ID = 1L;
    private static final long DRINK_ID = 1L;

    @Mock
    private final EntityManager entityManager = mock(EntityManager.class);

    DrinkMapper drinkMapper = new DrinkMapper(entityManager);

    @Test
    void mapDrinkDataToEntity() {
        // given
        DrinkData drinkData = DrinkData.builder()
                .name("Orange juice")
                .isBasicDrink(true)
                .visitorId("Lars P.")
                .ingredients(List.of(DrinkIngredient.builder()
                        .ingredientId(1L)
                        .amount(BigDecimal.valueOf(1))
                        .quantityType(QuantityTypeEnum.CL)
                        .build()))
                .build();
        IngredientEntity ingredientEntity = IngredientEntity.builder()
                .ingredientId(1L)
                .build();
        // when
        when(entityManager.find(IngredientEntity.class, 1L)).thenReturn(ingredientEntity);
        DrinkEntity result = drinkMapper.map(drinkData);

        // assert
        assertEquals(result.getName(), drinkData.getName());
        assertEquals(result.isBasicDrink(), drinkData.getIsBasicDrink());
        assertEquals(result.getVisitorId(), drinkData.getVisitorId());

        DrinkIngredientConceptionEntity drinkIngredientConceptionEntity = result.getIngredientConceptions().get(0);
        DrinkIngredient drinkIngredient = drinkData.getIngredients().get(0);
        assertEquals(drinkIngredientConceptionEntity.getAmount(),
                drinkIngredient.getAmount().intValue());
        assertEquals(drinkIngredientConceptionEntity.getQuantityType(),
                drinkIngredient.getQuantityType());
        assertEquals(drinkIngredientConceptionEntity.getIngredient().getIngredientId(),
                drinkIngredient.getIngredientId());
    }

    @Test
    void mapDrinkEntityToData() {
        // given
        DrinkEntity drinkEntity = new DrinkEntity("Lars P.", "Orange Juice", true);
        drinkEntity.setDrinkId(DRINK_ID);
        drinkEntity.addConception(DrinkIngredientConceptionEntity.builder()
                        .amount(2)
                        .quantityType(QuantityTypeEnum.CL)
                .ingredient(IngredientEntity.builder()
                        .ingredientId(INGREDIENT_ID)
                        .build())
                .build());

        // when
        DrinkData result = drinkMapper.map(drinkEntity);

        // verify
        assertEquals(result.getName(), drinkEntity.getName());
        assertEquals(result.getIsBasicDrink(), drinkEntity.isBasicDrink());
        assertEquals(result.getVisitorId(), drinkEntity.getVisitorId());
        assertEquals(result.getDrinkId().get(), DRINK_ID);
        DrinkIngredient drinkIngredientResult = result.getIngredients().get(0);
        DrinkIngredientConceptionEntity drinkIngredientConceptionEntity = drinkEntity.getIngredientConceptions().get(0);
        assertEquals(drinkIngredientResult.getIngredientId(),
                drinkIngredientConceptionEntity.getIngredient().getIngredientId());
        assertEquals(drinkIngredientResult.getAmount().intValue(),
                drinkIngredientConceptionEntity.getAmount());
        assertEquals(drinkIngredientResult.getQuantityType(),
                drinkIngredientConceptionEntity.getQuantityType());

    }

}