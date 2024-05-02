package hybernate.service.impl;

import hybernate.dao.AgencyDao;
import hybernate.dao.impl.AgencyDaoImpl;
import hybernate.entity.Address;
import hybernate.entity.Agency;
import hybernate.service.AgencyService;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Optional;

public class AgencyServiceImpl implements AgencyService {
    AgencyDao agencyDao = new AgencyDaoImpl();

    @Override
    public String saveAgency(Agency agency, Address address) {
        return agencyDao.saveAgency(agency, address);
    }

    @Override
    public Optional<Agency> findAgencyById(Long agencyId) {
        return agencyDao.findAgencyById(agencyId);
    }

    @Override
    public List<Agency> findAllAgencies() {
        return agencyDao.findAllAgencies();
    }

    @Override
    public String updateAgencyById(Long agencyId, Agency newAgency) {
        return agencyDao.updateAgencyById(agencyId, newAgency);
    }

    @Override
    public String deleteAgencyById(Long agencyId) {
        return agencyDao.deleteAgencyById(agencyId);
    }

    @Override
    public void assignAddressIdToAgency(Long addressId, Long agencyId) {
        agencyDao.assignAddressIdToAgency(addressId, agencyId);
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface PhoneNumberConstraint {
        String message() default "Invalid phone number";

        String regex() default "^\\+996\\d{13}$";
    }
}
