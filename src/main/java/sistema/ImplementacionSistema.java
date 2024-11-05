package sistema;
import dominio.Equipo;
import dominio.Jugador;
import interfaz.*;
import tads.ABB.ABB;
import tads.Grafo.Conexion;
import tads.Grafo.Grafo;
import tads.Grafo.Sucursal;
import tads.Lista.Lista;

public class ImplementacionSistema implements Sistema {
    private ABB<Jugador> jugadores;
    private ABB<Jugador> principiantes;
    private ABB<Jugador> estandares;
    private ABB<Jugador> profesionales;
    private ABB<Equipo> equipos;
    private Grafo grafo;
    private int cantidadSucursales;
    private int maxSucursales;


    public ImplementacionSistema() {
        this.grafo = null;
        this.jugadores = new ABB<Jugador>();
        this.equipos =new ABB<Equipo>();
        this.estandares = new ABB<Jugador>();
        this.profesionales =new ABB<Jugador>();
        this.principiantes = new ABB<Jugador>();
        this.cantidadSucursales=0;

    }

    @Override
    public Retorno inicializarSistema(int maxSucursales) {
        /*ERROR 1. Si maxSucursales es menor o igual a 3.*/
        if (maxSucursales <= 3) {
            return Retorno.error1("El máximo de sucursales para el sistema debe ser mayor a 3");
        }
        /*OK Si el sistema fue inicializado exitosamente*/
        else if (maxSucursales > 3) {
            this.maxSucursales = maxSucursales;
            return Retorno.ok();
        } else {
            return Retorno.noImplementada();
        }
    }

    @Override
    public Retorno registrarJugador(String alias, String nombre, String apellido, Categoria categoria) {
        // ERROR 1: Verificar si alguno de los parámetros es nulo o vacío
        if (alias == null || alias.isEmpty() || nombre == null || nombre.isEmpty() || apellido == null || apellido.isEmpty() || categoria == null) {
            return Retorno.error1("Los datos no pueden ser nulos ni estar vacíos");
        }

              // ERROR 2: Verificar si ya existe un jugador con ese alias
              Jugador aux = new Jugador(alias, "", "", null); // Creas un jugador temporal solo con el alias
              Jugador jugadorEncontrado = this.jugadores.buscarSimple(aux); // Buscas por alias

        if (jugadorEncontrado != null) {
            return Retorno.error2("Ya existe un jugador con ese alias");
        } else if(jugadorEncontrado == null){// Si no se encuentra el jugador, lo agregas
                Jugador aAgregar = new Jugador(alias, nombre, apellido, categoria);
                this.jugadores.insertar(aAgregar);
                if (categoria.getTexto().equals("Profesional")) {
                    this.profesionales.insertar(aAgregar);
                }
                if (categoria.getTexto().equals("Estándar")) {
                    this.estandares.insertar(aAgregar);
                }
                if (categoria.getTexto().equals("Principiante")) {
                    this.principiantes.insertar(aAgregar);
                }
                return Retorno.ok();}
        else{
            return Retorno.noImplementada();
            }
        }

    @Override
    public Retorno buscarJugador(String alias) {
        /*ERROR 1. Si el alias es vacío o null.*/
        if (alias == null || alias.isEmpty()) {
            return Retorno.error1("El alias no puede estar vacío ni nulo");
        } else {
            Jugador aux = new Jugador(alias, "", "", null);
            ABB.ResultadoBusqueda resultado = this.jugadores.buscar(aux);
            if (resultado.getDato() == null) {
                return Retorno.error2("No existe jugador con ese alias");
            } else if (resultado.getDato() != null) {
             /*OK Si el jugador se encontró.
                  Retorna en valorString los datos del jugador.
                  Retorna en valorEntero la cantidad de elementos recorridos durante la
                  búsqueda.*/
                Jugador jugadorEncontrado = (Jugador) resultado.getDato(); // Obtenemos el jugador desde el nodo.
                String datosJugador = jugadorEncontrado.getAlias()+";"+jugadorEncontrado.getNombre()+";"+jugadorEncontrado.getApellido()+";"+jugadorEncontrado.getCategoria();
                int elementosRecorridos = resultado.getContador();
                // Retornamos OK con el número de elementos recorridos y los datos del jugador.
                return Retorno.ok(elementosRecorridos, datosJugador);
            } else {
                return Retorno.noImplementada();
            }
        }
    }

