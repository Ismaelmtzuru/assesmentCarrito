package com.assessment.carritocompras.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.assessment.carritocompras.Model.Productos;
@Repository
public interface ProductosRepository extends CrudRepository<Productos,Long>{

    
} 
