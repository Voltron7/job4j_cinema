package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.repository.HallRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class SimpleHallService implements HallService {
    private final HallRepository hallRepository;

    public SimpleHallService(HallRepository sql2oHallRepository) {
        this.hallRepository = sql2oHallRepository;
    }

    @Override
    public Optional<Hall> findByName(String name) {
        return hallRepository.findByName(name);
    }

    @Override
    public List<Integer> getRowByName(String name) {
        List<Integer> result = new ArrayList<>();
        for (int i = 1; i <= hallRepository.findByName(name).get().getRowCount(); i++) {
            result.add(i);
        }
        return result;
    }

    @Override
    public List<Integer> getPlaceByName(String name) {
        List<Integer> result = new ArrayList<>();
        for (int i = 1; i <= hallRepository.findByName(name).get().getPlaceCount(); i++) {
            result.add(i);
        }
        return result;
    }

    @Override
    public Optional<Hall> findById(int id) {
        return hallRepository.findById(id);
    }

    @Override
    public Collection<Hall> findAll() {
        return hallRepository.findAll();
    }
}
