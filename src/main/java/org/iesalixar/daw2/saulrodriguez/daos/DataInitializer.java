package org.iesalixar.daw2.saulrodriguez.daos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase encargada de inicializar la base de datos
 * mediante el archivo data.sql.
 *
 * @author Saúl Rodríguez
 */
public class DataInitializer {

    private static final Logger logger =
            LoggerFactory.getLogger(DataInitializer.class);

    /**
     * Carga y ejecuta las sentencias SQL contenidas en un archivo.
     *
     * @param sqlFileStream archivo SQL
     */
    public static void loadDataFromSQL(InputStream sqlFileStream) {

        logger.info("Entrando en loadDataFromSQL()");

        if (sqlFileStream == null) {

            logger.error("El archivo SQL no se ha encontrado");
            return;
        }

        Connection connection = DatabaseConnectionManager.getConnection();

        if (connection == null) {

            logger.error("No existe conexión con la base de datos");
            return;
        }

        try {

            StringBuilder sqlBuilder = new StringBuilder();

            try (BufferedReader reader =
                         new BufferedReader(new InputStreamReader(sqlFileStream))) {

                String line;

                while ((line = reader.readLine()) != null) {

                    if (!line.trim().startsWith("--")) {
                        sqlBuilder.append(line).append("\n");
                    }
                }
            }

            String[] statements = sqlBuilder.toString().split(";");

            connection.setAutoCommit(false);

            try (Statement statement = connection.createStatement()) {

                for (String sql : statements) {

                    if (!sql.isBlank()) {

                        logger.info("Ejecutando sentencia SQL");

                        statement.execute(sql.trim());
                    }
                }

                connection.commit();

                logger.info("Archivo data.sql ejecutado correctamente");

            } catch (SQLException e) {

                connection.rollback();

                logger.error(
                        "Error al ejecutar data.sql. Transacción revertida: {}",
                        e.getMessage(),
                        e
                );

            } finally {

                connection.setAutoCommit(true);
            }

        } catch (SQLException | IOException e) {

            logger.error(
                    "Error durante la inicialización de la base de datos: {}",
                    e.getMessage(),
                    e
            );
        }

        logger.info("Saliendo de loadDataFromSQL()");
    }
}