/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.IConexionBD;
import Entidades.EstadoRetiro;
import Entidades.Retiro;
import Excepciones.PersistenciaException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author katia
 */
public class RetiroDAO implements IRetiroDAO{
    private final IConexionBD conexionBD;

    public RetiroDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }
    
    @Override
    public Retiro registrarRetiro(int idCuentaOrigen, double monto) throws PersistenciaException {
        String sqlTransaccion = "INSERT INTO Transacciones (monto, idCuentaOrigen) VALUES (?, ?)";
        String sqlRetiro = "INSERT INTO Retiros (idTransaccion, folio, contraseña, estado) VALUES (?, ?, ?, ?)";

        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement psTrans = conn.prepareStatement(sqlTransaccion, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement psRetiro = conn.prepareStatement(sqlRetiro)) {

            conn.setAutoCommit(false); 

            psTrans.setDouble(1, monto);
            psTrans.setInt(2, idCuentaOrigen);
            psTrans.executeUpdate();

            ResultSet rs = psTrans.getGeneratedKeys();
            if (!rs.next()) {
                throw new PersistenciaException("Error al generar la transacción del retiro.");
            }
            int idTransaccion = rs.getInt(1);
            LocalDateTime fechaHora = LocalDateTime.now();
            int folio = generarFolio();
            String contraseña = generarContraseña();

            psRetiro.setInt(1, idTransaccion);
            psRetiro.setInt(2, folio);
            psRetiro.setString(3, contraseña);
            psRetiro.setString(4, EstadoRetiro.NO_COBRADO.name());
            psRetiro.executeUpdate();

            conn.commit();
            return new Retiro(folio, contraseña, EstadoRetiro.NO_COBRADO, idTransaccion, monto, fechaHora, idCuentaOrigen);

        } catch (SQLException e) {
            throw new PersistenciaException("Error al registrar retiro", e);
        }
    }
    
    @Override
    public boolean cobrarRetiro(int idTransaccion) throws PersistenciaException {
        String sqlValidar = """
            SELECT r.estado, t.fechaHora
            FROM Retiros r
            JOIN Transacciones t ON r.idTransaccion = t.idTransaccion
            WHERE r.idTransaccion = ?
        """;

        String sqlActualizar = "UPDATE Retiros SET estado = 'COBRADO' WHERE idTransaccion = ? AND estado = 'NO_COBRADO'";

        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement psValidar = conn.prepareStatement(sqlValidar);
             PreparedStatement psActualizar = conn.prepareStatement(sqlActualizar)) {

            psValidar.setInt(1, idTransaccion);
            ResultSet rs = psValidar.executeQuery();

            if (rs.next()) {
                String estado = rs.getString("estado");
                LocalDateTime fechaHora = rs.getTimestamp("fechaHora").toLocalDateTime();

                if (estado.equalsIgnoreCase("COBRADO")) {
                    throw new PersistenciaException("El retiro ya ha sido cobrado.");
                }

                long minutosTranscurridos = Duration.between(fechaHora, LocalDateTime.now()).toMinutes();
                if (minutosTranscurridos > 10) {
                    throw new PersistenciaException("El retiro ha expirado. Solo se puede cobrar dentro de los primeros 10 minutos.");
                }

                psActualizar.setInt(1, idTransaccion);
                return psActualizar.executeUpdate() > 0;

            } else {
                throw new PersistenciaException("El retiro no existe.");
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Error al cobrar retiro", e);
        }
    }



    @Override
    public Retiro obtenerRetiroPorId(int idTransaccion) throws PersistenciaException {
        String sql = "SELECT r.idTransaccion, t.monto, t.fechaHora, t.idCuentaOrigen, r.folio, r.contraseña, r.estado "
                   + "FROM Transacciones t "
                   + "JOIN Retiros r ON t.idTransaccion = r.idTransaccion "
                   + "WHERE r.idTransaccion = ?";

        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTransaccion);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Retiro(
                    rs.getInt("folio"),
                    rs.getString("contraseña"),
                    EstadoRetiro.valueOf(rs.getString("estado")),
                    rs.getInt("idTransaccion"),
                    rs.getBigDecimal("monto").doubleValue(),
                    rs.getTimestamp("fechaHora").toLocalDateTime(),
                    rs.getInt("idCuentaOrigen")
                );
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al obtener retiro por ID", e);
        }
        return null;
    }
    
    @Override
    public List<Retiro> obtenerRetirosCobradosPorCuenta(int idCuenta) throws PersistenciaException {
        List<Retiro> retiros = new ArrayList<>();
        String sql = "SELECT r.idTransaccion, t.monto, t.fechaHora, t.idCuentaOrigen, r.folio, r.contraseña, r.estado "
                   + "FROM Transacciones t "
                   + "JOIN Retiros r ON t.idTransaccion = r.idTransaccion "
                   + "WHERE t.idCuentaOrigen = ? AND r.estado = 'COBRADO' "
                   + "ORDER BY t.fechaHora DESC"; 

        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCuenta);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                retiros.add(new Retiro(
                    rs.getInt("folio"),
                    rs.getString("contraseña"),
                    EstadoRetiro.valueOf(rs.getString("estado")),
                    rs.getInt("idTransaccion"),
                    rs.getBigDecimal("monto").doubleValue(), 
                    rs.getTimestamp("fechaHora").toLocalDateTime(),
                    rs.getInt("idCuentaOrigen")
                ));
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al obtener retiros cobrados de la cuenta", e);
        }
        return retiros; 
    }

    @Override
    public boolean cobrarRetiroPorFolioYContraseña(int folio, String contraseña) throws PersistenciaException {
        String sqlValidar = """
            SELECT r.idTransaccion, r.estado, t.fechaHora
            FROM Retiros r
            JOIN Transacciones t ON r.idTransaccion = t.idTransaccion
            WHERE r.folio = ? AND r.contraseña = ?
        """;

        String sqlActualizar = "UPDATE Retiros SET estado = 'COBRADO' WHERE idTransaccion = ? AND estado = 'NO_COBRADO'";

        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement psValidar = conn.prepareStatement(sqlValidar);
             PreparedStatement psActualizar = conn.prepareStatement(sqlActualizar)) {

            psValidar.setInt(1, folio);
            psValidar.setString(2, contraseña);
            ResultSet rs = psValidar.executeQuery();

            if (rs.next()) {
                int idTransaccion = rs.getInt("idTransaccion");
                String estado = rs.getString("estado");
                LocalDateTime fechaHora = rs.getTimestamp("fechaHora").toLocalDateTime();

                if (estado.equalsIgnoreCase("COBRADO")) {
                    throw new PersistenciaException("El retiro ya fue cobrado.");
                }

                long minutosTranscurridos = Duration.between(fechaHora, LocalDateTime.now()).toMinutes();
                if (minutosTranscurridos > 10) {
                    throw new PersistenciaException("El retiro ha expirado. Solo es válido durante los primeros 10 minutos.");
                }

                psActualizar.setInt(1, idTransaccion);
                return psActualizar.executeUpdate() > 0;

            } else {
                throw new PersistenciaException("Folio o contraseña incorrectos.");
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Error al intentar cobrar el retiro", e);
        }
    }

    @Override
    public List<Retiro> obtenerRetirosFiltradosPorCuenta(int idCuenta, LocalDateTime fechaInicio, LocalDateTime fechaFin) throws PersistenciaException{
        List<Retiro> retiros = new ArrayList<>();
        String sql = "SELECT r.idTransaccion, t.monto, t.fechaHora, t.idCuentaOrigen, r.folio, r.contraseña, r.estado " +
                     "FROM Transacciones t " +
                     "JOIN Retiros r ON t.idTransaccion = r.idTransaccion " +
                     "WHERE t.idCuentaOrigen = ? AND r.estado = 'COBRADO'";

        if (fechaInicio != null) {
            sql += " AND t.fechaHora >= ?";
        }
        if (fechaFin != null) {
            sql += " AND t.fechaHora <= ?";
        }

        sql += " ORDER BY t.fechaHora DESC";

        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int index = 1;
            ps.setInt(index++, idCuenta);

            if (fechaInicio != null) {
                ps.setTimestamp(index++, Timestamp.valueOf(fechaInicio));
            }
            if (fechaFin != null) {
                ps.setTimestamp(index++, Timestamp.valueOf(fechaFin));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                retiros.add(new Retiro(
                    rs.getInt("folio"),
                    rs.getString("contraseña"),
                    EstadoRetiro.valueOf(rs.getString("estado")),
                    rs.getInt("idTransaccion"),
                    rs.getBigDecimal("monto").doubleValue(),
                    rs.getTimestamp("fechaHora").toLocalDateTime(),
                    rs.getInt("idCuentaOrigen")
                ));
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Error al obtener retiros filtrados", e);
        }

        return retiros;
    }

    

    private int generarFolio() {
        return 100000 + (int) (Math.random() * 900000);
    }

    private String generarContraseña() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        StringBuilder contraseña = new StringBuilder();
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < 8; i++) { 
            int index = random.nextInt(caracteres.length());
            contraseña.append(caracteres.charAt(index));
        }
        return contraseña.toString();
    }
}
