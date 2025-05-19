/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.capanegociobanco;

import BO.TransaccionBO;
import Conexion.ConexionBD;
import DAO.ITransaccionDAO;
import DAO.TransaccionDAO;
import DTO.TransaccionDTO;
import Exception.NegocioException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author katia
 */
public class PruebaTransaccion {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        ITransaccionDAO transaccionDAO = new TransaccionDAO(new ConexionBD());
//        TransaccionBO transaccionBO = new TransaccionBO(transaccionDAO);
//
//        int idCuenta = 1;
//
//        try {
//            // 1. Obtener todas las transacciones
//            List<TransaccionDTO> todas = transaccionBO.obtenerTransaccionesPorCuenta(idCuenta);
//            System.out.println("Transacciones totales:");
//            todas.forEach(t -> System.out.println("- ID: " + t.getIdTransaccion() + " $" + t.getMonto() + " [" + t.getFechaHora() + "]"));
//
//            // 2. Obtener por ID
//            if (!todas.isEmpty()) {
//                int id = todas.get(0).getIdTransaccion();
//                TransaccionDTO t = transaccionBO.obtenerPorId(id);
//                System.out.println("\nTransacción por ID: $" + t.getMonto() + " en " + t.getFechaHora());
//            }
//
//            // 3. Obtener transacciones filtradas por tipo y fechas
//            LocalDate inicio = LocalDate.now().minusDays(30);
//            LocalDate fin = LocalDate.now();
//            String tipo = "TRANSFERENCIA"; 
//
//            List<TransaccionDTO> filtradas = transaccionBO.obtenerTransaccionesConFiltro(idCuenta, tipo, inicio, fin);
//            System.out.println("\nTransacciones filtradas [" + tipo + "]:");
//            filtradas.forEach(t -> System.out.println("- ID: " + t.getIdTransaccion() + " $" + t.getMonto() + " [" + t.getFechaHora() + "]"));
//
//        } catch (NegocioException e) {
//            System.err.println("Error: " + e.getMessage());
//        }
    }
    
}
