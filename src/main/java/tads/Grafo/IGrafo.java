package tads.Grafo;

import tads.Lista.Lista;

public interface IGrafo {

    void agregarSucursal(Sucursal v);

    void agregarConexion(Sucursal origen, Sucursal destino, int peso);

    void borrarSucursal(Sucursal v);

    void borrarConexion(Sucursal origen, Sucursal destino);

    boolean esVacio();

    /*Lista<String> sucursalesAdyacentes(String v);*/
    public void actualizarConexion(String codigoSucursal1, String codigoSucursal2, int latencia);
    public boolean esSucursalCritica(Sucursal sucursal);
    boolean sonAdyacentes(Sucursal v1, Sucursal v2);

    boolean existeSucursal(Sucursal v);

    void bfs(String v);

    void dfs(String v);

    void dijkstra(Sucursal vInicial,Sucursal vFinal);
}
