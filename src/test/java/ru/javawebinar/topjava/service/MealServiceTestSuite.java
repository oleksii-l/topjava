package ru.javawebinar.topjava.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        JdbcMealServiceTest.class,
        JpaMealServiceTest.class,
        DatajpaMealServiceTest.class
})
public class MealServiceTestSuite {
}
