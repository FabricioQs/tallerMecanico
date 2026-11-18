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

import com.krakedev.taller.entidades.Vehiculo;
import com.krakedev.taller.services.VehiculoService;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

	// Inyeccion del servicio
	private final VehiculoService service;

	public VehiculoController(VehiculoService service) {
		this.service = service;
	}

	// CREAR VEHICULO (POST -> http://localhost:8080/api/vehiculos)
	@PostMapping
	public ResponseEntity<?> crear(@RequestBody Vehiculo vehiculo) {
		try {
			Vehiculo nuevoVehiculo = service.guardar(vehiculo);
			return ResponseEntity.status(HttpStatus.CREATED).body(nuevoVehiculo);
		} catch (RuntimeException e) {
			// Captura la validacion si la placa ya existe en la BD (400 Bad Request)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error interno al guardar el vehiculo: " + e.getMessage());
		}
	}

	// LISTAR TODOS LOS VEHICULOS (GET -> http://localhost:8080/api/vehiculos)
	@GetMapping
	public ResponseEntity<?> listar() {
		try {
			List<Vehiculo> lista = service.listar();
			return ResponseEntity.ok(lista);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al listar los vehiculos: " + e.getMessage());
		}
	}

	// BUSCAR VEHICULO POR PLACA (GET -> http://localhost:8080/api/vehiculos/ABC-1234)
	@GetMapping("/{placa}")
	public ResponseEntity<?> buscar(@PathVariable String placa) {
		try {
			Vehiculo vehiculo = service.buscar(placa);

			if (vehiculo == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("No se encontro el vehiculo con placa: " + placa);
			}

			return ResponseEntity.ok(vehiculo);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al buscar el vehiculo: " + e.getMessage());
		}
	}

	// ACTUALIZAR VEHICULO (PUT -> http://localhost:8080/api/vehiculos/ABC-1234)
	@PutMapping("/{placa}")
	public ResponseEntity<?> actualizar(@PathVariable String placa, @RequestBody Vehiculo datos) {
		try {
			Vehiculo vehiculoActualizado = service.actualizar(placa, datos);

			if (vehiculoActualizado == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("No se puede actualizar. Vehiculo no encontrado con placa: " + placa);
			}

			return ResponseEntity.ok(vehiculoActualizado);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al actualizar el vehiculo: " + e.getMessage());
		}
	}

	// ELIMINAR VEHICULO (DELETE -> http://localhost:8080/api/vehiculos/ABC-1234)
	@DeleteMapping("/{placa}")
	public ResponseEntity<?> eliminar(@PathVariable String placa) {
		try {
			boolean eliminado = service.eliminar(placa);

			if (!eliminado) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("No se puede eliminar. Vehiculo no encontrado con placa: " + placa);
			}

			return ResponseEntity.ok("Vehiculo eliminado correctamente");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al eliminar el vehiculo: " + e.getMessage());
		}
	}
}