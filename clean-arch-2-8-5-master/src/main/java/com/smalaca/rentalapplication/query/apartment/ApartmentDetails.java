package com.smalaca.rentalapplication.query.apartment;

public class ApartmentDetails {
    private static final ApartmentReadModel NO_APARTMENT = null;
    private static final ApartmentBookingHistoryReadModel NO_HISTORY = null;
    private final ApartmentReadModel apartment;
    private final ApartmentBookingHistoryReadModel bookingHistory;

    ApartmentDetails(ApartmentReadModel apartment, ApartmentBookingHistoryReadModel bookingHistory) {
        this.apartment = apartment;
        this.bookingHistory = bookingHistory;
    }

    static ApartmentDetails notExisting() {
        return new ApartmentDetails(NO_APARTMENT,NO_HISTORY);
    }

    static ApartmentDetails withoutHistory(final ApartmentReadModel apartmentReadModel) {
        return new ApartmentDetails(apartmentReadModel,NO_HISTORY);
    }

    static ApartmentDetails withHistory(final ApartmentReadModel apartmentReadModel, final ApartmentBookingHistoryReadModel apartmentBookingHistoryReadModel) {
        return new ApartmentDetails(apartmentReadModel,apartmentBookingHistoryReadModel);
    }

    public ApartmentReadModel getApartment() {
        return apartment;
    }

    public ApartmentBookingHistoryReadModel getBookingHistory() {
        return bookingHistory;
    }
}
