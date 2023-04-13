Feature: The Login API provide to user the function for Login and Logout of application

  Scenario: Yo como usuario registrado con un token valido
  quiero poder crear un producto
  para ampliar el catalogo
    Given Soy un usuario autenticado con un token valido
    When invoco el servicio de crear producto
    And envío los datos <codigo>, <nombre>, <precio>
    Then obtengo un status code 201
    And un mensaje que indica "Se creo el producto"
