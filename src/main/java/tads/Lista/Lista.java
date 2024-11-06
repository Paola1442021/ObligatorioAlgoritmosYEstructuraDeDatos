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

    public String listarAscendente() {
        // Asegurarse de que la lista esté ordenada
        ordenar();

        // Crear un StringBuilder para almacenar los elementos en orden ascendente
        StringBuilder resultado = new StringBuilder();
        NodoLista<T> actual = this.inicio;

        // Recorrer la lista y agregar cada elemento a la cadena con el separador "|"
        while (actual != null) {
            resultado.append(actual.getDato().toString());
            if (actual.getSig() != null) { // Solo agregar "|" si no es el último elemento
                resultado.append("|");
            }
            actual = actual.getSig();
        }

        return resultado.toString(); // Devuelve la cadena final
    }

    public void ordenar() {
        // Llamamos a mergeSort pasando el nodo de inicio
        this.inicio = mergeSort(this.inicio);
    }
    private NodoLista<T> mergeSort(NodoLista<T> head) {
        // Caso base: lista vacía o de un solo elemento, ya está ordenada
        if (head == null || head.sig == null) {
            return head;
        }

        // Encuentra el nodo medio de la lista
        NodoLista<T> middle = getMiddle(head);
        NodoLista<T> nextOfMiddle = middle.sig;

        // Divide la lista en dos mitades
        middle.sig = null;

        // Aplica mergeSort en ambas mitades
        NodoLista<T> left = mergeSort(head);
        NodoLista<T> right = mergeSort(nextOfMiddle);

        // Combina las dos mitades ordenadas
        return merge(left, right);
    }

    private NodoLista<T> merge(NodoLista<T> left, NodoLista<T> right) {
        // Caso base: si una de las listas es nula, devolvemos la otra
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }

        NodoLista<T> result;

        // Compara los elementos y ordena en forma ascendente
        if (left.dato.compareTo(right.dato) <= 0) {
            result = left;
            result.sig = merge(left.sig, right);
        } else {
            result = right;
            result.sig = merge(left, right.sig);
        }

        return result;
    }
    private NodoLista<T> getMiddle(NodoLista<T> head) {
        if (head == null) {
            return head;
        }

        NodoLista<T> slow = head;
        NodoLista<T> fast = head;

        // Usa un puntero rápido y uno lento para encontrar el nodo medio
        while (fast.sig != null && fast.sig.sig != null) {
            slow = slow.sig;
            fast = fast.sig.sig;
        }

        return slow;
    }
    public String listarDescendente() {
        // Utilizamos un StringBuilder para almacenar el resultado
        StringBuilder resultado = new StringBuilder();
        listarDescendenteRecursivo(inicio, resultado);
        return resultado.toString().trim();
    }

    private void listarDescendenteRecursivo(NodoLista<T> nodo, StringBuilder resultado) {
        if (nodo == null) {
            return; // Caso base: llegamos al final de la lista
        }
        // Llamada recursiva al siguiente nodo
        listarDescendenteRecursivo(nodo.getSig(), resultado);

        // Después de volver de la recursión, agregamos el dato al StringBuilder
        resultado.append(nodo.getDato().toString()).append(" ");
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
