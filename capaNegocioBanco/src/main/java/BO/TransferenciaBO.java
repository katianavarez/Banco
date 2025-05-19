/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import DAO.ICuentaDAO;
import DAO.ITransferenciaDAO;
import DTO.TransferenciaDTO;
import Entidades.Cuenta;
import Entidades.EstadoCuenta;
import Entidades.Transferencia;
import Excepciones.PersistenciaException;
import Exception.NegocioException;
import Mapper.TransferenciaMapper;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author katia
 */
public class TransferenciaBO {
    private final ITransferenciaDAO transferenciaDAO;
    private final ICuentaDAO cuentaDAO;
    private final TransferenciaMapper mapper;

    public TransferenciaBO(ITransferenciaDAO transferenciaDAO, ICuentaDAO cuentaDAO) {
        this.transferenciaDAO = transferenciaDAO;
        this.cuentaDAO = cuentaDAO;
        this.mapper = new TransferenciaMapper();
    }

    public boolean realizarTransferencia(int idCuentaOrigen, int idCuentaDestino, double monto) throws NegocioException {
        if (idCuentaOrigen == idCuentaDestino)
            throw new NegocioException("La cuenta origen y destino no pueden ser la misma.");

        if (monto <= 0)
            throw new NegocioException("El monto debe ser mayor a cero.");

        try {
            Cuenta origen = cuentaDAO.obtenerCuentaPorId(idCuentaOrigen);
            Cuenta destino = cuentaDAO.obtenerCuentaPorId(idCuentaDestino);

            if (origen == null)
                throw new NegocioException("La cuenta origen no existe.");
            
            if (destino == null)
                throw new NegocioException("La cuenta destino no existe.");

            if (!origen.getEstado().equals(EstadoCuenta.Activa) || !destino.getEstado().equals(EstadoCuenta.Activa))
                throw new NegocioException("Ambas cuentas deben estar activas.");

            if (origen.getSaldo() < monto)
                throw new NegocioException("Saldo insuficiente en la cuenta origen.");

            return transferenciaDAO.realizarTransferencia(idCuentaOrigen, idCuentaDestino, monto);

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al realizar la transferencia.", e);
        }
    }

    public TransferenciaDTO obtenerPorId(int idTransaccion) throws NegocioException {
        try {
            Transferencia transferencia = transferenciaDAO.obtenerTransferenciaPorId(idTransaccion);
            return mapper.toDTO(transferencia);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al obtener transferencia.", e);
        }
    }

    public List<TransferenciaDTO> obtenerPorCuenta(int idCuenta) throws NegocioException {
        try {
            return transferenciaDAO.obtenerTransferenciasPorCuenta(idCuenta).stream()
                    .map(mapper::toDTO)
                    .toList();
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al obtener transferencias de cuenta.", e);
        }
    }

    public List<TransferenciaDTO> obtenerPorRango(int idCuenta, LocalDateTime inicio, LocalDateTime fin) throws NegocioException {
        try {
            return transferenciaDAO.obtenerTransferenciasPorRangoFecha(idCuenta, inicio, fin).stream()
                    .map(mapper::toDTO)
                    .toList();
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al obtener transferencias por rango de fecha.", e);
        }
    }
    
    public List<TransferenciaDTO> obtenerTransferenciasFiltradas(int idCuenta, boolean org, boolean dest,
    LocalDateTime fechaInicio, LocalDateTime fechaFin) throws NegocioException {
    try {
        return transferenciaDAO
                .obtenerTransferenciasFiltradas(idCuenta, org, dest, fechaInicio, fechaFin)
                .stream()
                .map(mapper::toDTO)
                .toList();
    } catch (PersistenciaException e) {
        throw new NegocioException("Error al filtrar transferencias.", e);
    }
}

}
