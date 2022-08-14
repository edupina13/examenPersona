package com.examenPersona.persona.model;

import com.examenPersona.persona.utills.MySQLConnection;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoPersona {

    public class DaoPokemon {
        Connection conn;
        PreparedStatement pstm;
        CallableStatement cstm;
        ResultSet rs;

        public List<BeanPersona> findAll() {
            List<BeanPersona> personas = new LinkedList<>();
            BeanPersona persona = null;
            try {
                conn = new MySQLConnection().getConnection();
                String query = "SELECT * FROM personas;";
                pstm = conn.prepareStatement(query);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    persona = new BeanPersona();
                    persona.setId(rs.getInt("id"));
                    persona.setName(rs.getString("name"));
                    persona.setSurname(rs.getString("surname"));
                    persona.setCurp(rs.getString("curp"));
                    persona.setBirthday(rs.getString("birthday"));
                    personas.add(persona);
                }
            } catch (SQLException e) {
                Logger.getLogger(DaoPokemon.class.getName())
                        .log(Level.SEVERE, "Error findAll", e);
            } finally {
                closeConnections();
            }
            return personas;
        }

        public boolean save(BeanPersona persona) {
            try {
                conn = new MySQLConnection().getConnection();
                String query = "INSERT INTO personas" +
                        "(id, name, surname, curp, birthday)" +
                        " VALUES (?,?,?,?,?)";
                pstm = conn.prepareStatement(query);
                pstm.setInt(1, persona.getId());
                pstm.setString(2, persona.getName());
                pstm.setString(3, persona.getSurname());
                pstm.setString(4, persona.getCurp());
                pstm.setString(5, persona.getBirthday());

                return pstm.executeUpdate() == 1;
            } catch (SQLException e) {
                Logger.getLogger(DaoPokemon.class.getName())
                        .log(Level.SEVERE, "Error save", e);
                return false;
            } finally {
                closeConnections();
            }
        }



        public boolean update(BeanPersona pokemon) {
            try {
                conn = new MySQLConnection().getConnection();
                String query = "UPDATE personas SET name = ?, surname = ?, curp = ?," +
                        "birthday = ? WHERE id = ?";
                pstm = conn.prepareStatement(query);
                pstm.setString(1, pokemon.getName());
                pstm.setString(2, pokemon.getSurname());
                pstm.setString(3, pokemon.getCurp());
                pstm.setString(4, pokemon.getBirthday());



                return pstm.executeUpdate() == 1;
            } catch (SQLException e) {
                Logger.getLogger(DaoPokemon.class.getName())
                        .log(Level.SEVERE, "Error update", e);
                return false;
            } finally {
                closeConnections();
            }
        }

        public boolean delete(Long id) {
            try {
                conn = new MySQLConnection().getConnection();
                String query = "DELETE FROM personas WHERE id = ?";
                pstm = conn.prepareStatement(query);
                pstm.setLong(1, id);
                return pstm.executeUpdate() == 1;
            } catch (SQLException e) {
                Logger.getLogger(DaoPokemon.class.getName())
                        .log(Level.SEVERE, "Error delete method");
                return false;
            } finally {
                closeConnections();
            }
        }

        public void closeConnections() {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
                if (cstm != null) {
                    cstm.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
            }
        }
    }

}