    @Override
    public Retorno listarJugadoresAscendente() {
        if (this.jugadores != null) {
            String jugadores = this.jugadores.listarAscendenteString();
            return Retorno.ok(jugadores);
        }
        return Retorno.noImplementada();
    }

    @Override
    public Retorno listarJugadoresPorCategoria(Categoria unaCategoria) {
        if (unaCategoria.getTexto().equals("Principiante")) {
            if (this.principiantes != null) {
                String jugadores = this.principiantes.listarAscendenteString();
                return Retorno.ok(jugadores);
            }
        }
        if (unaCategoria.getTexto().equals("Estándar")) {
            if (this.estandares != null) {
                String jugadores = this.estandares.listarAscendenteString();
                return Retorno.ok(jugadores);
            }
        }
        if (unaCategoria.getTexto().equals("Profesional")) {
            if (this.profesionales != null) {
                String jugadores = this.profesionales.listarAscendenteString();
                return Retorno.ok(jugadores);
            }
        }
        return Retorno.noImplementada();
    }

    @Override
    public Retorno registrarEquipo(String nombre, String manager) {
        /*ERROR 1. Si alguno de los parámetros es vacío o null.*/
        if (nombre == null || nombre.isEmpty() || manager == null || manager.isEmpty()) {
            return Retorno.error1("Los datos no pueden ser nulos ni estar vacíos");
        }
        /*Error2. Si ya existe un equipo con ese nombre*/
        Equipo aux = new Equipo(nombre, "");
        Equipo equipoEncontrado = this.equipos.buscarSimple(aux);
        if (equipoEncontrado != null) {
            return Retorno.error2("Ya existe un equipo con ese nombre");
        } else if (equipoEncontrado == null) {
            Equipo aAgregar = new Equipo(nombre, manager);
            this.equipos.insertar(aAgregar);
            return Retorno.ok();
        } else {
            return Retorno.noImplementada();
        }
    }

    @Override
    public Retorno agregarJugadorAEquipo(String nombreEquipo, String aliasJugador) {
        /*ERROR 1. Si alguno de los parámetros es vacío o null.*/
        if (nombreEquipo == null || nombreEquipo.isEmpty() || aliasJugador == null || aliasJugador.isEmpty()) {
            return Retorno.error1("Los datos no pueden ser nulos ni estar vacíos");
        }
        //creamos los elementos auxiliares
        Jugador jugAux = new Jugador(aliasJugador, "", "", null);
        Jugador resultadoJugador = this.jugadores.buscarSimple(jugAux);
        Equipo EqAux = new Equipo(nombreEquipo, "");
        Equipo resultadoEquipo = this.equipos.buscarSimple(EqAux);
        /*Error2.Si no existe un equipo con ese nombre..*/

        if (resultadoEquipo == null) {
            return Retorno.error2("No existe un equipo con ese nombre");
        } else {
            //Si el equipo ya tiene 5 integrantes.
            if (resultadoEquipo.getJugadores().contarNodos() == 5) {
                return Retorno.error4("El equipo ya tiene 5 integrantes");
            }
        }
        /*Error 3.Si no existe un jugador con ese alias.*/
        if (resultadoJugador == null) {
            return Retorno.error3("No existe un jugador con ese alias");
        } else if (resultadoJugador != null) {
            //5. Si el jugador no tiene la categoría profesional.
            if (!resultadoJugador.getCategoria().getTexto().equals("Profesional")) {
                return Retorno.error5("El jugador no se encuentra dentro de la categoría profesional, así que no se puede agregar al equipo");
            }

            //6. Si el jugador ya pertenece a otro equipo.
            if (resultadoJugador.tieneEquipo()) {
                return Retorno.error6("El jugador ya pertenece a otro equipo");
            } else {
                //lo agregamos al equipo
                resultadoEquipo.getJugadores().insertar(resultadoJugador);
                resultadoJugador.setTieneEquipo(true);
                return Retorno.ok();
                //   return Retorno.noImplementada();
            }
        } else {
            return Retorno.noImplementada();
        }

    }

