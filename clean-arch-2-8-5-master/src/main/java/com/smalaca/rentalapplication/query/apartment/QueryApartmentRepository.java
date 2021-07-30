package com.smalaca.rentalapplication.query.apartment;

import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class QueryApartmentRepository {
    private final SpringQueryApartmentRepository springQueryApartmentRepository;
    private final SpringQueryApartmentBookingHistoryRepository springQueryApartmentBookingHistoryRepository;

    public QueryApartmentRepository(
            SpringQueryApartmentRepository springQueryApartmentRepository, SpringQueryApartmentBookingHistoryRepository springQueryApartmentBookingHistoryRepository) {
        this.springQueryApartmentRepository = springQueryApartmentRepository;
        this.springQueryApartmentBookingHistoryRepository = springQueryApartmentBookingHistoryRepository;
    }

    public Iterable<ApartmentReadModel> findAll() {
        return springQueryApartmentRepository.findAll();
    }

    public ApartmentDetails findById(String id) {
        Optional<ApartmentReadModel> found = springQueryApartmentRepository.findById(UUID.fromString(id));

        if(found.isPresent()){
            ApartmentReadModel apartmentReadModel = found.get();
            Optional<ApartmentBookingHistoryReadModel> foundHistory = springQueryApartmentBookingHistoryRepository.findById(id);
            if(foundHistory.isPresent()){
                ApartmentBookingHistoryReadModel apartmentBookingHistoryReadModel = foundHistory.get();
                return ApartmentDetails.withHistory(apartmentReadModel, apartmentBookingHistoryReadModel);
            }else{
                return ApartmentDetails.withoutHistory(found.get());
            }

        }else{
            return ApartmentDetails.notExisting();
        }


    }
}
