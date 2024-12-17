package com.ptoject.VkFriendsWeather.controller.exception;

import com.ptoject.VkFriendsWeather.dto.ErrorResponse;
import com.ptoject.VkFriendsWeather.exception.AuthorizationException;
import com.ptoject.VkFriendsWeather.exception.FriendException;
import com.ptoject.VkFriendsWeather.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ErrorResponse> authorizationException(AuthorizationException ex) {
        log.error("Ошибка во время авторизации пользователя ", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getLocalizedMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> userNotFound(UserNotFoundException ex) {
        log.error("Пользователь не найден ", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getLocalizedMessage()));
    }

    @ExceptionHandler(FriendException.class)
    public ResponseEntity<ErrorResponse> friendException(FriendException ex) {
        log.error("Ошибка во время получения пользователя ", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getLocalizedMessage()));
    }
}
