package umg.progra2;


import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import umg.progra2.Bot.TareaBot;

public class Main {
    public static void main(String[] args) {

        try{
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            TareaBot MiloMindBot = new TareaBot();

            botsApi.registerBot(MiloMindBot);
            System.out.println("Milo Mind Bot está funcionando correctamente.");
        }
        catch(Exception ex){
            System.out.println("error"+ex.getMessage());
        }

    }
}