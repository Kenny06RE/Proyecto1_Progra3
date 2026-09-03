package model;

public class RecursoAsignado {

    private String categoriaId;
    private String recursoId;

    public RecursoAsignado() { }

    public RecursoAsignado(String categoriaId, String recursoId) {
        this.categoriaId = categoriaId;
        this.recursoId = recursoId;
    }

    public String getCategoriaId() { return categoriaId; }
    public void setCategoriaId(String categoriaId) { this.categoriaId = categoriaId; }

    public String getRecursoId() { return recursoId; }
    public void setRecursoId(String recursoId) { this.recursoId = recursoId; }
}