package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.FilmSessionService;
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.TicketService;

@Controller
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;
    private final FilmSessionService filmSessionService;
    private final HallService hallService;

    public TicketController(TicketService ticketService, FilmSessionService filmSessionService, HallService hallService) {
        this.ticketService = ticketService;
        this.filmSessionService = filmSessionService;
        this.hallService = hallService;
    }

    @GetMapping("/{id}")
    public String getFilmSessionById(Model model, @PathVariable int id) {
        var filmSessionDtoOptional = filmSessionService.findById(id);
        if (filmSessionDtoOptional.isEmpty()) {
            model.addAttribute("message", "Сеанс с указанным идентификатором не найден");
            return "errors/404";
        }
        String hallsName = filmSessionDtoOptional.get().getHallsName();
        model.addAttribute("filmSession", filmSessionDtoOptional.get());
        model.addAttribute("rowNumbers", hallService.getRowByName(hallsName));
        model.addAttribute("placeNumbers", hallService.getPlaceByName(hallsName));
        return "tickets/buy";
    }

    @PostMapping("/buy")
    public String buy(Model model, @ModelAttribute Ticket ticket) {
        var savedTicket = ticketService.save(ticket);
        if (savedTicket.isEmpty()) {
            model.addAttribute("message", "Билет на таком ряде и месте уже куплен");
            return "errors/404";
        }
        model.addAttribute("message", String.format("Вы купили билет на %s ряд %s место",
                savedTicket.get().getRowNumber(), savedTicket.get().getPlaceNumber()));
        return "tickets/success";
    }
}
