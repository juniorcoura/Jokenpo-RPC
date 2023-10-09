import java.net.URL;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

public class ClienteRPC {


    private static final String serverURL = "http://localhost:5000";
    private XmlRpcClient client;

    public ClienteRPC(){
        try{
            XmlRpcClientConfigImpl clientConfig = new XmlRpcClientConfigImpl();
            clientConfig.setServerURL(new URL(serverURL));

            client = new XmlRpcClient();
            client.setConfig(clientConfig);
        } catch(Exception e){
            System.out.println(e);
        }
    }

    public String jokenpo(String message) throws Exception{
        Object[] parametr = new Object[]{new String(message)};
        String result = (String) client.execute("jkp.getAnswer", parametr);
        return result;

    }

    public static void main(String[] args) {
        
    }
}
