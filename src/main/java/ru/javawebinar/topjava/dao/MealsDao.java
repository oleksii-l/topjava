package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;
import java.util.UUID;

public interface MealsDao {
    void create(Meal meal);

    Meal read(UUID id);

    void update(UUID id, Meal newValue);

    void delete(UUID id);

    List<Meal> getAll();
}
