package com.examenPersona.persona.controller;

import com.examenPersona.persona.model.BeanPersona;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ServletPersona",
        urlPatterns = {
                "/get-personas",
                "/add-persona",
                "/create-persona",
                "/save-persona",
                "/get-persona",
                "/delete-persona"
        })
public class ServletPersona extends HttpServlet {
    private String action;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        action = request.getServletPath();
        switch (action) {
            case "/add-persona":
                try {
                    String fileName;
                    for (Part part : request.getParts()) {
                        fileName = part.getSubmittedFileName();
                        if (fileName != null) {
                            part.write(uploadPath + fileName);
                        }
                    }

                    String name = request.getParameter("name");
                    String surname = request.getParameter("surname");
                    String curp = request.getParameter("curp");
                    String birthday = request.getParameter("birthday");

                    BeanPersona persona = new BeanPersona();
                    persona.setFileName(fileName);
                    persona.setName(name);
                    persona.setApellido(Double.parseDouble(damage));
                    persona.setWeight(Double.parseDouble(peso));
                    persona.setHeight(Double.parseDouble(estatura));
                    persona.setHealth(Double.parseDouble(health));
                    persona.setPersonaType(type);
                    ResultAction result = servicePersona.save(persona);
                    urlRedirect = "/get-personas?result=" +
                            result.isResult() + "&message=" +
                            URLEncoder.encode(result.getMessage(), StandardCharsets.UTF_8.name())
                            + "&status=" + result.getStatus();
                } catch (Exception e) {
                    Logger.getLogger(ServletPersona.class.getName()).log(Level.SEVERE, "Error Persona" + e.getMessage());
                    urlRedirect = "/get-personas?result=false&message" +
                            URLEncoder.encode("Error al registrar a la persona", StandardCharsets.UTF_8.name())
                            + "&status=400";
                }
                break;

            case "/save-persona":
                String nombre2 = request.getParameter("name");
                String type2 = request.getParameter("type");
                String health2 = request.getParameter("health");
                String estatura2 = request.getParameter("estatura");
                String damage2 = request.getParameter("damage");
                String peso2 = request.getParameter("peso");
                String id = request.getParameter("id");
                BeanPersona persona2 = new BeanPersona();
                persona2.setId(Long.parseLong(id));
                persona2.setName(nombre2);
                persona2.setPower(Double.parseDouble(damage2));
                persona2.setWeight(Double.parseDouble(peso2));
                persona2.setHeight(Double.parseDouble(estatura2));
                persona2.setHealth(Double.parseDouble(health2));
                persona2.setPersonaType(type2);
                ResultAction result2 = servicePersona.update(persona2);
                urlRedirect = "/get-personas?result=" +
                        result2.isResult() + "&message=" +
                        URLEncoder.encode(result2.getMessage(), StandardCharsets.UTF_8.name())
                        + "&status=" + result2.getStatus();
                break;
            case "/delete-persona":
                String idPersona = request.getParameter("id");
                ResultAction deleteResult = servicePersona.delete(idPersona);
                urlRedirect = "/get-personas?result=" +
                        deleteResult.isResult() + "&message=" +
                        URLEncoder.encode(deleteResult.getMessage(), StandardCharsets.UTF_8.name())
                        + "&status=" + deleteResult.getStatus();
                break;
            default:
                urlRedirect = "/get-personas";
                break;
        }
        response.sendRedirect(request.getContextPath() + urlRedirect);
    }
}
