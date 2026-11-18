package com.krakedev.taller.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krakedev.taller.entidades.Reparacion;
import com.krakedev.taller.services.ReparacionService;

@RestController
@RequestMapping("/api/reparaciones")
public class ReparacionController {

	// Inyeccion del servicio
	private final ReparacionService service;

	public ReparacionController(ReparacionService service) {
		this.service = service;
	}

	// CREAR REPARACION (POST -> http://localhost:8080/api/reparaciones)
	@PostMapping
	public ResponseEntity<?> crear(@RequestBody Reparacion reparacion) {
		try {
			Reparacion nuevaReparacion = service.guardar(reparacion);
			return ResponseEntity.status(HttpStatus.CREATED).body(nuevaReparacion);
		} catch (RuntimeException e) {
			// Captura cuando el vehiculo o alguno de los mecanicos no existe en la BD (400 Bad Request)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error interno al registrar la reparacion: " + e.getMessage());
		}
	}

	// LISTAR TODAS LAS REPARACIONES (GET -> http://localhost:8080/api/reparaciones)
	@GetMapping
	public ResponseEntity<?> listar() {
		try {
			List<Reparacion> lista = service.listar();
			return ResponseEntity.ok(lista);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al listar las reparaciones: " + e.getMessage());
		}
	}

	// BUSCAR REPARACION POR ID (GET -> http://localhost:8080/api/reparaciones/1)
	@GetMapping("/{id}")
	public ResponseEntity<?> buscar(@PathVariable int id) {
		try {
			Reparacion reparacion = service.buscar(id);

			if (reparacion == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("No se encontro la reparacion con ID: " + id);
			}

			return ResponseEntity.ok(reparacion);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al buscar la reparacion: " + e.getMessage());
		}
	}

	// ACTUALIZAR REPARACION (PUT -> http://localhost:8080/api/reparaciones/1)
	@PutMapping("/{id}")
	public ResponseEntity<?> actualizar(@PathVariable int id, @RequestBody Reparacion datos) {
		try {
			// Nota: Se llama actulizar tal como esta definido en ReparacionService
			Reparacion reparacionActualizada = service.actulizar(id, datos);

			if (reparacionActualizada == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("No se puede actualizar. Reparacion no encontrada con ID: " + id);
			}

			return ResponseEntity.ok(reparacionActualizada);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al actualizar la reparacion: " + e.getMessage());
		}
	}

	// ELIMINAR REPARACION (DELETE -> http://localhost:8080/api/reparaciones/1)
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable int id) {
		try {
			boolean eliminado = service.eliminar(id);

			if (!eliminado) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("No se puede eliminar. Reparacion no encontrada con ID: " + id);
			}

			return ResponseEntity.ok("Reparacion eliminada correctamente");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al eliminar la reparacion: " + e.getMessage());
		}
	}
}