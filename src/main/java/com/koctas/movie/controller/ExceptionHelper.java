package com.koctas.movie.controller;

import com.koctas.movie.model.ProcessStatus;
import com.koctas.movie.model.data.ServiceResponseData;
import com.koctas.movie.service.exception.model.ModelNotFoundException;
import com.koctas.movie.service.exception.model.ModelRemoveException;
import com.koctas.movie.service.exception.model.ModelSaveException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author hkaynar on 27.11.2021
 */
@ControllerAdvice
@Slf4j
public class ExceptionHelper {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(value = {ModelSaveException.class, ModelRemoveException.class, ModelNotFoundException.class})
    public ResponseEntity<ServiceResponseData> handleModelException(RuntimeException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(fillServiceResponseData(ex), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<ServiceResponseData> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error(String.join("Illegal Argument Exception: ", ex.getMessage()));
        return new ResponseEntity<>(fillServiceResponseData(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {TransactionSystemException.class})
    public ResponseEntity<ServiceResponseData> handleTransactionSystemException(TransactionSystemException ex) {
        log.error(String.join("TransactionSystem Exception: ", ex.getMessage()));
        return new ResponseEntity<>(fillServiceResponseData(ex), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ServiceResponseData> handleException(Exception ex) {
        log.error(String.join("Exception: ", ex.getMessage()));
        return new ResponseEntity<>(fillServiceResponseData(ex), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ServiceResponseData fillServiceResponseData(Exception ex) {
        var serviceResponseData = new ServiceResponseData();
        serviceResponseData.setStatus(ProcessStatus.ERROR);

        if (ex instanceof TransactionSystemException && ExceptionUtils.getRootCause(ex) instanceof ConstraintViolationException) {
            ConstraintViolationException modelValidatorEx =
                    ((ConstraintViolationException) ((TransactionSystemException) ex).getRootCause());
            Set<ConstraintViolation<?>> constraintViolations = modelValidatorEx.getConstraintViolations();
            if (CollectionUtils.isNotEmpty(constraintViolations)) {
                var message = messageSource.getMessage(StringUtils.substringBetween(constraintViolations.iterator().next().getMessage(), "{", "}"), null, null);
                serviceResponseData.setErrorMessage(message);
            } else {
                serviceResponseData.setErrorMessage(ExceptionUtils.getMessage(ex));
            }
        } else if (ex instanceof DataIntegrityViolationException) {
            serviceResponseData.setErrorMessage(messageSource.getMessage(ex.getMessage(), null, null));

        } else {
            serviceResponseData.setErrorMessage(ExceptionUtils.getMessage(ex));
        }
        serviceResponseData.setErrorMessageDetail(Arrays.asList(ExceptionUtils.getStackFrames(ex)).stream().limit(10).collect(Collectors.joining()));

        return serviceResponseData;
    }
}
