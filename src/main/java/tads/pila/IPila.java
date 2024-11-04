package tads.pila;

public interface IPila<T> {
    void push(T dato);

    T top();

    T pop();

    boolean estaVacia();

    int largo();
}