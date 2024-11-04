package tads.Grafo;


public class Conexion {
    private int latencia;
    private boolean existe;

    public Conexion() {
        this.latencia = 0;
        this.existe = false;
    }

    public Conexion(int peso) {
        this.latencia = peso;
        this.existe = true;
    }

    public int getLatencia() {
        return latencia;
    }

    public void setLatencia(int peso) {
        this.latencia = latencia;
    }

    public boolean isExiste() {
        return existe;
    }

    public void setExiste(boolean existe) {
        this.existe = existe;
    }
}
