package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Reserva implements EntidadTabla {

    public static final String PREFIJO_ID = "RES-";
    private static final DateTimeFormatter FMT_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FMT_HORA = DateTimeFormatter.ofPattern("HH:mm");

    private String id;
    private String funcionarioId;
    private String actividad;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private EstadoReserva estado;
    private List<RecursoAsignado> recursos = new ArrayList<>();

    public Reserva() { }

    public Reserva(String id, String funcionarioId, String actividad, LocalDate fecha,
                   LocalTime horaInicio, LocalTime horaFin, EstadoReserva estado,
                   List<RecursoAsignado> recursos) {
        this.id = id;
        this.funcionarioId = funcionarioId;
        this.actividad = actividad;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.estado = estado;
        this.recursos = recursos != null ? recursos : new ArrayList<>();
    }

    @Override public String getId() { return id; }
    @Override public void setId(String id) { this.id = id; }

    public String getFuncionarioId() { return funcionarioId; }
    public void setFuncionarioId(String funcionarioId) { this.funcionarioId = funcionarioId; }

    public String getActividad() { return actividad; }
    public void setActividad(String actividad) { this.actividad = actividad; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }

    public EstadoReserva getEstado() { return estado; }
    public void setEstado(EstadoReserva estado) { this.estado = estado; }

    public List<RecursoAsignado> getRecursos() { return recursos; }
    public void setRecursos(List<RecursoAsignado> recursos) { this.recursos = recursos; }


    // Aca se usa true si la reserva es en el futuro para poder cancelarla
    public boolean esFutura(LocalDate hoy, LocalTime ahora) {
        if (fecha.isAfter(hoy)) return true;
        if (fecha.isEqual(hoy)) return horaInicio.isAfter(ahora);
        return false;
    }

    @Override
    public Object[] toFila() {
        String horario = (horaInicio != null ? horaInicio.format(FMT_HORA) : "") + " - "
                + (horaFin != null ? horaFin.format(FMT_HORA) : "");
        String recursosTexto = recursos.stream()
                .map(RecursoAsignado::getRecursoId)
                .collect(Collectors.joining(", "));
        return new Object[]{
                id,
                actividad,
                fecha != null ? fecha.format(FMT_FECHA) : "",
                horario,
                recursosTexto,
                estado != null ? estado.name() : ""
        };
    }
}