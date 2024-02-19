package at.altin.bikemedlager.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Produkt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "delivery_time_days", nullable = false)
    private Integer deliveryTimeDays;
}
