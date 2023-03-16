package co.edu.uniquindio.ingesis.microprofile.ejemplo.test.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps {
    @Given("Soy un usuario registrado del sistema usando credenciales validas")
    public void soyUnUsuarioRegistradoDelSistemaUsandoCredencialesValidas() {

    }

    @When("invoco el servicio de autenticación")
    public void invocoElServicioDeAutenticacion() {
    }

    @Then("obtengo un status code {string}")
    public void obtengoUnStatusCode(String status) {
    }

    @And("un token de autenticación")
    public void unTokenDeAutenticación() {
    }
}
