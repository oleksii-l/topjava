package ru.javawebinar.topjava.repository.mock;

import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.computeIfAbsent(AuthorizedUser.id(), key -> new ConcurrentHashMap<>())
                    .put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        Map<Integer, Meal> mealByUser = repository.computeIfPresent(AuthorizedUser.id(),
                (k, v) -> v);
        mealByUser.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        return meal;
    }

    @Override
    public void delete(int id, int userId) {
        Map<Integer, Meal> mealByUser = repository.getOrDefault(userId, new HashMap<>());
        mealByUser.remove(id);
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> mealByUser = repository.getOrDefault(userId, new HashMap<>());
        return mealByUser.get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.get(userId).values().stream()
                .sorted((o1, o2) -> -o1.getDateTime().compareTo(o2.getDateTime()))
                .collect(Collectors.toList());
    }
}
