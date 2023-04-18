package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.repository.FilmSessionRepository;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SimpleFilmSessionService implements FilmSessionService {
    private final FilmSessionRepository filmSessionRepository;
    private final FilmService filmService;
    private final HallService hallService;

    public SimpleFilmSessionService(FilmSessionRepository sql2oFilmSessionRepository, FilmService filmService, HallService hallService) {
        this.filmSessionRepository = sql2oFilmSessionRepository;
        this.filmService = filmService;
        this.hallService = hallService;
    }

    @Override
    public Optional<FilmSessionDto> findById(int id) {
        Optional<FilmSession> filmSession = filmSessionRepository.findById(id);
        if (filmSession.isPresent()) {
            var filmSessionDto = new FilmSessionDto(
                    filmSession.get().getId(),
                    getFilmNameById(filmSession.get()),
                    getHallNameById(filmSession.get()),
                    filmSession.get().getStartTime(),
                    filmSession.get().getEndTime(),
                    String.valueOf(filmSession.get().getPrice()));
            return Optional.of(filmSessionDto);
        }
        return Optional.empty();
    }

    @Override
    public Collection<FilmSessionDto> findAll() {
        return filmSessionRepository.findAll().stream()
                .map(filmSession -> new FilmSessionDto(filmSession.getId(), getFilmNameById(filmSession), getHallNameById(filmSession),
                        filmSession.getStartTime(), filmSession.getEndTime(), String.valueOf(filmSession.getPrice())))
                .collect(Collectors.toList());
    }

    private String getFilmNameById(FilmSession filmSession) {
        String result = null;
        Optional<Film> film = filmService.findById(filmSession.getFilmId());
        if (film.isPresent()) {
            result = film.get().getName();
        }
        return result;
    }

    private String getHallNameById(FilmSession filmSession) {
        String result = null;
        Optional<Hall> hall = hallService.findById(filmSession.getHallsId());
        if (hall.isPresent()) {
            result = hall.get().getName();
        }
        return result;
    }
}
