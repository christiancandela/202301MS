package co.edu.uniquindio.ingesis.autenticacion.repository;

import java.io.Serializable;
/**
 * Interfaz que define un elemento a ser almacenado. Todo elemento a ser almacenado debe poder proporcionar su id.
 *
 * @author Alexandra Ruiz Gaona
 * @author Christian A. Candela-Uribe
 * @author Luis E. Sepúlveda-Rodríguez
 * @since 2023
 * <p>
 * (<a href="https://raw.githubusercontent.com/grid-uq/poo/main/LICENSE">Licencia GNU/GPL V3.0</a>)
 */
public interface Entity extends Serializable {
    String id();
}
