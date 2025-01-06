package ra.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "product_name",length = 100,unique = true,nullable = false)
    private String productName;
    @Column(name = "price",nullable = false)
    private Double price;
    @Column(name = "image",nullable = true)
    private String image;
    @Column(name = "status",nullable = false)
    private Boolean status;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
