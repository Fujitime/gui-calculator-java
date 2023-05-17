package fuji.belajar;

import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FujiBelajar {
    private JFrame frame;
    private JTextField inputField;
    private JButton addButton, subtractButton, multiplyButton, divideButton, equalButton, clearButton;
    private int firstOperand;
    private String operator;
    private boolean operatorPressed, errorOccurred;

    public FujiBelajar() {
        operatorPressed = false;
        errorOccurred = false;
        createUI();
    }

    private void createUI() {
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new GridLayout(0, 4));

        inputField = new JTextField(20);
        inputField.setEditable(false);
        inputField.setHorizontalAlignment(SwingConstants.RIGHT);

        addButton = new JButton("+");
        addButton.addActionListener(new OperationButtonListener("+"));

        subtractButton = new JButton("-");
        subtractButton.addActionListener(new OperationButtonListener("-"));

        multiplyButton = new JButton("*");
        multiplyButton.addActionListener(new OperationButtonListener("*"));

        divideButton = new JButton("/");
        divideButton.addActionListener(new OperationButtonListener("/"));

        equalButton = new JButton("=");
        equalButton.addActionListener(new EqualsButtonListener());

        clearButton = new JButton("C");
        clearButton.addActionListener(new ClearButtonListener());

        panel.add(inputField);
        panel.add(addButton);
        panel.add(subtractButton);
        panel.add(multiplyButton);
        panel.add(divideButton);
        panel.add(equalButton);
        panel.add(clearButton);

        for (int i = 1; i <= 9; i++) {
            JButton numberButton = new JButton(Integer.toString(i));
            numberButton.addActionListener(new NumberButtonListener(i));
            panel.add(numberButton);
        }
        JButton zeroButton = new JButton("0");
        zeroButton.addActionListener(new NumberButtonListener(0));
        panel.add(zeroButton);

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    private class NumberButtonListener implements ActionListener {
        private final int number;

        public NumberButtonListener(int number) {
            this.number = number;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (errorOccurred) {
                clearCalculator();
                errorOccurred = false;
            }
            if (operatorPressed) {
                inputField.setText("");
                operatorPressed = false;
            }
            inputField.setText(inputField.getText() + Integer.toString(number));
        }
    }

    private class OperationButtonListener implements ActionListener {
        private final String operation;

        public OperationButtonListener(String operation) {
            this.operation = operation;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!errorOccurred) {
                try {
                    firstOperand = Integer.parseInt(inputField.getText());
                    operator = operation;
                    operatorPressed = true;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Input tidak valid. Harap masukkan angka bulat yang benar.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    errorOccurred = true;
                }
            }
        }
    }

    private class EqualsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!errorOccurred) {
                try {
                    int secondOperand = Integer.parseInt(inputField.getText());
                    int result = 0;
                    switch (operator) {
                        case "+":
                            result = firstOperand + secondOperand;
                            break;
                        case "-":
                            result = firstOperand - secondOperand;
                            break;
                        case "*":
                            result = firstOperand * secondOperand;
                            break;
                        case "/":
                            if (secondOperand == 0) {
                                throw new ArithmeticException("Pembagian dengan nol tidak diperbolehkan.");
                            }
                            result = firstOperand / secondOperand;
                            break;
                    }
                    inputField.setText(Integer.toString(result));
                    operatorPressed = true;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Input tidak valid. Mohon masukkan bilangan bulat yang benar.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    errorOccurred = true;
                } catch (ArithmeticException ex) {
                    JOptionPane.showMessageDialog(frame, "Arithmetic error: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                    errorOccurred = true;
                }
            }
        }
    }

    private class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            clearCalculator();
        }
    }

    private void clearCalculator() {
        inputField.setText("");
        operatorPressed = false;
        errorOccurred = false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new FujiBelajar();
            }
        });
    }
}
