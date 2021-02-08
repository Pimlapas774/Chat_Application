import java.net.InetAddress;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BoxChatP {
    String nameP = "BoxChatP";
    String versionP = "1.0";
    DateTimeFormatter dformat;
    public BoxChatP(){
        dformat = DateTimeFormatter.ofPattern("yyyy/MM/dd, HH:mm:ss");
    }

    public String BoxChatPMsgFormatter(String content, String method){
        return method+"฿"+content+"฿"+nameP+"฿"+versionP+"฿";
    }

    public String printMessageRequest(String method, String content){
        try {
            String request_msg = method + "/" + nameP + "/"+versionP+"/" + "request"
                    + "\n" + "host: " + InetAddress.getLocalHost()
                    + "\n" + "content: " + content + "\n\n";
            return request_msg;
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return "";
    }

    public String printMessageResponse(String content){
        String response_msg = nameP+"/"+versionP+"/"+"response"
                +"\n"+"date: "+dformat.format(LocalDateTime.now())
                +"\n"+"content: "+content+"\n\n";
        return response_msg;
    }


}
