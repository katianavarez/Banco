/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.capanegociobanco;

import BO.CuentaBO;
import Conexion.ConexionBD;
import DAO.CuentaDAO;
import DAO.ICuentaDAO;
import DTO.CuentaDTO;
import Exception.NegocioException;
import java.util.List;

/**
 *
 * @author katia
 */
public class PruebaCuenta {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        ICuentaDAO cuentaDAO = new CuentaDAO(new ConexionBD());
//        CuentaBO cuentaBO = new CuentaBO(cuentaDAO);
//
//        try {
//            int idCliente = 10;
//
//            // 1. Registrar una nueva cuenta
//            CuentaDTO nuevaCuenta = new CuentaDTO();
//            nuevaCuenta.setSaldo(4500.00);
//            nuevaCuenta.setIdCliente(idCliente);
//
//            boolean registrada = cuentaBO.registrarCuenta(nuevaCuenta);
//            System.out.println("Cuenta registrada: " + registrada);
//
//            // 2. Obtener todas las cuentas del cliente
//            List<CuentaDTO> cuentas = cuentaBO.obtenerPorCliente(idCliente);
//            System.out.println("\nCuentas del cliente " + idCliente + ":");
//            for (CuentaDTO c : cuentas) {
//                System.out.println(" - ID: " + c.getIdCuenta() +
//                                   ", NumCuenta: " + c.getNumCuenta() +
//                                   ", Fecha: " + c.getFechaApertura() +
//                                   ", Saldo: $" + c.getSaldo() +
//                                   ", Estado: " + c.getEstado());
//            }
//
//            // Usaremos la última cuenta registrada
//            CuentaDTO cuenta = cuentas.get(cuentas.size() - 1);
//            int idCuenta = cuenta.getIdCuenta();
//
//            // 3. Obtener cuenta por ID
//            CuentaDTO cuentaObtenida = cuentaBO.obtenerPorId(idCuenta);
//            System.out.println("\nCuenta obtenida por ID: " + cuentaObtenida.getNumCuenta());
//
//            // 4. Actualizar saldo
//            boolean saldoActualizado = cuentaBO.actualizarSaldo(idCuenta, 9999.99);
//            System.out.println("Saldo actualizado: " + saldoActualizado);
//
//            // Confirmar nuevo saldo
//            CuentaDTO cuentaActualizada = cuentaBO.obtenerPorId(idCuenta);
//            System.out.println("Saldo actual: $" + cuentaActualizada.getSaldo());
//
//            // 5. Cancelar la cuenta
//            boolean cancelada = cuentaBO.cancelarCuenta(idCuenta);
//            System.out.println("Cuenta cancelada: " + cancelada);
//
//            // Confirmar estado
//            CuentaDTO cuentaCancelada = cuentaBO.obtenerPorId(idCuenta);
//            System.out.println("Estado final: " + cuentaCancelada.getEstado());
//
//        } catch (NegocioException e) {
//            System.err.println("Error de negocio: " + e.getMessage());
//        }
    
    }
    
}
