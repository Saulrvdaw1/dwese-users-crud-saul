package org.iesalixar.daw2.saulrodriguez.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Locale;

/**
 * Servlet encargado de cambiar el idioma de la aplicación.
 *
 * @author Saúl Rodríguez
 */
@WebServlet("/changeLanguage")
public class LanguageServlet extends HttpServlet {

    private static final Logger logger =
            LoggerFactory.getLogger(LanguageServlet.class);

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        logger.info("Entrando en el método doGet del servlet LanguageServlet");

        String lang = request.getParameter("lang");

        Locale locale;

        if ("en".equals(lang)) {
            locale = Locale.ENGLISH;
            logger.info("Idioma seleccionado: inglés");
        } else {
            locale = new Locale("es");
            logger.info("Idioma seleccionado: español");
        }

        request.getSession().setAttribute("locale", locale);

        String referer = request.getHeader("referer");

        if (referer != null) {
            response.sendRedirect(referer);
        } else {
            response.sendRedirect(request.getContextPath() + "/users");
        }

        logger.info("Saliendo del método doGet del servlet LanguageServlet");
    }
}