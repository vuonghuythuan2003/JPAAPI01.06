package ra.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="categories")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class Category {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(name = "category_name", length=100, unique = true, nullable = false)
    private String categoryName;
    @Column(name="category_status")
    private Boolean categoryStatus;
    @Column(name="img_category")
    private String imgCategory;

}
