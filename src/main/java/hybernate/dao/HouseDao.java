package hybernate.dao;

import hybernate.entity.Address;
import hybernate.entity.Agency;
import hybernate.entity.House;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HouseDao {
    //            CRUD
    //    House-тузулуп жатканда бир owner-ге тиешелуу болуп тузулсун
    String saveHouse(Long ownerId, House house);

    Optional<House> findHouseById(Long houseId);

    List<House> findAllHouses();

    String updateHouseById(Long house, House newHouse);

    //    House-ту очуруп жатканда адреси чогу очсун .Бирок ижарасы жок болсо очсун, эгерде ижарасы
    //    бар болсо checkout датасын текшерсин. Учурдагы датадан мурун болсо rent_info менен чогу очуп кетсин
    String deleteHouseById(Long houseId);

    //    Бир региондогу бардык уйлор чыксын
    List<House> getHousesInRegion(String region);

    //    Бир агентсвога тиешелуу бардык уйлор чыксын
    List<House> allHousesByAgencyId(Long agencyId);

    //    Бир owner-ге тиешелуу бардык уйлор чыксын
    List<House> allHousesByOwnerId(Long ownerId);

    //    Эки датанын ортосундагы checkIn болгон бардык уйлор чыксын
    List<House> housesBetweenDates(LocalDate fromDate, LocalDate toDate);

}
