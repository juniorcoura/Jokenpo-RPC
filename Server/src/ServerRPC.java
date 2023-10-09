import org.apache.xmlrpc.webserver.WebServer;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.PropertyHandlerMapping;

public class ServerRPC {

    private ServerRPC(){
        try{
            //Cria um servidor na porta 5000
            WebServer ws = new WebServer(5000);
            XmlRpcServer server = ws.getXmlRpcServer();
            //Adiciona um novo handler ao PHM
            PropertyHandlerMapping phm = new PropertyHandlerMapping();
            phm.addHandler("jkp", RockPaperScissors.class);
            //Define um Handler no servidor 
            server.setHandlerMapping(phm);
            //Inicia o servidor
            ws.start();
            System.out.println("Servidor Iniciado");
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        new ServerRPC();
    }
}
