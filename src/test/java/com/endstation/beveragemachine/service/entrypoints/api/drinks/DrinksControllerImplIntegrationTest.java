package com.endstation.beveragemachine.service.entrypoints.api.drinks;

import com.endstation.beveragemachine.service.dataprovider.db.drinks.DrinkIngredientConceptionRepository;
import com.endstation.beveragemachine.service.dataprovider.db.drinks.DrinkRepository;
import com.endstation.beveragemachine.service.dataprovider.db.ingredients.IngredientsRepository;
import com.endstation.beveragemachine.service.model.*;
import com.endstation.beveragemachine.service.model.DrinkIngredient.QuantityTypeEnum;
import com.endstation.beveragemachine.service.model.IngredientData.LiquidTypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class DrinksControllerImplIntegrationTest {

    @Autowired
    DrinkIngredientConceptionRepository drinkIngredientConceptionRepository;

    @Autowired
    IngredientsRepository ingredientsRepository;

    @Autowired
    DrinkRepository drinkRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private DrinkData drink;
    private Long orangeJuiceId;
    private IngredientData orangeJuice;
    private Long drinkId;

    @BeforeEach
    void setup() {
        drinkRepository.deleteAll();
        drinkIngredientConceptionRepository.deleteAll();
        ingredientsRepository.deleteAll();
    }

    @Test
    void shouldCreateAndDeleteDrink() {
        createDrink();

        // delete drink by id
        ResponseEntity<Void> deleteResult = testRestTemplate.exchange("/api/v1/drinks/" + drinkId, HttpMethod.DELETE, new HttpEntity<>(null), Void.class);
        assertEquals(deleteResult.getStatusCode(), HttpStatus.OK);

        // assert
        assertEquals(2, (long) ingredientsRepository.findAll().size());
        assertEquals(0, (long) drinkIngredientConceptionRepository.findAll().size());
        assertEquals(0, (long) drinkRepository.findAll().size());
    }

    @Test
    void shouldCreateDrinkAndDeleteIngredient() {
        createDrink();

        // remove ingredient
        ResponseEntity<IngredientResponse> resultFour = testRestTemplate.exchange("/api/v1/drinks/ingredient/" + orangeJuiceId, HttpMethod.DELETE, new HttpEntity<>(orangeJuice), IngredientResponse.class);
        assertEquals(resultFour.getStatusCode(), HttpStatus.OK);
        assertEquals((long) ingredientsRepository.findAll().size(), 1);
        assertEquals((long) drinkIngredientConceptionRepository.findAll().size(), 1);
        assertEquals((long) drinkRepository.findAll().size(), 1);
    }

    @Test
    public void shouldCreateAndModifyDrink() {
        createDrink();

        // edit drink by removing one conception
        drink.setIngredients(List.of(drink.getIngredients().get(0)));
        ResponseEntity<Void> updateResult = testRestTemplate.exchange("/api/v1/drinks/" + drinkId, HttpMethod.POST, new HttpEntity<>(drink), Void.class);
        assertEquals(updateResult.getStatusCode(), HttpStatus.OK);

        // assert db
        assertEquals((long) ingredientsRepository.findAll().size(), 2);
        assertEquals((long) drinkIngredientConceptionRepository.findAll().size(), 1);
        assertEquals((long) drinkRepository.findAll().size(), 1);

    }


    private void createDrink() {
        // given
        orangeJuice = IngredientData.builder()
                .name("Orange Juice")
                .liquidType(LiquidTypeEnum.SOFTDRINK)
                .build();
        IngredientData vodka = IngredientData.builder()
                .name("Vodka")
                .liquidType(LiquidTypeEnum.ALCOHOL)
                .build();
        // when
        // create two Ingredients
        ResponseEntity<IngredientResponse> resultOne = testRestTemplate.exchange("/api/v1/drinks/ingredient", HttpMethod.POST, new HttpEntity<>(orangeJuice), IngredientResponse.class);
        ResponseEntity<IngredientResponse> resultTwo = testRestTemplate.exchange("/api/v1/drinks/ingredient", HttpMethod.POST, new HttpEntity<>(vodka), IngredientResponse.class);

        assertEquals(resultOne.getStatusCode(), HttpStatus.CREATED);
        assertEquals(resultTwo.getStatusCode(), HttpStatus.CREATED);

        orangeJuiceId = Objects.requireNonNull(resultOne.getBody()).getIngredientId();
        Long vodkaId = Objects.requireNonNull(resultTwo.getBody()).getIngredientId();

        drink = DrinkData.builder()
                .name("Vodka-O")
                .visitorId("Lars P.")
                .isBasicDrink(true)
                .ingredients(List.of(DrinkIngredient.builder()
                                .ingredientId(vodkaId)
                                .amount(BigDecimal.valueOf(1))
                                .quantityType(QuantityTypeEnum.CL)
                                .build(),
                        DrinkIngredient.builder()
                                .ingredientId(orangeJuiceId)
                                .amount(BigDecimal.valueOf(2))
                                .quantityType(QuantityTypeEnum.CL)
                                .build()))
                .build();
        // create drink with Ingredients
        ResponseEntity<DrinkDataResponse> resultThree = testRestTemplate.exchange("/api/v1/drinks", HttpMethod.POST, new HttpEntity<>(drink), DrinkDataResponse.class);
        assertEquals(resultThree.getStatusCode(), HttpStatus.CREATED);

        // check database
        assertEquals((long) ingredientsRepository.findAll().size(), 2);
        assertEquals((long) drinkIngredientConceptionRepository.findAll().size(), 2);
        assertEquals((long) drinkRepository.findAll().size(), 1);

        drinkId = Objects.requireNonNull(resultThree.getBody()).getDrinkId();
    }
}