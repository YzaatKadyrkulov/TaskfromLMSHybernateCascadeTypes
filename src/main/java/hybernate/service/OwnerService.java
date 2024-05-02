package hybernate.service;

import hybernate.entity.House;
import hybernate.entity.Owner;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OwnerService {
    String saveOwner(Owner owner);

    String saveOwner(Owner owner, House house);

    Optional<Owner> findOwnerById(Long ownerId);

    List<Owner> findAllOwners();

    String updateOwnerById(Long ownerId, Owner newOwner);

    String deleteOwnerById(Long ownerId);

    String assignOwnerToAgency(Long ownerId, Long agencyId);

    List<Owner> getOwnersByAgencyId(Long agencyId);

    Map<String, Integer> getOwnerNameAndAge();

}
