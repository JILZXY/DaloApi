package org.example.repository;

import org.example.config.DatabaseConfig;
import org.example.model.Calificacion;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalificacionRepository {

    // findAll: regresa una lista de todas las calificaciones
    public List<Calificacion> findAll() throws SQLException {
        List<Calificacion> calificaciones = new ArrayList<>();
        String query = "SELECT * FROM CALIFICACION";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement stmt = c.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                Calificacion cal = new Calificacion();
                cal.setIdCalificacion(rs.getInt("id_calificacion"));
                cal.setIdUsuarioCliente(rs.getInt("id_usuario_cliente"));
                cal.setIdUsuarioAbogado(rs.getInt("id_usuario_abogado"));
                cal.setIdRespuesta(rs.getInt("id_respuesta"));
                cal.setAtencion(rs.getDouble("atencion"));
                cal.setProfesionalismo(rs.getDouble("profesionalismo"));
                cal.setClaridad(rs.getDouble("claridad"));
                cal.setEmpatia(rs.getDouble("empatia"));
                cal.setCalificacionGeneral(rs.getDouble("calificacion_general"));
                cal.setFechaCalificacion(rs.getTimestamp("fecha_calificacion"));
                calificaciones.add(cal);
            }
        }
        return calificaciones;
    }

    // save: inserta una nueva calificación y regresa el ID generado
    public int save(Calificacion calificacion) {
        String query = "INSERT INTO CALIFICACION (id_usuario_cliente, id_usuario_abogado, id_respuesta, atencion, profesionalismo, claridad, empatia, calificacion_general, fecha_calificacion) VALUES (?,?,?,?,?,?,?,?,?)";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, calificacion.getIdUsuarioCliente());
            ps.setInt(2, calificacion.getIdUsuarioAbogado());
            ps.setInt(3, calificacion.getIdRespuesta());
            ps.setDouble(4, calificacion.getAtencion());
            ps.setDouble(5, calificacion.getProfesionalismo());
            ps.setDouble(6, calificacion.getClaridad());
            ps.setDouble(7, calificacion.getEmpatia());
            ps.setDouble(8, calificacion.calcularPromedio());
            ps.setTimestamp(9, calificacion.getFechaCalificacion());

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error insertando calificación", e);
        }
        return -1;
    }

    // update: actualiza una calificación existente
    public boolean update(int id_calificacion, Calificacion calificacion) {
        String query = "UPDATE CALIFICACION SET id_usuario_cliente = ?, id_usuario_abogado = ?, id_respuesta = ?, atencion = ?, profesionalismo = ?, claridad = ?, empatia = ?, calificacion_general = ?, fecha_calificacion = ? WHERE id_calificacion = ?";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            ps.setInt(1, calificacion.getIdUsuarioCliente());
            ps.setInt(2, calificacion.getIdUsuarioAbogado());
            ps.setInt(3, calificacion.getIdRespuesta());
            ps.setDouble(4, calificacion.getAtencion());
            ps.setDouble(5, calificacion.getProfesionalismo());
            ps.setDouble(6, calificacion.getClaridad());
            ps.setDouble(7, calificacion.getEmpatia());
            ps.setDouble(8, calificacion.getCalificacionGeneral());
            ps.setTimestamp(9, calificacion.getFechaCalificacion());
            ps.setInt(10, id_calificacion);

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error actualizando calificación", e);
        }
    }

    // delete: elimina una calificación por su ID
    public boolean delete(int id_calificacion) {
        String query = "DELETE FROM CALIFICACION WHERE id_calificacion = ?";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            ps.setInt(1, id_calificacion);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error eliminando calificación", e);
        }
    }


    public Map<String, BigDecimal> promedioAbogado(int idAbogado) throws SQLException {
        String sql = """
        SELECT 
            AVG(atencion)       AS atencion,
            AVG(profesionalismo) AS profesionalismo,
            AVG(claridad)       AS claridad,
            AVG(empatia)        AS empatia
        FROM CALIFICACION
        WHERE id_usuario_abogado = ?
    """;

        try (
                Connection conn = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, idAbogado);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    BigDecimal atencion        = rs.getBigDecimal("atencion");
                    BigDecimal profesionalismo = rs.getBigDecimal("profesionalismo");
                    BigDecimal claridad        = rs.getBigDecimal("claridad");
                    BigDecimal empatia         = rs.getBigDecimal("empatia");

                    // Si no hay ningún promedio, devuelve ceros directamente
                    if (atencion == null && profesionalismo == null && claridad == null && empatia == null) {
                        return generarResumenConCeros();
                    }

                    // Se garantiza que BigDecimal.setScale no se aplica a null
                    BigDecimal total = BigDecimal.ZERO;
                    int contador = 0;

                    Map<String, BigDecimal> resumen = new HashMap<>();

                    if (atencion != null) {
                        resumen.put("atencion", atencion.setScale(1, RoundingMode.HALF_UP));
                        total = total.add(atencion);
                        contador++;
                    } else resumen.put("atencion", BigDecimal.ZERO);

                    if (profesionalismo != null) {
                        resumen.put("profesionalismo", profesionalismo.setScale(1, RoundingMode.HALF_UP));
                        total = total.add(profesionalismo);
                        contador++;
                    } else resumen.put("profesionalismo", BigDecimal.ZERO);

                    if (claridad != null) {
                        resumen.put("claridad", claridad.setScale(1, RoundingMode.HALF_UP));
                        total = total.add(claridad);
                        contador++;
                    } else resumen.put("claridad", BigDecimal.ZERO);

                    if (empatia != null) {
                        resumen.put("empatia", empatia.setScale(1, RoundingMode.HALF_UP));
                        total = total.add(empatia);
                        contador++;
                    } else resumen.put("empatia", BigDecimal.ZERO);

                    BigDecimal promedio = (contador > 0)
                            ? total.divide(new BigDecimal(contador), 1, RoundingMode.HALF_UP)
                            : BigDecimal.ZERO;

                    resumen.put("promedio_general", promedio);
                    return resumen;
                }
            }
        }

        return generarResumenConCeros();
    }

    // Método auxiliar
    private Map<String, BigDecimal> generarResumenConCeros() {
        Map<String, BigDecimal> vacio = new HashMap<>();
        vacio.put("atencion", BigDecimal.ZERO);
        vacio.put("profesionalismo", BigDecimal.ZERO);
        vacio.put("claridad", BigDecimal.ZERO);
        vacio.put("empatia", BigDecimal.ZERO);
        vacio.put("promedio_general", BigDecimal.ZERO);
        return vacio;
    }

    // promediosPorRespuesta: calcula los promedios individuales y general para una respuesta específica
    public Map<String, BigDecimal> promediosPorRespuesta(int idRespuesta) throws SQLException {
        String query = """
        SELECT 
            AVG(atencion) AS atencion,
            AVG(profesionalismo) AS profesionalismo,
            AVG(claridad) AS claridad,
            AVG(empatia) AS empatia
        FROM CALIFICACION
        WHERE id_respuesta = ?
    """;

        try (
                Connection conn = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setInt(1, idRespuesta);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Map<String, BigDecimal> resultado = new HashMap<>();
                    BigDecimal atencion        = rs.getBigDecimal("atencion");
                    BigDecimal profesionalismo = rs.getBigDecimal("profesionalismo");
                    BigDecimal claridad        = rs.getBigDecimal("claridad");
                    BigDecimal empatia         = rs.getBigDecimal("empatia");

                    BigDecimal total = BigDecimal.ZERO;
                    int contador = 0;

                    if (atencion != null) {
                        resultado.put("atencion", atencion.setScale(1, RoundingMode.HALF_UP));
                        total = total.add(atencion);
                        contador++;
                    } else resultado.put("atencion", BigDecimal.ZERO);

                    if (profesionalismo != null) {
                        resultado.put("profesionalismo", profesionalismo.setScale(1, RoundingMode.HALF_UP));
                        total = total.add(profesionalismo);
                        contador++;
                    } else resultado.put("profesionalismo", BigDecimal.ZERO);

                    if (claridad != null) {
                        resultado.put("claridad", claridad.setScale(1, RoundingMode.HALF_UP));
                        total = total.add(claridad);
                        contador++;
                    } else resultado.put("claridad", BigDecimal.ZERO);

                    if (empatia != null) {
                        resultado.put("empatia", empatia.setScale(1, RoundingMode.HALF_UP));
                        total = total.add(empatia);
                        contador++;
                    } else resultado.put("empatia", BigDecimal.ZERO);

                    BigDecimal promedioGeneral = (contador > 0)
                            ? total.divide(new BigDecimal(contador), 1, RoundingMode.HALF_UP)
                            : BigDecimal.ZERO;

                    resultado.put("promedio_general", promedioGeneral);
                    return resultado;
                }
            }
        }

        // Si no hay registros, devuelve mapa con ceros
        Map<String, BigDecimal> vacio = new HashMap<>();
        vacio.put("atencion", BigDecimal.ZERO);
        vacio.put("profesionalismo", BigDecimal.ZERO);
        vacio.put("claridad", BigDecimal.ZERO);
        vacio.put("empatia", BigDecimal.ZERO);
        vacio.put("promedio_general", BigDecimal.ZERO);
        return vacio;
    }

}

