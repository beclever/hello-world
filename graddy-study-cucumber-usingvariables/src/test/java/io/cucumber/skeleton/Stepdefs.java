package io.cucumber.skeleton;

import static org.junit.Assert.assertEquals;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author esnahow
 * @date 01/18/2019 11:08
 */

public class Stepdefs {
	private String today;
    private String actualAnswer;
    
	@Given("^today is \"([^\"]*)\"$")
    public void today_is(String today) {
        this.today = today;
    }
	
	@When("^I ask whether it's Friday yet$")
	public void i_ask_whether_it_s_Friday_yet() {
		actualAnswer = IsItFriday.isItFriday(today);
	}
	
	@Then("^I should be told \"([^\"]*)\"$")
	public void i_should_be_told(String expectedAnswer) {
		assertEquals(expectedAnswer, actualAnswer);
	}
}

class IsItFriday {
	static String isItFriday(String today) {
	    if (today.equals("Friday")) {
	        return "TGIF";
	    }
	    return "Nope";
	}
}
