package dominio;

import tads.ABB.ABB;

import java.util.Objects;

public class Equipo implements Comparable<Equipo> {
    private String nombre;
    private String manager;
    private ABB<Jugador> jugadores;

    public Equipo(String nombre, String manager) {
        this.nombre = nombre;
        this.manager = manager;
        this.jugadores=new ABB<Jugador>();
    }
    public String getNombre() {
        return nombre;
    }

    public void seNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }


    public ABB<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(ABB<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    /*Si un equipo tiene el mismo nombre, quiere decir que es el mismo equipo*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipo equipo = (Equipo) o;
        return Objects.equals(nombre, equipo.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }
    //luego editar el comparable

    @Override
    public int compareTo(Equipo otroEquipo) {
        // Comparar alfabéticamente por nombre
        return this.nombre.compareTo(otroEquipo.nombre);
    }
    @Override
    public String toString() {
        return nombre + ";" + manager + ";" + this.jugadores.contarNodos();
    }



}
