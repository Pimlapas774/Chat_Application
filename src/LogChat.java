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
}
