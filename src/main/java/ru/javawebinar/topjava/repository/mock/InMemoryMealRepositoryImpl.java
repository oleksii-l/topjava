package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> this.save(meal, AuthorizedUser.id()));
    }

    @Override
    public Meal save(Meal meal, int userId) {

        // search basket. if absent - create new
        repository.computeIfAbsent(userId, key -> new ConcurrentHashMap<>());

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.get(userId).put(meal.getId(), meal);
            return meal;
        }

        repository.get(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> mealByUser = repository.getOrDefault(userId, new HashMap<>());
        return mealByUser.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> mealByUser = repository.getOrDefault(userId, new HashMap<>());
        return mealByUser.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.getOrDefault(userId, new HashMap<>()).values().stream()
                .sorted((o1, o2) -> -o1.getDateTime().compareTo(o2.getDateTime()))
                .collect(Collectors.toList());
    }
}

