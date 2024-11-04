package tads.pila;

public class Nodo<T> {
    private T dato;
    private Nodo sig;

    public Nodo(T dato){
        this.dato=dato;
        this.sig=null;
    }

    public Nodo(T dato,Nodo sig){
        this.dato=dato;
        this.sig=sig;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public Nodo getSig() {
        return sig;
    }

    public void setSig(Nodo sig) {
        this.sig = sig;
    }
}