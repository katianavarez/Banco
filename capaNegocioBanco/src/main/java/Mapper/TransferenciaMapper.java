/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mapper;

import DTO.TransferenciaDTO;
import Entidades.Transferencia;

/**
 *
 * @author katia
 */
public class TransferenciaMapper {
    public TransferenciaDTO toDTO(Transferencia entidad) {
        return new TransferenciaDTO(
            entidad.getIdTransaccion(),
            entidad.getMonto(),
            entidad.getFechaHora(),
            entidad.getIdCuentaOrigen(),
            entidad.getIdCuentaDestino()
        );
    }

    public Transferencia toEntidad(TransferenciaDTO dto) {
        return new Transferencia(
            dto.getIdCuentaDestino(),
            dto.getIdTransaccion(),
            dto.getMonto(),
            dto.getFechaHora(),
            dto.getIdCuentaOrigen()
        );
    }
}
