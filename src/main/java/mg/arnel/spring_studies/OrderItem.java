package mg.arnel.spring_studies;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class OrderItem {
    private String produit;
    private int quantity;
    private double unitPrice;
    private double sousTotal;
}
