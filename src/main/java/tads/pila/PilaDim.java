package tads.pila;

public class PilaDim<T> implements IPila{
    private Nodo top;

    @Override
    public void push(Object dato) {
         /*Nodo nuevoDato=new Nodo(dato);
        nuevoDato.setSig(this.top);
        this.top =nuevoDato;*/
        this.top=new Nodo(dato,this.top);
    }

    @Override
    public T top() {
        return (T) this.top.getDato();
    }

    @Override
    public T pop() {
        T dato= (T) this.top.getDato();
        this.top=this.top.getSig();
        return dato;
    }

    @Override
    public boolean estaVacia() {
        return this.top==null;
    }

    @Override
    public int largo() {
        return 0;
    }


}
