/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.capanegociobanco;

import BO.TransferenciaBO;
import Conexion.ConexionBD;
import DAO.CuentaDAO;
import DAO.ICuentaDAO;
import DAO.ITransferenciaDAO;
import DAO.TransferenciaDAO;
import DTO.TransferenciaDTO;
import Exception.NegocioException;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author katia
 */
public class PruebaTransferencia {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        ITransferenciaDAO transferenciaDAO = new TransferenciaDAO(new ConexionBD());
//        ICuentaDAO cuentaDAO = new CuentaDAO(new ConexionBD());
//        TransferenciaBO transferenciaBO = new TransferenciaBO(transferenciaDAO, cuentaDAO);
//
//        int idCuentaOrigen = 1;  
//        int idCuentaDestino = 2;
//
//        try {
//            // 1. Realizar transferencia
//            boolean realizada = transferenciaBO.realizarTransferencia(idCuentaOrigen, idCuentaDestino, 500.00);
//            System.out.println("Transferencia realizada: " + realizada);
//
//            // 2. Obtener transferencias por cuenta
//            List<TransferenciaDTO> lista = transferenciaBO.obtenerPorCuenta(idCuentaOrigen);
//            System.out.println("\nTransferencias de la cuenta origen:");
//            for (TransferenciaDTO t : lista) {
//                System.out.println("- ID: " + t.getIdTransaccion() +
//                                   ", Monto: $" + t.getMonto() +
//                                   ", Fecha: " + t.getFechaHora() +
//                                   ", Destino: " + t.getIdCuentaDestino());
//            }
//
//            // 3. Obtener por ID
//            if (!lista.isEmpty()) {
//                int idTransaccion = lista.get(0).getIdTransaccion();
//                TransferenciaDTO detalle = transferenciaBO.obtenerPorId(idTransaccion);
//                System.out.println("\nConsulta por ID: $" + detalle.getMonto() + " a cuenta " + detalle.getIdCuentaDestino());
//            }
//            
//            System.out.println("\nTransferencias donde soy origen o destino en los últimos 15 días:");
//            LocalDateTime desde = LocalDateTime.now().minusDays(15);
//            LocalDateTime hasta = LocalDateTime.now();
//
//            List<TransferenciaDTO> filtradas = transferenciaBO.obtenerTransferenciasFiltradas(
//                    idCuentaOrigen, true, true, desde, hasta);
//
//            for (TransferenciaDTO t : filtradas) {
//                System.out.println("- ID: " + t.getIdTransaccion()
//                                 + ", Monto: $" + t.getMonto()
//                                 + ", Fecha: " + t.getFechaHora()
//                                 + ", Origen: " + t.getIdCuentaOrigen()
//                                 + ", Destino: " + t.getIdCuentaDestino());
//            }
//
//
//        } catch (NegocioException e) {
//            System.err.println("Error de negocio: " + e.getMessage());
//        }
    }
    
}
