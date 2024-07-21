package app.reservation;

import app.AppException;
import domain.reservation.ReservationManager;
import domain.reservation.ReservationException;

/**
 * Form class for Cancel Reservation
 * 
 */
public class CancelReservationForm {

    private String reservationNumber;

    public void cancelReservation() throws AppException {
        ReservationManager reservationManager = new ReservationManager();
        try {
            reservationManager.cancelReservation(reservationNumber);
        } catch (ReservationException e) {
            AppException exception = new AppException("Failed to cancel reservation", e);
            exception.getDetailMessages().add(e.getMessage());
            exception.getDetailMessages().addAll(e.getDetailMessages());
            throw exception;
        }
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
    }
}
