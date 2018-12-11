package com.databootcamp.dataprovider.controllers;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import com.databootcamp.dataprovider.models.ModelKeyValue;
import com.databootcamp.dataprovider.repositories.KeyValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/*
 * This code could be reworked to hide service code implemented here inline inside controller
 */
@RestController
@RequestMapping("/v1")
@CrossOrigin("*")
public class KeyValueController {

	@Autowired
	KeyValueRepository keyValueRepository;

	@GetMapping("/key/{key}")
	public ResponseEntity<ModelKeyValue> getModel(@PathVariable("key") String key) {
		return keyValueRepository.findById(key).map(model -> ResponseEntity.ok().body(model))
				.orElse(ResponseEntity.notFound().header("keyNotFound", key).build());
	}

	@PostMapping("/key/{key}/value/{value}")
	public ResponseEntity<?> createUpdateModel(@PathVariable("key") String key, @PathVariable("value") String value) {
		return keyValueRepository.findById(key).map(model -> {
			model.setValue(value);
			final String error = validateModelKeyValue(model);
			if (error != null) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
			}
			ModelKeyValue updatedModel = keyValueRepository.save(model);
			return ResponseEntity.ok().body(updatedModel);
		}).orElseGet(() -> {
			ModelKeyValue model = new ModelKeyValue(key, value);
			final String error = validateModelKeyValue(model);
			if (error != null) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
			}
			ModelKeyValue updatedModel = keyValueRepository.save(model);
			return ResponseEntity.ok().body(updatedModel);
		});
	}

	private String validateModelKeyValue(final ModelKeyValue entity) {
		Validator validatorModel = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<ModelKeyValue>> violations = violations = validatorModel.validate(entity);
		if (violations.size() > 0) {
			ConstraintViolation<ModelKeyValue> violation = violations.iterator().next();
			return violation.getPropertyPath().toString() + " : " + violation.getMessage();
		}
		return null;
	}
}
