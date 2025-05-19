/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import DAO.ICuentaDAO;
import DTO.CuentaDTO;
import Entidades.Cuenta;
import Entidades.EstadoCuenta;
import Excepciones.PersistenciaException;
import Exception.NegocioException;
import Mapper.CuentaMapper;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author katia
 */
public class CuentaBO {
    private final ICuentaDAO cuentaDAO;
    private final CuentaMapper mapper;

    public CuentaBO(ICuentaDAO cuentaDAO) {
        this.cuentaDAO = cuentaDAO;
        this.mapper = new CuentaMapper();
    }

    public boolean registrarCuenta(CuentaDTO dto) throws NegocioException {
        if (dto == null) throw new NegocioException("Datos de cuenta no proporcionados.");

        if (dto.getSaldo() < 0) throw new NegocioException("El saldo inicial no puede ser negativo.");
        if (dto.getIdCliente() <= 0) throw new NegocioException("Debe especificarse un cliente válido.");

        try {
            long numCuentaGenerado = generarNumCuenta();
            LocalDate fecha = LocalDate.now();

            Cuenta cuenta = new Cuenta(
                numCuentaGenerado,
                fecha,
                dto.getSaldo(),
                dto.getIdCliente(),
                EstadoCuenta.Activa
            );

            return cuentaDAO.registrarCuenta(cuenta);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al registrar cuenta.", e);
        }
    }

    public CuentaDTO obtenerPorId(int idCuenta) throws NegocioException {
        try {
            Cuenta cuenta = cuentaDAO.obtenerCuentaPorId(idCuenta);
            return mapper.toDTO(cuenta);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al obtener cuenta.", e);
        }
    }

    public boolean actualizarSaldo(int idCuenta, double nuevoSaldo) throws NegocioException {
        if (nuevoSaldo < 0) throw new NegocioException("El saldo no puede ser negativo.");

        try {
            return cuentaDAO.actualizarSaldo(idCuenta, nuevoSaldo);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al actualizar saldo.", e);
        }
    }

    public boolean cancelarCuenta(int idCuenta) throws NegocioException {
        try {
            return cuentaDAO.cancelarCuenta(idCuenta);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al cancelar cuenta.", e);
        }
    }

    public List<CuentaDTO> obtenerPorCliente(int idCliente) throws NegocioException {
        try {
            return cuentaDAO.obtenerCuentasPorCliente(idCliente)
                    .stream()
                    .map(mapper::toDTO)
                    .toList();
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al obtener cuentas del cliente.", e);
        }
    }
    
    public CuentaDTO obtenerPorNumeroCuenta(long numCuenta) throws NegocioException {
        try {
            Cuenta cuenta = cuentaDAO.obtenerCuentaPorNumero(numCuenta);
            if (cuenta == null) return null;
            return mapper.toDTO(cuenta);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al buscar cuenta por número.", e);
        }
    }

    
    private long generarNumCuenta() {
        long base = 1000000000L;
        long random = (long) (Math.random() * 900000000L);
        return base + random;
    }

}
