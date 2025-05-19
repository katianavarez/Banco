/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import DAO.ITransaccionDAO;
import DTO.TransaccionDTO;
import Entidades.Transaccion;
import Excepciones.PersistenciaException;
import Exception.NegocioException;
import Mapper.TransaccionMapper;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author katia
 */
public class TransaccionBO {
    private final ITransaccionDAO transaccionDAO;
    private final TransaccionMapper mapper;

    public TransaccionBO(ITransaccionDAO transaccionDAO) {
        this.transaccionDAO = transaccionDAO;
        this.mapper = new TransaccionMapper();
    }

    public TransaccionDTO obtenerPorId(int idTransaccion) throws NegocioException {
        try {
            Transaccion t = transaccionDAO.obtenerTransaccionPorId(idTransaccion);
            return t != null ? mapper.toDTO(t) : null;
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al obtener transacción.", e);
        }
    }

    public List<TransaccionDTO> obtenerTransaccionesPorCuenta(int idCuenta) throws NegocioException {
        try {
            return transaccionDAO.obtenerTransaccionesPorCuenta(idCuenta).stream()
                    .map(mapper::toDTO)
                    .toList();
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al obtener transacciones.", e);
        }
    }

    public List<TransaccionDTO> obtenerTransaccionesConFiltro(int idCuenta, String tipo, LocalDate fechaInicio, LocalDate fechaFin) throws NegocioException {
        try {
            return transaccionDAO.obtenerTransaccionesPorCuentaConFiltro(idCuenta, tipo, fechaInicio, fechaFin)
                    .stream()
                    .map(mapper::toDTO)
                    .toList();
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al filtrar transacciones.", e);
        }
    }
}
