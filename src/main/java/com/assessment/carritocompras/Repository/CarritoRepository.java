package com.assessment.carritocompras.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.assessment.carritocompras.Model.Carrito;

@Repository
public interface CarritoRepository extends CrudRepository<Carrito,Long>{
    
}


