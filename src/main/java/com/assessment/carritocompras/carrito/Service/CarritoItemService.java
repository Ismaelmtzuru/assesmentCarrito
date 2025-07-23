package com.assessment.carritocompras.carrito.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.assessment.carritocompras.Model.Carrito;
import com.assessment.carritocompras.Model.CarritoItem;
import com.assessment.carritocompras.Repository.CarritoItemRepository;
import com.assessment.carritocompras.Repository.CarritoRepository;

@Service
public class CarritoItemService {
    private final CarritoItemRepository itemRepository;
    private final CarritoRepository carritoRepository;

    public CarritoItemService(CarritoItemRepository itemRepository, CarritoRepository carritoRepository){
        this.itemRepository=itemRepository;
        this.carritoRepository = carritoRepository;
    }
    
    //Obtener todos los elementos de un carrito, buscado por ID
    public List<CarritoItem> getCartAllItemsById(Long id){
        Optional<Carrito> carrito = carritoRepository.findById(id);
        if (carrito.isPresent()){
            return carrito.get().getCarritoItem();
        }
        return new ArrayList<>();
    }


    //Borrar todos los elementos de un carrito, carrito buscado por id
    public String deleteAllItemsFromCartById(Long id){
        Optional<Carrito> carrito = carritoRepository.findById(id);
        if (carrito.isPresent()){
            carrito.get().getCarritoItem().clear();
            carritoRepository.save(carrito.get());
            return "Shopping carg with ID: " + id + " is now empty";
        }else{
            return "Shopping cart with ID: " + id + "not found";
        }
    }


    //Obtener un elemento en específico del carrito id y id del elemento
    public CarritoItem getItemByCartAndProductId(Long carritoId,Long productId){
        Optional<Carrito> carrito = carritoRepository.findById(carritoId);
        if(carrito.isPresent()){
            return carrito.get().getCarritoItem().stream().filter(item ->item.getProducto().getId().equals(productId)).findFirst().orElseThrow(() -> new RuntimeException("Product ID: " + productId + " nof found"));
        }else{
            throw new RuntimeException("Shopping cart with ID: " + carritoId + " not found");
        }
    }

    //Eliminar un elemento en específico del carrito id y id del elemento
    public String deleteItemByCartAndProductId(Long carritoId,Long productId){
        Optional<Carrito> carrito = carritoRepository.findById(carritoId);
        if(carrito.isPresent()){
            CarritoItem elementoEliminar = carrito.get().getCarritoItem().stream().filter(item->item.getProducto().getId().equals(productId)).findFirst().orElseThrow(()-> new RuntimeException("Product not found in shopping cart"));
            itemRepository.delete(elementoEliminar);
            return "Item eliminado del carrito";
        }else{
            throw new RuntimeException("Cart not found");
        }
    }

    public String addItemToCartById(Long cartId, CarritoItem carritoItem){
        Optional<Carrito> carrito = carritoRepository.findById(cartId);
        if(carrito.isPresent()){
            carritoItem.setCarritoCompras(carrito.get());
            itemRepository.save(carritoItem);
            return "Item added to cart with id: " + cartId;
        }else{
            throw new RuntimeException("Cart not found");
        }
        }

}
