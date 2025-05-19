/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mapper;

import DTO.ClienteDTO;
import Entidades.Cliente;

/**
 *
 * @author katia
 */
public class ClienteMapper {
    public ClienteDTO toDTO(Cliente entidad) {
        if (entidad == null) return null;
        return new ClienteDTO(
            entidad.getIdCliente(),
            entidad.getNombre(),
            entidad.getApellidoPaterno(),
            entidad.getApellidoMaterno(),
            entidad.getDomicilio(),
            entidad.getFechaNacimiento()
        );
    }

    public Cliente toEntidad(ClienteDTO dto, String contraseñaEncriptada) {
        if (dto == null) return null;
        return new Cliente(
            dto.getIdCliente(),
            dto.getNombre(),
            dto.getApellidoPaterno(),
            dto.getApellidoMaterno(),
            dto.getDomicilio(),
            dto.getFechaNacimiento(),
            contraseñaEncriptada
        );
    }
}
