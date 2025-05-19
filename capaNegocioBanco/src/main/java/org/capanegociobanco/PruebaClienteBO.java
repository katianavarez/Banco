/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.capanegociobanco;

import BO.ClienteBO;
import Conexion.ConexionBD;
import DAO.ClienteDAO;
import DAO.IClienteDAO;
import DTO.ClienteDTO;
import Exception.NegocioException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author katia
 */
public class PruebaClienteBO {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        IClienteDAO clienteDAO = new ClienteDAO(new ConexionBD());
//        ClienteBO clienteBO = new ClienteBO(clienteDAO);
//
//        try {
//            // 1. Registrar cliente nuevo
//            ClienteDTO nuevoCliente = new ClienteDTO();
//            nuevoCliente.setNombre("Pedro");
//            nuevoCliente.setApellidoPaterno("Espinoza");
//            nuevoCliente.setApellidoMaterno("López");
//            nuevoCliente.setDomicilio("Av. No 123");
//            nuevoCliente.setFechaNacimiento(LocalDate.of(1991, 3, 10));
//
//            boolean registrado = clienteBO.registrarCliente(nuevoCliente, "12345678");
//            System.out.println("Cliente registrado: " + registrado);
//
//            // 2. Obtener por ID
//            ClienteDTO cliente = clienteBO.obtenerPorId(10);
//            System.out.println("Cliente obtenido: " + cliente.getNombre());
//
//            // 3. Actualizar cliente
//            cliente.setDomicilio("Nueva dirección 444");
//            boolean actualizado = clienteBO.actualizarCliente(cliente);
//            System.out.println("Cliente actualizado: " + actualizado);
//
//            // 4. Autenticación
//            ClienteDTO logueado = clienteBO.autenticarCliente(10, "12345678");
//            System.out.println("Login exitoso: " + logueado.getNombre());
//
//            // 5. Obtener todos
//            List<ClienteDTO> lista = clienteBO.obtenerTodos();
//            System.out.println("Clientes registrados: " + lista.size());
//
//        } catch (NegocioException e) {
//            System.err.println("Error de negocio: " + e.getMessage());
//        }
    }
    
}
