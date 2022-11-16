package com.unam.cienciastop.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.unam.cienciastop.dao.DaoPumapuntos;
import com.unam.cienciastop.dao.DaoUsuario;
import com.unam.cienciastop.dto.UsuarioDTO;
import com.unam.cienciastop.entity.Pumapuntos;
import com.unam.cienciastop.entity.Usuario;
import com.unam.cienciastop.exceptionHandler.ApiException;

@Service
public class SvcUsuarioImpl implements SvcUsuario {

    @Autowired
    private DaoUsuario repoUsuario;

    @Autowired
    private DaoPumapuntos repoPumapuntos;

    @Override
    public List<Usuario> getUsuariosActivos() {
        try {
            return repoUsuario.getUsuariosActivos();
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.NOT_FOUND, "error en la consulta a la base de datos");
        } catch (Exception e) {
            throw new ApiException(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        }
    }

    /*
     * Metodo que recibe un nombre y regresa la lista de objetos Usuario asociado a dicho nombre.
     */
    @Override
    public List<Usuario> getUsuarios_nombre(String nombre) {
        try {
            return repoUsuario.getUsuarios_nombre(nombre);
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "error en la consulta a la base de datos");
        } catch (Exception e) {
            throw new ApiException(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        }
    }

    /*
     * Metodo que recibe un numero institucional y regresa la lista de objetos Usuario asociados a
     * dicho numero.
     */
    @Override
    public List<Usuario> getUsuarios_numeroInstitucional(String num_institucional) {
        try {
            return repoUsuario.getUsuarios_numeroInstitucional(num_institucional);
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "error en la consulta a la base de datos");
        } catch (Exception e) {
            throw new ApiException(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        }
    }

    /*
     * Metodo que recibe correo y regresa la lista de objetos Usuario asociados a dicho correo.
     */
    @Override
    public List<Usuario> getUsuarios_correo(String correo) {
        try {
            return repoUsuario.getUsuarios_correo(correo);
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "error en la consulta a la base de datos");
        } catch (Exception e) {
            throw new ApiException(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        }
    }

    /*
     * Metodo que recibe informacion de un usuario nuevo y lo crea en la base de datos
     */
    @Override
    public Usuario crearUsuario(Usuario usuario) {
        try {
            Usuario nuevo = (Usuario) repoUsuario.save(usuario);
            Pumapuntos registro = new Pumapuntos(LocalDate.now().getMonthValue(), 100, usuario);
            repoPumapuntos.save(registro);
            return nuevo;
        } catch (DataIntegrityViolationException e) {
            throw new ApiException(HttpStatus.NOT_FOUND,
                    "error, ya hay un usuario registrado con ese correo o no. cuenta / trabajador");
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "error en la consulta a la base de datos");
        } catch (Exception e) {
            throw new ApiException(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        }
    }

    @Override
    public Usuario editarUsuario(Integer id_usuario, UsuarioDTO usuarioDto) {
        Usuario usuario = repoUsuario.findById(id_usuario)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,
                        "error, no se puede modificar un usuario inexistente."));

        if (usuarioDto.getNombre() != null)
            usuario.setNombre(usuarioDto.getNombre());
        if (usuarioDto.getCorreo() != null)
            usuario.setCorreo(usuarioDto.getCorreo());
        if (usuarioDto.getTelefono() != null)
            usuario.setTelefono(usuarioDto.getTelefono());
        if (usuarioDto.getEsProveedor() != null)
            usuario.setEsProveedor(usuarioDto.getEsProveedor());
        if (usuarioDto.getEsAdmin() != null)
            usuario.setEsAdmin(usuarioDto.getEsAdmin());

        try {
            repoUsuario.save(usuario);
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "error en la consulta a la base de datos");
        } catch (Exception e) {
            throw new ApiException(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        }
        

        return usuario;
    }
}
