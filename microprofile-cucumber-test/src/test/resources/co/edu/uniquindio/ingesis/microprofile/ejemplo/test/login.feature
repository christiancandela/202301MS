Feature: The Login API provide to user the function for Login and Logout of application

  Scenario: Yo como usuario registrado
  quiero poder autenticarme en el sistema
  para poder hacer uso de las funcionalidades
    Given Soy un usuario registrado del sistema usando credenciales validas
    When invoco el servicio de autenticación
    Then obtengo un status code 201
    And un token de autenticación

  Scenario: Yo como usuario usando datos invalidos
  quiero que al tratar de autenticarme el sistema me informe con claridad el error
  para poder corregirlo
    Given Soy un usuario registrado del sistema usando credenciales invalidas
    When invoco el servicio de autenticación
    Then obtengo un status code 401
    And un mensaje que indica que el "Nombre de usuario o clave incorrectas."

  Scenario Outline: Yo como usuario usando datos incompletos
  quiero que al tratar de autenticarme el sistema me informe con claridad el error
  para poder corregirlo
    Given Soy un usuario registrado del sistema omitiendo el "<campo>"
    When invoco el servicio de autenticación
    Then obtengo un status code 400
    And un mensaje que indica que el "<mensaje>"
    Examples:
      | campo    | mensaje                              |
      |  usuario | El nombre de usuario es obligatorio. |
      |  clave   | La clave es obligatoria.             |
      |          | La clave es obligatoria.             |
      |          | El nombre de usuario es obligatorio. |