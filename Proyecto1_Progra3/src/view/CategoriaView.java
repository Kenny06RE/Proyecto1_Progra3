package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import util.BotonTablaRenderer;
import util.BotonTablaEditor;

public class CategoriaView extends JFrame {

    public JTextField campoId;
    public JTextField campoDescripcion;
    public JTextField campoBusqueda;

    public JButton botonAgregar;
    public JButton botonBuscar;
    public JButton botonActualizar;
    public JButton botonEliminar;
    public JButton botonLimpiar;
    public JButton botonRegresar;

    public JTable tablaCategorias;
    public DefaultTableModel modeloTabla;

    public CategoriaView() {
        super("Gestión de Categorías de Recursos");
        configurarVentana();
        crearComponentes();
    }

    private void configurarVentana() {
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    private void crearComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titulo = new JLabel("Gestión de Categorías de Recursos", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        panelBusqueda.setBorder(BorderFactory.createTitledBorder("Búsqueda por descripción"));
        campoBusqueda = new JTextField(20);
        botonBuscar = new JButton("Buscar");
        panelBusqueda.add(new JLabel("Descripción:"));
        panelBusqueda.add(campoBusqueda);
        panelBusqueda.add(botonBuscar);

        JPanel panelFormulario = new JPanel(new GridLayout(2, 2, 8, 8));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos de la categoría"));

        campoId = new JTextField();
        campoId.setEditable(false);
        campoDescripcion = new JTextField();

        panelFormulario.add(new JLabel("ID:"));
        panelFormulario.add(campoId);
        panelFormulario.add(new JLabel("Descripción:"));
        panelFormulario.add(campoDescripcion);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        botonAgregar = new JButton("Agregar");
        botonActualizar = new JButton("Actualizar");
        botonEliminar = new JButton("Eliminar");
        botonLimpiar = new JButton("Limpiar");
        botonRegresar = new JButton("Regresar");

        panelBotones.add(botonAgregar);
        panelBotones.add(botonActualizar);
        panelBotones.add(botonEliminar);
        panelBotones.add(botonLimpiar);
        panelBotones.add(botonRegresar);

        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
        panelSuperior.add(panelBusqueda);
        panelSuperior.add(panelFormulario);
        panelSuperior.add(panelBotones);
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);

        String[] columnas = {"ID", "Descripción", "Editar", "Eliminar"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int fila, int columna) {
                return columna == 2 || columna == 3;
            }
        };
        tablaCategorias = new JTable(modeloTabla);
        tablaCategorias.setRowHeight(28);

        tablaCategorias.getColumn("Editar").setCellRenderer(new BotonTablaRenderer("Editar"));
        tablaCategorias.getColumn("Eliminar").setCellRenderer(new BotonTablaRenderer("Eliminar"));
        tablaCategorias.getColumn("Editar").setMaxWidth(90);
        tablaCategorias.getColumn("Eliminar").setMaxWidth(90);

        JScrollPane scroll = new JScrollPane(tablaCategorias);
        panelPrincipal.add(scroll, BorderLayout.CENTER);

        add(panelPrincipal);
    }

    public void configurarAccionesTabla(BotonTablaEditor.AccionFila accionEditar, BotonTablaEditor.AccionFila accionEliminar) {
        tablaCategorias.getColumn("Editar").setCellEditor(new BotonTablaEditor("Editar", accionEditar));
        tablaCategorias.getColumn("Eliminar").setCellEditor(new BotonTablaEditor("Eliminar", accionEliminar));
    }

    public void alSeleccionarFila(Runnable accion) {
        tablaCategorias.getSelectionModel().addListSelectionListener(evento -> {
            if (!evento.getValueIsAdjusting() && tablaCategorias.getSelectedRow() != -1) {
                accion.run();
            }
        });
    }
}