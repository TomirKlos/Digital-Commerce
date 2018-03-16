package pl.klaster.digitalcommerce.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.klaster.ecommerce.sales.readmodel.productcatalog.ProductDto;
import pl.klaster.ecommerce.canonicalmodel.Identifier;
import pl.klaster.ecommerce.sales.application.api.AddToBasketCommand;
import pl.klaster.ecommerce.sales.application.api.AddToBasketHandler;
import pl.klaster.ecommerce.sales.domain.productcatalog.ProductNotAvailableException;
import pl.klaster.ecommerce.sales.readmodel.productcatalog.ProductFinder;


import java.util.List;

@RestController
public class ProductsController {

    @Autowired
    private AddToBasketHandler addToBasketHandler;

    @Autowired
    private ProductFinder productFinder;


    @RequestMapping("/products")
    @ResponseBody
    public List<ProductDto> products() {
        return productFinder.all();
    }

    @RequestMapping("/add-to-basket/{id}")
    public String addToBasket(@PathVariable(value="id") String id) {
        try {
            addToBasketHandler.handle(new AddToBasketCommand(new Identifier(id)));
            return "OK";
        } catch (ProductNotAvailableException e) {
            return "Something is not YES!!!!!";
        }
    }


}