    @Override
    public Retorno listarJugadoresDeEquipo(String nombreEquipo) {
        //ERROR 1. Si el nombre es vacío o null.
        if (nombreEquipo == null || nombreEquipo.isEmpty()) {
            return Retorno.error1("El nombre no puede ser nulo ni estar vacío");
        }
        Equipo aux = new Equipo(nombreEquipo, "");
        Equipo equipoEncontrado = this.equipos.buscarSimple(aux);
        if (equipoEncontrado == null) {
            //2. Si no existe un equipo con ese nombre.
            return Retorno.error2("No existe un equipo con ese nombre");
        } else if (equipoEncontrado != null) {
            // OK Si se pudo listar los jugadores que pertenecen a dicho equipo.
            ABB<Jugador> jugadores = equipoEncontrado.getJugadores();
            String jugadoresEncontrados = jugadores.listarAscendenteString();
            return Retorno.ok(jugadoresEncontrados);
        } else {

            return Retorno.noImplementada();
        }
    }

    @Override
    public Retorno listarEquiposDescendente() {
        if (this.equipos != null) {
            String equipos = this.equipos.listarDescendenteString();
            return Retorno.ok(equipos);
        }
        return Retorno.noImplementada();
    }

    @Override
    public Retorno registrarSucursal(String codigo, String nombre) {

        if (this.cantidadSucursales == this.maxSucursales) {
            return Retorno.error1("El sistema ya tiene registrado el máximo de sucursales");
        }
        if (codigo == null || codigo.isEmpty() || nombre == null || nombre.isEmpty()) {
            return Retorno.error2("El código y el nombre no puede ser vacío ni nulo");
        }
        Sucursal aux = new Sucursal(codigo, nombre);
        boolean resultado = this.grafo.existeSucursal(aux);
        if (resultado) {
            return Retorno.error3("Ya existe una sucursal con ese código");
        } else if (!resultado) {
            /* this.sucursales.insertar(resultado);*/
            this.grafo.agregarSucursal(aux);
            this.cantidadSucursales++;
            return Retorno.ok("Registro exitoso");
        } else {
            return Retorno.noImplementada();
        }
    }

    @Override
    public Retorno registrarConexion(String codigoSucursal1, String codigoSucursal2, int latencia) {
        if (latencia < 0) {
            return Retorno.error1("La latencia no puede ser menor a 0");
        }
        if (codigoSucursal1 == null || codigoSucursal1.isEmpty() || codigoSucursal2 == null || codigoSucursal2.isEmpty()) {
            return Retorno.error2("Los parámteros no pueden ser vacíos ni nulos");
        }
        Sucursal aux1 = new Sucursal(codigoSucursal1, "");
        Sucursal aux2 = new Sucursal(codigoSucursal2, "");
        boolean resultado1 = this.grafo.existeSucursal(aux1);
        boolean resultado2 = this.grafo.existeSucursal(aux2);
        if (!resultado1 || !resultado2) {
            return Retorno.error3("No existe alguna o las dos sucursales");
        }
        boolean resultado = this.grafo.sonAdyacentes(aux1, aux2);
        if (resultado) {
            return Retorno.error4("Ya existe una conexión entre las dos sucursales");
        } else if (!resultado) {
            grafo.agregarConexion(aux1, aux2, latencia);
            return Retorno.ok("Conexión creada exitosamente");
        } else {
            return Retorno.noImplementada();

        }
    }

