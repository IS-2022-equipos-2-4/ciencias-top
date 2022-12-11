package com.unam.cienciastop.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.unam.cienciastop.dao.DaoUsuario;
import com.unam.cienciastop.dto.ProductosDelMesDTO;
import com.unam.cienciastop.dto.ProductoDTO;
import com.unam.cienciastop.dto.RespuestaDevolverEjemplarDTO;
import com.unam.cienciastop.dto.RespuestaGetEjemplaresDTO;
import com.unam.cienciastop.dao.DaoEjemplarProducto;
import com.unam.cienciastop.dao.DaoHistorialRentas;
import com.unam.cienciastop.dao.DaoProducto;
import com.unam.cienciastop.dao.DaoPumapuntos;
import com.unam.cienciastop.entity.EjemplarProducto;
import com.unam.cienciastop.entity.HistorialRentas;
import com.unam.cienciastop.entity.Producto;
import com.unam.cienciastop.entity.Pumapuntos;
import com.unam.cienciastop.entity.Usuario;
import com.unam.cienciastop.exceptionHandler.ApiException;

@Service
public class SvcProductoImpl implements SvcProducto {

    @Autowired
    private DaoUsuario repoUsuario;

    @Autowired
    private DaoProducto repoProducto;

    @Autowired
    private DaoEjemplarProducto repoEjemplarProducto;

    @Autowired
    private DaoPumapuntos repoPumapuntos;

    @Autowired
    private DaoHistorialRentas repoHistorialRentas;

    @Autowired
    private SvcEjemplarProducto svcEProducto;

    @Autowired
    private SvcUsuario svcUsuario;

    @Autowired
    private SvcPumapuntos svcPumapuntos;

    @Override
    public List<Producto> getProductos() {
        try {
            return (List<Producto>) repoProducto.findAll();
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "error en la consulta a la base de datos");
        } catch (Exception e) {
            throw new ApiException(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        }
    }

    /**
     * Metodo que despliega los productos mas rentados del mes.
     */
    @Override
    public List<ProductosDelMesDTO> getProductosDelMes() {
        try {
            return repoProducto.getProductosDelMes();
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "error en la consulta a la base de datos");
        } catch (Exception e) {
            throw new ApiException(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        }
    }

    /*
     * Metodo que recibe un id y regresa el objeto Producto asociado a dicho id.
     */
    @Override
    public Producto getProducto_id(int id) {
        try {
            return repoProducto.getProducto_id(id);
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "error en la consulta a la base de datos");
        } catch (Exception e) {
            throw new ApiException(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        }
    }

    /*
     * Metodo que recibe un nombre y regresa la lista de objetos Producto asociado a dicho nombre.
     */
    @Override
    public List<Producto> getProductos_codigo(String codigo) {
        try {
            return repoProducto.getProductos_codigo(codigo);
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "error en la consulta a la base de datos");
        } catch (Exception e) {
            throw new ApiException(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        }
    }

    /**
     * Metodo que recibe un nombre y regresa la lista de objetos Producto asociado a dicho nombre.
     * 
     * @param nombre
     * @return List<Producto>
     */
    @Override
    public List<Producto> getProductos_nombre(String nombre) {
        try {
            return repoProducto.getProductos_nombre(nombre);
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "error en la consulta a la base de datos");
        } catch (Exception e) {
            throw new ApiException(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        }
    }


    @Override
    public Producto crearProducto(Producto producto, Integer idProveedor) {
        try {
            Usuario proveedor = (Usuario) repoUsuario.findById(idProveedor).get();
            producto.setProveedor(proveedor);
            Producto nuevo = (Producto) repoProducto.save(producto);
            Integer unidades = producto.getStock();
            for (int i = 0; i < unidades; i++) {
                EjemplarProducto e = new EjemplarProducto(true, nuevo);
                svcEProducto.crearEjemplar(e);
            }
            return nuevo;
        } catch (DataIntegrityViolationException e) {
            throw new ApiException(HttpStatus.NOT_FOUND,
                    "ya hay un producto registrado con ese codigo");
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "error en la consulta a la base de datos");
        } catch (Exception e) {
            throw new ApiException(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        }
    }

