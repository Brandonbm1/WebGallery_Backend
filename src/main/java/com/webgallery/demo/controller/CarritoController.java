package com.webgallery.demo.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.webgallery.demo.model.Carrito;
import com.webgallery.demo.model.Obra;
//import com.webgallery.demo.model.Obra;
import com.webgallery.demo.model.Usuario;
import com.webgallery.demo.repository.CarritoRepository;
import com.webgallery.demo.repository.UsuarioRepository;

@Controller
@RequestMapping("/api/carrito")
@CrossOrigin(origins = { "http://localhost:8080", "http://localhost:3000" })
public class CarritoController {
	@Autowired
	private CarritoRepository RepositorioCarrito;

	private UsuarioRepository RepositorioUsuario;

	private static Logger LOG = LoggerFactory.getLogger(CarritoController.class);

	public ResponseEntity<Carrito> crearCarrito(Usuario user) {

		Optional<Usuario> datoUsuario = RepositorioUsuario.findById(user.getId());
		if (datoUsuario.isPresent()) {
			try {
				Carrito cart = new Carrito();
				cart.setComprador(user);
				Carrito carrito = RepositorioCarrito.save(cart);
				return new ResponseEntity<>(carrito, HttpStatus.CREATED);
			} catch (Exception e) {
				LOG.error(e.getMessage());
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@GetMapping("/all/{id}")
	public ResponseEntity<List<Obra>> productosCarrito(@PathVariable("id") int id){
		List<Obra> listaObras = RepositorioCarrito.findById(id).get().getObras();
		if(listaObras.isEmpty()) {
			LOG.warn("No hay obras");
			return new ResponseEntity<> (HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<> (listaObras, HttpStatus.OK);
		}
	}

	@PutMapping("/agregar/{id}")
	public ResponseEntity<Carrito> agregarACarrito(@PathVariable("id") int id, @RequestBody Obra o) {
		Optional<Carrito> datosCarrito = RepositorioCarrito.findById(id);
		if (datosCarrito.isPresent()) {
			Carrito cart = datosCarrito.get();
			try {
				List<Obra> obras = cart.getObras();
				obras.add(o);
				cart.setObras(obras);

				Carrito carritoActualizado = RepositorioCarrito.save(cart);

				return new ResponseEntity<>(carritoActualizado, HttpStatus.OK);
			} catch (Exception e) {
				LOG.error(e.getMessage());
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@PutMapping("/quitar/{id}")
	public ResponseEntity<Carrito> eliminarDeCarrito(@PathVariable("id") int id, @RequestBody Obra o) {
		Optional<Carrito> datosCarrito = RepositorioCarrito.findById(id);
		if (datosCarrito.isPresent()) {
			Carrito cart = datosCarrito.get();
			LOG.info("El carrito no contiene el producto");
			if (cart.getObras().contains(o)) {
				try {
					cart.getObras().remove(o);
					return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				} catch (Exception e) {
					// TODO: handle exception
					LOG.error(e.getMessage());
				}

			}
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
