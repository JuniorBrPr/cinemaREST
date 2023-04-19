package cinema.models;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Cinema {
    private int total_rows;
    private int total_columns;
    private int total_seats;
    private int purchased_tickets;
    private int current_income;
    private int total_income;
    private ConcurrentHashMap<Integer, Seat> seats;

    public Cinema(int total_rows, int total_columns) {
        this.total_rows = total_rows;
        this.total_columns = total_columns;
        this.total_seats = total_rows * total_columns;
        this.purchased_tickets = 0;
        this.current_income = 0;
        this.total_income = 0;
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

    public void setTotal_rows(int total_rows) {
        this.total_rows = total_rows;
    }

    public int getTotal_columns() {
        return total_columns;
    }

    public void setTotal_columns(int total_columns) {
        this.total_columns = total_columns;
    }

    public int getTotal_seats() {
        return total_seats;
    }

    public void setTotal_seats(int total_seats) {
        this.total_seats = total_seats;
    }

    public int getPurchased_tickets() {
        return purchased_tickets;
    }

    public void setPurchased_tickets(int purchased_tickets) {
        this.purchased_tickets = purchased_tickets;
    }

    public int getCurrent_income() {
        return current_income;
    }

    public void setCurrent_income(int current_income) {
        this.current_income = current_income;
    }

    public int getTotal_income() {
        return total_income;
    }

    public void setTotal_income(int total_income) {
        this.total_income = total_income;
    }

    public ConcurrentHashMap<Integer, Seat> getSeats() {
        return seats;
    }

    public void setSeats(ConcurrentHashMap<Integer, Seat> seats) {
        this.seats = seats;
    }
}
