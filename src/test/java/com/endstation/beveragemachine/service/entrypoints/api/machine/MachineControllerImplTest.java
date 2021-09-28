package com.endstation.beveragemachine.service.entrypoints.api.machine;

import com.endstation.beveragemachine.service.core.usecase.machine.MachineService;
import com.endstation.beveragemachine.service.model.MachineIngredientsRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

class MachineControllerImplTest {

    @Mock
    private final MachineIngredientsRequest machineIngredientsRequest = mock(MachineIngredientsRequest.class);

    @Mock
    private final MachineService machineService = mock(MachineService.class);

    MachineControllerImpl cut = new MachineControllerImpl(machineService);

    @Test
    void shouldExecuteGetSlotsServiceFunction() {
        // when
        cut.getSlots();
        // verify
        verify(machineService, times(1)).getSlots();
    }
    @Test
    void setSlots() {

        // when
        cut.setSlots(machineIngredientsRequest);
        // verify
        verify(machineService, times(1)).setSlots(machineIngredientsRequest);
    }
}