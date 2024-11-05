package dominio;

import interfaz.Categoria;

import java.util.Objects;

public class Jugador implements Comparable<Jugador> {
    private String alias;
    private String nombre;
    private String apellido;
    private Categoria categoria;
    private boolean tieneEquipo;

    public Jugador(String alias, String nombre, String apellido, Categoria categoria) {
        this.alias = alias;
        this.nombre = nombre;
        this.apellido = apellido;
        this.categoria = categoria;
        this.tieneEquipo =false;
    }
    public String getAlias() {
        return alias;
    }

    public boolean tieneEquipo() {
        return tieneEquipo;
    }

    public void setTieneEquipo(boolean tieneEquipo) {
        this.tieneEquipo = tieneEquipo;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    /*Si un Jugador tiene el mismo alias, quiere decir que es el mismo jugador*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jugador jugador = (Jugador) o;
        return Objects.equals(alias, jugador.alias);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alias);
    }
    //luego editar el comparable

    @Override
    public int compareTo(Jugador otroJugador) {
        // Comparar alfabéticamente por alias
        return this.alias.compareTo(otroJugador.alias);
    }

    @Override
    public String toString() {
        return this.getAlias() + ";" +
                (this.getNombre() != null ? this.getNombre() : "N/A") + ";" +
                (this.getApellido() != null ? this.getApellido() : "N/A") + ";" +
                (this.getCategoria() != null ? this.getCategoria() : "Sin categoría");
    }


}

