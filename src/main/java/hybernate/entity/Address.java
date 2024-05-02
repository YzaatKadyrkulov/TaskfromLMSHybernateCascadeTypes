package hybernate.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "addresses")
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_gen")
    @SequenceGenerator(name = "address_gen",sequenceName = "address_sqe",allocationSize = 1)
    private Long id;
    @Column(nullable = false, unique = true)
    private String city;
    @Column(nullable = false, unique = true)
    private String region;

    @Column(nullable = false)
    private String street;

    @OneToOne(mappedBy = "address",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Agency agency;

    public Address(String city, String region, String street) {
        this.city = city;
        this.region = region;
        this.street = street;
    }

    @Override
    public String toString() {
        return "\nAddress{" +
                "   id      =" +  id +
                ", city     ='" + city + '\'' +
                ", region   ='" + region + '\'' +
                ", street   ='" + street + '\'' +
                '}';
    }
}
