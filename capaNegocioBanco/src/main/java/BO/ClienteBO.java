/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import DAO.IClienteDAO;
import DTO.ClienteDTO;
import Entidades.Cliente;
import Excepciones.PersistenciaException;
import Exception.NegocioException;
import Mapper.ClienteMapper;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author katia
 */
public class ClienteBO {
    private final IClienteDAO clienteDAO;
    private final ClienteMapper mapper;

    public ClienteBO(IClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
        this.mapper = new ClienteMapper();
    }

    public int registrarCliente(ClienteDTO dto, String contraseñaPlana) throws NegocioException {
        if (dto == null) {
            throw new NegocioException("Datos del cliente no proporcionados.");
        }

        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new NegocioException("El nombre del cliente es obligatorio.");
        } else if (!esNombreValido(dto.getNombre().trim())) {
            throw new NegocioException("El nombre solo puede contener letras y espacios.");
        }

        if (dto.getApellidoPaterno() == null || dto.getApellidoPaterno().trim().isEmpty()) {
            throw new NegocioException("El apellido paterno del cliente es obligatorio.");
        } else if (!esNombreValido(dto.getApellidoPaterno().trim())) {
            throw new NegocioException("El apellido paterno solo puede contener letras y espacios.");
        }

        if (dto.getApellidoMaterno() != null && !dto.getApellidoMaterno().trim().isEmpty()) {
            if (!esNombreValido(dto.getApellidoMaterno().trim())) {
                throw new NegocioException("El apellido materno solo puede contener letras y espacios.");
            }
        }

        if (dto.getDomicilio() == null || dto.getDomicilio().trim().isEmpty()) {
            throw new NegocioException("El domicilio del cliente es obligatorio.");
        }

        if (dto.getFechaNacimiento() == null || dto.getFechaNacimiento().isAfter(LocalDate.now())) {
            throw new NegocioException("La fecha de nacimiento no puede ser futura.");
        }

        if (contraseñaPlana == null || contraseñaPlana.trim().length() < 8) {
            throw new NegocioException("La contraseña debe tener al menos 8 caracteres.");
        }

        try {
            String encriptada = Seguridad.Encriptador.encriptar(contraseñaPlana.trim());
            Cliente entidad = mapper.toEntidad(dto, encriptada);
            return clienteDAO.registrarCliente(entidad);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al registrar cliente.", e);
        }
    }


    public ClienteDTO obtenerPorId(int id) throws NegocioException {
        try {
            Cliente cliente = clienteDAO.obtenerClientePorId(id);
            return mapper.toDTO(cliente);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al obtener cliente.", e);
        }
    }

    public boolean actualizarCliente(ClienteDTO dto) throws NegocioException {
        if (dto == null) {
            throw new NegocioException("Datos del cliente no proporcionados.");
        }

        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new NegocioException("El nombre del cliente es obligatorio.");
        } else if (!esNombreValido(dto.getNombre().trim())) {
            throw new NegocioException("El nombre solo puede contener letras y espacios.");
        }

        if (dto.getApellidoPaterno() == null || dto.getApellidoPaterno().trim().isEmpty()) {
            throw new NegocioException("El apellido paterno del cliente es obligatorio.");
        } else if (!esNombreValido(dto.getApellidoPaterno().trim())) {
            throw new NegocioException("El apellido paterno solo puede contener letras y espacios.");
        }

        if (dto.getApellidoMaterno() != null && !dto.getApellidoMaterno().trim().isEmpty()) {
            if (!esNombreValido(dto.getApellidoMaterno().trim())) {
                throw new NegocioException("El apellido materno solo puede contener letras y espacios.");
            }
        }

        if (dto.getDomicilio() == null || dto.getDomicilio().trim().isEmpty()) {
            throw new NegocioException("El domicilio del cliente es obligatorio.");
        }

        if (dto.getFechaNacimiento() == null || dto.getFechaNacimiento().isAfter(LocalDate.now())) {
            throw new NegocioException("La fecha de nacimiento no puede ser futura.");
        }

        try {
            Cliente entidad = clienteDAO.obtenerClientePorId(dto.getIdCliente());
            if (entidad == null) {
                throw new NegocioException("El cliente no existe.");
            }

            entidad.setNombre(dto.getNombre().trim());
            entidad.setApellidoPaterno(dto.getApellidoPaterno().trim());
            entidad.setApellidoMaterno(dto.getApellidoMaterno() != null ? dto.getApellidoMaterno().trim() : null);
            entidad.setDomicilio(dto.getDomicilio().trim());
            entidad.setFechaNacimiento(dto.getFechaNacimiento());

            return clienteDAO.actualizarCliente(entidad);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al actualizar cliente.", e);
        }
    }


    public List<ClienteDTO> obtenerTodos() throws NegocioException {
        try {
            List<Cliente> clientes = clienteDAO.obtenerTodosLosClientes();
            return clientes.stream()
                           .map(mapper::toDTO)
                           .toList();
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al obtener lista de clientes.", e);
        }
    }
    
    public ClienteDTO autenticarCliente(int idCliente, String contraseñaPlana) throws NegocioException {
        try {
            String encriptada = Seguridad.Encriptador.encriptar(contraseñaPlana.trim());
            Cliente cliente = clienteDAO.obtenerClientePorCredenciales(idCliente, encriptada);

            if (cliente == null) {
                throw new NegocioException("ID o contraseña incorrectos.");
            }

            return mapper.toDTO(cliente);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al autenticar cliente.", e);
        }
    }
    
    private boolean esNombreValido(String texto) {
        return texto != null && texto.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$");
    }


}
