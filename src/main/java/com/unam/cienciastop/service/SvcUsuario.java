package com.unam.cienciastop.service;

import java.util.List;

import com.unam.cienciastop.dto.CarreraDTO;
import com.unam.cienciastop.dto.TopCincoSemanaUsuariosDTO;
import com.unam.cienciastop.dto.UsuarioDTO;
import org.springframework.http.ResponseEntity;

import com.unam.cienciastop.entity.Usuario;

public interface SvcUsuario {
    List<Usuario> getUsuariosActivos();

    /*
     * Metodo que recibe un nombre y regresa la lista de objetos Usuario asociado a dicho nombre.
     */
    public List<Usuario> getUsuarios_nombre(String nombre);

    /*
     * Metodo que lista las carreras y sus alumnos activos.
     */
    public List<CarreraDTO> getUsuariosCarrera();

    /*
     * Metodo que lista los 5 usuarios con mas rentas en el mes.
     */
    public List<TopCincoSemanaUsuariosDTO> getTopCincoUsuariosRentasSemana();

    /*
     * Metodo que recibe un numero institucional y regresa la lista de objetos Usuario asociados a
     * dicho numero.
     */
    public List<Usuario> getUsuarios_numeroInstitucional(String num_institucional);

    /**
     * Metodo que recibe un correo y regresa la lista de objetos Usuario asociados a dicho correo.
     */
    public List<Usuario> getUsuarios_correo(String correo);

    Usuario crearUsuario(Usuario usuario);

    Usuario findByNumInstitucional(String username);

    public Usuario editarUsuario(Integer id_usuario, UsuarioDTO usuarioDTO);

    public Usuario getUsuario_id(Integer id);

    /**
     * Método que marca un usuario como inactivo
     * @param id_usuario
     * @return Usuario
     */
    public Usuario deleteUsuario(Integer id_usuario, String numInstitucionalUsuario);

    public Usuario getPerfil(String numInstitucional);; 
}
