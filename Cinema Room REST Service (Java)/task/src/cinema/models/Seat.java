package cinema.models;

import java.util.UUID;

public class Seat {
    private final int row;
    private final int column;
    private final int price;
    private boolean isPurchased;
    private final UUID token;

    public Seat(int row, int column, int price) {
        this.row = row;
        this.column = column;
        this.price = price;
        this.isPurchased = false;
        this.token = UUID.randomUUID();
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }


    public int getPrice() {
        return price;
    }


    public boolean isPurchased() {
        return isPurchased;
    }

    public void setPurchased(boolean purchased) {
        isPurchased = purchased;
    }

    public UUID getToken() {
        return token;
    }

    public int getKey() {
        return row * 100 + column;
    }
}
