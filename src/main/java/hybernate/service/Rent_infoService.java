package hybernate.service;

import hybernate.entity.Rent_info;

import java.time.LocalDate;
import java.util.List;

public interface Rent_infoService {
    List<Rent_info> rentInfoBetweenDates(LocalDate checkIn, LocalDate checkOut);

    Long housesByAgencyIdAndDate(Long agencyId);
}
