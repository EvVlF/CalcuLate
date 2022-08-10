package com.EvDroid;

import com.EvDroid.Exceptions.NotCorrectExpression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ValidationTest {
  private TestInfo testInfo;

  @BeforeEach
  void init(TestInfo testInfo) {
    this.testInfo = testInfo;
  }

  @Mock private Validation validation;

  @ParameterizedTest
  @DisplayName("Тест на верное выражение")
  @ValueSource(strings = {"4.2 + 2 * 3 / 3 - 6.1"})
  void testGetValidatedExpression(String enteredExpression) throws NotCorrectExpression {
    StringBuilder enteredExpressionTest = new StringBuilder(enteredExpression);
    ArrayDeque<StringBuilder> expectedEnteredExpression = new ArrayDeque<>();
    expectedEnteredExpression.add(new StringBuilder("4.2"));
    expectedEnteredExpression.add(new StringBuilder("+"));
    expectedEnteredExpression.add(new StringBuilder("2"));
    expectedEnteredExpression.add(new StringBuilder("*"));
    expectedEnteredExpression.add(new StringBuilder("3"));
    expectedEnteredExpression.add(new StringBuilder("/"));
    expectedEnteredExpression.add(new StringBuilder("3"));
    expectedEnteredExpression.add(new StringBuilder("-"));
    expectedEnteredExpression.add(new StringBuilder("6.1"));
    assertEquals(
        expectedEnteredExpression.toString(),
        Validation.getValidatedExpression(enteredExpressionTest).toString());
  }

  @ParameterizedTest(name = "true_ifDigitBuildedCorrect {0}")
  @ValueSource(strings = {"1.1", "111.1", "111"})
  void true_ifDigitBuildedCorrect(CharSequence subEnteredExpression)
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    System.out.println(testInfo.getDisplayName());
    Method buildDigitTest = Validation.class.getDeclaredMethod("buildDigit", CharSequence.class);
    buildDigitTest.setAccessible(true);
    assertThat(
        subEnteredExpression.toString(),
        equalTo(buildDigitTest.invoke(validation, subEnteredExpression).toString()));
  }

  @ParameterizedTest(name = "true_ifDigitHaveLess2Points {0}")
  @ValueSource(strings = {"1.1", "1"})
  void true_ifDigitHaveLess2Points(CharSequence digitFromSubEnteredExpression)
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    System.out.println(testInfo.getDisplayName());
    Method isDotsInDigitLessThan2Test =
        Validation.class.getDeclaredMethod("isDotsInDigitLessThan2", CharSequence.class);
    isDotsInDigitLessThan2Test.setAccessible(true);
    assertThat(
        true,
        equalTo(isDotsInDigitLessThan2Test.invoke(validation, digitFromSubEnteredExpression)));
  }

  @ParameterizedTest(name = "false_ifDigitHaveMoreThan2Points {0}")
  @ValueSource(strings = {"1..1", "..1", "0..."})
  void false_ifDigitHaveMoreThan2Points(CharSequence digitFromSubEnteredExpression)
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    System.out.println(testInfo.getDisplayName());
    Method isDotsInDigitLessThan2Test =
        Validation.class.getDeclaredMethod("isDotsInDigitLessThan2", CharSequence.class);
    isDotsInDigitLessThan2Test.setAccessible(true);
    assertThat(
        false,
        equalTo(isDotsInDigitLessThan2Test.invoke(validation, digitFromSubEnteredExpression)));
  }

  @ParameterizedTest(name = "throw_ifDigitHaveMoreThan2Points {0}")
  @ValueSource(strings = {"1..1", "..1", "0..."})
  void throw_ifDigitHaveMoreThan2Points(CharSequence subEnteredExpression)
      throws NoSuchMethodException, IllegalAccessException {
    System.out.println(testInfo.getDisplayName());
    Method buildDigitTest = Validation.class.getDeclaredMethod("buildDigit", CharSequence.class);
    buildDigitTest.setAccessible(true);
    try {
      buildDigitTest.invoke(validation, subEnteredExpression);
    } catch (InvocationTargetException ex) {
      assertThat(ex.getCause(), instanceOf(NotCorrectExpression.class));
    }
  }

  @ParameterizedTest(name = "throw_ifExpressionStartOrEndNotCorrectAndDidntValidate {0}")
  @ValueSource(strings = {"11.11 + 22.22.", " 11.11 + 22.22"})
  void throw_ifExpressionStartOrEndNotCorrectAndDidntValidate(StringBuilder subEnteredExpression)
      throws NoSuchMethodException, IllegalAccessException {
    System.out.println(testInfo.getDisplayName());
    Method validateEnteredExpression =
        Validation.class.getDeclaredMethod("validateEnteredExpression", StringBuilder.class);
    validateEnteredExpression.setAccessible(true);
    try {
      validateEnteredExpression.invoke(validation, subEnteredExpression);
    } catch (InvocationTargetException ex) {
      assertThat(ex.getCause(), instanceOf(NotCorrectExpression.class));
    }
  }

  @ParameterizedTest(name = "throw_ifExpressionIsNotCorrectAndDidntValidate {0}")
  @ValueSource(strings = {"11.11 ( 22.22", "11.11 + 22.22 ^ 4"})
  void throw_ifExpressionIsNotCorrectAndDidntValidate(StringBuilder subEnteredExpression)
      throws NoSuchMethodException, IllegalAccessException {
    System.out.println(testInfo.getDisplayName());
    Method validateEnteredExpression =
        Validation.class.getDeclaredMethod("validateEnteredExpression", StringBuilder.class);
    validateEnteredExpression.setAccessible(true);
    try {
      validateEnteredExpression.invoke(validation, subEnteredExpression);
    } catch (InvocationTargetException ex) {
      assertThat(ex.getCause(), instanceOf(RuntimeException.class));
    }
  }
}
