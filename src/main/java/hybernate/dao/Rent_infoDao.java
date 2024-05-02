package hybernate.dao;

import hybernate.entity.Rent_info;

import java.time.LocalDate;
import java.util.List;

public interface Rent_infoDao {
    //    get кылып жатканда эки дата берилет, ошол датанын ортосундагы check out болгон rent info лордо алып чыгып бериниз
    List<Rent_info> rentInfoBetweenDates(LocalDate checkIn, LocalDate checkOut);
    //    Agency id менен учурдагы датада канча уй ижарага берилген санын чыгарыныз.
    Long housesByAgencyIdAndDate(Long agencyId);
}
