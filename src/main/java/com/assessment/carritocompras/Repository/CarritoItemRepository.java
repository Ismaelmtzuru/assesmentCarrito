package com.assessment.carritocompras.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.assessment.carritocompras.Model.CarritoItem;

@Repository
public interface CarritoItemRepository extends CrudRepository<CarritoItem,Long>{
    List<CarritoItem> findByCarritoCompras_CartID(Long carritoId);
}
