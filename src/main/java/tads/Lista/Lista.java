package tads.Lista;

public class Lista<T extends Comparable<T>> {
    private NodoLista<T> inicio = null;
    protected int cantidad = 0;
    public Lista() {
        inicio=null;
        cantidad=0;
    }
    public Lista(NodoLista<T> inicio) {
        this.inicio = inicio;
        this.cantidad++;
    }
    public void insertar(T dato) {
        this.inicio = new NodoLista(dato,this.inicio);
        this.cantidad++;
    }

    public boolean existe(T dato) {
        return false;
    }


    public T recuperar(T dato) {
        return null;
    }

    public void eliminar(T dato) {

    }

    public T recuperar(int indice) {
        return null;
    }

    public void eliminar(int indice) {

    }

    public void mostrarIter() {

    }

    public void mostrarRec() {

    }
    public void vaciar() {
        this.inicio = null;
        this.cantidad = 0;
    }

    public boolean contiene(T datoBuscado) {
        for(NodoLista<T> aux = this.inicio; aux != null; aux = aux.getSig()) {
            if ((aux.getDato()).equals(datoBuscado)) {
                return true;
            }
        }

        return false;
    }

    public int largo() {
        return this.cantidad;
    }

    public boolean esVacia() {
        return this.cantidad == 0;
    }

    public void ordenar() {
        this.inicio = this.mergeSort(this.inicio);
    }
//quede aca
    private NodoLista<T> mergeSort(NodoLista<T> head) {
        if (head != null && head.sig != null) {
            Lista.NodoLista middle = this.getMiddle(head);
            Lista.NodoLista nextOfMiddle = middle.sig;
            middle.sig = null;
            Lista.NodoLista left = this.mergeSort(head);
            Lista.NodoLista right = this.mergeSort(nextOfMiddle);
            return this.merge(left, right);
        } else {
            return head;
        }
    }

    private NodoLista<T> merge(NodoLista<T> left, NodoLista<T> right) {
        if (left == null) {
            return right;
        } else if (right == null) {
            return left;
        } else {
            NodoLista result;
            if ((left.dato).compareTo(right.dato) <= 0) {
                result = left;
                left.sig = this.merge(left.sig, right);
            } else {
                result = right;
                right.sig = this.merge(left, right.sig);
            }

            return result;
        }
    }

    private NodoLista<T> getMiddle(NodoLista<T> head) {
        if (head == null) {
            return head;
        } else {
            Lista.NodoLista slow = head;

            for(Lista.NodoLista fast = head; fast.sig != null && fast.sig.sig != null; fast = fast.sig.sig) {
                slow = slow.sig;
            }

            return slow;
        }
    }

    private class NodoLista<T extends Comparable<T>> {
        private T dato;
        private NodoLista<T> sig;

        public NodoLista(T dato) {
            this.dato = dato;
            this.sig = null;
        }

        public NodoLista(T dato, NodoLista sig) {
            this.dato = dato;
            this.sig = sig;
        }

        public T getDato() {
            return this.dato;
        }

        public void setDato(T dato) {
            this.dato = dato;
        }

        public NodoLista<T> getSig() {
            return this.sig;
        }

        public void setSig(NodoLista<T> sig) {
            this.sig = sig;
        }

        public String toString() {
            return this.dato.toString();
        }
    }

}
