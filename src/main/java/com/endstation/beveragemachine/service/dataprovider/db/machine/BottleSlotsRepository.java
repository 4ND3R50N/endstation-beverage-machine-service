package com.endstation.beveragemachine.service.dataprovider.db.machine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BottleSlotsRepository extends JpaRepository<BottleSlotEntity, Long> {}