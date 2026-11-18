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

import com.krakedev.taller.entidades.Mecanico;
import com.krakedev.taller.services.MecanicoService;

@RestController
@RequestMapping("/api/mecanicos")
public class MecanicoController {

	// Inyeccion del servicio
	private final MecanicoService service;

	public MecanicoController(MecanicoService service) {
		this.service = service;
	}

	// CREAR MECANICO (POST -> http://localhost:8080/api/mecanicos)
	@PostMapping
	public ResponseEntity<?> crear(@RequestBody Mecanico mecanico) {
		try {
			Mecanico nuevoMecanico = service.guardar(mecanico);
			return ResponseEntity.status(HttpStatus.CREATED).body(nuevoMecanico);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al guardar el mecanico: " + e.getMessage());
		}
	}

	// LISTAR TODOS LOS MECANICOS (GET -> http://localhost:8080/api/mecanicos)
	@GetMapping
	public ResponseEntity<?> listar() {
		try {
			List<Mecanico> lista = service.listar();
			return ResponseEntity.ok(lista);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al listar los mecanicos: " + e.getMessage());
		}
	}

	// BUSCAR MECANICO POR ID (GET -> http://localhost:8080/api/mecanicos/1)
	@GetMapping("/{id}")
	public ResponseEntity<?> buscar(@PathVariable int id) {
		try {
			Mecanico mecanico = service.buscar(id);

			if (mecanico == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("No se encontro el mecanico con ID: " + id);
			}

			return ResponseEntity.ok(mecanico);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al buscar el mecanico: " + e.getMessage());
		}
	}

	// ACTUALIZAR MECANICO (PUT -> http://localhost:8080/api/mecanicos/1)
	@PutMapping("/{id}")
	public ResponseEntity<?> actualizar(@PathVariable int id, @RequestBody Mecanico datos) {
		try {
			Mecanico mecanicoActualizado = service.actualizar(id, datos);

			if (mecanicoActualizado == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("No se puede actualizar. Mecanico no encontrado con ID: " + id);
			}

			return ResponseEntity.ok(mecanicoActualizado);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al actualizar el mecanico: " + e.getMessage());
		}
	}

	// ELIMINAR MECANICO (DELETE -> http://localhost:8080/api/mecanicos/1)
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable int id) {
		try {
			boolean eliminado = service.eliminar(id);

			if (!eliminado) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("No se puede eliminar. Mecanico no encontrado con ID: " + id);
			}

			return ResponseEntity.ok("Mecanico eliminado correctamente");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al eliminar el mecanico: " + e.getMessage());
		}
	}
}
