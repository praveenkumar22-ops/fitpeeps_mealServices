package meal_service.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/check/connection/meal")
public class CheckConnection {
    @GetMapping("/ping")
    public ResponseEntity<String> greet(){
        return new ResponseEntity<>("Hello from the Meal-services", HttpStatus.OK);
    }
}
