/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Entidades.Transaccion;
import Excepciones.PersistenciaException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author katia
 */
public interface ITransaccionDAO {
    public Transaccion obtenerTransaccionPorId(int idTransaccion) throws PersistenciaException;
    public List<Transaccion> obtenerTransaccionesPorCuenta(int idCuenta) throws PersistenciaException;
    public List<Transaccion> obtenerTransaccionesPorCuentaConFiltro(int idCuenta, String tipo, LocalDate fechaInicio, LocalDate fechaFin) throws PersistenciaException;
}
