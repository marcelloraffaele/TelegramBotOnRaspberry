package it.rmarcello.raspberrybot;

import it.rmarcello.raspberrybot.tasks.GoAndBackTask;
import it.rmarcello.raspberrybot.tasks.NumericFlashTask;
import it.rmarcello.raspberrybot.tasks.ToggleTask;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 *
 * @author rmarcello
 */
public class BotMain extends TelegramLongPollingCommandBot {

    public static final String TOKEN = "YOURBOTTOKEN";

    public static final String BOT_USERNAME = "YOURBOTNAME";

    public static List<Long> chatList = new LinkedList<>();

    public RaspberryManager rm = new RaspberryManager();
    
    public static void main(String a[]) {

        ApiContextInitializer.init();

        BotMain b = new BotMain();

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(b);

            System.out.println("Starting!!!");

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    public BotMain() {
        super(new DefaultBotOptions(), true);

        rm.initialize();
        
        register(new BotCommand("start", "start_cmd") {
            @Override
            public void execute(AbsSender as, User user, Chat chat, String[] strings) {
                try {
                    ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboard();

                    SendMessage sm = new SendMessage() // Create a SendMessage object with mandatory fields
                            .setChatId(chat.getId())
                            .setText("Please select a test")
                            .setReplyMarkup(replyKeyboardMarkup);
                    as.sendMessage(sm);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        register(new BotCommand("flash", "flash_cmd") {
            @Override
            public void execute(AbsSender as, User user, Chat chat, String[] strings) {
                    
                    ToggleTask t = new ToggleTask(rm.getPinList(), 3, new Observer() {
                        @Override
                        public void update(Observable o, Object arg) {
                            try {
                                SendMessage sm = new SendMessage() // Create a SendMessage object with mandatory fields
                                .setChatId(chat.getId())
                                .setText( "flash: " + arg );
                                as.sendMessage(sm);
                            } catch(Exception e ) {}
                        }
                    });
                    t.start();

            }
        });
        
        register(new BotCommand("move", "move_cmd") {
            @Override
            public void execute(AbsSender as, User user, Chat chat, String[] strings) {
                    
                    GoAndBackTask t = new GoAndBackTask(rm.getPinList(), 3, new Observer() {
                        @Override
                        public void update(Observable o, Object arg) {
                            try {
                                SendMessage sm = new SendMessage() // Create a SendMessage object with mandatory fields
                                .setChatId(chat.getId())
                                .setText( "flash: " + arg );
                                as.sendMessage(sm);
                            } catch(Exception e ) {}
                        }
                    });
                    t.start();

            }
        });
        
        register(new BotCommand("count", "count_cmd") {
            @Override
            public void execute(AbsSender as, User user, Chat chat, String[] strings) {
                    
                    NumericFlashTask t = new NumericFlashTask(rm.getPinList(), 3, new Observer() {
                        @Override
                        public void update(Observable o, Object arg) {
                            try {
                                SendMessage sm = new SendMessage() // Create a SendMessage object with mandatory fields
                                .setChatId(chat.getId())
                                .setText( "flash: " + arg );
                                as.sendMessage(sm);
                            } catch(Exception e ) {}
                        }
                    });
                    t.start();

            }
        });
        
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public void processNonCommandUpdate(Update update) {

        System.out.println("received an update: " + update);
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {

            chatList.add(update.getMessage().getChatId());

//            if( update.getMessage().getText() )
            SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                    .setChatId(update.getMessage().getChatId())
                    .setReplyMarkup(createKeyboard())
                    .setText("Please select an action!");
            try {
                sendMessage(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Override this function in your bot implementation to filter messages with
     * commands
     *
     * For example, if you want to prevent commands execution incoming from
     * group chat: # # return !message.getChat().isGroupChat(); #
     *
     * @note Default implementation doesn't filter anything
     * @param message Received message
     * @return true if the message must be ignored by the command bot and
     * treated as a non command message, false otherwise
     */
    @Override
    protected boolean filter(Message message) {

        System.out.println("m= " + message);

        return false;
    }

    public ReplyKeyboardMarkup createKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboad(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add("/flash"); //Emoji.AIRPLANE.toString()
        keyboardFirstRow.add("/move");
        keyboardFirstRow.add("/count");
        keyboard.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

}
