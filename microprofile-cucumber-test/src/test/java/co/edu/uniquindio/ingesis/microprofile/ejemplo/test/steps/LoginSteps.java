package co.edu.uniquindio.ingesis.microprofile.ejemplo.test.steps;

import co.edu.uniquindio.ingesis.microprofile.ejemplo.test.dtos.TokenDTO;
import co.edu.uniquindio.ingesis.microprofile.ejemplo.test.dtos.UsuarioDTO;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class LoginSteps {
    private UsuarioDTO usuario;
    private Response response;
    @Given("Soy un usuario registrado del sistema usando credenciales validas")
    public void soyUnUsuarioRegistradoDelSistemaUsandoCredencialesValidas() {
        usuario = UsuarioDTO.builder().usuario("pedro").clave("pedro").build();
    }

    @When("invoco el servicio de autenticación")
    public void invocoElServicioDeAutenticación() {
        response = given().contentType(ContentType.JSON)
                .body(usuario).post("http://localhost:8080/api/tokens");
    }

    @Then("obtengo un status code {int}")
    public void obtengoUnStatusCode(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @And("un token de autenticación")
    public void unTokenDeAutenticación() {
        TokenDTO tokenDTO = response.then()
                .body("token",response->notNullValue())
                .body("vigencia",response->notNullValue())
                .body("usuario",response->notNullValue())
                .and()
                .extract().body().as(TokenDTO.class);
        assertNotNull(tokenDTO);
    }
}
