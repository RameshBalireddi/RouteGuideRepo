package routeGuide.advice;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.UnexpectedTypeException;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        ValidationErrorResponse errorResponse = new ValidationErrorResponse(Collections.singletonList(errors.toString()));
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(SpelEvaluationException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(SpelEvaluationException ex) {
        List<String> errors = Collections.singletonList(ex.getLocalizedMessage());

        ValidationErrorResponse errorResponse = new ValidationErrorResponse(Collections.singletonList(errors.toString()));
        return ResponseEntity.badRequest().body(errorResponse);
    }


    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(UnexpectedTypeException ex) {
        List<String> errors = Collections.singletonList("its must not be null "+ ex.getLocalizedMessage());

        ValidationErrorResponse errorResponse = new ValidationErrorResponse(Collections.singletonList(errors.toString()));
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ValidationErrorResponse> handleJwtException(ExpiredJwtException ex) {
        List<String> errors = Collections.singletonList("jwt token expired please login again "+ ex.getLocalizedMessage());

        ValidationErrorResponse errorResponse = new ValidationErrorResponse(Collections.singletonList(errors.toString()));
        return ResponseEntity.badRequest().body(errorResponse);
    }


}