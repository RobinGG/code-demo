package com.robingong;

import cucumber.api.java.en.But;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

public class CalculatorTest {

    private int result;

    @When("I calculate {int} and {int}")
    public void iCalculateAnd(int first, int second) {
        result = Calculator.add(first, second);
    }

    @Then("I should get {int}")
    public void iShouldGet(int result) {
        Assert.assertEquals(result, this.result);
    }

    @When("I calculate {int} minus {int}")
    public void iCalculateMinus(int first, int second) {
        result = Calculator.minus(first, second);
    }

    @But("I shouldn't get {int}")
    public void iShouldnTGet(int result) {
        Assert.assertNotEquals(result, this.result);
    }

    @Given("I reset calculator")
    public void iResetCalculator() {
        result = 0;
    }

    @When("I calculate {int} {string} {int}")
    public void iCalculateFirstSecond(int first, String operator, int second) {
        switch (operator) {
            case "and":
                result = Calculator.add(first, second);
                break;
            case "minus":
                result = Calculator.minus(first, second);
                break;
            default:
        }
    }
}
