import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginForm extends JDialog{
    private JTextField emailTF;
    private JPasswordField passTF;
    private JButton cancelButton;
    private JButton okButton;
    private JPanel loginPanel;

    public LoginForm(JFrame parent){
        super(parent); //hereda de
        setTitle("Login");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(500,300));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setDefaultLookAndFeelDecorated(true);
        //setVisible(true);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailTF.getText();
                String password = String.valueOf(passTF.getPassword());

                user = getAuthenticationUser(email, password);
                if (user!=null){
                    dispose();
                }
                else{
                    JOptionPane.showMessageDialog(
                            LoginForm.this,"email o password incorrectos",
                            "intente nuevamente",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
dispose();
            }
        });
        setVisible(true);
    }

    //Elementos de autenticacion
    public User user;

    private User getAuthenticationUser(String email, String password){
        User user = null;
        final String DB_URL = "jdbc:mysql://localhost:3306/minitienda?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement stnt = conn.createStatement();
            String sql = "SELECT * FROM users WHERE email=? AND password=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            System.out.println("Conexion ok");
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                user = new User();
                user.nombre = resultSet.getString("nombre");
                user.email = resultSet.getString("email");
                user.celular = resultSet.getString("celular");
                user.direccion = resultSet.getString("direccion");
                user.password = resultSet.getString("password");
            }
            stnt.close();
            conn.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return user;
    }
    public static void main(String[] args) {
        LoginForm loginForm = new LoginForm(null);
        User user = loginForm.user;
        JFrame panel2 = new JFrame();

        if(user != null){
            System.out.println("Auntenticacion correcta: "+user.nombre);
            System.out.println("email: "+user.email);
            System.out.println("calular: "+user.celular);
            System.out.println("direccion: "+user.direccion);

            DatosUsers bienvenido = new DatosUsers(user.nombre, user.email, user.celular, user.direccion);
            panel2.setContentPane(bienvenido.mainBienvenido);
            panel2.setVisible(true);
            panel2.setLocationRelativeTo(null);
            panel2.setTitle("Bienvenido");
            panel2.setSize(450, 250);
            panel2.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        }
        else{
            System.out.println("Autenticacion fallida");
        }
    }
}
