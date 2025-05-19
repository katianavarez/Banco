/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Entidades.Transferencia;
import Excepciones.PersistenciaException;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author katia
 */
public interface ITransferenciaDAO {
    boolean realizarTransferencia(int idCuentaOrigen, int idCuentaDestino, double monto) throws PersistenciaException;
    Transferencia obtenerTransferenciaPorId(int idTransaccion) throws PersistenciaException;
    List<Transferencia> obtenerTransferenciasPorCuenta(int idCuenta) throws PersistenciaException;
    public List<Transferencia> obtenerTransferenciasPorRangoFecha(int idCuenta, LocalDateTime fechaInicio, LocalDateTime fechaFin) throws PersistenciaException;
    public List<Transferencia> obtenerTransferenciasFiltradas(int idCuenta, boolean origen, boolean destino, LocalDateTime fechaInicio, LocalDateTime fechaFin) throws PersistenciaException;
}