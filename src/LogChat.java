import java.io.*;

public class LogChat {
    File log_file;
    public LogChat() {
    }

    public void CreateTextfile(){
        log_file = new File("ChatLog.txt");
        try {
            if(!log_file.exists()){
                log_file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printLogToClient(ServerThread user){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(log_file));
            BufferedWriter writer = new BufferedWriter(new PrintWriter(user.socket.getOutputStream(), true));
            String line;
            while ((line = reader.readLine()) != null){
                writer.write(line);
                writer.newLine();
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void writeLog_File(String msg){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(log_file,true));
            writer.write(msg);
            writer.newLine();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isGet_log(String method){
        if(method.equals("GET_LOG")){
            return true;
        }
        return false;
    }
}
