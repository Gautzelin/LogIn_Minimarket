import com.mysql.cj.x.protobuf.MysqlxDatatypes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DatosUsers extends JFrame {
    private JLabel nameTXT;
    private JLabel emailTXT;
    private JLabel celularTXT;
    JPanel mainBienvenido;
    private JLabel direccionTXT;
    private JButton salirButton;

    public DatosUsers(String nombre, String email, String celular, String direccion) {
        nameTXT.setText(nombre);
        emailTXT.setText(email);
        celularTXT.setText(celular);
        direccionTXT.setText(direccion);

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //dispose();
                System.exit(0);
            }
        });

    }
}

