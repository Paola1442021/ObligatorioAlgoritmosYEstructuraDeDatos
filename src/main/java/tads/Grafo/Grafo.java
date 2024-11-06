package tads.Grafo;

import tads.Lista.Lista;


public class Grafo implements IGrafo{

    private int cantMAxSucursales;
    private int cantActualSucursales;

    private boolean esDirigido;

    private Sucursal[] sucursales;
    private Conexion[][] matAdy;

    public Grafo(int cantMAxSucursales) {
        this.cantMAxSucursales = cantMAxSucursales;
        this.esDirigido = true;
        this.cantActualSucursales = 0;

        this.sucursales = new Sucursal[this.cantMAxSucursales];
        this.matAdy = new Conexion[this.cantMAxSucursales][this.cantMAxSucursales];

        for (int i = 0; i < this.cantMAxSucursales; i++) {
            for (int j = 0; j < this.cantMAxSucursales; j++) {
                this.matAdy[i][j] = new Conexion();
            }
        }
    }


    public Grafo(int cantMAxVertices, boolean esDirigido) {
        this.cantMAxSucursales = cantMAxVertices;
        this.esDirigido = esDirigido;
        this.cantActualSucursales = 0;
        this.sucursales = new Sucursal[this.cantMAxSucursales];
        this.matAdy = new Conexion[this.cantMAxSucursales][this.cantMAxSucursales];

        if (this.esDirigido) {
            for (int i = 0; i < this.cantMAxSucursales; i++) {
                for (int j = 0; j < this.cantMAxSucursales; j++) {
                    this.matAdy[i][j] = new Conexion();
                }
            }
        } else {
            for (int i = 0; i < this.cantMAxSucursales; i++) {
                for (int j = i; j < this.cantMAxSucursales; j++) {
                    this.matAdy[i][j] = new Conexion();
                    this.matAdy[j][i] = this.matAdy[i][j];

                }
            }
        }
    }

