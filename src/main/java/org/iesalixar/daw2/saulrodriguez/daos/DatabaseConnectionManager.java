package org.iesalixar.daw2.saulrodriguez.daos;

import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestiona la conexión a la base de datos utilizando el patrón Singleton.
 *
 * @author Saúl Rodríguez
 */
public class DatabaseConnectionManager {

    private static Connection connection = null;

    private static final Logger logger =
            LoggerFactory.getLogger(DatabaseConnectionManager.class);

    private static final Dotenv dotenv = Dotenv.load();

    /**
     * Constructor privado para evitar crear instancias.
     */
    private DatabaseConnectionManager() {
    }

    /**
     * Obtiene la conexión a la base de datos.
     *
     * @return conexión a la base de datos
     */
    public static Connection getConnection() {

        logger.info("Entrando en getConnection()");

        try {

            if (connection == null || connection.isClosed()) {

                logger.info("Iniciando la conexión a la base de datos...");

                String dbUrl = dotenv.get("DB_URL");
                String dbUser = dotenv.get("DB_USER");
                String dbPassword = dotenv.get("DB_PASSWORD");

                connection = DriverManager.getConnection(
                        dbUrl,
                        dbUser,
                        dbPassword
                );

                logger.info("Conexión a la base de datos establecida correctamente");
            }

        } catch (SQLException e) {

            logger.error(
                    "Error al conectar con la base de datos: {}",
                    e.getMessage(),
                    e
            );
        }

        logger.info("Saliendo de getConnection()");

        return connection;
    }

    /**
     * Cierra la conexión con la base de datos.
     */
    public static void closeConnection() {

        logger.info("Entrando en closeConnection()");

        try {

            if (connection != null && !connection.isClosed()) {

                connection.close();

                logger.info("Conexión cerrada correctamente");
            }

        } catch (SQLException e) {

            logger.error(
                    "Error al cerrar la conexión a la base de datos: {}",
                    e.getMessage(),
                    e
            );
        }

        logger.info("Saliendo de closeConnection()");
    }
}