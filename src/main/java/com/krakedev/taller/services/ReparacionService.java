package com.krakedev.taller.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.krakedev.taller.entidades.Mecanico;
import com.krakedev.taller.entidades.Reparacion;
import com.krakedev.taller.entidades.Vehiculo;
import com.krakedev.taller.repositories.MecanicoRepository;
import com.krakedev.taller.repositories.ReparacionRepository;
import com.krakedev.taller.repositories.VehiculoRepository;

@Service
public class ReparacionService {
	
	
	// inyecciones
	private final ReparacionRepository repository;
	private final VehiculoRepository vehiculoRepository;
	private final MecanicoRepository mecanicoRepository;
	
	
	
	//creamos el contructor de las inyecciones
	public ReparacionService(ReparacionRepository repository, VehiculoRepository vehiculoRepository, 
			MecanicoRepository mecanicoRepository ) {
		
		this.repository = repository;
		this.vehiculoRepository = vehiculoRepository;
		this.mecanicoRepository = mecanicoRepository;
		
	}
	
	
	//GUARDAR REPARACION
	//aqui el codigo verifica si existe el vehiculo o mecanico
	//no guarda datos que no existe en la base de datos
	//ua vez verifica si hay vehiculo y los mecanicos que existen 
	//y posterior mente se puede guardar
	public Reparacion guardar(Reparacion reparacion) {
		
		Vehiculo vehiculo = vehiculoRepository.findById(reparacion.getVehiculo().getPlaca())
				.orElseThrow(()-> new RuntimeException("Vehiculo no existe"));
		
		List<Mecanico> mecanicosDB = new ArrayList<>();
		
		for(Mecanico mecanico: reparacion.getMecanicos()) {
			
			Mecanico mecanicoReal = mecanicoRepository.findById(mecanico.getId())
					.orElseThrow(()-> new RuntimeException("Mecanico no existe"));
			
			mecanicosDB.add(mecanicoReal);
		}
		
		reparacion.setVehiculo(vehiculo);
		reparacion.setMecanicos(mecanicosDB);
		
		return repository.save(reparacion);
		
	}
	
	
	//metodo Listar una lista de reparaciones
	public List<Reparacion> listar(){
		return repository.findAll();
	}
	
	
	//Busca por ID la reparacion
	public Reparacion buscar(int id) {
		
		Optional<Reparacion> resultado = 
				repository.findById(id);
		
		return resultado.orElse(null);
	}
	
	
	
	//Metodo de actualizar la reparacion de los datos
	public Reparacion actulizar(int id, Reparacion datos) {
		
		Reparacion reparacion = buscar (id);
		
		if(reparacion == null) {
			return null;
		}
		
		reparacion.setCosto(datos.getCosto());
		
		reparacion.setDescripcion(datos.getDescripcion());
		
		reparacion.setFechaIngreso(datos.getFechaIngreso());
		
		reparacion.setFechaEntrega(datos.getFechaEntrega());
		
		return repository.save(reparacion);
	}
	
	
	
	
	//Metodo de eliminar reparacion todo lo busca por la ID
	public boolean eliminar(int id) {
		
		Reparacion reparacion = buscar(id);
		
		if(reparacion == null) {
			return false;
		}
		
		repository.deleteById(id);
		
		return true;
	}

}
