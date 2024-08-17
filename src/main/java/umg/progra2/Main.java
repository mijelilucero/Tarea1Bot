package umg.progra2;


import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import umg.progra2.Bot.TareaBot;

public class Main {
    public static void main(String[] args) {

        try{
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            TareaBot MiloMind = new TareaBot();

            botsApi.registerBot(MiloMind);
            System.out.println("Milo Mind Bot funciona correctamente.");
        }catch (Exception e){
            System.out.println("error"+e.getMessage());
        }

    }
}