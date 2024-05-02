package hybernate.entity;

import hybernate.enums.HouseType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.engine.spi.CascadeStyle;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "houses")
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "house_gen")
    @SequenceGenerator(name = "house_gen", sequenceName = "house_sqe", allocationSize = 1)
    private Long id;
    @Column(name = "house_type")
    private HouseType houseType;
    private double price;  //BigDecimal
    private double rating;
    private String description;
    private int room;
    private boolean furniture;

    @ManyToOne
    private Owner owner;

    @OneToOne(cascade = CascadeType.REMOVE,orphanRemoval = true)
    private Address address;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Rent_info rentInfo;

    public House(HouseType houseType, double price, double rating, String description, int room, boolean furniture) {
        this.houseType = houseType;
        this.price = price;
        this.rating = rating;
        this.description = description;
        this.room = room;
        this.furniture = furniture;
    }

    @Override
    public String toString() {
        return "House{" +
                "houseType=" + houseType +
                ", price=" + price +
                ", rating=" + rating +
                ", description='" + description + '\'' +
                ", room=" + room +
                ", furniture=" + furniture +
                '}';
    }
}
