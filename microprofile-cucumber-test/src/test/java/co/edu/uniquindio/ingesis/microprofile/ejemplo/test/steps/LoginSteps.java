package co.edu.uniquindio.ingesis.microprofile.ejemplo.test.steps;

import co.edu.uniquindio.ingesis.microprofile.ejemplo.test.dtos.LoginDTO;
import co.edu.uniquindio.ingesis.microprofile.ejemplo.test.dtos.TokenDTO;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;

public class LoginSteps {

    private LoginDTO loginDTO;
    private static final String BASE_URL = "http://localhost:8080/api/tokens";
    private Response response;

    @Given("Soy un usuario registrado del sistema usando credenciales validas")
    public void soyUnUsuarioRegistradoDelSistemaUsandoCredencialesValidas() {
        loginDTO = LoginDTO.builder().usuario("pedro").clave("pedro").build();
    }

    @When("invoco el servicio de autenticación")
    public void invocoElServicioDeAutenticacion() {
        baseURI = BASE_URL;
        response = given().contentType(ContentType.JSON).body(loginDTO).when().post();
    }

    @Then("obtengo un status code {int}")
    public void obtengoUnStatusCode(int status) {
        response.then().statusCode(status);
    }

    @And("un token de autenticación")
    public void unTokenDeAutenticación() {

    }
}
