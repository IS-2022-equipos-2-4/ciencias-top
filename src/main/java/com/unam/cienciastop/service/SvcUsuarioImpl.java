package com.unam.cienciastop.service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.unam.cienciastop.dao.DaoPumapuntos;
import com.unam.cienciastop.dao.DaoRoles;
import com.unam.cienciastop.dao.DaoUsuario;
import com.unam.cienciastop.dto.UsuarioDTO;
import com.unam.cienciastop.entity.Pumapuntos;
import com.unam.cienciastop.entity.Role;
import com.unam.cienciastop.entity.Usuario;
import com.unam.cienciastop.exceptionHandler.ApiException;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.bouncycastle.asn1.ua.UAObjectIdentifiers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class SvcUsuarioImpl implements SvcUsuario, UserDetailsService{

    private Logger logger = LoggerFactory.getLogger(SvcUsuarioImpl.class);
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private DaoUsuario repoUsuario;

    @Autowired
    private DaoPumapuntos repoPumapuntos;

    @Autowired
    private DaoRoles repoRoles;

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

    public Usuario getUsuario_id(Integer id_usuario) {
        return repoUsuario.findById(id_usuario)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,
                        "error, no se puede obtener un usuario inexistente."));
    };

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

            // encriptar la contraseña
            usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));

            // asignación de roles
            List<Role> roles = new LinkedList<>();
            roles.add(repoRoles.findById(2L).get()); // agregamos rol de user por default

            if (usuario.getEsAdmin())
                roles.add(repoRoles.findById(1L).get());
            if (usuario.getEsProveedor())
                roles.add(repoRoles.findById(3L).get());

            usuario.setRoles(roles);

            // guardamos usuario y creamos registro de pumapuntos
            Usuario nuevo = (Usuario) repoUsuario.save(usuario);
            Pumapuntos registro = new Pumapuntos(LocalDate.now().getMonthValue(), 100, usuario);

            repoPumapuntos.save(registro);
            return nuevo;
        } catch (DataIntegrityViolationException e) {
            throw new ApiException(HttpStatus.NOT_FOUND,
                    "error, ya hay un usuario registrado con ese correo o no. cuenta / trabajador");
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getLocalizedMessage());
        } catch (Exception e) {
            throw new ApiException(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        }
    }

    private boolean existeRol(Long idRol, List<Role> roles){
        for (Role role : roles) {
            if(role.getId() == idRol)
                return true;
        }
        return false;
    }

    @Override
    public Usuario editarUsuario(Integer id_usuario, UsuarioDTO usuarioDto) {
        Usuario usuario = repoUsuario.findById(id_usuario)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,
                        "error, no se puede modificar un usuario inexistente."));

        List<Role> roles = usuario.getRoles();

        if (usuarioDto.getNombre() != null)
            usuario.setNombre(usuarioDto.getNombre());
        if (usuarioDto.getCorreo() != null)
            usuario.setCorreo(usuarioDto.getCorreo());
        if (usuarioDto.getTelefono() != null)
            usuario.setTelefono(usuarioDto.getTelefono());
        if (usuarioDto.getEsProveedor() != null){
            if(!existeRol(3L, roles))
                roles.add(repoRoles.findById(3L).get());
            
            usuario.setEsProveedor(usuarioDto.getEsProveedor());

        } if (usuarioDto.getEsAdmin() != null){
            if(!existeRol(1L, roles))
                roles.add(repoRoles.findById(1L).get());

            usuario.setEsAdmin(usuarioDto.getEsAdmin());
        }

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

    @Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = repoUsuario.findByNumInstitucional(username);
		
		if(usuario == null) {
			logger.error("Error en el login: no existe el usuario '"+username+"' en el sistema!");
			throw new UsernameNotFoundException("Error en el login: no existe el usuario '"+username+"' en el sistema!");
		}
		
		List<GrantedAuthority> authorities = usuario.getRoles()
				.stream()
				.map(role -> new SimpleGrantedAuthority(role.getNombre()))
				.peek(authority -> logger.info("Role: " + authority.getAuthority()))
				.collect(Collectors.toList());
		
		return new User(usuario.getNumInstitucional(), usuario.getContraseña(), usuario.getActivo(), true, true, true, authorities);
	}

	@Override
	@Transactional(readOnly=true)
	public Usuario findByNumInstitucional(String username) {
		return repoUsuario.findByNumInstitucional(username);
	}
}
