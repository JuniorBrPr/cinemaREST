package cinema;

import cinema.models.Cinema;
import cinema.models.Seat;
import cinema.models.dtos.PurchaseDTO;
import cinema.models.dtos.SeatDTO;
import cinema.models.dtos.SeatListDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Objects;

@Component
public class CinemaRepository {
    private final Cinema cinema;

    public CinemaRepository() {
        this.cinema = new Cinema(9, 9);
    }

    public SeatListDTO getSeats() {
        return new SeatListDTO(
                cinema.getTotal_rows(),
                cinema.getTotal_columns(),
                getAvailableSeats()
        );
    }

    public ArrayList<SeatDTO> getAvailableSeats() {
        ArrayList<SeatDTO> availableSeats = new ArrayList<>(cinema.getSeats().size());
        cinema.getSeats().keySet().stream().sorted().forEach(key -> {
            if (!cinema.getSeats().get(key).isPurchased()) {
                availableSeats.add(
                        new SeatDTO(
                                cinema.getSeats().get(key).getRow(),
                                cinema.getSeats().get(key).getColumn(),
                                cinema.getSeats().get(key).getPrice()
                        ));
            }
        });
        return availableSeats;
    }

    public PurchaseDTO purchaseSeat(int row, int column) {
        int key = row * 100 + column;
        if (cinema.getSeats().containsKey(key) && !cinema.getSeats().get(key).isPurchased()) {
            cinema.getSeats().get(key).setPurchased(true);

            SeatDTO seat = new SeatDTO(
                    cinema.getSeats().get(key).getRow(),
                    cinema.getSeats().get(key).getColumn(),
                    cinema.getSeats().get(key).getPrice()
            );

            return new PurchaseDTO(
                    cinema.getSeats().get(key).getToken().toString(),
                    seat
            );
        }
        return null;
    }

    public SeatDTO returnSeat(String token) {
        int key = Objects.requireNonNull(findByToken(token)).getKey();

        if (cinema.getSeats().containsKey(key) && cinema.getSeats().get(key).isPurchased()) {
            cinema.getSeats().get(key).setPurchased(false);

            return new SeatDTO(
                    cinema.getSeats().get(key).getRow(),
                    cinema.getSeats().get(key).getColumn(),
                    cinema.getSeats().get(key).getPrice()
            );
        }

        return null;
    }

    public boolean isValidToken(String token) {
        return findByToken(token) != null;
    }

    private Seat findByToken(String token) {
        for (int key : cinema.getSeats().keySet()) {
            if (cinema.getSeats().get(key).getToken().toString().equals(token)) {
                return cinema.getSeats().get(key);
            }
        }
        return null;
    }

    public boolean isSeatAvailable(int row, int column) {
        int key = row * 100 + column;
        return cinema.getSeats().containsKey(key) && !cinema.getSeats().get(key).isPurchased();
    }

    public boolean isSeatAvailable(String token) {
        for (int key : cinema.getSeats().keySet()) {
            if (cinema.getSeats().get(key).getToken().toString().equals(token)
                    && !cinema.getSeats().get(key).isPurchased()) {
                return true;
            }
        }
        return false;
    }

    public boolean isOutOfBound(int row, int column) {
        return row > cinema.getTotal_rows() || column > cinema.getTotal_columns() || row < 1 || column < 1;
    }

    public int getNumberOfAvailableSeats() {
        return getAvailableSeats().size();
    }

    public int getNumberOfPurchasedTickets() {
        return cinema.getSeats().size() - getNumberOfAvailableSeats();
    }

    public int getCurrentIncome() {
        int currentIncome = 0;
        for (int key : cinema.getSeats().keySet()) {
            if (cinema.getSeats().get(key).isPurchased()) {
                currentIncome += cinema.getSeats().get(key).getPrice();
            }
        }
        return currentIncome;
    }
}
