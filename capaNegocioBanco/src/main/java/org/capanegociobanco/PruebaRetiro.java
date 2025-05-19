/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.capanegociobanco;

import BO.RetiroBO;
import Conexion.ConexionBD;
import DAO.CuentaDAO;
import DAO.ICuentaDAO;
import DAO.IRetiroDAO;
import DAO.RetiroDAO;
import DTO.RetiroDTO;
import Exception.NegocioException;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author katia
 */
public class PruebaRetiro {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        IRetiroDAO retiroDAO = new RetiroDAO(new ConexionBD());
//        ICuentaDAO cuentaDAO = new CuentaDAO(new ConexionBD());
//        RetiroBO retiroBO = new RetiroBO(retiroDAO, cuentaDAO);
//
//        int idCuenta = 1; 
//        double monto = 300;
//        
//        try {
//            // 1. Registrar un retiro
//            RetiroDTO retiro = retiroBO.registrarRetiro(idCuenta, monto);
//            System.out.println("Retiro registrado:");
//            System.out.println("Folio: " + retiro.getFolio() + " | Contraseña: " + retiro.getContraseña());
//
//            // 2. Obtener retiro por ID
//            RetiroDTO obtenido = retiroBO.obtenerRetiroPorId(retiro.getIdTransaccion());
//            System.out.println("\nRetiro obtenido:");
//            System.out.println("Monto: $" + obtenido.getMonto() + " | Estado: " + obtenido.getEstado());
//
//            // 3. Intentar cobrar retiro por folio y contraseña
//            boolean cobradoFolio = retiroBO.cobrarRetiroPorFolioYContraseña(obtenido.getFolio(), obtenido.getContraseña());
//            System.out.println("\nCobro por folio/contraseña exitoso: " + cobradoFolio);
//
//            // 4. Intentar cobrar retiro por ID
//            boolean cobradoId = retiroBO.cobrarRetiro(obtenido.getIdTransaccion());
//            System.out.println("Cobro por ID exitoso (debería fallar si ya fue cobrado): " + cobradoId);
//
//            // 5. Obtener retiros cobrados por cuenta
//            List<RetiroDTO> cobrados = retiroBO.obtenerRetirosCobradosPorCuenta(idCuenta);
//            System.out.println("\nRetiros cobrados:");
//            cobrados.forEach(r -> System.out.println("- ID: " + r.getIdTransaccion() + ", $" + r.getMonto() + ", Estado: " + r.getEstado()));
//
//            // 6. Obtener retiros por rango de fechas
//            LocalDateTime inicio = LocalDateTime.now().minusDays(15);
//            LocalDateTime fin = LocalDateTime.now();
//            List<RetiroDTO> filtrados = retiroBO.obtenerRetirosFiltradosPorCuenta(idCuenta, inicio, fin);
//            System.out.println("\nRetiros filtrados por fecha:");
//            filtrados.forEach(r -> System.out.println("- ID: " + r.getIdTransaccion() + ", $" + r.getMonto() + ", Fecha: " + r.getFechaHora()));
//
//        } catch (NegocioException e) {
//            System.err.println("Error de negocio: " + e.getMessage());
//        }   
    }
}
