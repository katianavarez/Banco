/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mapper;

import DTO.TransaccionDTO;
import Entidades.Transaccion;

/**
 *
 * @author katia
 */
public class TransaccionMapper {
    public TransaccionDTO toDTO(Transaccion entidad) {
        return new TransaccionDTO(
            entidad.getIdTransaccion(),
            entidad.getMonto(),
            entidad.getFechaHora(),
            entidad.getIdCuentaOrigen()
        );
    }

    public Transaccion toEntidad(TransaccionDTO dto) {
        return new Transaccion(
            dto.getIdTransaccion(),
            dto.getMonto(),
            dto.getFechaHora(),
            dto.getIdCuentaOrigen()
        );
    }
}
