package co.edu.uniquindio.ingesis.autenticacion.seguridad;

import io.jsonwebtoken.io.Encoders;
import org.eclipse.microprofile.config.ConfigProvider;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase utilitaria para el cargue de llaves
 * <p>
 * <a href="https://github.com/jwtk/jjwt">libraría usada</a>
 * <p>
 * @author Alexandra Ruiz Gaona
 * @author Christian A. Candela-Uribe
 * @author Luis E. Sepúlveda-Rodríguez
 * @since 2023
 * <p>
 * (<a href="https://raw.githubusercontent.com/grid-uq/poo/main/LICENSE">Licencia GNU/GPL V3.0</a>)
 */
public class KeyLoaderUtil {
    private static final Logger LOG= Logger.getLogger(KeyLoaderUtil.class.getName());
    /**
     * Nombre de la propiedad que contiene la llave
     */
    private final String propertyKey;
    /**
     * Nombre de la propiedad que contiene la ubicación del archivo que contiene la llave
     */
    private final String propertyKeyLocation;

    /**
     * Constructor de la clase
     * @param propertyKey Nombre de la propiedad que contiene la llave
     * @param propertyKeyLocation Nombre de la propiedad que contiene la ubicación del archivo que contiene la llave
     */
    public KeyLoaderUtil(String propertyKey, String propertyKeyLocation) {
        this.propertyKey = propertyKey;
        this.propertyKeyLocation = propertyKeyLocation;
    }

    /**
     * Obtiene la llave en forma de String eliminando las marcas de inicio y fin,
     * inicialmente las búsca en el archivo de propiedades, si no las encuentra las carga desde archivo.
     * @return Optional con la llave en forma de String sin las marcas de inicio y fin de llave,
     * si no encuentra ninguna llave retorna un Optional vacío.
     */
    public Optional<String> loadKeyAsString(){
        var optionalKey = readFromPropertyKey();
        if( optionalKey.isEmpty() ){
            optionalKey = readFromFile();
        }
        return optionalKey.map(this::removeBeginEnd);
    }

    /**
     * Permite obtener la llave desde el archivo de propiedades
     * @return Optional con la llave en formato String, o un Optional vacío en caso de no encontrar ninguna llave.
     */
    private Optional<String> readFromPropertyKey(){
        return ConfigProvider.getConfig().getOptionalValue(propertyKey, String.class);
    }

    /**
     * Lee de desde un archivo la llave pública a ser usada
     * @return String compuesto por la llave pública
     */
    private Optional<String> readFromFile() {
        return readFromFileConfigurated(propertyKeyLocation);
    }

    /**
     * Lee de desde un archivo la llave a ser usada
     * @param key ruta del archivo que contiene la llave a ser leida.
     * @return String compuesto por la llave leida
     */
    private Optional<String> readFromFileConfigurated(String key) {
        var optionalKey = ConfigProvider.getConfig().getOptionalValue(key, String.class);
        return optionalKey.map( this::readFromFile ).filter(Optional::isPresent).map(Optional::get);
    }

    /**
     * Permite obtener el Path a un archivo dado su nombre.
     * @param fileName nombre del archivo del que se quiere obtener el Path
     * @return Path del archivo especificado por el filename
     * @throws URISyntaxException Se genera la excepción cuando no se puede convertir el nombre del archivo a URI
     */
    private Path getPathToKeyFile(String fileName) throws URISyntaxException {
        Path path;
        URL url = TokenUtil.class.getResource(fileName);
        if (url != null) {
            path = Paths.get(url.toURI());
        } else {
            path = Paths.get(fileName);
        }
        return path;
    }

    /**
     * Remueve la marca de inicio y fin de la llave
     * @param pem String que contiene la llave
     * @return String con la llave sin las marcas de inicio y fin.
     */
    private String removeBeginEnd(String pem) {
        pem = pem.replaceAll("-----BEGIN (.*)-----", "");
        pem = pem.replaceAll("-----END (.*)----", "");
        pem = pem.replaceAll("\r\n", "");
        pem = pem.replaceAll("\n", "");
        return pem.trim();
    }
    protected static void saveKey(Key key, String fileName, String prefijo, String sufijo) {
        String secretString = prefijo + Encoders.BASE64.encode(key.getEncoded()) + sufijo;
        Path path = Paths.get(fileName);
        try {
            Files.write(path, secretString.getBytes());
        } catch (IOException e) {
            LOG.log(Level.WARNING,e.getLocalizedMessage(),e);
        }
    }

    /**
     * Lee de desde un archivo la llave a ser usada
     * @return Optional con la llave en su representación de String, en caso de no poder leerla retorna un Optional vacío
     */
    private Optional<String> readFromFile(String filePath) {
        String content = null;
        try{
            Path path = getPathToKeyFile(filePath);
            if (path.toFile().exists()) {
                content = new String(Files.readAllBytes(path));
            }
        } catch (URISyntaxException | IOException e){
            LOG.log(Level.WARNING,e.getLocalizedMessage(),e);
        }
        return Optional.ofNullable(content);
    }
}
