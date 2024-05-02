package hybernate;

import hybernate.entity.*;
import hybernate.enums.FamilyStatus;
import hybernate.enums.Gender;
import hybernate.enums.HouseType;
import hybernate.service.*;
import hybernate.service.impl.*;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        AgencyService agencyService = new AgencyServiceImpl();
        AddressService addressService = new AddressServiceImpl();
        CustomerService customerService = new CustomerServiceImpl();
        HouseService houseService = new HouseServiceImpl();
        OwnerService ownerService = new OwnerServiceImpl();
        Rent_infoService rentInfoService = new Rent_infoServiceImpl();
        while (true) {
            System.out.println("""
                    1.Agency
                    2.Address
                    3.Customer
                    4.House
                    5.Owner
                    6.Rent_info
                    7.Exit""");
            switch (new Scanner(System.in).nextInt()) {
                case 1 -> {
                    boolean name = false;
                    while (!name) {
                        System.out.println("""
                                    Agency
                                Select operation
                                1.save Agency and save Address
                                2.assign AddressId To Agency
                                3.find Agency By Id
                                4.find All Agencies
                                5.update Agency By Id
                                6.delete Agency By Id
                                7.Exit""");
                        switch (new Scanner(System.in).nextInt()) {
                            case 1 -> {
                                Agency agency = new Agency("Eldos", "996707123123");
                                Address address = new Address("Tokmok", "Chui", "Ak-Beshim");
                                System.out.println(agencyService.saveAgency(agency, address));
                            }
                            case 2 -> {
                                System.out.println("Write addressId: ");
                                Long addressId = new Scanner(System.in).nextLong();

                                System.out.println("Write agencyId: ");
                                Long agencyId = new Scanner(System.in).nextLong();

                                agencyService.assignAddressIdToAgency(addressId, agencyId);
                            }
                            case 3 -> {
                                System.out.println("Write agencyId: ");
                                Long agencyId = new Scanner(System.in).nextLong();

                                System.out.println(agencyService.findAgencyById(agencyId));
                            }
                            case 4 -> {
                                System.out.println(agencyService.findAllAgencies());
                            }
                            case 5 -> {
                                System.out.println("Write agencyId: ");
                                Long agencyId = new Scanner(System.in).nextLong();

                                System.out.println(agencyService.updateAgencyById(agencyId, new Agency("Adyl", "996703902321")));
                            }
                            case 6 -> {
                                System.out.println("Write agencyId: ");
                                Long agencyId = new Scanner(System.in).nextLong();

                                System.out.println(agencyService.deleteAgencyById(agencyId));
                            }
                            case 7 -> {
                                name = true;
                            }
                        }
                        break;

                    }
                }
                case 2 -> {
                    boolean out = false;
                    while (!out) {
                        System.out.println("""
                                Select operation
                                    Address
                                1.find Address By Id
                                2.find All Addresses
                                3.update Address By Id
                                4.output All Address with Agency
                                5.get Count Agencies By CityName
                                6.group By Region  
                                7.Exit""");
                        switch (new Scanner(System.in).nextInt()) {
                            case 1 -> {
                                System.out.println("Write addressId: ");
                                Long addressId = new Scanner(System.in).nextLong();
                                System.out.println(addressService.findAddressById(addressId));
                            }
                            case 2 -> {
                                System.out.println(addressService.findAllAddresses());
                            }
                            case 3 -> {
                                System.out.println("Write agencyId: ");
                                Long agencyId = new Scanner(System.in).nextLong();

                                System.out.println("Write city: ");
                                String city = new Scanner(System.in).nextLine();

                                System.out.println("Write region: ");
                                String region = new Scanner(System.in).nextLine();

                                System.out.println("Write street: ");
                                String street = new Scanner(System.in).nextLine();

                                Address address = new Address(city, region, street);

                                System.out.println(addressService.updateAddressById(agencyId, address));
                            }
                            case 4 -> {
                                System.out.println(addressService.outputAllAddressWithAgency());
                            }
                            case 5 -> {
                                System.out.println("Write a name of city: ");
                                String nameCity = new Scanner(System.in).nextLine();

                                System.out.println(addressService.getCountAgenciesByCityName(nameCity));
                            }
                            case 6 -> {
                                System.out.println(addressService.groupByRegion());
                            }
                            case 7 -> {
                                out = true;
                            }
                        }
                        break;
                    }
                }
                case 3 -> {
                    boolean myExit = false;
                    while (!myExit) {
                        System.out.println("""
                                1.saveCustomer
                                2.saveCustomerWithRent
                                3.findCustomerById
                                4.findAllCustomers
                                5.updateCustomerById
                                6.deleteCustomerById
                                7.getHouseRent_info
                                8.Exit""");
                        switch (new Scanner(System.in).nextInt()) {
                            case 1 -> {
                                Customer customer = new Customer("Eldos", "Abasbekov", "eldos@gmail.com", LocalDate.of(2005, 10, 26), Gender.MALE, "kyrgyz", FamilyStatus.SINGLE);
                                System.out.println(customerService.saveCustomer(customer));
                            }
                            case 2 -> {
                                Customer customer = new Customer("Aidar", "Myktybekov", "aidar@gmail.com", LocalDate.of(2006, 6, 20), Gender.MALE, "kyrgyz", FamilyStatus.SINGLE);

                                System.out.println("Write houseId: ");
                                Long houseId = new Scanner(System.in).nextLong();

                                System.out.println("Write agencyId: ");
                                Long agencyId = new Scanner(System.in).nextLong();

                                System.out.println("Write checkIn: ");
//                                LocalDate checkIn = LocalDate.ofEpochDay(new Scanner(System.in).nextLong());

                                System.out.println("Write checkOut: ");
//                                LocalDate checkOut = LocalDate.ofEpochDay(new Scanner(System.in).nextLong());

                                System.out.println(customerService.saveCustomerWithRent(customer, houseId, agencyId,
                                        LocalDate.of(2024, 5, 2), LocalDate.of(2024, 5, 4)));
                            }
                            case 3 -> {
                                System.out.println("Write customerId: ");
                                Long customerId = new Scanner(System.in).nextLong();
                                System.out.println(customerService.findCustomerById(customerId));
                            }
                            case 4 -> {
                                System.out.println(customerService.findAllCustomers());
                            }
                            case 5 -> {
                                System.out.println("Write customerId: ");
                                Long customerId = new Scanner(System.in).nextLong();

                                Customer customer = new Customer("Nurmuhammed", "Akimbekov", "nurmuhammed@gmail.com", LocalDate.of(1999, 8, 28), Gender.MALE, "kyrgyz", FamilyStatus.SINGLE);
                                System.out.println(customerService.updateCustomerById(customerId, customer));
                            }
                            case 6 -> {
                                System.out.println("Write customerId: ");
                                Long customerId = new Scanner(System.in).nextLong();

                                System.out.println(customerService.deleteCustomerById(customerId));
                            }
                            case 7 -> {
                                System.out.println("Write customerId: ");
                                Long customerId = new Scanner(System.in).nextLong();

                                System.out.println("Write houseId: ");
                                Long houseId = new Scanner(System.in).nextLong();

                                System.out.println("Write agencyId: ");
                                Long agencyId = new Scanner(System.in).nextLong();

                                System.out.println("Write checkIn: ");
                                LocalDate checkIn = LocalDate.ofEpochDay(new Scanner(System.in).nextLong());

                                System.out.println("Write checkOut: ");
                                LocalDate checkOut = LocalDate.ofEpochDay(new Scanner(System.in).nextLong());

                                System.out.println(customerService.getHouseRent_info(customerId, houseId, agencyId, checkIn, checkOut));
                            }
                            case 8 -> {
                                myExit = true;
                            }
                        }
                        break;
                    }
                }
                case 4 -> {
                    boolean now = false;
                    while (!now) {
                        System.out.println("""
                                1.saveHouse
                                2.findHouseById
                                3.findAllHouses
                                4.updateHouseById
                                5.findAllHouses
                                6.deleteHouseById
                                7.getHousesInRegion
                                8.allHousesByAgencyId
                                9.allHousesByOwnerId
                                10.housesBetweenDates
                                11.Exit""");
                        switch (new Scanner(System.in).nextInt()) {
                            case 1 -> {
                                System.out.println("Write ownerId: ");
                                Long ownerId = new Scanner(System.in).nextLong();

                                House house = new House(HouseType.HOUSE, 20000, 4.5, "Beautiful", 1, true);

                                System.out.println(houseService.saveHouse(ownerId, house));
                            }
                            case 2 -> {
                                System.out.println("Write houseId: ");
                                Long houseId = new Scanner(System.in).nextLong();

                                System.out.println(houseService.findHouseById(houseId));
                            }
                            case 3 -> {
                                System.out.println(houseService.findAllHouses());
                            }
                            case 4 -> {
                                House house = new House(HouseType.APARTMENT, 40000, 4.5, "beautiful", 1, true);

                                System.out.println("Write houseId: ");
                                Long houseId = new Scanner(System.in).nextLong();

                                System.out.println(houseService.updateHouseById(houseId, house));
                            }
                            case 5 -> {
                                System.out.println(houseService.findAllHouses());
                            }
                            case 6 -> {
                                System.out.println("Write houseId: ");
                                Long houseId = new Scanner(System.in).nextLong();

                                System.out.println(houseService.deleteHouseById(houseId));
                            }
                            case 7 -> {
                                System.out.println("Write region: ");
                                String region = new Scanner(System.in).nextLine();

                                System.out.println(houseService.getHousesInRegion(region));
                            }
                            case 8 -> {
                                System.out.println("Write agencyId: ");
                                Long agencyId = new Scanner(System.in).nextLong();

                                System.out.println(houseService.allHousesByAgencyId(agencyId));
                            }
                            case 9 -> {
                                System.out.println("Write ownerId: ");
                                Long ownerId = new Scanner(System.in).nextLong();

                                System.out.println(houseService.allHousesByOwnerId(ownerId));
                            }
                            case 10 -> {
                                System.out.println("Write checkIn: ");
                                LocalDate checkIn = LocalDate.ofEpochDay(new Scanner(System.in).nextLong());

                                System.out.println("Write checkOut: ");
                                LocalDate checkOut = LocalDate.ofEpochDay(new Scanner(System.in).nextLong());

                                System.out.println(houseService.housesBetweenDates(checkIn, checkOut));
                            }
                            case 11 -> {
                                now = true;
                            }
                        }
                        break;
                    }
                }
                case 5 -> {
                    boolean on = false;
                    while (!on) {
                        System.out.println("""
                                1.saveOwner
                                2.saveOwner
                                3.findOwnerById
                                4.findAllOwners
                                5.updateOwnerById
                                6.deleteOwnerById
                                7.assignOwnerToAgency
                                8.getOwnersByAgencyId
                                9.getOwnerNameAndAge
                                10.Exit""");
                        switch (new Scanner(System.in).nextInt()) {
                            case 1 -> {
                                Owner owner = new Owner("Ibragim", "Sagynov", "ibragim@gmail.com", LocalDate.of(2008, 12, 13), Gender.MALE);
                                System.out.println(ownerService.saveOwner(owner));

                            }
                            case 2 -> {
                                Owner owner = new Owner("Nursultan", "Abdrasulov", "nursultan@gmail.com", LocalDate.of(2006, 1, 13), Gender.MALE);
                                House house = new House(HouseType.APARTMENT, 80000, 5.0, "beautiful", 3, true);

                                System.out.println(ownerService.saveOwner(owner, house));
                            }
                            case 3 -> {
                                System.out.println("Write ownerId: ");
                                Long ownerId = new Scanner(System.in).nextLong();

                                System.out.println(ownerService.findOwnerById(ownerId));
                            }
                            case 4 -> {
                                System.out.println(ownerService.findAllOwners());
                            }
                            case 5 -> {
                                Owner owner = new Owner("Bektur", "Temirbekov", "bektur@gmail.com", LocalDate.of(1991, 12, 8), Gender.MALE);

                                System.out.println("Write ownerId: ");
                                Long ownerId = new Scanner(System.in).nextLong();

                                System.out.println(ownerService.updateOwnerById(ownerId, owner));
                            }
                            case 6 -> {
                                System.out.println("Write ownerId: ");
                                Long ownerId = new Scanner(System.in).nextLong();

                                System.out.println(ownerService.deleteOwnerById(ownerId));
                            }
                            case 7 -> {
                                System.out.println("Write ownerId: ");
                                Long ownerId = new Scanner(System.in).nextLong();

                                System.out.println("Write agencyId: ");
                                Long agencyId = new Scanner(System.in).nextLong();


                                System.out.println(ownerService.assignOwnerToAgency(ownerId, agencyId));

                            }
                            case 8 -> {
                                System.out.println("Write agencyId: ");
                                Long agencyId = new Scanner(System.in).nextLong();

                                System.out.println(ownerService.getOwnersByAgencyId(agencyId));

                            }
                            case 9 -> {
                                System.out.println(ownerService.getOwnerNameAndAge());
                            }
                            case 10 -> {
                                on = true;
                            }
                        }
                        break;
                    }
                }
                case 6 -> {
                    boolean in = false;
                    while (!in) {
                        System.out.println("""
                                1.rentInfoBetweenDates
                                2.housesByAgencyIdAndDate
                                3.Exit""");
                        switch (new Scanner(System.in).nextInt()) {
                            case 1 -> {
                                System.out.println("Write checkIn: ");
                                LocalDate checkIn = LocalDate.of(1970, 10, 10);

                                System.out.println("Write checkOut: ");
                                LocalDate checkOut =  LocalDate.of(1980, 10, 10);

                                System.out.println(rentInfoService.rentInfoBetweenDates(checkIn, checkOut));
                            }
                            case 2 -> {
                                System.out.println("Write agencyId: ");
                                Long agencyId = new Scanner(System.in).nextLong();

                                System.out.println(rentInfoService.housesByAgencyIdAndDate(agencyId));
                            }
                            case 3 -> {
                                in = true;
                            }
                        }
                        break;
                    }
                }
                case 7 -> {
                    System.out.println("See you soon");
                    return;
                }
            }
        }
    }
}