    @Override
    public Retorno actualizarConexion(String codigoSucursal1, String codigoSucursal2, int latencia) {
        if (latencia < 0) {
            return Retorno.error1("La latencia es menor a 0");
        }
        if (codigoSucursal1 == null || codigoSucursal1.isEmpty() || codigoSucursal2 == null || codigoSucursal2.isEmpty()) {
            return Retorno.error2("Los parámteros no pueden ser vacíos ni nulos");
        }
        Sucursal aux1 = new Sucursal(codigoSucursal1, "");
        Sucursal aux2 = new Sucursal(codigoSucursal2, "");
        boolean resultado1 = this.grafo.existeSucursal(aux1);
        boolean resultado2 = this.grafo.existeSucursal(aux2);
        if (!resultado1 || !resultado2) {
            return Retorno.error3("No existe alguna o las dos sucursales");
        }
        boolean resultado = this.grafo.sonAdyacentes(aux1, aux2);
        if (!resultado) {
            return Retorno.error4("No existe una conexión entre las dos sucursales");
        } else if (resultado) {
            this.grafo.actualizarConexion(codigoSucursal1, codigoSucursal2, latencia);
            return Retorno.ok("La conexión fue actualizada correctamente");
        } else {
            return Retorno.noImplementada();
        }
    }

    /*Dado una sucursal cargar en el valorString el texto “SI” si es crítica y “NO” si no lo es.
Retornos posibles
OK Retorna en valorString la palabra SI/NO según corresponda
NO_IMPLEMENTADA Cuando aún no se implementó*/
    @Override
    public Retorno analizarSucursal(String codigoSucursal) {
        if (codigoSucursal == null || codigoSucursal.isEmpty()) {
            return Retorno.error1("El código no puede ser vacío ni nulo");
        }
        Sucursal aux1 = new Sucursal(codigoSucursal, "");
        boolean resultado = this.grafo.existeSucursal(aux1);
        if (!resultado) {
            return Retorno.error2("No existe sucursal con ese código");
        } else if (resultado) {
            boolean esCri = this.grafo.esSucursalCritica(aux1);
            String esCritica = "";
            if (esCri) {
                esCritica = "SI";
            } else {
                esCritica = "NO";
            }
            return Retorno.ok(esCritica);
        } else {
            return Retorno.noImplementada();

        }
    }

    /*Retorno sucursalesParaTorneo(String codigoSucursalAnfitriona, int
    latenciaLimite);
    Descripción: Retorna en valorString del retorno una lista de las sucursales ordenadas por código
    creciente que tengan una cantidad menor o igual a la latencia indicada como parámetro a la sucursal
    anfitriona.
    Retornos posibles
    OK Si el camino pudo ser calculado exitosamente.
    Retorna en valorEntero la latencia más grande de las sucursales
    seleccionadas.
    Retorna en valorString el listado de las sucursales ordenadas creciente por
    código.
    ERROR 1. Si el código de la sucursal anfitriona es vacío o null.
    2. Si no existe el código de la sucursal anfitriona.
    3. Si la latencia es menor o igual a cero.
    NO_IMPLEMENTADA Cuando aún no se implementó.
    Formato de retorno del valorString:
    codigo1;nombre1|codigo2;nombre2*/
    @Override
    public Retorno sucursalesParaTorneo(String codigoSucursalAnfitriona, int latenciaLimite) {
        // Validaciones iniciales
       /* if (codigoSucursalAnfitriona == null || codigoSucursalAnfitriona.isEmpty()) {
            return Retorno.error1("El código de la sucursal anfitriona no puede ser vacío ni nulo.");
        }
        if (latenciaLimite <= 0) {
            return Retorno.error3("La latencia debe ser mayor que cero.");
        }
    */
        return Retorno.noImplementada();

    }
}