package com.assessment.carritocompras.carrito.Service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.assessment.carritocompras.Model.Carrito;
import com.assessment.carritocompras.Model.Productos;
import com.assessment.carritocompras.Repository.CarritoRepository;
import com.assessment.carritocompras.Repository.ProductosRepository;

@Service
public class CarritoService {
    private final CarritoRepository carritoRepository;
    private final ProductosRepository productRepository;


    public CarritoService(CarritoRepository carritoRepository,ProductosRepository productRepository){
        this.carritoRepository = carritoRepository;
        this.productRepository = productRepository;
    }

    public List<Productos> getAllProductsFromCatalog(){
        return (List<Productos>) productRepository.findAll();
    }

    public Carrito createNewCart(){
        Carrito newCarrito = new Carrito();
        return carritoRepository.save(newCarrito);
    }

    public String deleteCartById(Long id){
        Optional<Carrito> carrito = carritoRepository.findById(id);
        if(carrito.isPresent()){
            carritoRepository.deleteById(id);
            return "Shopping cart has been eliminated, ID: " + id;
        }
        return "Shopping cart with ID: " + id + " not found";
    }

    public Optional<Carrito> getCartById(Long id){
        return carritoRepository.findById(id);
    }


    public List<Carrito> getAllCarts(){
        List<Carrito> carts = new ArrayList<>();
        carritoRepository.findAll().forEach(carts::add);
        return carts;
    }


}
