package model;


public class Usuario implements EntidadTabla {

    public static final String TITULO = "Gestion de Funcionarios";
    public static final String NOMBRE_SINGULAR = "funcionario";
    public static final String[] COLUMNAS = {"ID", "Nombre", "Telefono"};

    private String id;
    private String clave;
    private RolUsuario rol;
    private String nombre;
    private String telefono;

    public Usuario() { }

    public Usuario(String id, String clave, RolUsuario rol, String nombre, String telefono) {
        this.id = id;
        this.clave = clave;
        this.rol = rol;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    // Constructor para crear un administrador
    public static Usuario nuevoAdministrador(String id, String clave) {
        return new Usuario(id, clave, RolUsuario.ADMINISTRADOR, "", "");
    }

    // Constructor para crear un funcionario nuevo
    public static Usuario nuevoFuncionario(String id, String nombre, String telefono) {
        return new Usuario(id, id, RolUsuario.FUNCIONARIO, nombre, telefono);
    }

    @Override public String getId() { return id; }
    @Override public void setId(String id) { this.id = id; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public RolUsuario getRol() { return rol; }
    public void setRol(RolUsuario rol) { this.rol = rol; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public boolean esAdministrador() { return rol == RolUsuario.ADMINISTRADOR; }
    public boolean esFuncionario() { return rol == RolUsuario.FUNCIONARIO; }

    @Override
    public Object[] toFila() {
        return new Object[]{id, nombre, telefono};
    }
}