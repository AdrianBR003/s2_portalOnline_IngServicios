package com.p2.portal_online.Service;

import com.p2.portal_online.Model.Product;
import com.p2.portal_online.Repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final IProductRepository iProductRepository;


    // Inyección en el constructor
    @Autowired
    public ProductService(IProductRepository iProductRepository){
        this.iProductRepository = iProductRepository;
    }



    public List<Product> inicializar(){
        List<Product> productList = iProductRepository.findAll();
        if(productList.isEmpty()){
            Product pr1 = new Product("Zapatillas 1","/images/zapatilla3.jpeg",80);
            productList.add(pr1);
            Product pr2 = new Product("Zapatillas 2","/images/zapatilla2.jpeg",80);
            productList.add(pr2);
            Product pr3 = new Product("Zapatillas 3","/images/zapatilla4.jpeg",80);
            productList.add(pr3);
            Product pr4 = new Product("Zapatillas 4","/images/zapatilla1.webp",80);
            productList.add(pr4);
            Product pr5 = new Product("Zapatillas 2","/images/zapatilla2.jpeg",80);
            productList.add(pr5);
            Product pr6 = new Product("Zapatillas 3","/images/zapatilla4.jpeg",80);
            productList.add(pr6);
            Product pr7 = new Product("Zapatillas 4","/images/zapatilla1.webp",80);
            productList.add(pr7);
            iProductRepository.saveAll(productList);
            return productList;
        }else{
            return productList;
        }
    }



}
