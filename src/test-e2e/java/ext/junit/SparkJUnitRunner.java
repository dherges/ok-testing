package ext.junit;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import spark.servlet.SparkApplication;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static spark.Spark.awaitInitialization;

/**
 * Usage: <code>@RunWith(SparkJUnitRunner.class) @SparkAppForTest(MyApplication.class)</code>
 */
public class SparkJUnitRunner extends BlockJUnit4ClassRunner {
  private final Class<? extends SparkApplication> sparkAppClz;
  private SparkApplication sparkApplication;

  //public (Class<?> clazz) throws InitializationError {
  public SparkJUnitRunner(Class<?> clazz) throws InitializationError {
    super(clazz);

    Optional<SparkAppForTest> annotation = Stream.of(getTestClass().getAnnotations())
      .filter((a) -> a.annotationType().isAssignableFrom(SparkAppForTest.class))
      .map((a) -> (SparkAppForTest) a)
      .findFirst();
    if (annotation.isPresent()) {
      sparkAppClz = annotation.get().value();
    } else {
      throw new InitializationError("Annotation missing: @SparkAppForTest(Class<? extends SparkApplication>)");
    }
  }

  @Override
  public void run(final RunNotifier notifier) {
    beforeTestClass();

    super.run(notifier);

    afterTestClass();
  }

  @Override
  protected void validateInstanceMethods(List<Throwable> errors) {
  }


  private void beforeTestClass() {
    // spawns the application
    try {
      sparkApplication = sparkAppClz.newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
    sparkApplication.init();

    awaitInitialization();
  }

  private void afterTestClass() {
    // destroys and quits the application
    sparkApplication.destroy();
  }

}
