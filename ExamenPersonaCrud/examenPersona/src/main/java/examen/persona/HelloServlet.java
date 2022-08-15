package examen.persona;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "helloServlet",
        urlPatterns = {
                "/get-personas",
                "/add-persona",
                "/create-persona",
                "/save-persona",
                "/get-persona",
                "/delete-persona"
        })

public class HelloServlet extends HttpServlet {
    String action;
    String urlRedirect = "/get-personas";
    DaoPersona ok = new DaoPersona();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        action = request.getServletPath();
        switch (action) {
            case "/get-personas":
                List<BeanPersona> personas = ok.findAll();
                System.out.println(personas.size());
                request.setAttribute("personas", personas);
                urlRedirect = "/consulta.jsp";
                break;
            case "/create-persona":
                urlRedirect = "/create.jsp";
                break;
            case "/get-persona":
                String id = request.getParameter("id");
                id = (id == null) ? "0" : id;
                try {
                    BeanPersona persona = ok.findOne(Long.parseLong(id));
                    request.setAttribute("persona", persona);
                    urlRedirect = "/update.jsp";
                } catch (Exception e) {
                    urlRedirect = "/get-personas";
                }
                break;
            default:
                request.setAttribute("personas", ok.findAll());
                urlRedirect = "/get-personas";
                break;
        }
        request.getRequestDispatcher(urlRedirect).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        action = request.getServletPath();
        switch (action) {
            case "/add-persona":
                String name = request.getParameter("name");
                String surname = request.getParameter("surname");
                String curp = request.getParameter("curp");
                String birthday = request.getParameter("birthday");
                BeanPersona persona = new BeanPersona();
                persona.setName(name);
                persona.setSurname(surname);
                persona.setCurp(curp);
                persona.setBirthday(birthday);
                ok.save(persona);
                urlRedirect = "/get-personas";
                break;
            case "/save-persona":
                String nameS = request.getParameter("name");
                String surnameS = request.getParameter("surname");
                String curpS = request.getParameter("curp");
                String birthdayS = request.getParameter("birthday");
                String id = request.getParameter("id");
                BeanPersona save = new BeanPersona();
                save.setId(Integer.parseInt((id)));
                save.setName(nameS);
                save.setSurname(surnameS);
                save.setCurp(curpS);
                save.setBirthday(birthdayS);
                ok.update(save);
                urlRedirect = "/get-personas";
                break;
            case "/delete-persona":
                String idP = request.getParameter("id");
                ok.delete(Long.valueOf(idP));
                urlRedirect = "/get-personas";
                break;
            default:
                urlRedirect = "/get-personas";
                break;
        }
        response.sendRedirect(request.getContextPath() + urlRedirect);
    }
}