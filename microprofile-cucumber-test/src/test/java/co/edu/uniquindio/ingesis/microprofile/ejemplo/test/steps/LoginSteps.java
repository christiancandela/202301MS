package co.edu.uniquindio.ingesis.microprofile.ejemplo.test.steps;

import co.edu.uniquindio.ingesis.microprofile.ejemplo.test.dtos.LoginDTO;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps {

    private LoginDTO loginDTO;

    @Given("Soy un usuario registrado del sistema usando credenciales validas")
    public void soyUnUsuarioRegistradoDelSistemaUsandoCredencialesValidas() {
        loginDTO = LoginDTO.of("pedro","pedro");
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
