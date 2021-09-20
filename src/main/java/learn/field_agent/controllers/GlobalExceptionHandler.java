package learn.field_agent.controllers;


import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.apache.log4j.Logger;

@ControllerAdvice // 1
public class GlobalExceptionHandler {

    private static Logger log = Logger.getLogger(GlobalExceptionHandler.class.getName());

    // DataAccessException is the super class of many Spring database exceptions
    // including BadSqlGrammarException.
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleException(DataAccessException ex) {

        // Log the exception?
        System.out.println(escapeMetaCharacters(ex.toString()));
        log.info("DataAccessException: "+escapeMetaCharacters(ex.toString()));


        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse("We can't show you the details, but something went wrong in our database. Sorry :("),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // IllegalArgumentException is the super class for many Java exceptions
    // including all formatting (number, date) exceptions.
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleException(IllegalArgumentException ex) {

        // Log the exception?
        log.info("IllegalArgumentException: "+escapeMetaCharacters(ex.toString()));

        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse(ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // This is our absolute last resort. Yuck.
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
//
//        // Log the exception?
//        log.info("Exception: "+escapeMetaCharacters(ex.toString()));
//
//        return new ResponseEntity<ErrorResponse>(
//                //new ErrorResponse("Something went wrong on our end. Your request failed. :("),
//                new ErrorResponse(ex.getMessage()),
//                HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    public String escapeMetaCharacters(String inputString){
        final String[] metaCharacters = {"\\","^","$","{","}","[","]","(",")",".","*","+","?","|","<",">","-","&","%","'",";"};

        for (int i = 0 ; i < metaCharacters.length ; i++){
            if(inputString.contains(metaCharacters[i])){
                inputString = inputString.replace(metaCharacters[i],"\\"+metaCharacters[i]);
            }
        }
        return inputString;
    }
}