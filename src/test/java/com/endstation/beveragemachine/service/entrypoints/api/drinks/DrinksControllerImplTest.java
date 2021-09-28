package com.endstation.beveragemachine.service.entrypoints.api.drinks;

import com.endstation.beveragemachine.service.core.usecase.drinks.DrinkService;
import com.endstation.beveragemachine.service.core.usecase.ingredients.IngredientsService;
import com.endstation.beveragemachine.service.model.DrinkData;
import com.endstation.beveragemachine.service.model.IngredientData;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class DrinksControllerImplTest {
    @Mock
    DrinkData drinkDataMock = mock(DrinkData.class);

    @Mock
    IngredientData ingredientData = mock(IngredientData.class);

    @Mock
    private final DrinkService drinkService = mock(DrinkService.class);

    @Mock
    private final IngredientsService ingredientsService = mock(IngredientsService.class);

    DrinksControllerImpl controller = new DrinksControllerImpl(drinkService, ingredientsService);

    @Test
    void shouldExecuteCreateIngredientServiceFunction() {
        // when
        controller.createIngredient(ingredientData);
        // verify
        verify(ingredientsService, times(1)).createIngredient(ingredientData);
    }

    @Test
    void shouldExecuteGetIngredientServiceFunction() {
        // when
        controller.getIngredients();
        // verify
        verify(ingredientsService, times(1)).getIngredients();
    }

    @Test
    void shouldExecuteGetIngredientByIdServiceFunction() {
        // when
        controller.getIngredient(12L);
        // verify
        verify(ingredientsService, times(1)).getIngredient(12L);
    }

    @Test
    void shouldExecuteDeleteIngredientByIdServiceFunction() {
        // when
        controller.deleteIngredient(12L);
        // verify
        verify(ingredientsService, times(1)).deleteIngredient(12L);
    }

    @Test
    void shouldExecuteUpdateIngredientserviceFunction() {
        IngredientData ingredientData = mock(IngredientData.class);
        // when
        controller.updateIngredient(12L, ingredientData);
        // verify
        verify(ingredientsService, times(1)).updateIngredient(12L, ingredientData);
    }

    @Test
    void shouldExecuteCreateDrinkServiceFunction() {
        DrinkData drinkData = mock(DrinkData.class);
        // when
        controller.createDrink(drinkData);
        // verify
        verify(drinkService, times(1)).createDrink(drinkData);
    }

    @Test
    void shouldExecuteGetDrinksServiceFunction() {
        // when
        controller.getDrinks();
        // verify
        verify(drinkService, times(1)).getDrinks();
    }

    @Test
    void shouldExecuteGetDrinkByIdServiceFunction() {
        Long id = 12L;
        // when
        controller.getDrink(id);
        // verify
        verify(drinkService, times(1)).getDrinkById(id);
    }

    @Test
    void shouldExecuteUpdateDrinkByIdServiceFunction() {
        Long id = 12L;
        // when
        controller.updateDrink(id, drinkDataMock);
        // verify
        verify(drinkService, times(1)).updateDrink(id, drinkDataMock);
    }

    @Test
    void shouldExecuteDeleteDrinkByIdServiceFunction() {
        Long id = 12L;
        // when
        controller.deleteDrink(id);
        // verify
        verify(drinkService, times(1)).deleteDrinkBy(id);
    }
}