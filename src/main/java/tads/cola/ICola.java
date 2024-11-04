package tads.cola;

public interface ICola<T> {
    void push(T dato); //simil push insertamos el elemento en la cola

    T pop(); //extraemos el elemento de la cola y lo devolvemos

    boolean esVacia();

    int largo();

    T top(); //devolvemos el primer elemento de nuestra cola

    void imprimirDatos();
}