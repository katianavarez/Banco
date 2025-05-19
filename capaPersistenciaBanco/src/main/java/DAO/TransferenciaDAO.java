/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.IConexionBD;
import Entidades.Transferencia;
import Excepciones.PersistenciaException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author katia
 */
public class TransferenciaDAO implements ITransferenciaDAO {
    private final IConexionBD conexionBD;

    public TransferenciaDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    @Override
    public boolean realizarTransferencia(int idCuentaOrigen, int idCuentaDestino, double monto) throws PersistenciaException {
        if (monto <=0){
            throw new PersistenciaException("El monto de la transferencia debe ser mayor a 0");
        }
        String sql = "{CALL realizarTransferencia(?, ?, ?)}"; 

        try (Connection conn = conexionBD.crearConexion();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idCuentaOrigen);
            stmt.setInt(2, idCuentaDestino);
            stmt.setDouble(3, monto);

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new PersistenciaException("Error al realizar la transferencia: " + e.getMessage(), e);
        }
    }

    @Override
    public Transferencia obtenerTransferenciaPorId(int idTransaccion) throws PersistenciaException {
        String sql = "SELECT t.idTransaccion, t.monto, t.fechaHora, t.idCuentaOrigen, tr.idCuentaDestino "
                   + "FROM Transacciones t "
                   + "JOIN Transferencias tr ON t.idTransaccion = tr.idTransaccion "
                   + "WHERE t.idTransaccion = ?";

        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idTransaccion);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Transferencia(
                    rs.getInt("idCuentaDestino"),
                    rs.getInt("idTransaccion"),
                    rs.getBigDecimal("monto").doubleValue(),
                    rs.getTimestamp("fechaHora").toLocalDateTime(),
                    rs.getInt("idCuentaOrigen")
                );
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al obtener transferencia por ID", e);
        }
        return null;
    }

    @Override
    public List<Transferencia> obtenerTransferenciasPorCuenta(int idCuenta) throws PersistenciaException {
        List<Transferencia> transferencias = new ArrayList<>();
        String sql = "SELECT t.idTransaccion, t.monto, t.fechaHora, t.idCuentaOrigen, tr.idCuentaDestino "
                   + "FROM Transacciones t "
                   + "JOIN Transferencias tr ON t.idTransaccion = tr.idTransaccion "
                   + "WHERE t.idCuentaOrigen = ? OR tr.idCuentaDestino = ? "
                   + "ORDER BY t.fechaHora DESC";

        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCuenta);
            ps.setInt(2, idCuenta);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                transferencias.add(new Transferencia(
                    rs.getInt("idCuentaDestino"),
                    rs.getInt("idTransaccion"),
                    rs.getBigDecimal("monto").doubleValue(),
                    rs.getTimestamp("fechaHora").toLocalDateTime(),
                    rs.getInt("idCuentaOrigen")
                ));
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al obtener transferencias de una cuenta", e);
        }
        return transferencias;
    }
    
    @Override
    public List<Transferencia> obtenerTransferenciasPorRangoFecha(int idCuenta, LocalDateTime fechaInicio, LocalDateTime fechaFin) throws PersistenciaException {
        List<Transferencia> transferencias = new ArrayList<>();
        String sql = "SELECT t.idTransaccion, t.monto, t.fechaHora, t.idCuentaOrigen, tr.idCuentaDestino "
                   + "FROM Transacciones t "
                   + "JOIN Transferencias tr ON t.idTransaccion = tr.idTransaccion "
                   + "WHERE (t.idCuentaOrigen = ? OR tr.idCuentaDestino = ?) ";

        if (fechaInicio != null) {
            sql += "AND t.fechaHora >= ? ";
        }
        if (fechaFin != null) {
            sql += "AND t.fechaHora <= ? ";
        }

        sql += "ORDER BY t.fechaHora DESC";

        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCuenta);
            ps.setInt(2, idCuenta);

            int index = 3;
            if (fechaInicio != null) {
                ps.setTimestamp(index++, Timestamp.valueOf(fechaInicio));
            }
            if (fechaFin != null) {
                ps.setTimestamp(index++, Timestamp.valueOf(fechaFin));
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                transferencias.add(new Transferencia(
                    rs.getInt("idCuentaDestino"),
                    rs.getInt("idTransaccion"),
                    rs.getDouble("monto"),
                    rs.getTimestamp("fechaHora").toLocalDateTime(),
                    rs.getInt("idCuentaOrigen")
                ));
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Error al obtener transferencias por rango de fecha", e);
        }
        return transferencias;
    }

    @Override
    public List<Transferencia> obtenerTransferenciasFiltradas(int idCuenta, boolean origen, boolean destino, LocalDateTime fechaInicio, LocalDateTime fechaFin) throws PersistenciaException{
        List<Transferencia> transferencias = new ArrayList<>();

        if (!origen && !destino) {
            return transferencias; 
        }

        String sql = "SELECT t.idTransaccion, t.monto, t.fechaHora, t.idCuentaOrigen, tr.idCuentaDestino "
                   + "FROM Transacciones t "
                   + "JOIN Transferencias tr ON t.idTransaccion = tr.idTransaccion "
                   + "WHERE ";

        List<String> condiciones = new ArrayList<>();
        if (origen) {
            condiciones.add("t.idCuentaOrigen = ?");
        }
        if (destino) {
            condiciones.add("tr.idCuentaDestino = ?");
        }

        sql += "(" + String.join(" OR ", condiciones) + ")";

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

            if (origen) {
                ps.setInt(index++, idCuenta);
            }
            if (destino) {
                ps.setInt(index++, idCuenta);
            }
            if (fechaInicio != null) {
                ps.setTimestamp(index++, Timestamp.valueOf(fechaInicio));
            }
            if (fechaFin != null) {
                ps.setTimestamp(index++, Timestamp.valueOf(fechaFin));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                transferencias.add(new Transferencia(
                    rs.getInt("idCuentaDestino"),
                    rs.getInt("idTransaccion"),
                    rs.getDouble("monto"),
                    rs.getTimestamp("fechaHora").toLocalDateTime(),
                    rs.getInt("idCuentaOrigen")
                ));
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Error al obtener transferencias filtradas", e);
        }

        return transferencias;
    }


}