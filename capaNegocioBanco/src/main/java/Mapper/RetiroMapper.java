/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mapper;

import DTO.RetiroDTO;
import Entidades.Retiro;

/**
 *
 * @author katia
 */
public class RetiroMapper {
    public RetiroDTO toDTO(Retiro retiro) {
        return new RetiroDTO(
            retiro.getIdTransaccion(),
            retiro.getMonto(),
            retiro.getFechaHora(),
            retiro.getIdCuentaOrigen(),
            retiro.getFolio(),
            retiro.getContraseña(),
            retiro.getEstado()
        );
    }

    public Retiro toEntidad(RetiroDTO dto) {
        return new Retiro(
            dto.getFolio(),
            dto.getContraseña(),
            dto.getEstado(),
            dto.getIdTransaccion(),
            dto.getMonto(),
            dto.getFechaHora(),
            dto.getIdCuentaOrigen()
        );
    }
}
