/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Entidades.Cuenta;
import Excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author katia
 */
public interface ICuentaDAO {
    public boolean registrarCuenta(Cuenta cuenta) throws PersistenciaException;
    public Cuenta obtenerCuentaPorId(int idCuenta) throws PersistenciaException;
    public boolean actualizarSaldo(int idCuenta, double nuevoSaldo) throws PersistenciaException;
    public List<Cuenta> obtenerCuentasPorCliente(int idCliente) throws PersistenciaException;
    public boolean cancelarCuenta(int idCuenta) throws PersistenciaException;
    public Cuenta obtenerCuentaPorNumero(long numCuenta) throws PersistenciaException;
}
