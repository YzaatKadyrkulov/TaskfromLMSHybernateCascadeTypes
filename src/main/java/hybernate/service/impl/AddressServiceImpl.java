package hybernate.service.impl;

import hybernate.dao.AddressDao;
import hybernate.dao.impl.AddressDaoImpl;
import hybernate.entity.Address;
import hybernate.entity.Agency;
import hybernate.service.AddressService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AddressServiceImpl implements AddressService {
    AddressDao addressDao = new AddressDaoImpl();

    @Override
    public Optional<Address> findAddressById(Long addressId) {
        return addressDao.findAddressById(addressId);
    }

    @Override
    public List<Address> findAllAddresses() {
        return addressDao.findAllAddresses();
    }

    @Override
    public String updateAddressById(Long agencyId, Address newAddress) {
        return addressDao.updateAddressById(agencyId,newAddress);
    }

    @Override
    public Map<Address, Agency> outputAllAddressWithAgency() {
        return addressDao.outputAllAddressWithAgency();
    }

    @Override
    public int getCountAgenciesByCityName(String cityName) {
        return addressDao.getCountAgenciesByCityName(cityName);
    }

    @Override
    public Map<String, List<Agency>> groupByRegion() {
        return addressDao.groupByRegion();
    }
}
