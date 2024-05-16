package aks;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import org.glassfish.jersey.client.internal.HttpUrlConnector;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import aks.replies.CommObjects;
import aks.replies.Commands;

@SuppressWarnings("deprecation")
public class Bot extends TelegramLongPollingBot{
    Main main = new Main();
    private int state = 0;
    private final int waitingState = 1;

    public CommObjects commObjects = new CommObjects();
    Commands currentCommand = null;
    

    @Override
    public void onUpdateReceived(Update update) {
        if(update.getMessage().getText().equals("/start") && state == 0){
            sendImageCaption(update.getMessage().getChatId().toString(), Strings.GIFTS_BANNER, "📦 𝐓𝐨 𝐬𝐭𝐚𝐫𝐭 𝐲𝐨𝐮𝐫 𝐨𝐫𝐝𝐞𝐫 /order");
        }
        if(update.getMessage().getText().equals("/order") && state == 0){
            reply(update.getMessage().getChatId().toString(), "1️⃣ /email 𝐭𝐨 𝐬𝐞𝐧𝐝 𝐲𝐨𝐮𝐫 𝐞𝐦𝐚𝐢𝐥. \n\n2️⃣ /ccp 𝐭𝐨 𝐬𝐞𝐧𝐝 𝐂𝐂𝐏. \n\n3️⃣ /confirm 𝐭𝐨 𝐜𝐨𝐧𝐟𝐢𝐫𝐦");
            commObjects.rEmail.canSend = true;
        }
        if(update.getMessage().getText().equals("/confirm") && state == 0){
            if(commObjects.rConfirm.canSend){
                commObjects.rConfirm.action(update);
            }
        }
        if(update.getMessage().getText().equals("/test")){
            test();
            System.out.println("niggering");
        }




        String messageText = update.getMessage().getText();
        Long currentChatId = update.getMessage().getChatId();




                
        if(state == 0){
            if(messageText.startsWith("/")){
                currentCommand = commObjects.returnObject(messageText);
                if(currentCommand.canSend){
                    sentCommand(currentChatId, currentCommand);
                }else{
                    currentCommand.notYet(update);
                }
                
            }
            
        }else if(state == waitingState){
            handler(currentCommand, update);
        }

    }

    @Override
    public String getBotUsername() {
        return "hadiyyaBot";
    }

    @Override
    public String getBotToken() {
        return System.getenv("BOT_TOKEN_HADIYYA");
    }

    //METHODS

    //REPLY
    public void reply(String who, String what){
        SendMessage sendMessage = SendMessage.builder()
        .chatId(who)
        .text(what).build();

        try{
            execute(sendMessage);
        }catch(TelegramApiException e){
            e.printStackTrace();
        }
    }
    // IMAGE REPLY WITH CAPTION
    public void sendImageCaption(String who, String imagePath, String caption){
        String imageUrl = imagePath;
        InputFile inputFile = new InputFile(imageUrl);
        caption.replace("\\n", "\n");

        SendPhoto sendPhoto = SendPhoto.builder()
        .chatId(who)
        .photo(inputFile)
        .caption(caption)
        .build();

        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    // SENT COMMAND
    public void sentCommand(Long chatId, Commands commands){
        SendMessage message = SendMessage.builder()
        .chatId(chatId)
        .text(commands.commandDesc)
        .build();
        try{
            execute(message);
            System.out.println("fk?");
            state = waitingState;
        }catch(TelegramApiException e) {
            e.printStackTrace();
        }
    }
    // HANDLE SENT COMMANDS
    public void handler(Commands command, Update update){
        System.out.println(command.commandkey);
        command.action(update);
        state = 0;
        
    }
    public void test(){
        String link = "http://sbah.duckdns.org/api/v1/orders/get";
        URI uri = URI.create(link);

        try{
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = "";


                while((line = br.readLine()) != null){
                    sb.append(line);
                }

                System.out.println(sb.toString());
            }else{
                System.out.println(connection.getResponseCode());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
