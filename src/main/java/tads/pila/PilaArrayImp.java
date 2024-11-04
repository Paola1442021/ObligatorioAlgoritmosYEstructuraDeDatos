package tads.pila;

public class PilaArrayImp<T> implements IPila {

    private T[] datos;
    private int indice;


    public PilaArrayImp(int cantMax) {
        this.datos = (T[]) new Object[cantMax];
        this.indice = -1;
    }

    @Override
    public void push(Object dato) {
        //indice++;
        datos[++indice] = (T) dato;
    }

    @Override
    public int largo() {
        return indice;
    }

    @Override
    public T top() {
        return datos[indice];
    }

    @Override
    public T pop() {
        return datos[indice--];
    }

    @Override
    public boolean estaVacia() {
        return indice == -1;
    }

}
