package cinema.models;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Cinema {
    private final int total_rows;
    private final int total_columns;
    private final ConcurrentHashMap<Integer, Seat> seats;

    public Cinema(int total_rows, int total_columns) {
        this.total_rows = total_rows;
        this.total_columns = total_columns;
        this.seats = createSeats();
    }

    private ConcurrentHashMap<Integer, Seat> createSeats() {
        HashMap<Integer, Seat> seats = new HashMap<>();
        for (int i = 1; i <= total_rows; i++) {
            for (int j = 1; j <= total_columns; j++) {
                seats.put(i * 100 + j, new Seat(i, j, i <= 4 ? 10 : 8));
            }
        }
        return new ConcurrentHashMap<>(seats);
    }

    public int getTotal_rows() {
        return total_rows;
    }

    public int getTotal_columns() {
        return total_columns;
    }

    public ConcurrentHashMap<Integer, Seat> getSeats() {
        return seats;
    }
}
