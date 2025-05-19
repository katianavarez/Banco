/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.IConexionBD;
import Entidades.Cuenta;
import Entidades.EstadoCuenta;
import Excepciones.PersistenciaException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author katia
 */
public class CuentaDAO implements ICuentaDAO{
    private final IConexionBD conexionBD;

    public CuentaDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    @Override
    public boolean registrarCuenta(Cuenta cuenta) throws PersistenciaException {
        String sql = "INSERT INTO Cuentas (numCuenta, fechaApertura, saldo, idCliente, estado) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, cuenta.getNumCuenta());
            ps.setDate(2, java.sql.Date.valueOf(cuenta.getFechaApertura()));
            ps.setDouble(3, cuenta.getSaldo());
            ps.setInt(4, cuenta.getIdCliente());
            ps.setString(5, "Activa");

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new PersistenciaException("Error al registrar cuenta", e);
        }
    }

    @Override
    public Cuenta obtenerCuentaPorId(int idCuenta) throws PersistenciaException {
        String sql = "SELECT * FROM Cuentas WHERE idCuenta = ?";

        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCuenta);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Cuenta(
                    rs.getInt("idCuenta"),
                    rs.getLong("numCuenta"),
                    rs.getDate("fechaApertura").toLocalDate(),
                    rs.getDouble("saldo"),
                    rs.getInt("idCliente"),
                    EstadoCuenta.valueOf(rs.getString("estado"))
                );
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al obtener cuenta por ID", e);
        }
        return null;
    }


    @Override
    public boolean actualizarSaldo(int idCuenta, double nuevoSaldo) throws PersistenciaException {
        String sql = "UPDATE Cuentas SET saldo = ? WHERE idCuenta = ? AND estado = 'Activa'";

        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, nuevoSaldo);
            ps.setInt(2, idCuenta);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new PersistenciaException("Error al actualizar saldo de la cuenta", e);
        }
    }


    @Override
    public List<Cuenta> obtenerCuentasPorCliente(int idCliente) throws PersistenciaException {
        List<Cuenta> cuentas = new ArrayList<>();
        String sql = "SELECT * FROM Cuentas WHERE idCliente = ? AND estado = 'Activa'";

        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                cuentas.add(new Cuenta(
                    rs.getInt("idCuenta"),
                    rs.getLong("numCuenta"),
                    rs.getDate("fechaApertura").toLocalDate(),
                    rs.getDouble("saldo"),
                    rs.getInt("idCliente"),
                    EstadoCuenta.valueOf(rs.getString("estado"))
                ));
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al obtener cuentas de un cliente", e);
        }
        return cuentas;
    }

    
    @Override
    public boolean cancelarCuenta(int idCuenta) throws PersistenciaException {
        String sql = "UPDATE Cuentas SET estado = 'Cancelada' WHERE idCuenta = ? AND estado = 'Activa'";

        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCuenta);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new PersistenciaException("Error al cancelar la cuenta", e);
        }
    }
    
    public Cuenta obtenerCuentaPorNumero(long numCuenta) throws PersistenciaException {
        String sql = "SELECT * FROM Cuentas WHERE numCuenta = ?";

        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, numCuenta);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Cuenta(
                    rs.getInt("idCuenta"),
                    rs.getLong("numCuenta"),
                    rs.getDate("fechaApertura").toLocalDate(),
                    rs.getDouble("saldo"),
                    rs.getInt("idCliente"),
                    EstadoCuenta.valueOf(rs.getString("estado"))
                );
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al obtener cuenta por número", e);
        }
        return null;
    }


}
