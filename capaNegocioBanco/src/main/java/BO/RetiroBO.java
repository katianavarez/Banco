/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import DAO.ICuentaDAO;
import DAO.IRetiroDAO;
import DTO.RetiroDTO;
import Entidades.Cuenta;
import Entidades.EstadoCuenta;
import Entidades.Retiro;
import Excepciones.PersistenciaException;
import Exception.NegocioException;
import Mapper.RetiroMapper;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author katia
 */
public class RetiroBO {
    private final IRetiroDAO retiroDAO;
    private final ICuentaDAO cuentaDAO;
    private final RetiroMapper mapper;

    public RetiroBO(IRetiroDAO retiroDAO, ICuentaDAO cuentaDAO) {
        this.retiroDAO = retiroDAO;
        this.cuentaDAO = cuentaDAO;
        this.mapper = new RetiroMapper();
    }

    public RetiroDTO registrarRetiro(int idCuentaOrigen, double monto) throws NegocioException {
        if (monto <= 0)
            throw new NegocioException("El monto del retiro debe ser mayor a 0.");

        try {
            Cuenta cuenta = cuentaDAO.obtenerCuentaPorId(idCuentaOrigen);
            if (cuenta == null || !cuenta.getEstado().equals(EstadoCuenta.Activa)) {
                throw new NegocioException("La cuenta origen no existe o no está activa.");
            }

            if (cuenta.getSaldo() < monto) {
                throw new NegocioException("Saldo insuficiente para realizar el retiro.");
            }
            double nuevoSaldo = cuenta.getSaldo() - monto;
            boolean actualizado = cuentaDAO.actualizarSaldo(cuenta.getIdCuenta(), nuevoSaldo);
            if (!actualizado) {
                throw new NegocioException("No se pudo actualizar el saldo de la cuenta.");
            }
            Retiro retiro = retiroDAO.registrarRetiro(idCuentaOrigen, monto);
            return mapper.toDTO(retiro);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al registrar el retiro.", e);
        }
    }

    public boolean cobrarRetiroPorFolioYContraseña(int folio, String contraseña) throws NegocioException {
        try {
            return retiroDAO.cobrarRetiroPorFolioYContraseña(folio, contraseña);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al cobrar el retiro.", e);
        }
    }

    public RetiroDTO obtenerRetiroPorId(int idTransaccion) throws NegocioException {
        try {
            return mapper.toDTO(retiroDAO.obtenerRetiroPorId(idTransaccion));
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al obtener el retiro por ID.", e);
        }
    }

    public List<RetiroDTO> obtenerRetirosCobradosPorCuenta(int idCuenta) throws NegocioException {
        try {
            return retiroDAO.obtenerRetirosCobradosPorCuenta(idCuenta).stream()
                    .map(mapper::toDTO)
                    .toList();
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al obtener retiros cobrados.", e);
        }
    }
    
    public boolean cobrarRetiro(int idTransaccion) throws NegocioException {
        try {
            return retiroDAO.cobrarRetiro(idTransaccion);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al cobrar retiro por ID de transacción.", e);
        }
    }
    
    public List<RetiroDTO> obtenerRetirosFiltradosPorCuenta(int idCuenta, LocalDateTime fechaInicio, LocalDateTime fechaFin) throws NegocioException {
        try {
            return retiroDAO.obtenerRetirosFiltradosPorCuenta(idCuenta, fechaInicio, fechaFin)
                            .stream()
                            .map(mapper::toDTO)
                            .toList();
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al obtener retiros filtrados.", e);
        }
    }

}
