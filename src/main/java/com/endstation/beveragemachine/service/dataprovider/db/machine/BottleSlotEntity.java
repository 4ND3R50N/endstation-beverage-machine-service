package com.endstation.beveragemachine.service.dataprovider.db.machine;

import com.endstation.beveragemachine.service.dataprovider.db.Audible;
import com.endstation.beveragemachine.service.dataprovider.db.drinks.DrinkIngredientConceptionEntity;
import com.endstation.beveragemachine.service.dataprovider.db.ingredients.IngredientEntity;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

import static com.endstation.beveragemachine.service.model.IngredientData.LiquidTypeEnum;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Ingredients")
public class BottleSlotEntity extends Audible<String> {
    @Id
    @Column(name = "ingredient_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer slotId;

    @OneToOne(fetch = FetchType.EAGER, optional = false, mappedBy = "slot")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private IngredientEntity ingredient;
}

