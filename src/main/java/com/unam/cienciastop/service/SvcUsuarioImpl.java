package com.unam.cienciastop.service;

import java.io.Console;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.unam.cienciastop.dao.DaoHistorialRentas;
import com.unam.cienciastop.dao.DaoProducto;
import com.unam.cienciastop.dao.DaoPumapuntos;
import com.unam.cienciastop.dao.DaoRoles;
import com.unam.cienciastop.dao.DaoUsuario;
import com.unam.cienciastop.dto.UsuarioConMasDevolucionesTardiasDTO;
import com.unam.cienciastop.dto.CarreraDTO;
import com.unam.cienciastop.dto.TopCincoSemanaUsuariosDTO;
import com.unam.cienciastop.dto.UsuarioDTO;
import com.unam.cienciastop.entity.EjemplarProducto;
import com.unam.cienciastop.entity.HistorialRentas;
import com.unam.cienciastop.entity.Producto;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class SvcUsuarioImpl implements SvcUsuario, UserDetailsService{

    private Logger logger = LoggerFactory.getLogger(SvcUsuarioImpl.class);
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private DaoUsuario repoUsuario;

    @Autowired
    private DaoProducto repoProducto;

    @Autowired
    private DaoPumapuntos repoPumapuntos;

    @Autowired
    private DaoRoles repoRoles;

    @Autowired
    private DaoHistorialRentas repoHistorialRentas;

    @Override
    public List<Usuario> getUsuarios() {
        try {
            return (List<Usuario>)repoUsuario.findAll();
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.NOT_FOUND, "error en la consulta a la base de datos");
        } catch (Exception e) {
            throw new ApiException(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        }
    }

    /**
     * Metodo que obtiene las carreras con el numero de usuarios activos.
     */
    @Override
    public List<CarreraDTO> getUsuariosCarrera() {
        try {
            return repoUsuario.getUsuariosCarrera();
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.NOT_FOUND, "error en la consulta a la base de datos");
        } catch (Exception e) {
            throw new ApiException(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        }
    }

    /**
     * Metodo que obtiene a los cinco usuarios con mas rentas en el mes.
     */
    @Override
    public List<TopCincoSemanaUsuariosDTO> getTopCincoUsuariosRentasSemana() {
        try {
            return repoUsuario.getTopCincoUsuariosRentasSemana();
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

    /**
     * Método que marca a un usuario como inactivo en la BD.
     */   
    @Override    
    public Usuario deleteUsuario(Integer id_usuario, String numInstitucionalUsuario){
        Usuario requester = findByNumInstitucional(numInstitucionalUsuario);
        Integer requester_ID = requester.getId();
        // revisa si el usuario existe
        Usuario usuario = repoUsuario.findById(id_usuario)
            .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,
                "error, no se puede modificar un usuario inexistente."));
        
        // revisa si el usuario es el usuario activo        
        if(requester_ID == id_usuario){
            throw new ApiException(HttpStatus.BAD_REQUEST, "No puedes eliminar tu propio usuario");
        }

        // revisa si el usuario es proveedor, si es así, revisa si tiene productos registrados
        if (usuario.getEsProveedor()) {
            List<Producto> productosProveedor = repoProducto.findByProveedor(usuario);
            if (productosProveedor.size() != 0 || productosProveedor == null){
                throw new ApiException(HttpStatus.NOT_FOUND, 
                    "Imposible eliminar el usuario. El usuario tiene productos registrados");            
            }
        }        

        // revisa si el usuario tiene productos rentados sin regresar
        List<HistorialRentas> productosRentados = repoHistorialRentas.rentasByIdUsuario(id_usuario);
        if (productosRentados.size() != 0){
            for (HistorialRentas producto : productosRentados) {
                if (producto.getDevuelto() == false) {
                    throw new ApiException(HttpStatus.NOT_FOUND, 
                            "Imposible eliminar el usuario. El usuario tiene adeudos");    
                }       
            }       
        }
            
        // establece el estado del usuario como 'no activo'
        usuario.setActivo(false);
        // guarda los cambios en la BD
        try {
            repoUsuario.save(usuario);        
            return usuario;
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

    @Override
    public Usuario editarUsuario(Integer id_usuario, UsuarioDTO usuarioDto) {
        Usuario usuario = repoUsuario.findById(id_usuario)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,
                        "Error, no se puede modificar un usuario inexistente."));

        List<Role> roles = usuario.getRoles();

        if(usuarioDto.getEsAdmin() && !usuario.getEsAdmin()){
            roles.add(repoRoles.findById(1L).get());
            usuario.setEsAdmin(true);

        } else if (!usuarioDto.getEsAdmin() && usuario.getEsAdmin()){
            if (usuario.getNumInstitucional().equals("999999999")) {
                throw new ApiException(HttpStatus.NOT_FOUND, 
                        "Los permisos del usuario "+ usuario.getNombre() +"no pueden ser modificados");
            }

            List<Producto> productosProveedor = repoProducto.findByProveedor(usuario);

            if (productosProveedor.size() != 0 || productosProveedor == null)
                throw new ApiException(HttpStatus.NOT_FOUND, 
                        "Imposible eliminar el rol admin. El usuario tiene productos registrados");

            // eliminamos el rol admin de su lista
            for (int i = 0; i < roles.size(); i++) {
                if (roles.get(i).getId() == 1L)
                    roles.remove(i);
            }

            usuario.setEsAdmin(false);
        } 

        if(usuarioDto.getEsProveedor() && !usuario.getEsProveedor()){
            roles.add(repoRoles.findById(3L).get());
            usuario.setEsProveedor(true);

        } else if (!usuarioDto.getEsProveedor() && usuario.getEsProveedor()){
            if (usuario.getNumInstitucional().equals("999999999")) {
                throw new ApiException(HttpStatus.NOT_FOUND, 
                        "Los permisos del usuario "+ usuario.getNombre() +"no pueden ser modificados");
            }

            List<Producto> productosProveedor = repoProducto.findByProveedor(usuario);

            if (productosProveedor.size() != 0 || productosProveedor == null)
                throw new ApiException(HttpStatus.NOT_FOUND, 
                        "Imposible eliminar el rol proveedor. El usuario tiene productos registrados");

            // eliminamos el rol proveedor de su lista
            for (int i = 0; i < roles.size(); i++) {
                if (roles.get(i).getId() == 3L)
                    roles.remove(i);
            }

            usuario.setEsProveedor(false);
        } 

        usuario.setNombre(usuarioDto.getNombre());
        usuario.setCorreo(usuarioDto.getCorreo());
        usuario.setTelefono(usuarioDto.getTelefono());

        try {
            repoUsuario.save(usuario);
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "error en la consulta a la base de datos");
        } catch (Exception e) {
            throw new ApiException(HttpStatus.I_AM_A_TEAPOT, e.getLocalizedMessage());
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

    @Override
    public void editarContraseña(String num_institucional, String contraseña) {
        //Verificar que la contraseña no sea vacia
        if(contraseña == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, 
                        "Imposible cambiar contraseña, contraseña vacia.");

		}else {

            try {

				Usuario usuario = repoUsuario.findByNumInstitucional(num_institucional);
				// encriptar la contraseña
				usuario.setContraseña(passwordEncoder.encode(contraseña));
                repoUsuario.save(usuario);
            } catch (DataAccessException e) {
                throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "error en la consulta a la base de datos");
				
            } catch (Exception e) {
                throw new ApiException(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
            }    
        } 
    }

   
    public Integer getNumUsuariosInactivos() {
        try {
            return repoUsuario.getUsuariosInactivos().size();
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "error en la consulta a la base de datos");
        } catch (Exception e) {
            throw new ApiException(HttpStatus.I_AM_A_TEAPOT, e.getLocalizedMessage());
        }
    }

    @Override
    public List<UsuarioConMasDevolucionesTardiasDTO> getUsuariosConMasDevolucionesTardias() {
        try{
            return repoUsuario.getUsuariosConMasDevolucionesTardias();
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "error en la consulta a la base de datos");
        } catch (Exception e) {
            throw new ApiException(HttpStatus.I_AM_A_TEAPOT, e.getLocalizedMessage());
        }
    }
    
    @Override
    @Transactional(readOnly=true)
    public Usuario getPerfil(String numInstitucional) {
        try{
            Usuario usuario= repoUsuario.findByNumInstitucional(numInstitucional);
        
            return usuario;
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "error en la consulta a la base de datos");
        } catch (Exception e) {
            throw new ApiException(HttpStatus.I_AM_A_TEAPOT, e.getLocalizedMessage());
        }
    }
}