    @Override
    public EjemplarProducto rentarProducto(Integer idProducto, String numInstitucionalUsuario) {
        Usuario usuario = this.svcUsuario.findByNumInstitucional(numInstitucionalUsuario);
        Integer idUsuario = usuario.getId();

        Producto producto = this.repoProducto.getProducto_id(idProducto);

        if (producto == null) {
            throw new ApiException(HttpStatus.NOT_ACCEPTABLE,
                    "no existe un producto con el id especificado");
        }

        Pumapuntos pumapuntos = repoPumapuntos.getPumapuntos(idUsuario);

        if (pumapuntos.getSaldo() < producto.getCosto()) {
            throw new ApiException(HttpStatus.NOT_ACCEPTABLE,
                    "el usuario no tiene suficientes pumapuntos para rentar este producto");
        }

        List<EjemplarProducto> ejemplaresDisponibles =
                this.repoEjemplarProducto.getEjemplaresDisponiblesByIdProducto(idProducto);

        if (ejemplaresDisponibles.size() == 0) {
            throw new ApiException(HttpStatus.NOT_ACCEPTABLE,
                    "no hay ejemplares disponibles de este producto");
        }

        LocalDate inicioDelMes = LocalDate.now().withDayOfMonth(1);
        List<HistorialRentas> rentas = this.repoHistorialRentas.rentasByIdUsuario(idUsuario);
        int numRentasDelMes = (int) rentas.stream().filter(renta -> {
            LocalDate fechaRenta = renta.getFechaRenta();
            return fechaRenta.isAfter(inicioDelMes) || fechaRenta.isEqual(inicioDelMes);
        }).count();

        if (numRentasDelMes >= 3) {
            throw new ApiException(HttpStatus.NOT_ACCEPTABLE,
                    "la cantidad máxima de rentas de este mes del usuario se ha alcanzado");
        }


        EjemplarProducto ejemplar = ejemplaresDisponibles.get(0);
        ejemplar.setDisponible(false);
        repoEjemplarProducto.save(ejemplar);

        producto.setStock(producto.getStock() - 1);
        repoProducto.save(producto);

        pumapuntos.setSaldo(pumapuntos.getSaldo() - producto.getCosto());
        repoPumapuntos.save(pumapuntos);

        HistorialRentas renta = new HistorialRentas();
        renta.setDevuelto(false);
        renta.setUsuario(usuario);
        renta.setItemProducto(ejemplar);
        renta.setFechaDevolucion(null);
        renta.setFechaRenta(LocalDate.now());
        repoHistorialRentas.save(renta);


        return ejemplar;
    }

    @Override
    public RespuestaDevolverEjemplarDTO devolverEjemplar(Integer idEjemplar) {
        Optional<EjemplarProducto> maybeEjemplar = this.repoEjemplarProducto.findById(idEjemplar);

        if (!maybeEjemplar.isPresent()) {
            throw new ApiException(HttpStatus.NOT_ACCEPTABLE,
                    "no existe el ejemplar que se quiere devolver");
        }

        List<HistorialRentas> rentas = this.repoHistorialRentas.rentasByIdEjemplar(idEjemplar);

        Optional<HistorialRentas> maybeRenta = rentas.stream().filter(renta -> {
            return renta.getItemProducto().getIdEjemplar() == idEjemplar && !renta.getDevuelto();
        }).findAny();

        if (!maybeRenta.isPresent()) {
            throw new ApiException(HttpStatus.NOT_ACCEPTABLE,
                    "este ejemplar no está rentado actualmente");
        }

        LocalDate fechaActual = LocalDate.now();

        HistorialRentas renta = (HistorialRentas) maybeRenta.get();
        renta.setFechaDevolucion(fechaActual);
        renta.setDevuelto(true);
        repoHistorialRentas.save(renta);

        Usuario usuario = renta.getUsuario();
        Integer idUsuario = usuario.getId();

        EjemplarProducto ejemplar = maybeEjemplar.get();
        ejemplar.setDisponible(true);
        repoEjemplarProducto.save(ejemplar);

        Producto producto = ejemplar.getProducto();
        producto.setStock(producto.getStock() + 1);
        repoProducto.save(producto);

        boolean devolucionTardia = false;
        if (ChronoUnit.DAYS.between(renta.getFechaRenta(), fechaActual) > producto
                .getLimitePrestamo()) {
            Pumapuntos pumapuntos = repoPumapuntos.getPumapuntos(idUsuario);
            pumapuntos.setSaldo(Math.max(pumapuntos.getSaldo() - 20, 0));
            repoPumapuntos.save(pumapuntos);

            devolucionTardia = true;
        }

        return new RespuestaDevolverEjemplarDTO(devolucionTardia);
    }

