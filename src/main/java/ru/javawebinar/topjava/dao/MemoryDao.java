package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class MemoryDao implements MealsDao {
    private Map<UUID, Meal> storage;

    private List<Meal> meals = Arrays.asList(
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    );

    public MemoryDao() {
        storage = meals.stream()
                .collect(Collectors.toMap(Meal::getId, m -> m ));
    }

    @Override
    public void create(Meal meal) {
        storage.put(meal.getId(), meal);
    }

    @Override
    public Meal read(UUID id) {
        return storage.get(id);
    }

    @Override
    public void update(UUID id, Meal newValue) {
        storage.put(id, newValue);
    }

    @Override
    public void delete(UUID id) {
        storage.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        return storage.values().stream().collect(Collectors.toList());

    }
}
