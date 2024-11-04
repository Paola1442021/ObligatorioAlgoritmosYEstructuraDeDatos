package tads.ABB;

import tads.Lista.Lista;

public class ABB <T extends Comparable<T>>{
    protected NodoABB<T> raiz;
    //creamos resultadobusqueda para retornar el nodo y la cantidad de veces que se recorrió
    public ResultadoBusqueda<T> resultado;

    public void insertar(T dato) {
        if (raiz == null)
            raiz = new NodoABB<>(dato);
        else
            insertarRec(dato, raiz);
    }
    public int contarNodos() {
        return contarNodos(raiz);
    }

    private int contarNodos(NodoABB<T> nodo) {
        if (nodo == null) {
            return 0; // Si el nodo es nulo, no hay nada que contar, retorna 0
        } else {
            // Suma 1 por el nodo actual, y llama recursivamente para el subárbol izquierdo y derecho
            return 1 + contarNodos(nodo.izq) + contarNodos(nodo.der);
        }
    }

    private void insertarRec(T dato, NodoABB<T> nodo) {
        if (nodo.dato.compareTo(dato) > 0) {
            if (nodo.izq == null)
                nodo.izq = new NodoABB<>(dato);
            else
                insertarRec(dato, nodo.izq);
        } else if (nodo.dato.compareTo(dato) < 0) {
            if (nodo.der == null)
                nodo.der = new NodoABB<>(dato);
            else
                insertarRec(dato, nodo.der);
        }
    }

    public boolean pertenece(T dato) {
        return pertenece(dato, raiz);
    }

    private boolean pertenece(T dato, NodoABB<T> nodo) {
        if (nodo != null) {
            if (nodo.dato.equals(dato))
                return true;
            else if (nodo.dato.compareTo(dato) > 0) {
                return pertenece(dato, nodo.izq);
            } else if (nodo.dato.compareTo(dato) < 0) {
                return pertenece(dato, nodo.der);
            }
        }
        return false;
    }

    public void listarAscendente() {
        listarAscendente(raiz);
    }

    private void listarAscendente(NodoABB<T> nodo) {
        if (nodo != null) {
            listarAscendente(nodo.izq);
            System.out.println(nodo.dato);
            listarAscendente(nodo.der);
        }
    }

    public String listarAscendenteString() {
        return listarAscendenteString(raiz);
    }

    private String listarAscendenteString(NodoABB<T> nodo) {
        if (nodo != null) {
            return listarAscendenteString(nodo.izq) + "|" + nodo.dato + "|" + listarAscendenteString(nodo.der);
        }
        return "";
    }


    public void listarDescendente() {
        listarDescendente(raiz);
    }

    private void listarDescendente(NodoABB<T> nodo) {
        if (nodo != null) {
            listarDescendente(nodo.der);
            System.out.println(nodo.dato);
            listarDescendente(nodo.izq);
        }
    }
    public String listarDescendenteString() {
        return listarDescendenteString(raiz);
    }

    private String listarDescendenteString(NodoABB<T> nodo) {
        if (nodo != null) {
            return listarDescendenteString(nodo.der) + "|" + nodo.dato + "|" + listarDescendenteString(nodo.izq);
        }
        return "";
    }

    public T borrarMinimo() {
        if (raiz.izq == null) {
            raiz = raiz.der;
            return raiz.dato;
        } else {
            return borrarMinimo(raiz);
        }
    }

    private T borrarMinimo(NodoABB<T> nodo) {
        if (nodo.izq.izq == null) {
            T borrado = nodo.izq.dato;
            nodo.izq = nodo.izq.der;
            return borrado;
        } else {
            return borrarMinimo(nodo.izq);
        }
    }

    public int contarElementosMayoresA(T k) {
        return contarElementosMayoresA(raiz, k);
    }

    private int contarElementosMayoresA(NodoABB<T> nodo, T k) {
        if (nodo != null) {
            if (nodo.dato.compareTo(k) <= 0) {
                return contarElementosMayoresA(nodo.der, k);
            } else {
                return 1 + contarElementosMayoresA(nodo.izq, k) + contarElementosMayoresA(nodo.der, k);
            }
        }
        return 0;
    }

