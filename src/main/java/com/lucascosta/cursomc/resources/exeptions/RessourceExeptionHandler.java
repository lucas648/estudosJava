package com.lucascosta.cursomc.resources.exeptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.lucascosta.cursomc.servicies.exceptions.DataIntegrityException;
import com.lucascosta.cursomc.servicies.exceptions.ObjectNotFoundException;

/*
 * permite que a camada de REST intercepte o erro 
 */
@ControllerAdvice
public class RessourceExeptionHandler {
	/*
	 * assinatura ppadrão para classes que interceptam as exeções
	 * recebendo a exeção e as informações da requisição
	 */
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
	
	  StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(),e.getMessage(), System.currentTimeMillis());
	  return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request){
	
	  StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(),e.getMessage(), System.currentTimeMillis());
	  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
}
