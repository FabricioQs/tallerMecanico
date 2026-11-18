package com.krakedev.taller.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.krakedev.taller.entidades.Vehiculo;
import com.krakedev.taller.repositories.VehiculoRepository;

@Service
public class VehiculoService {
	
	// Inyeccion del repositorio
	private final VehiculoRepository repository;
	
	// Creamos el constructor de la inyeccion
	public VehiculoService(VehiculoRepository repository) {
		this.repository = repository;
	}
	
	// GUARDAR VEHICULO
	// Verifica que la placa no exista previamente en la base de datos antes de guardar
	public Vehiculo guardar(Vehiculo vehiculo) {
		
		if (repository.existsById(vehiculo.getPlaca())) {
			throw new RuntimeException("Ya existe un vehiculo registrado con esa placa");
		}
		
		return repository.save(vehiculo);
	}
	
	// Metodo Listar una lista de vehiculos
	public List<Vehiculo> listar() {
		return repository.findAll();
	}
	
	// Busca por PLACA el vehiculo
	public Vehiculo buscar(String placa) {
		Optional<Vehiculo> resultado = repository.findById(placa);
		return resultado.orElse(null);
	}
	
	// Metodo de actualizar los datos del vehiculo (la placa no se modifica porque es el ID)
	public Vehiculo actualizar(String placa, Vehiculo datos) {
		
		Vehiculo vehiculo = buscar(placa);
		
		if (vehiculo == null) {
			return null;
		}
		
		vehiculo.setMarca(datos.getMarca());
		vehiculo.setModelo(datos.getModelo());
		vehiculo.setAnio(datos.getAnio());
		
		return repository.save(vehiculo);
	}
	
	// Metodo de eliminar vehiculo buscandolo por su PLACA
	public boolean eliminar(String placa) {
		
		Vehiculo vehiculo = buscar(placa);
		
		if (vehiculo == null) {
			return false;
		}
		
		repository.deleteById(placa);
		return true;
	}

}