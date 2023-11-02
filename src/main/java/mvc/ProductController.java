package mvc;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ProductController {

    @PostMapping("/add")
    public ModelAndView add(HttpSession session, @Valid @ModelAttribute("product") Product product,
            BindingResult bindingResult) {
        System.out
                .println("error ------------------------>" + bindingResult.getAllErrors() + bindingResult.hasErrors());
        Map<String, Object> params = new HashMap<>();
        if (bindingResult.hasErrors()) {
            params.put("error", "error");
            return new ModelAndView("addproduct", params);
        }
        if (product != null) {
            // get the productlist from the session
            Map<String, Product> productList = (Map<String, Product>) session.getAttribute("productList");
            // if there is no productlist in the session, create one.
            if (productList == null) {
                productList = new HashMap<String, Product>();
                session.setAttribute("carList", productList);
            }
            // add the product to the productlist
            productList.put(product.getProductNumber(), product);
            params.put("productList", productList.values());
        }
        return new ModelAndView("products", params);
    }

    @GetMapping("/products")
    public ModelAndView init(HttpSession session) {
        // get the productlist from the session
        Map<String, Product> productList = (Map<String, Product>) session.getAttribute("productList");
        // if there is no productlist in the session, create one.
        if (productList == null) {
            productList = new HashMap<String, Product>();
            session.setAttribute("productList", productList);
        }
        Map<String, Object> params = new HashMap<>();
        params.put("productList", productList.values());
        return new ModelAndView("products", params);
    }

    @PostMapping("/addproduct")
    public ModelAndView addProduct(HttpSession session) {
        Map<String, Object> params = new HashMap<>();
        params.put("product", new Product());
        return new ModelAndView("addproduct", params);
    }

    @PostMapping("/removeproduct")
    public ModelAndView removeProduct(@RequestParam("pnumber") String pnumber, HttpSession session) {
        Map<String, Object> params = new HashMap<>();
        if (pnumber != null) {
            // get the productlist from the session
            Map<String, Product> productList = (Map<String, Product>) session.getAttribute("productList");
            // if there is no productlist in the session, create one.
            if (productList == null) {
                productList = new HashMap<String, Product>();
                session.setAttribute("productList", productList);
            }
            // add the car to the productlist
            productList.remove(pnumber);
            params.put("productList", productList.values());
        }
        return new ModelAndView("products", params);
    }
}
