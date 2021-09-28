package com.endstation.beveragemachine.service.core.usecase.machine;

import com.endstation.beveragemachine.service.dataprovider.db.ingredients.IngredientEntity;
import com.endstation.beveragemachine.service.dataprovider.db.machine.BottleSlotEntity;
import com.endstation.beveragemachine.service.dataprovider.db.machine.BottleSlotsRepository;
import com.endstation.beveragemachine.service.model.BottleSlots;
import com.endstation.beveragemachine.service.model.MachineIngredientsRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MachineServiceTest {

    @Mock
    private final IngredientEntity ingredientEntityMock = mock(IngredientEntity.class);


    @Mock
    private final BottleSlotEntity bottleSlotEntityOne = mock(BottleSlotEntity.class);

    @Mock
    private final BottleSlotEntity bottleSlotEntityTwo = mock(BottleSlotEntity.class);

    @Mock
    private final BottleSlotsRepository bottleSlotsRepository = mock(BottleSlotsRepository.class);

    @Mock
    private final MachineIngredientsRequest machineIngredientsRequest = mock(MachineIngredientsRequest.class);


    @Mock
    private final EntityManager entityManager = mock(EntityManager.class);

    @Mock
    IngredientEntity ingredientMock = mock(IngredientEntity.class);

    @Mock
    IngredientEntity ingredientMockTwo = mock(IngredientEntity.class);

    private MachineService machineService = new MachineService(bottleSlotsRepository, entityManager);


    @Test
    void shouldReturnSlots() {
        // when
        when(bottleSlotsRepository.findAll()).thenReturn(List.of(bottleSlotEntityOne, bottleSlotEntityTwo));
        when(bottleSlotEntityOne.getIngredient()).thenReturn(ingredientMock);
        when(ingredientMock.getIngredientId()).thenReturn(1L);
        when(bottleSlotEntityTwo.getIngredient()).thenReturn(ingredientMockTwo);
        when(ingredientMock.getIngredientId()).thenReturn(2L);
        ResponseEntity<List<BottleSlots>> result = machineService.getSlots();

        // verify
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(2, Objects.requireNonNull(result.getBody()).size());

    }

    @Test
    void setSlots() {

        // when
        when(entityManager.getReference(any(), anyLong())).thenReturn(ingredientEntityMock);
        when(machineIngredientsRequest.getSlotId()).thenReturn(BigDecimal.ONE);
        when(machineIngredientsRequest.getIngredientId()).thenReturn(BigDecimal.TEN);
        when(bottleSlotsRepository.findAll()).thenReturn(List.of(bottleSlotEntityOne, bottleSlotEntityTwo));

        when(bottleSlotsRepository.findAll()).thenReturn(List.of(bottleSlotEntityOne, bottleSlotEntityTwo));
        when(bottleSlotEntityOne.getIngredient()).thenReturn(ingredientMock);
        when(ingredientMock.getIngredientId()).thenReturn(1L);
        when(bottleSlotEntityTwo.getIngredient()).thenReturn(ingredientMockTwo);
        when(ingredientMock.getIngredientId()).thenReturn(2L);

        ResponseEntity<List<BottleSlots>> result = machineService.setSlots(machineIngredientsRequest);

        // verify
        verify(bottleSlotsRepository, times(1)).save(any());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(2, Objects.requireNonNull(result.getBody()).size());
    }
}