/*
 * Copyright(C) 2007-2013 National Institute of Informatics, All rights reserved.
 */
package app.cui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

import util.DateUtil;
import app.AppException;
import app.checkin.CheckInRoomForm;
import app.checkout.CheckOutRoomForm;
import app.reservation.CancelReservationForm;
import app.reservation.ReserveRoomForm;
import domain.reservation.Reservation;
import domain.reservation.ReservationException;
import domain.reservation.ReservationManager;

/**
 * CUI class for Hotel Reservation Systems
 * 
 */
public class CUI {

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	private BufferedReader reader;

	CUI() {
		reader = new BufferedReader(new InputStreamReader(System.in));
	}

	private void execute() throws IOException {
		try {
			while (true) {
				int selectMenu;
				System.out.println("");
				System.out.println("Menu");
				System.out.println("1: Reservation");
				System.out.println("2: Check-in");
				System.out.println("3: Check-out");
				System.out.println("4: Cancel Reservation");
				System.out.println("5: Show Reservation Details");
				System.out.println("9: End");
				System.out.print("> ");

				try {
					String menu = reader.readLine();
					selectMenu = Integer.parseInt(menu);
				}
				catch (NumberFormatException e) {
					selectMenu = 0;
				}

				if (selectMenu == 9) {
					break;
				}

				switch (selectMenu) {
					case 1:
						reserveRoom();
						break;
					case 2:
						checkInRoom();
						break;
					case 3:
						checkOutRoom();
						break;
					case 4:
                        cancelReservation();
                        break;
					case 5:
						showAllReservations();
                        break;
					default:
                        System.out.println("Invalid choice. Please try again.");
				}
			}
			System.out.println("Ended");
		}
		catch (AppException e) {
			System.err.println("Error");
			System.err.println(e.getFormattedDetailMessages(LINE_SEPARATOR));
		}
		finally {
			reader.close();
		}
	}

	private void reserveRoom() throws IOException, AppException {
		System.out.println("Input arrival date in the form of yyyy/mm/dd");
		System.out.print("> ");

		String dateStr = reader.readLine();

		// Validate input
		Date stayingDate = DateUtil.convertToDate(dateStr);
		if (stayingDate == null) {
			System.out.println("Invalid input");
			return;
		}

		ReserveRoomForm reserveRoomForm = new ReserveRoomForm();
		reserveRoomForm.setStayingDate(stayingDate);
		String reservationNumber = reserveRoomForm.submitReservation();

		System.out.println("Reservation has been completed.");
		System.out.println("Arrival (staying) date is " + DateUtil.convertToString(stayingDate) + ".");
		System.out.println("Reservation number is " + reservationNumber + ".");
	}

	private void checkInRoom() throws IOException, AppException {
		System.out.println("Input reservation number");
		System.out.print("> ");

		String reservationNumber = reader.readLine();

		if (reservationNumber == null || reservationNumber.length() == 0) {
			System.out.println("Invalid reservation number");
			return;
		}

		CheckInRoomForm checkInRoomForm = new CheckInRoomForm();
		checkInRoomForm.setReservationNumber(reservationNumber);

		String roomNumber = checkInRoomForm.checkIn();
		System.out.println("Check-in has been completed.");
		System.out.println("Room number is " + roomNumber + ".");

	}

	private void checkOutRoom() throws IOException, AppException {
		System.out.println("Input room number");
		System.out.print("> ");

		String roomNumber = reader.readLine();

		if (roomNumber == null || roomNumber.length() == 0) {
			System.out.println("Invalid room number");
			return;
		}

		CheckOutRoomForm checkoutRoomForm = new CheckOutRoomForm();
		checkoutRoomForm.setRoomNumber(roomNumber);
		checkoutRoomForm.checkOut();
		System.out.println("Check-out has been completed.");
	}

    private void cancelReservation() throws IOException, AppException {
        System.out.println("Input reservation number");
        System.out.print("> ");

        String reservationNumber = reader.readLine();

        if (reservationNumber == null || reservationNumber.length() == 0) {
            System.out.println("Invalid reservation number");
            return;
        }

        CancelReservationForm cancelReservationForm = new CancelReservationForm();
        cancelReservationForm.setReservationNumber(reservationNumber);
        cancelReservationForm.cancelReservation();
        System.out.println("Reservation has been cancelled.");
    }
    
	
    private void showAllReservations() throws IOException, AppException {
        ReservationManager reservationManager = new ReservationManager();
        try {
            List<Reservation> reservations = reservationManager.getAllReservations();
            if (reservations.isEmpty()) {
                System.out.println("No reservations found.");
            } else {
                for (Reservation reservation : reservations) {
                    System.out.println("Reservation Number: " + reservation.getReservationNumber());
                    System.out.println("Staying Date: " + DateUtil.convertToString(reservation.getStayingDate()));
                    System.out.println("Status: " + reservation.getStatus());
                    System.out.println("-----------------------------");
                }
            }
        } catch (ReservationException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


	public static void main(String[] args) throws Exception {
		CUI cui = new CUI();
		cui.execute();
	}
}
