package com.unam.cienciastop.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.unam.cienciastop.dao.DaoPumapuntos;
import com.unam.cienciastop.dao.DaoUsuario;
import com.unam.cienciastop.entity.Pumapuntos;
import com.unam.cienciastop.entity.Usuario;
import com.unam.cienciastop.exceptionHandler.ApiException;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class SvcUsuarioImpl implements SvcUsuario, UserDetailsService{

    private Logger logger = LoggerFactory.getLogger(SvcUsuarioImpl.class);

    @Autowired
    private DaoUsuario repoUsuario;

    @Autowired
    private DaoPumapuntos repoPumapuntos;

    @Override
    public List<Usuario> getUsuariosActivos() {
        try {
            return repoUsuario.getUsuariosActivos();    
        } catch (DataAccessException e){
            throw new ApiException(HttpStatus.NOT_FOUND, "error en la consulta a la base de datos");
        } catch (Exception e) {
            throw new ApiException(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        }
    }

    /*
     * Metodo que recibe un nombre y regresa la lista de objetos 
     * Usuario asociado a dicho nombre.
     */
    @Override
    public List<Usuario> getUsuarios_nombre(String nombre){
        try {
            return repoUsuario.getUsuarios_nombre(nombre);   
        } catch (DataAccessException e){
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "error en la consulta a la base de datos");
        } catch (Exception e) {
            throw new ApiException(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        }
    }

    /*
     * Metodo que recibe un numero institucional y regresa la lista de objetos Usuario
     * asociados a dicho numero.
     */
    @Override
    public List<Usuario> getUsuarios_numeroInstitucional(String num_institucional){
        try {
            return repoUsuario.getUsuarios_numeroInstitucional(num_institucional);
        } catch (DataAccessException e){
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "error en la consulta a la base de datos");
        } catch (Exception e) {
            throw new ApiException(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        }
    }

    /*
     * Metodo que recibe correo y regresa la lista de objetos Usuario
     * asociados a dicho correo.
     */
    @Override
    public List<Usuario> getUsuarios_correo(String correo){
        try {
            return repoUsuario.getUsuarios_correo(correo);    
        } catch (DataAccessException e){
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "error en la consulta a la base de datos");
        } catch (Exception e) {
            throw new ApiException(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        } 
    }
    /*
     * Metodo que recibe informacion de un usuario nuevo y lo crea
     * en la base de datos
     */
    @Override
    public Usuario crearUsuario(Usuario usuario){
        try {
            Usuario nuevo = (Usuario) repoUsuario.save(usuario);
            Pumapuntos registro = new Pumapuntos(LocalDate.now().getMonthValue(), 100, usuario);
            repoPumapuntos.save(registro);
            return nuevo;
        } catch (DataIntegrityViolationException e){
            throw new ApiException(HttpStatus.NOT_FOUND, "error, ya hay un usuario registrado con ese correo o no. cuenta / trabajador");
        } catch (DataAccessException e){
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "error en la consulta a la base de datos");
        } catch (Exception e) {
            throw new ApiException(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        }
    }

    @Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = repoUsuario.findByUsername(username);
		
		if(usuario == null) {
			logger.error("Error en el login: no existe el usuario '"+username+"' en el sistema!");
			throw new UsernameNotFoundException("Error en el login: no existe el usuario '"+username+"' en el sistema!");
		}
		
		List<GrantedAuthority> authorities = usuario.getRoles()
				.stream()
				.map(role -> new SimpleGrantedAuthority(role.getNombre()))
				.peek(authority -> logger.info("Role: " + authority.getAuthority()))
				.collect(Collectors.toList());
		
		return new User(usuario.getNombre(), usuario.getContraseña(), usuario.getActivo(), true, true, true, authorities);
	}

	@Override
	@Transactional(readOnly=true)
	public Usuario findByUsername(String username) {
		return repoUsuario.findByUsername(username);
	}

    
 
}