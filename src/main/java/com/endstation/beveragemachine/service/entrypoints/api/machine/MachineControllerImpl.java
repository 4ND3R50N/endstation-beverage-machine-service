package com.endstation.beveragemachine.service.entrypoints.api.machine;

import com.endstation.beveragemachine.service.api.BeverageMachineApiDelegate;
import com.endstation.beveragemachine.service.core.usecase.machine.MachineService;
import com.endstation.beveragemachine.service.model.BottleSlots;
import com.endstation.beveragemachine.service.model.MachineIngredientsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MachineControllerImpl implements BeverageMachineApiDelegate {

    private final MachineService machineService;

    public ResponseEntity<List<BottleSlots>> getSlots() {
        return machineService.getSlots();
    }

    public ResponseEntity<List<BottleSlots>> setSlots(MachineIngredientsRequest machineIngredientsRequest) {
        return machineService.setSlots(machineIngredientsRequest);
    }
}
