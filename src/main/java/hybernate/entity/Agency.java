package hybernate.entity;

import hybernate.service.impl.AgencyServiceImpl;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "agencies")
@Builder
public class Agency {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agency_gen")
    @SequenceGenerator(name = "agency_gen", sequenceName = "agency_sqe", allocationSize = 1)
    private Long id;
    private String name;

    @AgencyServiceImpl.PhoneNumberConstraint(regex = "^\\+996\\d{13}$", message = "Invalid phone number")
    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne(cascade = {CascadeType.DETACH,
            CascadeType.REMOVE})
    private Address address;

    @ManyToMany(mappedBy = "agencies")
    private List<Owner> owners;

    @OneToMany(cascade = {CascadeType.DETACH,
            CascadeType.REMOVE})
    private List<Rent_info> rentInfos;

    public Agency(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "\nAgency{" +
                "  id           =" + id +
                ", name         ='" + name + '\'' +
                ", phoneNumber  ='" + phoneNumber + '\'' +
                '}';
    }
}
