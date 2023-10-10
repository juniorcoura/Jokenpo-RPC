import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ConfigRPCController {

    public static Stage _primaryStage;

    private static String _cancel = "cancel";

    private static final String serverDefaultURL = "http://localhost:5000";
    private XmlRpcClient client;

    public String getAnswer(String message) throws Exception {
        Object[] parametr = new Object[] { new String(message) };
        String result = (String) client.execute("jkp.getAnswer", parametr);

        return result;

    }

    public void initialize() {

        setConnection(serverDefaultURL);

        ObservableList<String> items = FXCollections.observableArrayList(
                "Pedra",
                "Papel",
                "Tesoura");

        selectOption.setItems(items);
    }

    private void setConnection(String url) {
        try {
            XmlRpcClientConfigImpl clientConfig = new XmlRpcClientConfigImpl();
            clientConfig.setServerURL(new URL(url));

            client = new XmlRpcClient();
            client.setConfig(clientConfig);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private Button buttonCancel;

    @FXML
    private Button buttonSend;

    @FXML
    private TextField urlField;

    @FXML
    private ChoiceBox<String> selectOption;

    @FXML
    private AnchorPane connectionGroup;

    @FXML
    void onCancel(ActionEvent event) {
        sendMessage(true);
    }

    @FXML
    void onSend(ActionEvent event) {
        if (urlField.getText() != null) {
            setConnection(urlField.getText());
        }
        sendMessage(false);
    }

    private void sendMessage(boolean isCancel) {
        try (DatagramSocket socket = new DatagramSocket()) {
            String messageToSend = isCancel ? _cancel : selectOption.valueProperty().getValue();
            String messageReceiver = this.getAnswer(messageToSend);
            if (!isCancel) {
                this.showMessage(messageReceiver, "Resultado:");
                return;
            }

        } catch (Exception e) {
            this.showMessage(e.getMessage(), "Error:");
        }

        System.exit(0);
        _primaryStage.close();
    }

    private void showMessage(String receiverMessage, String title) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(this.formatReceiverMessage(receiverMessage)[1].toUpperCase() + "!!");
        alert.setContentText("Seu adversário escolheu: " + this.formatReceiverMessage(receiverMessage)[0]);
        alert.showAndWait();
    }

    private String[] formatReceiverMessage(String receiverMessage) {
        String[] receiverList = receiverMessage.split(";");
        return receiverList;
    }

}