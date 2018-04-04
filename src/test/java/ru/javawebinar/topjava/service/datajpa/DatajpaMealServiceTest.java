package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.MealServiceTest;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(profiles = {Profiles.DATAJPA})
public class DatajpaMealServiceTest extends MealServiceTest {

    @Test
    public void getMealsWithUserFetch() {
        assertMatchWithUser(service.getWithUser(MEAL1_ID, USER_ID), MEAL1);
    }
}
