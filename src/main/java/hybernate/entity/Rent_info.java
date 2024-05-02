package hybernate.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

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
    @Check(constraints = "check_in <= check_out")
    private LocalDate checkIn;

    @Column(name = "check_out")
    @Check(constraints = "check_out >= check_in")
    private LocalDate checkOut;

    @OneToOne(cascade = {CascadeType.DETACH})
    private House house;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Agency agency;

    @ManyToOne
    private Owner owner;

    public Rent_info(LocalDate checkIn, LocalDate checkOut) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    @Override
    public String toString() {
        return "Rent_info{" +
                "id=" + id +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                '}';
    }
}
