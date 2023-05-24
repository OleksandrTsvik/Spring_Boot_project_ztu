package ztu.education.spring_boot_web_project.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "oredered_dishes")
public class OrderedDish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_dish")
    private Dish dish;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_order")
    private Order order;

    @Column(name = "count", nullable = false)
    private int count;

    @Column(name = "price", nullable = false)
    private BigDecimal price;
}