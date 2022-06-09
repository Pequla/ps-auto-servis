package rs.ac.singidunum.autoservis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateOrderItemModel {

    private Integer orderId;
    private Integer itemId;
    private Double amount;
    private Double discount;
}
