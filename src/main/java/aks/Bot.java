package aks;

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
            reply(update.getMessage().getChatId().toString(), "You are about to order a code, type /email to input the email where you want to recieve your code, then /ccp to input your ccp number (10 numbers)");
            commObjects.rEmail.canSend = true;
        }
        if(update.getMessage().getText().equals("/confirm") && state == 0){
            if(commObjects.rConfirm.canSend){
                commObjects.rConfirm.action(update);
            }
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
}