    @Override
    public Producto editarProducto(Integer id_producto, ProductoDTO productodto) {
        Producto prod = repoProducto.findById(id_producto)
            .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, 
                "error, no se puede editar un producto que no existe."));

        prod.setCodigo(productodto.getCodigo());
        prod.setNombre(productodto.getNombre());
        prod.setDescripcion(productodto.getDescripcion());
        prod.setCosto(productodto.getCosto());
        prod.setLimitePrestamo(productodto.getLimitePrestamo());

        try {
            repoProducto.save(prod);
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,
                "error al consultar la base de datos");
        } catch (Exception e) {
            throw new ApiException(HttpStatus.I_AM_A_TEAPOT, e.getLocalizedMessage());
        }
        
        return prod;
    }
    
    /**
     * Metodo que recibe un numInstitucionalUsuario y regresa la lista de objetos HistorialRentas
     * asociado a dicho idEjemplar.
     * 
     * @param numInstitucionalUsuario
     * @return List<HistorialRentas>
     */
    @Override
    public List<HistorialRentas> verProdRentados(String numInstitucionalUsuario) {
        Usuario usuario = this.svcUsuario.findByNumInstitucional(numInstitucionalUsuario);
        Integer idUsuario = usuario.getId();
        try {
            return repoHistorialRentas.rentasByIdUsuario(idUsuario);
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "error en la consulta a la base de datos");
        } catch (Exception e) {
            throw new ApiException(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        }
    }

    @Override
    public List<RespuestaGetEjemplaresDTO> getEjemplares(Integer idProducto) {
        Optional<Producto> maybeProducto = this.repoProducto.findById(idProducto);

        if (!maybeProducto.isPresent()) {
            throw new ApiException(HttpStatus.NOT_ACCEPTABLE, "no existe este producto");
        }
        List<HistorialRentas> rentas = new ArrayList<HistorialRentas>();
        this.repoHistorialRentas.findAll().forEach(rentas::add);

        List<EjemplarProducto> ejemplares =
                this.repoEjemplarProducto.getEjemplaresByIdProducto(idProducto);

        List<RespuestaGetEjemplaresDTO> respuesta = ejemplares.stream().map(ejemplar -> {
            Integer idEjemplar = ejemplar.getIdEjemplar();

            RespuestaGetEjemplaresDTO respuestaGetEjemplaresDTO = new RespuestaGetEjemplaresDTO();
            respuestaGetEjemplaresDTO.idEjemplar = idEjemplar;

            Optional<HistorialRentas> maybeRenta = rentas.stream().filter(renta -> {
                return renta.getItemProducto().getIdEjemplar() == idEjemplar
                        && !renta.getDevuelto();
            }).findAny();

            respuestaGetEjemplaresDTO.rentado = false;
            if (maybeRenta.isPresent()) {
                HistorialRentas renta = maybeRenta.get();

                respuestaGetEjemplaresDTO.fecha_renta = renta.getFechaRenta();
                respuestaGetEjemplaresDTO.numInstitucionalUsuario =
                        renta.getUsuario().getNumInstitucional();
                respuestaGetEjemplaresDTO.rentado = true;
            }

            return respuestaGetEjemplaresDTO;
        }).collect(Collectors.toList());

        return respuesta;
    }

    @Override
    public List<Producto> getProductosMenorCosto() {
        return repoProducto.getProductosMenorCosto();
    }
}
