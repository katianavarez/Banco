/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Entidades.Retiro;
import Excepciones.PersistenciaException;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author katia
 */
public interface IRetiroDAO {
    public boolean cobrarRetiro(int idTransaccion) throws PersistenciaException;
    public Retiro obtenerRetiroPorId(int idTransaccion) throws PersistenciaException;
    public Retiro registrarRetiro(int idCuentaOrigen, double monto) throws PersistenciaException;
    public List<Retiro> obtenerRetirosCobradosPorCuenta(int idCuenta) throws PersistenciaException;
    public boolean cobrarRetiroPorFolioYContraseña(int folio, String contraseña) throws PersistenciaException;
    public List<Retiro> obtenerRetirosFiltradosPorCuenta(int idCuenta, LocalDateTime fechaInicio, LocalDateTime fechaFin) throws PersistenciaException;
}