package com.codegym.controller;

import com.codegym.model.Cart;
import com.codegym.model.Product;
import com.codegym.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@SessionAttributes("cart")
public class ProductController {
	@Autowired
	private ProductService productService;
	@ModelAttribute ("cart")
	public Cart setupCart (){
		return new Cart();
	}

	@GetMapping ("/shop")
	public ModelAndView showShop (){
		ModelAndView modelAndView = new ModelAndView("/shop");
		Iterable <Product> products = productService.findAll();
		modelAndView.addObject("products", products);
		return modelAndView;
	}
	@GetMapping ("/add/{id}")
	public String addToCart (@PathVariable Long id, @ModelAttribute Cart cart, @RequestParam String action){
		Optional<Product> product = productService.findById(id);
		if (!product.isPresent()){
			return "/error.404";
		}
		if (action.equals("show")){
			cart.addProduct(product.get());
			return "redirect:/shopping-cart";
		}
		cart.addProduct(product.get());
		return "redirect:/shop";
	}
	@GetMapping ("/reduce/{id}")
	public String reduceProduct (@PathVariable Long id, @ModelAttribute Cart cart, @RequestParam String action){
		Optional<Product> product = productService.findById(id);
		if (!product.isPresent()){
			return "/error.404";
		}
		if (action.equals("show")){
			cart.reduceProduct(product.get());
			return "redirect:/shopping-cart";
		}
		cart.reduceProduct(product.get());
		return "redirect:/shop";
	}
}
