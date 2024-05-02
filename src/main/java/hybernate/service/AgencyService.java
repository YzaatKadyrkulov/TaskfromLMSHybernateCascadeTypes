package hybernate.service;

import hybernate.entity.Address;
import hybernate.entity.Agency;

import java.util.List;
import java.util.Optional;

public interface AgencyService {
    String saveAgency(Agency agency,Address address);

    Optional<Agency> findAgencyById(Long agencyId);

    List<Agency> findAllAgencies();

    String updateAgencyById(Long agencyId, Agency newAgency);

    String deleteAgencyById(Long agencyId);

    void assignAddressIdToAgency(Long addressId, Long agencyId);
}
