package controller;

import javax.swing.*;
import java.util.ArrayList;
import model.Categoria;
import repository.CategoriaRepository;
import util.Validaciones;
import view.CategoriaView;

public class CategoriaController {

    private final CategoriaView view;
    private final CategoriaRepository categoriaRepository;
    private final Runnable accionRegresar;

    public CategoriaController(CategoriaView view, CategoriaRepository categoriaRepository, Runnable accionRegresar) {
        this.view = view;
        this.categoriaRepository = categoriaRepository;
        this.accionRegresar = accionRegresar;
        inicializarEventos();
        cargarTablaCompleta();
    }

    private void inicializarEventos() {
        view.botonAgregar.addActionListener(e -> agregarCategoria());
        view.botonBuscar.addActionListener(e -> buscarPorDescripcion());
        view.botonActualizar.addActionListener(e -> actualizarCategoria());
        view.botonEliminar.addActionListener(e -> eliminarCategoria());
        view.botonLimpiar.addActionListener(e -> limpiarCampos());
        view.botonRegresar.addActionListener(e -> regresar());

        view.alSeleccionarFila(this::cargarDatosDesdeTabla);

        view.configurarAccionesTabla(
                filaModelo -> cargarDatosDesdeTabla(filaModelo), // botón "Editar" de la fila
                filaModelo -> {
                    int id = (int) view.modeloTabla.getValueAt(filaModelo, 0);
                    view.campoId.setText(String.valueOf(id));
                    eliminarCategoria();
                }
        );
    }

    private void cargarTablaCompleta() {
        mostrarEnTabla(categoriaRepository.listar());
    }

    private void mostrarEnTabla(ArrayList<Categoria> lista) {
        view.modeloTabla.setRowCount(0);
        for (Categoria categoria : lista) {
            view.modeloTabla.addRow(new Object[]{
                    categoria.getId(),
                    categoria.getDescripcion(),
                    "Editar",
                    "Eliminar"
            });
        }
    }

    private void agregarCategoria() {
        String descripcion = view.campoDescripcion.getText().trim();

        if (Validaciones.campoVacio(descripcion)) {
            JOptionPane.showMessageDialog(view, "La descripción es obligatoria.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int nuevoId = categoriaRepository.obtenerSiguienteId();
        Categoria nuevaCategoria = new Categoria(nuevoId, descripcion);
        categoriaRepository.agregar(nuevaCategoria);

        cargarTablaCompleta();
        JOptionPane.showMessageDialog(view, "Categoría agregada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        limpiarCampos();
    }

    private void buscarPorDescripcion() {
        String textoBuscado = view.campoBusqueda.getText().trim().toLowerCase();

        if (Validaciones.campoVacio(textoBuscado)) {
            cargarTablaCompleta();
            return;
        }

        ArrayList<Categoria> resultado = new ArrayList<>();
        for (Categoria categoria : categoriaRepository.listar()) {
            if (categoria.getDescripcion().toLowerCase().contains(textoBuscado)) {
                resultado.add(categoria);
            }
        }

        mostrarEnTabla(resultado);

        if (resultado.isEmpty()) {
            JOptionPane.showMessageDialog(view, "No se encontraron categorías con esa descripción.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void actualizarCategoria() {
        String textoId = view.campoId.getText().trim();

        if (Validaciones.campoVacio(textoId)) {
            JOptionPane.showMessageDialog(view, "Seleccione una categoría de la tabla antes de actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id = Integer.parseInt(textoId);
        Categoria categoria = categoriaRepository.buscarPorId(id);
        if (categoria == null) {
            JOptionPane.showMessageDialog(view, "La categoría ya no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nuevaDescripcion = view.campoDescripcion.getText().trim();
        if (Validaciones.campoVacio(nuevaDescripcion)) {
            JOptionPane.showMessageDialog(view, "La descripción es obligatoria.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        categoria.setDescripcion(nuevaDescripcion);
        categoriaRepository.actualizar(categoria);

        cargarTablaCompleta();
        JOptionPane.showMessageDialog(view, "Categoría actualizada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        limpiarCampos();
    }

    private void eliminarCategoria() {
        String textoId = view.campoId.getText().trim();

        if (Validaciones.campoVacio(textoId)) {
            JOptionPane.showMessageDialog(view, "Seleccione una categoría de la tabla antes de eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id = Integer.parseInt(textoId);
        Categoria categoria = categoriaRepository.buscarPorId(id);
        if (categoria == null) {
            JOptionPane.showMessageDialog(view, "La categoría ya no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                view,
                "Está segura de eliminar la categoría \"" + categoria.getDescripcion() + "\"?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            categoriaRepository.eliminar(categoria);
            cargarTablaCompleta();
            JOptionPane.showMessageDialog(view, "Categoría eliminada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
        }
    }

    private void cargarDatosDesdeTabla() {
        int fila = view.tablaCategorias.getSelectedRow();
        if (fila != -1) {
            cargarDatosDesdeTabla(fila);
        }
    }

    private void cargarDatosDesdeTabla(int filaModelo) {
        int id = (int) view.modeloTabla.getValueAt(filaModelo, 0);
        Categoria categoria = categoriaRepository.buscarPorId(id);
        if (categoria != null) {
            view.campoId.setText(String.valueOf(categoria.getId()));
            view.campoDescripcion.setText(categoria.getDescripcion());
        }
    }

    private void limpiarCampos() {
        view.campoId.setText("");
        view.campoDescripcion.setText("");
        view.campoBusqueda.setText("");
        view.tablaCategorias.clearSelection();
        cargarTablaCompleta();
    }

    private void regresar() {
        view.dispose();
        accionRegresar.run();
    }
}