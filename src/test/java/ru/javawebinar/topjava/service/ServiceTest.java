package ru.javawebinar.topjava.service;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;

import java.util.concurrent.TimeUnit;

import static org.slf4j.LoggerFactory.getLogger;

public class ServiceTest {
    protected static final Logger log = getLogger("result");
    protected static StringBuilder results = new StringBuilder();
    @Rule
    // http://stackoverflow.com/questions/14892125/what-is-the-best-practice-to-determine-the-execution-time-of-the-bussiness-relev
    public Stopwatch stopwatch;

    @Before
    public void init() {
        stopwatch = new Stopwatch() {
            @Override
            protected void finished(long nanos, Description description) {
                String result = String.format("\n%-25s %7d", description.getMethodName(), TimeUnit.NANOSECONDS.toMillis(nanos));
                results.append(result);
                log.info(result + " ms\n");
            }
        };
    }

    @AfterClass
    public static void printResult() {
        log.info("\n---------------------------------" +
                "\nTest                 Duration, ms" +
                "\n---------------------------------" +
                results +
                "\n---------------------------------");
    }
}
