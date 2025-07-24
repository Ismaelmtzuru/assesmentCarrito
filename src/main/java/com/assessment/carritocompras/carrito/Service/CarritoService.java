package com.assessment.carritocompras.carrito.Service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import com.assessment.carritocompras.Model.Carrito;
import com.assessment.carritocompras.Model.CarritoItem;
import com.assessment.carritocompras.Model.Productos;
import com.assessment.carritocompras.Repository.CarritoItemRepository;
import com.assessment.carritocompras.Repository.CarritoRepository;
import com.assessment.carritocompras.Repository.ProductosRepository;

@Service
public class CarritoService {
    private final CarritoRepository carritoRepository;
    private final ProductosRepository productRepository;
    private final CarritoItemRepository itemRepository;

    public CarritoService(CarritoItemRepository itemRepository,CarritoRepository carritoRepository,ProductosRepository productRepository){
        this.carritoRepository = carritoRepository;
        this.productRepository = productRepository;
        this.itemRepository = itemRepository;
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

     public Object getCartById(Long id){
            Optional<Carrito> carrito = carritoRepository.findById(id);
            if(carrito.isEmpty()){
                return "Cart not found";
            }
            
            // Verificar items usando el repositorio directamente
            List<CarritoItem> items = itemRepository.findByCarritoCompras_CartID(id);
            if(items.isEmpty()){
                return "Shopping cart is empty, need an item to be assigned";
            }
            
            return items;  // Carrito con items
        }



    public List<Carrito> getAllCarts(){
        List<Carrito> carts = new ArrayList<>();
        carritoRepository.findAll().forEach(carts::add);
        return carts;
    }

    //Borrar todos los elementos de un carrito, carrito buscado por id
    public String deleteAllItemsFromCartById(Long id){
        if(!carritoRepository.existsById(id)){
            return "Shopping cart with id:" + id + " not found";
        }
        List<CarritoItem> items= itemRepository.findByCarritoCompras_CartID(id);
        if(!items.isEmpty()){
            itemRepository.deleteAll(items);
            return "Shopping cart with id: " + id + " emptied";
        }else{
            return "Shopping cart its already empty";
        }

    }


}
