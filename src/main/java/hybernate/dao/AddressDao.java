package hybernate.dao;

import hybernate.entity.Address;
import hybernate.entity.Agency;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AddressDao {
    Optional<Address> findAddressById(Long addressId);

    List<Address> findAllAddresses();

    String updateAddressById(Long agencyId, Address newAddress);

    //Бир метод ар бир адрести агентсвосу менен чыгарсын.
    Map<Address, Agency> outputAllAddressWithAgency();

    //  Колдонуучу бир шаардын атын жазса ошол шаарда канча агентсво бар экенин эсептеп чыгарсын
    int getCountAgenciesByCityName(String cityName);

    //Ар бир регион жана ошол региондун агентсволары баары чыксын Map<String, List<Agency>> groupByRegion.
    Map<String, List<Agency>> groupByRegion();
}
