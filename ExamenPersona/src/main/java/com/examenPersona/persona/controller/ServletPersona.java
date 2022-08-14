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
                    for (Part part : request.getParts()) {
                        fileName = part.getSubmittedFileName();
                        if (fileName != null) {
                            part.write(uploadPath + fileName);
                        }
                    }

                    String name = request.getParameter("nombre");
                    String surname = request.getParameter("apellido");
                    String curp = request.getParameter("curp");
                    String birth = request.getParameter("fecha_nacimiento");

                    BeanPersona persona = new BeanPersona();
                    persona.setFileName(fileName);
                    persona.setNombre(nombre);
                    persona.setApellido(Double.parseDouble(damage));
                    persona.setWeight(Double.parseDouble(peso));
                    persona.setHeight(Double.parseDouble(estatura));
                    persona.setHealth(Double.parseDouble(health));
                    persona.setPersonaType(type);
                    ResultAction result = servicePokemon.save(pokemon);
                    urlRedirect = "/get-pokemons?result=" +
                            result.isResult() + "&message=" +
                            URLEncoder.encode(result.getMessage(), StandardCharsets.UTF_8.name())
                            + "&status=" + result.getStatus();
                } catch (Exception e) {
                    Logger.getLogger(ServletPokemon.class.getName()).log(Level.SEVERE, "Error Pokemon" + e.getMessage());
                    urlRedirect = "/get-pokemons?result=false&message" +
                            URLEncoder.encode("Error al registrar el pokemon", StandardCharsets.UTF_8.name())
                            + "&status=400";
                }
                break;

            case "/save-pokemon":
                String nombre2 = request.getParameter("name");
                String type2 = request.getParameter("type");
                String health2 = request.getParameter("health");
                String estatura2 = request.getParameter("estatura");
                String damage2 = request.getParameter("damage");
                String peso2 = request.getParameter("peso");
                String id = request.getParameter("id");
                BeanPokemon pokemon2 = new BeanPokemon();
                pokemon2.setId(Long.parseLong(id));
                pokemon2.setName(nombre2);
                pokemon2.setPower(Double.parseDouble(damage2));
                pokemon2.setWeight(Double.parseDouble(peso2));
                pokemon2.setHeight(Double.parseDouble(estatura2));
                pokemon2.setHealth(Double.parseDouble(health2));
                pokemon2.setPokemonType(type2);
                ResultAction result2 = servicePokemon.update(pokemon2);
                urlRedirect = "/get-pokemons?result=" +
                        result2.isResult() + "&message=" +
                        URLEncoder.encode(result2.getMessage(), StandardCharsets.UTF_8.name())
                        + "&status=" + result2.getStatus();
                break;
            case "/delete-persona":
                String idPokemon = request.getParameter("id");
                ResultAction deleteResult = servicePersona.delete(idPersona);
                urlRedirect = "/get-pokemons?result=" +
                        deleteResult.isResult() + "&message=" +
                        URLEncoder.encode(deleteResult.getMessage(), StandardCharsets.UTF_8.name())
                        + "&status=" + deleteResult.getStatus();
                break;
            default:
                urlRedirect = "/get-personass";
                break;
        }
        response.sendRedirect(request.getContextPath() + urlRedirect);
    }
}

