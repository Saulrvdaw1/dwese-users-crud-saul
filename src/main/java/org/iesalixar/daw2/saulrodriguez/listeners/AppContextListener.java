package org.iesalixar.daw2.saulrodriguez.listeners;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import org.iesalixar.daw2.saulrodriguez.daos.DataInitializer;
import org.iesalixar.daw2.saulrodriguez.daos.DatabaseConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

/**
 * Listener que se ejecuta al iniciar y cerrar la aplicación.
 *
 * @author Saúl Rodríguez
 */
@WebListener
public class AppContextListener implements ServletContextListener {

    private static final Logger logger =
            LoggerFactory.getLogger(AppContextListener.class);

    /**
     * Se ejecuta al iniciar la aplicación.
     *
     * @param sce evento del contexto
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        logger.info("Entrando en contextInitialized()");

        DatabaseConnectionManager.getConnection();

        InputStream sqlFile =
                getClass().getClassLoader().getResourceAsStream("data.sql");

        DataInitializer.loadDataFromSQL(sqlFile);

        logger.info("Aplicación iniciada y base de datos inicializada");

        logger.info("Saliendo de contextInitialized()");
    }

    /**
     * Se ejecuta al cerrar la aplicación.
     *
     * @param sce evento del contexto
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        logger.info("Entrando en contextDestroyed()");

        DatabaseConnectionManager.closeConnection();

        logger.info("Saliendo de contextDestroyed()");
    }
}