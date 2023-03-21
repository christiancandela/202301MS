package co.edu.uniquindio.ingesis.microprofile.ejemplo.test.steps;

import co.edu.uniquindio.ingesis.microprofile.ejemplo.test.dto.TokenDTO;
import co.edu.uniquindio.ingesis.microprofile.ejemplo.test.dto.UsuarioDTO;
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


public class LoginSteps {
    private UsuarioDTO usuario;
    private Response response;
    @Given("Soy un usuario registrado del sistema usando credenciales validas")
    public void soyUnUsuarioRegistradoDelSistemaUsandoCredencialesValidas() {
        usuario = UsuarioDTO
                .builder()
                .usuario("pedro")
                .clave("pedro")
                .build();
    }

    @When("invoco el servicio de autenticación")
    public void invocoElServicioDeAutenticación() {
        response = given()
                .contentType(ContentType.JSON)
                .body(usuario)
                .post("http://localhost:8080/api/tokens");
    }

    @Then("obtengo un status code {int}")
    public void obtengoUnStatusCode(int status) {
        response.then().statusCode(status);
    }

    @And("un token de autenticación")
    public void unTokenDeAutenticación() {
        TokenDTO tokenDTO = response.then()
                .header("Authorization",response->notNullValue())
                .body("usuario",response->notNullValue())
                .body("token",response->notNullValue())
                .body("vigencia",response->notNullValue())
                .and().extract().body().as(TokenDTO.class);

        Assertions.assertNotNull(tokenDTO);
    }
}
