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

import com.unam.cienciastop.dao.DaoHistorialRentas;
import com.unam.cienciastop.dao.DaoProducto;
import com.unam.cienciastop.dao.DaoPumapuntos;
import com.unam.cienciastop.dao.DaoRoles;
import com.unam.cienciastop.dao.DaoUsuario;
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
    private DaoProducto repoProducto;

    @Autowired
    private DaoPumapuntos repoPumapuntos;

    @Autowired
    private DaoRoles repoRoles;

    @Autowired
    private DaoHistorialRentas repoHistorialRentas;

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

    @Override
    public Usuario editarUsuario(Integer id_usuario, UsuarioDTO usuarioDto) {
        Usuario usuario = repoUsuario.findById(id_usuario)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,
                        "error, no se puede modificar un usuario inexistente."));

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

    //Preguntar a Jasso sobre la etiqueta Transactional y la carpeta Dto
    @Override
    @Transactional(readOnly=true)
    public Usuario getPerfil(Integer id) {
        Usuario usuario= repoUsuario.findById(id).get();
        /*Pumapuntos pumapuntos=(Pumapuntos) repoPumapuntos.getPumapuntos(id);
        List<HistorialRentas> historialRentas=(List<HistorialRentas>) repoHistorialRentas.rentasByIdUsuario(id);

        String[] atributos=new String[9];
        atributos[0]=usuario.getNombre();
        atributos[1]=usuario.getCorreo();
        atributos[2]=usuario.getNumInstitucional();
        atributos[3]=usuario.getCarrera();
        atributos[4]=usuario.getTelefono();
        
        atributos[5]=pumapuntos.getSaldo().toString();
        //aqui iria el get productos rentados
        String productosRentados="";
        if(historialRentas.size()==0)
           productosRentados="No hay productos rentados";
        else   
          for (int i=0; i<historialRentas.size();i++){
           
              HistorialRentas r=historialRentas.get(i);
              EjemplarProducto e=r.getItemProducto();
              Producto p=e.getProducto();
              if(i==historialRentas.size()-1)
                productosRentados+=p.getNombre();
              else
                productosRentados+=p.getNombre()+",";
          }

        atributos[6]=productosRentados;
        //aqui iria el caso para productos aun no devueltos
        String productos_no_devueltos="";
        
        for(int i=0; i<historialRentas.size();i++){

            HistorialRentas r=historialRentas.get(i);
            if(r.getDevuelto()==false){
              EjemplarProducto e=r.getItemProducto();
              Producto p=e.getProducto();
            
              if(i==historialRentas.size()-1)
                productos_no_devueltos+=p.getNombre();
              else
                productos_no_devueltos+=p.getNombre()+",";
            //}
        }
        
        if (productos_no_devueltos=="")
           productos_no_devueltos="No hay productos que falten por devolver";

           
        atributos[7]=productos_no_devueltos;
        //Preguntar a Jasso sobre si agregar este atributo que no viene en los requerimientos del proyecto 
        String roles="Usuario";
        if (usuario.getEsProveedor()==true){
            roles+=",Proveedor";
        }
        if (usuario.getEsAdmin()==true){
            roles+=",Admin";
        }

        atributos[8]=roles;
        return atributos;*/


    //}
    return usuario;
    }
}
