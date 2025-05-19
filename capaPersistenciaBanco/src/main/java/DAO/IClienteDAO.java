/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entidades.Cliente;
import Excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author katia
 */
public interface IClienteDAO {
    public int registrarCliente(Cliente cliente) throws PersistenciaException;
    public Cliente obtenerClientePorId(int idCliente) throws PersistenciaException;
    public boolean actualizarCliente(Cliente cliente) throws PersistenciaException;
    public List<Cliente> obtenerTodosLosClientes() throws PersistenciaException;
    public Cliente obtenerClientePorCredenciales(int idCliente, String contraseña) throws PersistenciaException;
}
