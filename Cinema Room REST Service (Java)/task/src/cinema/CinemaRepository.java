package cinema;

import cinema.models.Cinema;
import cinema.models.SeatDTO;
import cinema.models.SeatListDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

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

    public SeatDTO purchaseSeat(int row, int column) {
        int key = row * 100 + column;
        if (cinema.getSeats().containsKey(key) && !cinema.getSeats().get(key).isPurchased()) {
            cinema.getSeats().get(key).setPurchased(true);
            return new SeatDTO(
                    cinema.getSeats().get(key).getRow(),
                    cinema.getSeats().get(key).getColumn(),
                    cinema.getSeats().get(key).getPrice()
            );
        }
        return null;
    }

    public boolean isSeatAvailable(int row, int column) {
        int key = row * 100 + column;
        return cinema.getSeats().containsKey(key) && !cinema.getSeats().get(key).isPurchased();
    }

    public boolean isOutOfBound(int row, int column) {
        return row > cinema.getTotal_rows() || column > cinema.getTotal_columns() || row < 1 || column < 1;
    }
}
