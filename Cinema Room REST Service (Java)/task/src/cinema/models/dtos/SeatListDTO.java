package cinema.models.dtos;

import java.util.List;

public record SeatListDTO(int total_rows, int total_columns, List<SeatDTO> available_seats) {
}
