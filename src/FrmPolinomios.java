import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionEvent;

public class FrmPolinomios extends JFrame {
    private JTextField txtCoeficiente, txtExponente;
    private JComboBox<String> cmbPolinomio, cmbOperacion;
    private JLabel lblPolinomio1, lblPolinomio2, lblResultado, lblResiduo;
    private JButton btnAgregar, btnLimpiar, btnCalcular;

    private Polinomio p1 = new Polinomio();
    private Polinomio p2 = new Polinomio();

    public FrmPolinomios() {
        setTitle("Calculadora de Polinomios");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        int campoAltura = 30;
        int campoAncho = 50;
        int margen = 10;
        int xInicial = 20;
        int yInicial = 20;

        txtCoeficiente = new JTextField();
        txtCoeficiente.setBounds(xInicial, yInicial, campoAncho, campoAltura);
        add(txtCoeficiente);

        JLabel lblX = new JLabel("x");
        lblX.setBounds(xInicial + campoAncho + margen, yInicial + 5, 30, campoAltura);
        lblX.setFont(new java.awt.Font("Times New Roman", java.awt.Font.ITALIC, 50));
        add(lblX);

        txtExponente = new JTextField();
        txtExponente.setBounds(xInicial + campoAncho + margen + 20, yInicial - 10, campoAncho, 20);
        add(txtExponente);

        cmbPolinomio = new JComboBox<>(new String[]{"Polinomio 1", "Polinomio 2"});
        cmbPolinomio.setBounds(xInicial + 2 * (campoAncho + margen) + 50, yInicial, 120, campoAltura);
        add(cmbPolinomio);

        btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(xInicial + 2 * (campoAncho + margen) + 180, yInicial, 100, campoAltura);
        btnAgregar.setBackground(Color.GREEN);
        btnAgregar.setForeground(Color.BLACK);
        add(btnAgregar);
        btnAgregar.addActionListener(this::agregarTermino);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(xInicial + 2 * (campoAncho + margen) + 300, yInicial, 100, campoAltura);
        btnLimpiar.setBackground(Color.RED);
        btnLimpiar.setForeground(Color.BLACK);
        add(btnLimpiar);
        btnLimpiar.addActionListener(this::limpiarPolinomio);

        lblPolinomio1 = new JLabel("Polinomio 1: ");
        lblPolinomio1.setBounds(margen, yInicial + campoAltura + margen, 580, campoAltura);
        lblPolinomio1.setBackground(Color.GREEN);
        lblPolinomio1.setOpaque(true);
        lblPolinomio1.setForeground(Color.BLACK);
        add(lblPolinomio1);

        lblPolinomio2 = new JLabel("Polinomio 2: ");
        lblPolinomio2.setBounds(margen, yInicial + 2 * (campoAltura + margen), 580, campoAltura);
        lblPolinomio2.setBackground(Color.GREEN);
        lblPolinomio2.setOpaque(true);
        lblPolinomio2.setForeground(Color.BLACK);
        add(lblPolinomio2);

        cmbOperacion = new JComboBox<>(new String[]{"Suma", "Resta", "Multiplicación", "División", "Derivada"});
        cmbOperacion.setBounds(margen, yInicial + 3 * (campoAltura + margen), 150, campoAltura);
        add(cmbOperacion);

        btnCalcular = new JButton("Calcular");
        btnCalcular.setBounds(margen + 170, yInicial + 3 * (campoAltura + margen), 100, campoAltura);
        btnCalcular.setBackground(Color.ORANGE);
        btnCalcular.setForeground(Color.BLACK);
        add(btnCalcular);
        btnCalcular.addActionListener(this::calcularOperacion);

        lblResultado = new JLabel("Resultado:");
        lblResultado.setBounds(margen, yInicial + 4 * (campoAltura + margen), 580, campoAltura);
        lblResultado.setBackground(Color.ORANGE);
        lblResultado.setOpaque(true);
        lblResultado.setForeground(Color.BLACK);
        add(lblResultado);

        lblResiduo = new JLabel("Residuo:");
        lblResiduo.setBounds(margen, yInicial + 5 * (campoAltura + margen), 580, campoAltura);
        lblResiduo.setBackground(Color.CYAN);
        lblResiduo.setOpaque(true);
        lblResiduo.setForeground(Color.BLACK);
        add(lblResiduo);
    }

    private void agregarTermino(ActionEvent evt) {
        try {
            double coef = Double.parseDouble(txtCoeficiente.getText());
            int expo = Integer.parseInt(txtExponente.getText());
            Nodo termino = new Nodo(coef, expo);

            if (cmbPolinomio.getSelectedIndex() == 0) {
                p1.agregar(termino);
                lblPolinomio1.setText("Polinomio 1: " + p1);
            } else {
                p2.agregar(termino);
                lblPolinomio2.setText("Polinomio 2: " + p2);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Coeficiente o exponente inválido.");
        }
    }

    private void limpiarPolinomio(ActionEvent evt) {
        if (cmbPolinomio.getSelectedIndex() == 0) {
            p1 = new Polinomio();
            lblPolinomio1.setText("Polinomio 1: ");
        } else {
            p2 = new Polinomio();
            lblPolinomio2.setText("Polinomio 2: ");
        }
    }

    private void calcularOperacion(ActionEvent evt) {
        int operacion = cmbOperacion.getSelectedIndex();
        Polinomio resultado = null;
        Polinomio residuo = null;

        switch (operacion) {
            case 0:
                resultado = Polinomio.sumar(p1, p2);
                break;
            case 1:
                resultado = Polinomio.restar(p1, p2);
                break;
            case 2:
                resultado = Polinomio.multiplicar(p1, p2);
                break;
            case 3:
                Polinomio[] division = Polinomio.dividir(p1, p2);
                if (division != null) {
                    resultado = division[0];
                    residuo = division[1];
                } else {
                    lblResultado.setText("Error: División inválida");
                    return;
                }
                break;
            case 4:
                resultado = p1.derivar();
                break;
        }

        lblResultado.setText("Resultado: " + (resultado != null ? resultado : ""));
        lblResiduo.setText("Residuo: " + (residuo != null ? residuo : ""));
    }
}