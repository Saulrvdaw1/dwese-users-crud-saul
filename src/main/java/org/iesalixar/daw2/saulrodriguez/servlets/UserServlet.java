package org.iesalixar.daw2.saulrodriguez.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.iesalixar.daw2.saulrodriguez.daos.DatabaseConnectionManager;
import org.iesalixar.daw2.saulrodriguez.daos.UserDAO;
import org.iesalixar.daw2.saulrodriguez.daos.UserDAOImpl;
import org.iesalixar.daw2.saulrodriguez.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Servlet encargado de gestionar el CRUD de usuarios.
 *
 * @author Saúl Rodríguez
 */
@WebServlet("/users")
public class UserServlet extends HttpServlet {

    private static final Logger logger =
            LoggerFactory.getLogger(UserServlet.class);

    private UserDAO userDAO;

    /**
     * Inicializa el DAO de usuarios.
     */
    @Override
    public void init() {

        logger.info("Entrando en init()");

        userDAO = new UserDAOImpl(
                DatabaseConnectionManager.getConnection()
        );

        logger.info("Saliendo de init()");
    }

    /**
     * Gestiona las peticiones GET.
     *
     * @param request petición HTTP
     * @param response respuesta HTTP
     */
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        logger.info("Entrando en doGet()");

        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        switch (action) {

            case "new":
                showNewForm(request, response);
                break;

            case "edit":
                showEditForm(request, response);
                break;

            default:
                listUsers(request, response);
                break;
        }

        logger.info("Saliendo de doGet()");
    }

    /**
     * Gestiona las peticiones POST.
     *
     * @param request petición HTTP
     * @param response respuesta HTTP
     */
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        logger.info("Entrando en doPost()");

        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }

        switch (action) {

            case "insert":
                insertUser(request, response);
                break;

            case "update":
                updateUser(request, response);
                break;

            case "delete":
                deleteUser(request, response);
                break;

            default:
                response.sendRedirect(
                        request.getContextPath() + "/users"
                );
                break;
        }

        logger.info("Saliendo de doPost()");
    }

    /**
     * Muestra la lista de usuarios.
     */
    private void listUsers(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        logger.info("Entrando en listUsers()");

        List<User> users = userDAO.findAll();

        request.setAttribute("users", users);

        request.getRequestDispatcher(
                "/views/users/user-list.jsp"
        ).forward(request, response);

        logger.info("Saliendo de listUsers()");
    }

    /**
     * Muestra el formulario para crear un usuario.
     */
    private void showNewForm(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        logger.info("Entrando en showNewForm()");

        request.getRequestDispatcher(
                "/views/users/user-form.jsp"
        ).forward(request, response);

        logger.info("Saliendo de showNewForm()");
    }

    /**
     * Muestra el formulario para editar un usuario.
     */
    private void showEditForm(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        logger.info("Entrando en showEditForm()");

        Long id = Long.valueOf(
                request.getParameter("id")
        );

        User user = userDAO.findById(id);

        request.setAttribute("user", user);

        request.getRequestDispatcher(
                "/views/users/user-form.jsp"
        ).forward(request, response);

        logger.info("Saliendo de showEditForm()");
    }

    /**
     * Inserta un nuevo usuario.
     */
    private void insertUser(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        logger.info("Entrando en insertUser()");

        String username = request.getParameter("username");
        String passwordHash = request.getParameter("passwordHash");
        String lastPasswordChangeString =
                request.getParameter("lastPasswordChange");

        String[] requiredFields = {
                username,
                passwordHash,
                lastPasswordChangeString
        };

        for (String field : requiredFields) {

            if (field == null || field.isBlank()) {

                request.setAttribute(
                        "error",
                        "Hay campos obligatorios vacíos"
                );

                showNewForm(request, response);
                return;
            }
        }

        if (userDAO.existsByUsername(username)) {

            request.setAttribute(
                    "error",
                    "El nombre de usuario ya existe"
            );

            showNewForm(request, response);
            return;
        }

        Boolean active =
                Boolean.valueOf(request.getParameter("active"));

        Boolean accountNonLocked =
                Boolean.valueOf(
                        request.getParameter("accountNonLocked")
                );

        LocalDateTime lastPasswordChange =
                LocalDateTime.parse(lastPasswordChangeString);

        LocalDateTime passwordExpiresAt =
                lastPasswordChange.plusMonths(3);

        Integer failedLoginAttempts =
                Integer.valueOf(
                        request.getParameter("failedLoginAttempts")
                );

        Boolean emailVerified =
                Boolean.valueOf(
                        request.getParameter("emailVerified")
                );

        Boolean mustChangePassword =
                Boolean.valueOf(
                        request.getParameter("mustChangePassword")
                );

        User user = new User(
                null,
                username,
                passwordHash,
                active,
                accountNonLocked,
                lastPasswordChange,
                passwordExpiresAt,
                failedLoginAttempts,
                emailVerified,
                mustChangePassword
        );

        userDAO.insert(user);

        response.sendRedirect(
                request.getContextPath() + "/users"
        );

        logger.info("Saliendo de insertUser()");
    }

    /**
     * Actualiza un usuario existente.
     */
    private void updateUser(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        logger.info("Entrando en updateUser()");

        Long id = Long.valueOf(request.getParameter("id"));

        String username = request.getParameter("username");
        String passwordHash = request.getParameter("passwordHash");

        LocalDateTime lastPasswordChange =
                LocalDateTime.parse(
                        request.getParameter("lastPasswordChange")
                );

        LocalDateTime passwordExpiresAt =
                lastPasswordChange.plusMonths(3);

        Boolean active =
                Boolean.valueOf(request.getParameter("active"));

        Boolean accountNonLocked =
                Boolean.valueOf(
                        request.getParameter("accountNonLocked")
                );

        Integer failedLoginAttempts =
                Integer.valueOf(
                        request.getParameter("failedLoginAttempts")
                );

        Boolean emailVerified =
                Boolean.valueOf(
                        request.getParameter("emailVerified")
                );

        Boolean mustChangePassword =
                Boolean.valueOf(
                        request.getParameter("mustChangePassword")
                );

        User user = new User(
                id,
                username,
                passwordHash,
                active,
                accountNonLocked,
                lastPasswordChange,
                passwordExpiresAt,
                failedLoginAttempts,
                emailVerified,
                mustChangePassword
        );

        userDAO.update(user);

        response.sendRedirect(
                request.getContextPath() + "/users"
        );

        logger.info("Saliendo de updateUser()");
    }

    /**
     * Elimina un usuario.
     */
    private void deleteUser(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        logger.info("Entrando en deleteUser()");

        Long id = Long.valueOf(
                request.getParameter("id")
        );

        userDAO.delete(id);

        response.sendRedirect(
                request.getContextPath() + "/users"
        );

        logger.info("Saliendo de deleteUser()");
    }
}