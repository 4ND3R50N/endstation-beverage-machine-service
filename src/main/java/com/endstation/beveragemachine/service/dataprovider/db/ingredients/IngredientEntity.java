package com.endstation.beveragemachine.service.dataprovider.db.ingredients;

import com.endstation.beveragemachine.service.dataprovider.db.Audible;
import com.endstation.beveragemachine.service.dataprovider.db.drinks.DrinkIngredientConceptionEntity;
import com.endstation.beveragemachine.service.dataprovider.db.machine.BottleSlotEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
@Table(name = "ingredients")
public class IngredientEntity extends Audible<String> {
    @Id
    @Column(name = "ingredient_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long ingredientId;

    @Column(unique = true)
    private String name;

    @Column(name = "liquid_type")
    @Enumerated(EnumType.STRING)
    private LiquidTypeEnum liquidType;

    @OneToMany(
            mappedBy = "ingredient",
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<DrinkIngredientConceptionEntity> drinkConceptions;

    @OneToOne(fetch = FetchType.EAGER)
    private BottleSlotEntity slot;
}

