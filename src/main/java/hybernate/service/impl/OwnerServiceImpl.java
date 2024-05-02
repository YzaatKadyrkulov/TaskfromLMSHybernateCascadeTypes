package hybernate.service.impl;

import hybernate.dao.OwnerDao;
import hybernate.dao.impl.OwnerDaoImpl;
import hybernate.entity.House;
import hybernate.entity.Owner;
import hybernate.service.OwnerService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OwnerServiceImpl implements OwnerService {
    OwnerDao ownerDao = new OwnerDaoImpl();
    @Override
    public String saveOwner(Owner owner) {
        return ownerDao.saveOwner(owner);
    }

    @Override
    public String saveOwner(Owner owner, House house) {
        return ownerDao.saveOwner(owner,house);
    }

    @Override
    public Optional<Owner> findOwnerById(Long ownerId) {
        return ownerDao.findOwnerById(ownerId);
    }

    @Override
    public List<Owner> findAllOwners() {
        return ownerDao.findAllOwners();
    }

    @Override
    public String updateOwnerById(Long ownerId, Owner newOwner) {
        return ownerDao.updateOwnerById(ownerId,newOwner);
    }

    @Override
    public String deleteOwnerById(Long ownerId) {
        return ownerDao.deleteOwnerById(ownerId);
    }

    @Override
    public String assignOwnerToAgency(Long ownerId, Long agencyId) {
        return ownerDao.assignOwnerToAgency(ownerId,agencyId);
    }

    @Override
    public List<Owner> getOwnersByAgencyId(Long agencyId) {
        return ownerDao.getOwnersByAgencyId(agencyId);
    }

    @Override
    public Map<String, Integer> getOwnerNameAndAge() {
        return ownerDao.getOwnerNameAndAge();
    }
}
