package com.krakedev.taller.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.krakedev.taller.entidades.Mecanico;
import com.krakedev.taller.repositories.MecanicoRepository;

@Service
public class MecanicoService {
	
	// Inyeccion del repositorio
	private final MecanicoRepository repository;
	
	// Creamos el constructor de la inyeccion
	public MecanicoService(MecanicoRepository repository) {
		this.repository = repository;
	}
	
	// GUARDAR MECANICO
	// Guarda un nuevo mecanico directamente en la base de datos
	public Mecanico guardar(Mecanico mecanico) {
		return repository.save(mecanico);
	}
	
	// Metodo Listar una lista de mecanicos
	public List<Mecanico> listar() {
		return repository.findAll();
	}
	
	// Busca por ID al mecanico
	public Mecanico buscar(int id) {
		Optional<Mecanico> resultado = repository.findById(id);
		return resultado.orElse(null);
	}
	
	// Metodo de actualizar los datos del mecanico
	public Mecanico actualizar(int id, Mecanico datos) {
		
		Mecanico mecanico = buscar(id);
		
		if (mecanico == null) {
			return null;
		}
		
		mecanico.setNombre(datos.getNombre());
		mecanico.setEspecialidad(datos.getEspecialidad());
		
		return repository.save(mecanico);
	}
	
	// Metodo de eliminar mecanico buscandolo por su ID
	public boolean eliminar(int id) {
		
		Mecanico mecanico = buscar(id);
		
		if (mecanico == null) {
			return false;
		}
		
		repository.deleteById(id);
		return true;
	}

}