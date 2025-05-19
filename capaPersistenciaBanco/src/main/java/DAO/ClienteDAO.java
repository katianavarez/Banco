/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.IConexionBD;
import Entidades.Cliente;
import Excepciones.PersistenciaException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author katia
 */
public class ClienteDAO implements IClienteDAO{
    private final IConexionBD conexionBD;
    
    public ClienteDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    @Override
    public int registrarCliente(Cliente cliente) throws PersistenciaException{
        String sql = "INSERT INTO Clientes (nombre, apellidoPaterno, apellidoMaterno, domicilio, fechaNacimiento, contraseña) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
             
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellidoPaterno());
            ps.setString(3, cliente.getApellidoMaterno());
            ps.setString(4, cliente.getDomicilio());
            ps.setDate(5, java.sql.Date.valueOf(cliente.getFechaNacimiento()));
            ps.setString(6, cliente.getContraseña());
            
            int filas = ps.executeUpdate();
            if (filas == 0) {
                throw new PersistenciaException("No se pudo registrar el cliente.");
            }

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); 
            } else {
                throw new PersistenciaException("No se pudo obtener el ID del cliente.");
            }
            
        } catch (SQLException e) {
            throw new PersistenciaException("Error al registrar cliente", e);
        }
    }

    @Override
    public Cliente obtenerClientePorId(int idCliente) throws PersistenciaException{
        String sql = "SELECT * FROM Clientes WHERE idCliente = ?";
        
        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Cliente(
                    rs.getInt("idCliente"),
                    rs.getString("nombre"),
                    rs.getString("apellidoPaterno"),
                    rs.getString("apellidoMaterno"),
                    rs.getString("domicilio"),
                    rs.getDate("fechaNacimiento").toLocalDate(),
                    rs.getString("contraseña")
                );
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al obtener cliente por ID", e);
        }
        return null;
    }

    @Override
    public boolean actualizarCliente(Cliente cliente) throws PersistenciaException{
        String sql = "UPDATE Clientes SET nombre = ?, apellidoPaterno = ?, apellidoMaterno = ?, domicilio = ?, fechaNacimiento = ? WHERE idCliente = ?";
        
        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellidoPaterno());
            ps.setString(3, cliente.getApellidoMaterno());
            ps.setString(4, cliente.getDomicilio());
            ps.setDate(5, java.sql.Date.valueOf(cliente.getFechaNacimiento()));
            ps.setInt(6, cliente.getIdCliente());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new PersistenciaException("Error al actualizar cliente", e);
        }
    }

    @Override
    public List<Cliente> obtenerTodosLosClientes() throws PersistenciaException{
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Clientes";
        
        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                clientes.add(new Cliente(
                    rs.getInt("idCliente"),
                    rs.getString("nombre"),
                    rs.getString("apellidoPaterno"),
                    rs.getString("apellidoMaterno"),
                    rs.getString("domicilio"),
                    rs.getDate("fechaNacimiento").toLocalDate(),
                    rs.getString("contraseña")
                ));
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al obtener lista de clientes", e);
        }
        return clientes;
    }
    
    @Override
    public Cliente obtenerClientePorCredenciales(int idCliente, String contraseña) throws PersistenciaException {
        String sql = "SELECT * FROM Clientes WHERE idCliente = ? AND contraseña = ?";
        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCliente);
            ps.setString(2, contraseña);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Cliente(
                    rs.getInt("idCliente"),
                    rs.getString("nombre"),
                    rs.getString("apellidoPaterno"),
                    rs.getString("apellidoMaterno"),
                    rs.getString("domicilio"),
                    rs.getDate("fechaNacimiento").toLocalDate(),
                    rs.getString("contraseña")
                );
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al verificar credenciales", e);
        }
        return null;
    }

}
