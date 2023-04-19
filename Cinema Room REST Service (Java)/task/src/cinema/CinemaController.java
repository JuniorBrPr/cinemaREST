package cinema;

import cinema.models.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


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

    @PostMapping("/return")
    public ResponseEntity<?> returnTicket(@RequestBody TokenDTO token) {
        if (!cinemaRepository.isValidToken(token.token())) {
            return new ResponseEntity<>(new ErrorDTO("Wrong token!"), HttpStatus.BAD_REQUEST);
        }

        if (cinemaRepository.isSeatAvailable(token.token())) {
            return new ResponseEntity<>(new ErrorDTO("That ticket has not been purchased!"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new ReturnedSeatDTO(cinemaRepository.returnSeat(token.token())),
                HttpStatus.OK);
    }
}
