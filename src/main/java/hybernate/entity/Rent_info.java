package hybernate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rent_infos")
public class Rent_info {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rent_info_gen")
    @SequenceGenerator(name = "rent_info_gen", sequenceName = "rent_info_sqe", allocationSize = 1)
    private Long id;

    @Column(name = "check_in")
    private LocalDate checkIn;

    @Column(name = "check_out")
    private LocalDate checkOut;

    @OneToOne
    private House house;

    @ManyToOne
    private Customer customer;

    @ManyToOne(optional = true)
    private Agency agency;

    @ManyToOne
    private Owner owner;

    public Rent_info(LocalDate checkIn, LocalDate checkOut) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    @Override
    public String toString() {
        return "RentInfo{" +
                "checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                '}';
    }
}
