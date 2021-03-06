package com.endstation.beveragemachine.service.dataprovider.db.ingredients;

import com.endstation.beveragemachine.service.model.IngredientData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
class IngredientsRepositoryTest {

    @Autowired
    private IngredientsRepository ingredientsRepository;

    @BeforeEach
    void setup() {
        ingredientsRepository.deleteAll();
    }

    @Test
    void injectedComponentsAreNotNull() {
        // given
        assertThat(ingredientsRepository).isNotNull();

        IngredientEntity ingredientEntity = IngredientEntity.builder()
                .name("orange juice")
                .liquidType(IngredientData.LiquidTypeEnum.SOFTDRINK)
                .build();

        // when
        Long result = ingredientsRepository.save(ingredientEntity).getIngredientId();

        // then
        assertThat(result).isNotNull();
        assertThat(ingredientsRepository.count()).isEqualTo(1);

    }

}