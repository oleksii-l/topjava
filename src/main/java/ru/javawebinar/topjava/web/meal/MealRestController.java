package ru.javawebinar.topjava.web.meal;

import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceImpl;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;

public class MealRestController {
    private MealService service = new MealServiceImpl();

    public Meal save(Meal meal, int userId) {
        checkIfUserAuthorized(userId);

        return service.save(meal, AuthorizedUser.id());
    }

    public void delete(int id, int userId) {
        checkIfUserAuthorized(userId);

        service.delete(id, AuthorizedUser.id());
    }

    public Meal get(int id, int userId) {
        checkIfUserAuthorized(userId);

        return service.get(id, AuthorizedUser.id());
    }

    public Collection<MealWithExceed> getAll(int userId) {
        checkIfUserAuthorized(userId);

        return MealsUtil.getWithExceeded(service.getAll(AuthorizedUser.id()), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    private void checkIfUserAuthorized(int userId) {
        if (userId != AuthorizedUser.id()) {
            throw new NotFoundException("Wrong user");
        }
    }

}