package ext.junit;

import spark.servlet.SparkApplication;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface SparkAppForTest {

  /** @return Type of the spark application that is being tested */
  Class<? extends SparkApplication> value();
}
