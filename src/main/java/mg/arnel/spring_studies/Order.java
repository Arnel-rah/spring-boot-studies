package mg.arnel.spring_studies;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class Order {
    private int id;
    private String client;
    private double total;
    private String status;
    private LocalDateTime createdAt;

}