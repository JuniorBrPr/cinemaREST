package cinema;

import cinema.models.ErrorDTO;
import cinema.models.SeatDTO;
import cinema.models.SeatListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
public class CinemaController {
    private final CinemaRepository cinemaRepository;

    @Autowired
    public CinemaController(CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }

    @GetMapping("/seats")
    public SeatListDTO getSeats() {
        return cinemaRepository.getSeats();
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchase(@RequestBody SeatDTO requestSeat) {
        if (cinemaRepository.isOutOfBound(requestSeat.row(), requestSeat.column())) {
            return new ResponseEntity<>(new ErrorDTO("The number of a row or a column is out of bounds!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (!cinemaRepository.isSeatAvailable(requestSeat.row(), requestSeat.column())) {
            return new ResponseEntity<>(new ErrorDTO("The ticket has been already purchased!"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(cinemaRepository.purchaseSeat(requestSeat.row(), requestSeat.column()),
                HttpStatus.OK);
    }
}
