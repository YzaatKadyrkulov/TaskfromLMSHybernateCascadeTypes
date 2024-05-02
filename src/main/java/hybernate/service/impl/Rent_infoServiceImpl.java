package hybernate.service.impl;

import hybernate.dao.Rent_infoDao;
import hybernate.dao.impl.Rent_infoDaoImpl;
import hybernate.entity.Rent_info;
import hybernate.service.Rent_infoService;

import java.time.LocalDate;
import java.util.List;

public class Rent_infoServiceImpl implements Rent_infoService {
    Rent_infoDao rentInfoDao = new Rent_infoDaoImpl();

    @Override
    public List<Rent_info> rentInfoBetweenDates(LocalDate checkIn, LocalDate checkOut) {
        return rentInfoDao.rentInfoBetweenDates(checkIn, checkOut);

    }

    @Override
    public Long housesByAgencyIdAndDate(Long agencyId) {
        return rentInfoDao.housesByAgencyIdAndDate(agencyId);
    }
}
