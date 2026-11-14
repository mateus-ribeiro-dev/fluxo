package br.com.fiap.fluxo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorGlobalException {

    @ExceptionHandler(LinhaInexistenteException.class)
    public ProblemDetail tratar(LinhaInexistenteException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Linha inexistente");
        problemDetail.setDetail(e.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(EstacaoInexistenteException.class)
    public ProblemDetail tratar(EstacaoInexistenteException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Estação inexistente");
        problemDetail.setDetail(e.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(OcupacaoInexistenteException.class)
    public ProblemDetail tratar(OcupacaoInexistenteException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Ocupação inexistente");
        problemDetail.setDetail(e.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(AlertaInexistenteException.class)
    public ProblemDetail tratar(AlertaInexistenteException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Alerta inexistente");
        problemDetail.setDetail(e.getMessage());
        return problemDetail;
    }
}
