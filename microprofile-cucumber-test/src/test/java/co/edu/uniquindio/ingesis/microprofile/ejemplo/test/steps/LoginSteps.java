package co.edu.uniquindio.ingesis.microprofile.ejemplo.test.steps;

import co.edu.uniquindio.ingesis.microprofile.ejemplo.test.dtos.LoginDTO;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;

public class LoginSteps {

    private LoginDTO loginDTO;
    private static final String BASE_URL = "http://localhost:8080/api/tokens";

    @Given("Soy un usuario registrado del sistema usando credenciales validas")
    public void soyUnUsuarioRegistradoDelSistemaUsandoCredencialesValidas() {
        loginDTO = LoginDTO.builder().usuario("pedro").clave("pedro").build();
    }

    @When("invoco el servicio de autenticación")
    public void invocoElServicioDeAutenticacion() {
        baseURI = BASE_URL;
        given().contentType(ContentType.JSON).body(loginDTO).when().post();
    }

    @Then("obtengo un status code {string}")
    public void obtengoUnStatusCode(String status) {
    }

    @And("un token de autenticación")
    public void unTokenDeAutenticación() {
    }
}
