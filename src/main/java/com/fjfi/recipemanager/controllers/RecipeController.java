package com.fjfi.recipemanager.controllers;

import com.fjfi.recipemanager.model.Recipe;
import com.fjfi.recipemanager.services.RecipeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
//@AllArgsConstructor
//@NoArgsConstructor
@Validated
public class RecipeController {
    RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/recipe/new")
    public ResponseEntity<Object> postRecipe(@Valid @RequestBody Recipe recipe, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            Map<String, String> errorMessages = new HashMap<>();
            for (ObjectError error : errors) {
                errorMessages.put(error.getObjectName(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }
        Recipe newRecipe = recipeService.save(recipe);
        return ResponseEntity.ok(Map.of("id", newRecipe.getId()));
    }

    @GetMapping("/recipe/{id}")
    public Recipe getRecipe(@PathVariable("id") @Min(1) long id) {
        return recipeService.findRecipeById(id);


    }

    @DeleteMapping("/recipe/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteRecipe(@PathVariable long id) {
        recipeService.deleteRecipeById(id);
    }

}