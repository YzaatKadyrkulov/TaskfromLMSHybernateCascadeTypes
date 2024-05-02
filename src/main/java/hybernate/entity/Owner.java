package hybernate.entity;

import hybernate.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "owners")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "owner_gen")
    @SequenceGenerator(name = "owner_gen", sequenceName = "owner_sqe", allocationSize = 1)
    private Long id;
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    private Gender gender;

    @ManyToMany
    private List<Agency> agencies;

    @OneToMany(mappedBy = "owner")
    private List<House> houses;

    @OneToMany(mappedBy = "owner")
    private List<Rent_info> rentInfos;

    public Owner(String firstName, String lastName, String email, LocalDate dateOfBirth, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public void addHouse(House house) {
        if (houses == null) {
            houses = new ArrayList<>();
        }
        houses.add(house);
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender=" + gender +
                '}';
    }
}