    private int obtenerPosVacia() {
        for (int i = 0; i < this.cantMAxSucursales; i++) {
            if (this.sucursales[i] == null) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void agregarSucursal(Sucursal v) {
        if (this.cantActualSucursales < this.cantMAxSucursales) {
            int poscicionvacia = this.obtenerPosVacia();
            this.sucursales[poscicionvacia] = v;
            this.cantActualSucursales++;
        }
    }

    private int obtenerPosSucursal(Sucursal v) {
        for (int i = 0; i < this.cantMAxSucursales; i++) {
            if (this.sucursales[i] != null && this.sucursales[i].equals(v)) {
                return i;
            }
        }
        return -1;
    }


    @Override
    public void borrarSucursal(Sucursal v) {
        int posSucursal = this.obtenerPosSucursal(v);
        this.sucursales[posSucursal] = null;
        this.cantActualSucursales--;
        for (int i = 0; i < this.cantMAxSucursales; i++) {
            this.matAdy[posSucursal][i] = new Conexion();
            this.matAdy[i][posSucursal] = new Conexion();
        }

    }

    @Override
    public void agregarConexion(Sucursal origen, Sucursal destino, int peso) {
        int posOrigen = this.obtenerPosSucursal(origen);
        int posDestino = this.obtenerPosSucursal(destino);

        if (posOrigen >= 0 && posDestino >= 0) {
            this.matAdy[posOrigen][posDestino].setExiste(true);
            this.matAdy[posOrigen][posDestino].setLatencia(peso);
            if (!this.esDirigido) {
                this.matAdy[posDestino][posOrigen].setExiste(true);
                this.matAdy[posDestino][posOrigen].setLatencia(peso);
            }
        }
    }

    @Override
    public void borrarConexion(Sucursal origen, Sucursal destino) {
        int posOrigen = this.obtenerPosSucursal(origen);
        int posDestino = this.obtenerPosSucursal(destino);
        this.matAdy[posOrigen][posDestino] = new Conexion();
        if (!this.esDirigido) {
            this.matAdy[posDestino][posOrigen] = new Conexion();
        }
    }


    @Override
    public boolean esVacio() {
        return this.cantActualSucursales == 0;
    }
    @Override
    public boolean sonAdyacentes(Sucursal v1, Sucursal v2) {
        int posOrigen = this.obtenerPosSucursal(v1);
        int posDestino = this.obtenerPosSucursal(v2);

        if (posOrigen == -1 || posDestino == -1) {
            System.out.println("Una de las sucursales no existe en el grafo.");
            return false;
        }

        // Verifica la conexión en ambas direcciones para grafos no dirigidos
        return this.matAdy[posOrigen][posDestino].isExiste() || this.matAdy[posDestino][posOrigen].isExiste();
    }

    @Override
    public boolean existeSucursal(Sucursal v) {
        return this.obtenerPosSucursal(v) >= 0;
    }

    @Override
    public void bfs(String v) {

    }

    @Override
    public void dfs(String v) {

    }

    @Override
    public void dijkstra(Sucursal vInicial, Sucursal vFinal) {
        int posOrigen = this.obtenerPosSucursal(vInicial);
        int posDestino = this.obtenerPosSucursal(vFinal);

        boolean[] visitados = new boolean[this.cantMAxSucursales];
        int[] costos = new int[this.cantMAxSucursales];
        int[] anteriores = new int[this.cantMAxSucursales];

        for (int i = 0; i < this.cantMAxSucursales; i++) {
            visitados[i] = false;
            costos[i] = Integer.MAX_VALUE;
            anteriores[i] = -1;
        }

        //Marco en 0 mi costo del vertice inicial
        costos[posOrigen] = 0;
        visitados[posOrigen] = true;
        int minpos = posOrigen;
        while (minpos > -1) {
            actualizar_costos_anteriores(costos, minpos, visitados, anteriores);
            minpos = this.obtenerPosMenorCostoNoVisitado(costos, visitados, posDestino);
        }
        for (int i = 0; i < this.cantMAxSucursales; i++) {
            System.out.println(this.sucursales[i] + " (i:" + i + " -> " + costos[i] + ") -> " + anteriores[i]);
        }

    }

    private void actualizar_costos_anteriores(int[] costos, int posActual, boolean[] visitados, int[] anteriores) {
        for (int i = 0; i < this.cantMAxSucursales; i++) {
            if (i != posActual) {
                int costo_ir_desde_vertice_actual = costos[posActual] + matAdy[posActual][i].getLatencia();
                if (matAdy[posActual][i].isExiste() && !visitados[i] && costos[i] > costo_ir_desde_vertice_actual) {
                    costos[i] = costo_ir_desde_vertice_actual;
                    anteriores[i] = posActual;
                }
            }
        }
    }

    private int obtenerPosMenorCostoNoVisitado(int[] costos, boolean[] visitados, int posDestino) {
        int minPos = -1;
        int minValue = Integer.MAX_VALUE;
        for (int i = 0; i < this.cantMAxSucursales; i++) {
            if (!visitados[i] && costos[i] < minValue) {
                minValue = costos[i];
                if (i == posDestino) {
                    //Seria logica de finalizacion sin marcar como visitado en el obligatorio
                    visitados[i] = true;
                    return -1;
                }
                visitados[i] = true;
                minPos = i;
            }
        }
        return minPos;
    }
    public void actualizarConexion(String codigoSucursal1, String codigoSucursal2, int latencia) {
        // Crear instancias de Sucursal para buscar sus posiciones
        Sucursal sucursal1 = new Sucursal(codigoSucursal1, null); // nombre es irrelevante para la búsqueda
        Sucursal sucursal2 = new Sucursal(codigoSucursal2, null); // nombre es irrelevante para la búsqueda

        // Obtener posiciones de las sucursales
        int posSucursal1 = this.obtenerPosSucursal(sucursal1);
        int posSucursal2 = this.obtenerPosSucursal(sucursal2);

        // Verificar si ambas sucursales existen
        if (posSucursal1 == -1 || posSucursal2 == -1) {


            // Actualizar la latencia en la matriz de adyacencia
            this.matAdy[posSucursal1][posSucursal2].setLatencia(latencia);
            this.matAdy[posSucursal2][posSucursal1].setLatencia(latencia); // si el grafo es no dirigido
        }
    }
    public boolean esSucursalCritica(Sucursal sucursal) {
        int posSucursal = this.obtenerPosSucursal(sucursal);
        if (posSucursal == -1) {
            return false; // La sucursal no existe
        }

        boolean[] visitados = new boolean[this.cantMAxSucursales];

        // Elegimos un nodo inicial para el DFS, asegurándonos de que no sea el que queremos "eliminar"
        int nodoInicial = 0;
        while (nodoInicial < this.cantMAxSucursales && (this.sucursales[nodoInicial] == null || nodoInicial == posSucursal)) {
            nodoInicial++;
        }

        if (nodoInicial >= this.cantMAxSucursales) {
            return false; // No hay otros nodos para verificar
        }

        // Ejecutamos DFS excluyendo la sucursal crítica
        dfsSinSucursal(nodoInicial, visitados, posSucursal);

        // Revisamos si quedaron nodos no alcanzados (lo cual haría crítica a la sucursal eliminada)
        for (int i = 0; i < this.cantMAxSucursales; i++) {
            if (i != posSucursal && this.sucursales[i] != null && !visitados[i]) {
                return true; // Si algún nodo no fue alcanzado, la sucursal es crítica
            }
        }
        return false; // La sucursal no es crítica si todos los demás nodos son alcanzables
    }
    private void dfsSinSucursal(int nodo, boolean[] visitados, int posSucursalEliminada) {
        visitados[nodo] = true;
        System.out.println("Visitando nodo: " + nodo);

        for (int i = 0; i < this.cantMAxSucursales; i++) {
            // Verificar si el nodo i está conectado al nodo actual, no ha sido visitado, y no es el nodo a eliminar
            if (i != posSucursalEliminada && !visitados[i] && matAdy[nodo][i].isExiste()) {
                System.out.println("Explorando conexión entre nodo " + nodo + " y nodo " + i);
                dfsSinSucursal(i, visitados, posSucursalEliminada);
            }
        }
    }







}
