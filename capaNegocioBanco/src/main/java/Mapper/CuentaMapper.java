/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mapper;

import DTO.CuentaDTO;
import Entidades.Cuenta;
import Entidades.EstadoCuenta;

/**
 *
 * @author katia
 */
public class CuentaMapper {
    public CuentaDTO toDTO(Cuenta entidad) {
        if (entidad == null) return null;
        return new CuentaDTO(
            entidad.getIdCuenta(),
            entidad.getNumCuenta(),
            entidad.getFechaApertura(),
            entidad.getSaldo(),
            entidad.getIdCliente(),
            entidad.getEstado().name()
        );
    }

    public Cuenta toEntidad(CuentaDTO dto) {
        if (dto == null) return null;
        return new Cuenta(
            dto.getIdCuenta(),
            dto.getNumCuenta(),
            dto.getFechaApertura(),
            dto.getSaldo(),
            dto.getIdCliente(),
            EstadoCuenta.valueOf(dto.getEstado())
        );
    }
}
