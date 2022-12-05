package com.unam.cienciastop.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.unam.cienciastop.dto.CarreraDTO;
import com.unam.cienciastop.dto.TopCincoSemanaUsuariosDTO;
import com.unam.cienciastop.entity.Usuario;

public interface DaoUsuario extends CrudRepository<Usuario,Integer>{

    @Query(value = "SELECT * FROM usuarios WHERE activo = true", nativeQuery = true)
    public List<Usuario> getUsuariosActivos();

    /**
     * Metodo que obtiene las carreras con mas usuarios activos.
     * @return Query con las carreras y sus numeros de estudiantes.
     */
    @Query(value = "SELECT carrera, COUNT(carrera) FILTER (WHERE activo = true) AS estudiantes_activos FROM usuarios GROUP BY carrera ORDER BY estudiantes_activos DESC", nativeQuery = true)
    public List<CarreraDTO> getUsuariosCarrera();

    /**
     * Metodo que obtiene a los usuarios con mas productos rentados en la semana
     */
    @Query(value = "SELECT historial_rentas.id_usuario, carrera, nombre, num_institucional, COUNT(historial_rentas.id_usuario) AS numero_rentas FROM historial_rentas LEFT JOIN usuarios ON historial_rentas.id_usuario = usuarios.id_usuario WHERE EXTRACT(DAY FROM NOW() - fecha_renta) < 7 GROUP BY historial_rentas.id_usuario, usuarios.id_usuario ORDER BY numero_rentas DESC fetch first 5 rows only", nativeQuery = true)
    public List<TopCincoSemanaUsuariosDTO> getTopCincoUsuariosRentasSemana();

    /**
     * Metodo que recibe un nombre y regresa la lista de objetos
     * Usuario asociado a dicho nombre.
     * @param nombre
     * @return List<Usuario>
     */
    @Query(value = "SELECT * FROM usuarios WHERE POSITION (:nombre IN nombre)>0", nativeQuery = true)
    public List<Usuario> getUsuarios_nombre(@Param("nombre") String nombre);

    /**
     * Metodo que recibe un numero institucional y regresa la lista de objetos Usuario
     * asociados a dicho numero.
     * @param num_institucional
     * @return List<Usuario>
     */
    @Query(value = "SELECT * FROM usuarios WHERE POSITION (:num_institucional IN num_institucional)>0", nativeQuery = true)
    public List<Usuario> getUsuarios_numeroInstitucional(@Param("num_institucional") String num_institucional);

    /**
     * Metodo que recibe un correo y regresa la lista de objetos Usuario
     * asociados a dicho correo.
     * @param correo
     * @return List<Usuario>
     */
    @Query(value = "SELECT * FROM usuarios WHERE POSITION (:correo IN correo)>0", nativeQuery = true)
    public List<Usuario> getUsuarios_correo(@Param("correo") String correo);

    Usuario findByNumInstitucional(String numInstitucional);
}
