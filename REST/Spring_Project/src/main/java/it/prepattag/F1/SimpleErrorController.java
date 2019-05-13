package it.prepattag.F1;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@ControllerAdvice
@RequestMapping("/error")
public class SimpleErrorController implements ErrorController {
    @RequestMapping
    public Map error() {
        HashMap<String, Object> m = new HashMap<>();
        m.put("error", "la pagina non esiste");
        return m;
    }


    @Override
    public String getErrorPath() {
        return "/error";
    }
}
