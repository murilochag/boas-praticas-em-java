package br.com.alura.adopet.api.excpetion;

public class ValidacaoException extends RuntimeException {
    public ValidacaoException(String mensage) {
        super(mensage);
    }
}
