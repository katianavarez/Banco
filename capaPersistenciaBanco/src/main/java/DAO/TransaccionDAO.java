/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.IConexionBD;
import Entidades.Transaccion;
import Excepciones.PersistenciaException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author katia
 */
public class TransaccionDAO implements ITransaccionDAO{
    private final IConexionBD conexionBD;

    public TransaccionDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    @Override
    public Transaccion obtenerTransaccionPorId(int idTransaccion) throws PersistenciaException {
        String sql = "SELECT * FROM Transacciones WHERE idTransaccion = ?";

        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setInt(1, idTransaccion);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Transaccion(
                    rs.getInt("idTransaccion"),
                    rs.getDouble("monto"),
                    rs.getTimestamp("fechaHora").toLocalDateTime(),
                    rs.getInt("idCuentaOrigen")
                );
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al obtener transacción por ID", e);
        }
        return null;
    }

    @Override
    public List<Transaccion> obtenerTransaccionesPorCuenta(int idCuenta) throws PersistenciaException {
        List<Transaccion> transacciones = new ArrayList<>();
        String sql = """
            SELECT t.idTransaccion, t.monto, t.fechaHora, t.idCuentaOrigen
            FROM Transacciones t
            LEFT JOIN Retiros r ON t.idTransaccion = r.idTransaccion
            LEFT JOIN Transferencias tf ON t.idTransaccion = tf.idTransaccion
            WHERE t.idCuentaOrigen = ?
            AND (
                tf.idTransaccion IS NOT NULL
                OR r.estado = 'COBRADO'
                OR (r.idTransaccion IS NULL AND tf.idTransaccion IS NULL)
            )
            ORDER BY t.fechaHora DESC
        """;

        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCuenta);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                transacciones.add(new Transaccion(
                    rs.getInt("idTransaccion"),
                    rs.getDouble("monto"),
                    rs.getTimestamp("fechaHora").toLocalDateTime(),
                    rs.getInt("idCuentaOrigen")
                ));
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al obtener transacciones de una cuenta", e);
        }
        return transacciones;
    }
    
    @Override
    public List<Transaccion> obtenerTransaccionesPorCuentaConFiltro(int idCuenta, String tipo, LocalDate fechaInicio, LocalDate fechaFin) throws PersistenciaException {
        List<Transaccion> transacciones = new ArrayList<>();

        String sql = """
            SELECT t.idTransaccion, t.monto, t.fechaHora, t.idCuentaOrigen
            FROM Transacciones t
            LEFT JOIN Retiros r ON t.idTransaccion = r.idTransaccion
            LEFT JOIN Transferencias tf ON t.idTransaccion = tf.idTransaccion
            WHERE t.idCuentaOrigen = ?
            AND t.fechaHora BETWEEN ? AND ?
        """;

        if ("RETIRO".equalsIgnoreCase(tipo)) {
            sql += " AND r.idTransaccion IS NOT NULL AND r.estado = 'COBRADO'";
        } else if ("TRANSFERENCIA".equalsIgnoreCase(tipo)) {
            sql += " AND tf.idTransaccion IS NOT NULL";
        } else {
            sql += " AND (r.estado IS NULL OR r.estado = 'COBRADO')";
        }

        sql += " ORDER BY t.fechaHora DESC";

        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCuenta);
            ps.setTimestamp(2, Timestamp.valueOf(fechaInicio.atStartOfDay()));
            ps.setTimestamp(3, Timestamp.valueOf(fechaFin.atTime(23, 59, 59)));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                transacciones.add(new Transaccion(
                    rs.getInt("idTransaccion"),
                    rs.getDouble("monto"),
                    rs.getTimestamp("fechaHora").toLocalDateTime(),
                    rs.getInt("idCuentaOrigen")
                ));
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Error al obtener transacciones filtradas", e);
        }

        return transacciones;
    }
}
