package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;

public class MealServiceImpl implements MealService {

    private MealRepository repository = new InMemoryMealRepositoryImpl();

    @Override
    public Meal save(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    @Override
    public void delete(int id, int userId) {
        repository.delete(id, userId);
    }

    @Override
    public Meal get(int id, int userId) {
        Meal result = repository.get(id, userId);
        if (result == null) {
            throw new NotFoundException("Meal is absent");
        }
        return result;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }
}