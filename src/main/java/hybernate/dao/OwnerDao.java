package hybernate.dao;

import hybernate.entity.Address;
import hybernate.entity.Agency;
import hybernate.entity.House;
import hybernate.entity.Owner;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OwnerDao {
    //    CRUD
//    Owner эки жол менен тузулсун(1-озу жалгыз 2- house менен чогу ), жашы 18-ден кичине owner-лер тузулбосун
    String saveOwner(Owner owner);

    String saveOwner(Owner owner, House house);

    Optional<Owner> findOwnerById(Long ownerId);

    List<Owner> findAllOwners();

    String updateOwnerById(Long ownerId, Owner newOwner);
    //    Owner-ди очуруп жатканда, house-тары чогу очот. Бирок ижарасы жок болсо очсун, эгерде
//    ижарасы бар болсо checkout датасын текшерсин. Учурдагы датадан мурун болсо rent_info менен
//    чогу очуп кетсин. Бирок customer очпосун.

    String deleteOwnerById(Long ownerId);

    //    Owner-ди agency-ге assign кылган метод болсун
    String assignOwnerToAgency(Long ownerId, Long agencyId);

    //    Constraint: email unique
//    Агентсвонун id-си менен owner-лерди ала турган метод туз
    List<Owner> getOwnersByAgencyId(Long agencyId);

    //Бардык owner-лердин аттарын жана жаштарын чыгарган метод болсун
    Map<String, Integer> getOwnerNameAndAge();

}