    public Lista<T> listarElementosAsc() {
        Lista<T> elementos = new Lista<>();
        listarElementosAsc(raiz, elementos);
        return elementos;
    }

    private void listarElementosAsc(NodoABB<T> nodo, Lista<T> lista) {
        if (nodo != null) {
            listarElementosAsc(nodo.izq, lista);
            lista.insertar(nodo.dato);
            listarElementosAsc(nodo.der, lista);
        }
    }

    public ResultadoBusqueda<T> buscar(T dato) {
        return buscar(dato,raiz, 0);
    }

    private ResultadoBusqueda<T> buscar(T dato, NodoABB<T> nodo, int contador) {
        if (nodo != null) {
            if (nodo.dato.equals(dato)) {
                return new ResultadoBusqueda<>(nodo.dato, contador + 1); // Nodo encontrado, retorna el nodo y el contador
            } else if (nodo.dato.compareTo(dato) > 0) {
                return buscar(dato, nodo.izq, contador + 1); // Incrementa contador y busca en el subárbol izquierdo
            } else {
                return buscar(dato, nodo.der, contador + 1); // Incrementa contador y busca en el subárbol derecho
            }
        }
        return new ResultadoBusqueda<>(null, contador); // Nodo no encontrado, retorna null y el contador
    }
    public T buscarSimple(T dato) {
        return buscarSimple(dato, raiz);
    }

    private T buscarSimple(T dato, NodoABB<T> nodo) {
        if (nodo != null) {
            if (nodo.dato.equals(dato)) {
                return nodo.dato;  // Dato encontrado
            } else if (nodo.dato.compareTo(dato) > 0) {
                return buscarSimple(dato, nodo.izq);  // Buscar en el subárbol izquierdo
            } else {
                return buscarSimple(dato, nodo.der);  // Buscar en el subárbol derecho
            }
        }
        return null;  // Dato no encontrado
    }
    public void imprimirElementosDelNivel(int k) {
        imprimirElementosDelNivel(raiz, k, 0);
    }

    private void imprimirElementosDelNivel(NodoABB<T> nodo, int k, int nivel) {
        if (nodo != null) {
            if (nivel == k) {
                System.out.println(nodo.dato);
            } else {
                imprimirElementosDelNivel(nodo.izq, k, nivel + 1);
                imprimirElementosDelNivel(nodo.der, k, nivel + 1);
            }
        }
    }

    public void imprimirElementosDelNivelOtroMetodo(int k) {
        imprimirElementosDelNivelOtroMetodo(raiz, k);
    }

    private void imprimirElementosDelNivelOtroMetodo(NodoABB<T> nodo, int k) {
        if (nodo != null) {
            if (k == 0) {
                System.out.println(nodo.dato);
            } else {
                imprimirElementosDelNivelOtroMetodo(nodo.izq, k - 1);
                imprimirElementosDelNivelOtroMetodo(nodo.der, k - 1);
            }
        }
    }
    //Como Java no admite la devolución de múltiples valores directamente,
    // creo una clase auxiliar que contenga tanto el nodo encontrado como el contador.
    public class ResultadoBusqueda<T> {
        private T dato;
        private int contador;

        // Constructor que recibe un NodoABB y un contador
        public ResultadoBusqueda(T dato, int contador) {
            this.dato = dato;
            this.contador = contador;
        }

        // Método para obtener el dato del nodo
        public T getDato() {
            return dato; // Si 'dato' es null, retornará null
        }
        public int getContador() {
            return contador;
        }

        @Override
        public String toString() {
            return "Dato: " + (dato != null ? dato : "null") + ", Cantidad de elementos recorridos: " + contador;
        }
    }

    /*la clase nodo la hice publica porque sino era imposible accceder con el contador*/
    protected class NodoABB<Q> {
        protected Q dato;
        protected NodoABB<Q> izq;
        protected NodoABB<Q> der;

        public NodoABB(Q dato) {
            this.dato = dato;
        }

        @Override
        public String toString() {
            return "NodoABB[" + dato + "]";
        }
    }
}
