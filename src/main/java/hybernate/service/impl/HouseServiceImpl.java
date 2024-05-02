package hybernate.service.impl;

import hybernate.dao.HouseDao;
import hybernate.dao.impl.HouseDaoImpl;
import hybernate.entity.House;
import hybernate.service.HouseService;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class HouseServiceImpl implements HouseService {
    HouseDao houseDao = new HouseDaoImpl();

    @Override
    public String saveHouse(Long ownerId, House house) {
        return houseDao.saveHouse(ownerId, house);
    }

    @Override
    public Optional<House> findHouseById(Long houseId) {
        return houseDao.findHouseById(houseId);
    }

    @Override
    public List<House> findAllHouses() {
        return houseDao.findAllHouses();
    }

    @Override
    public String updateHouseById(Long house, House newHouse) {
        houseDao.findHouseById(house).orElseThrow(() -> new NoSuchElementException(String.format("House with id : %s not found!", house)));
        return houseDao.updateHouseById(house, newHouse);
    }

    @Override
    public String deleteHouseById(Long houseId) {
        return houseDao.deleteHouseById(houseId);
    }

    @Override
    public List<House> getHousesInRegion(String region) {
        return houseDao.getHousesInRegion(region);
    }

    @Override
    public List<House> allHousesByAgencyId(Long agencyId) {
        return houseDao.allHousesByAgencyId(agencyId);
    }

    @Override
    public List<House> allHousesByOwnerId(Long ownerId) {
        return houseDao.allHousesByOwnerId(ownerId);
    }

    @Override
    public List<House> housesBetweenDates(LocalDate checkIn, LocalDate checkOut) {
        return houseDao.housesBetweenDates(checkIn, checkOut);
    }
}
