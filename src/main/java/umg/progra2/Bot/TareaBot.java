package umg.progra2.Bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TareaBot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "@MiloMind_bot";
    }


    @Override
    public String getBotToken() {
        return "7400553095:AAFQjfYLT-k9jsl2t-lU3OQrYABu9VA4DSU";
    }


    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();
            String nombre = update.getMessage().getFrom().getFirstName();
            String apellido = update.getMessage().getFrom().getLastName();
            String nickName = update.getMessage().getFrom().getUserName();


            System.out.printf("""
                    User id: %d
                    User info: %s %s %s
                    Message: %s
                    
                    """,chat_id, nombre, apellido, nickName, message_text);

            String mensaje_para_enviar= null;
            double tipo_cambio = 8.67;

            if (message_text.equalsIgnoreCase("/start")){
                mensaje_para_enviar = String.format("""
                            ¡Hola %s!
                            Soy MiloMind, tu compañero virtual.


                            Aquí tienes una lista de comandos que puedes usar:
                            
                            1. /info
                               - Conoce algunos datos sobre la persona que me ha configurado.

                            2. /progra
                               - Recibe comentarios sobre la materia de programación.

                            3. /hola
                               - Obten un mensaje personalizado con tus datos y la fecha actual.

                            4. /cambio [cantidad en euros]
                               - Convierte Euros a Quetzales. Solo escribe el monto en euros y te diré a cuánto equivale en quetzales.
                                 Ejemplo: /cambio 897
                            
                            5. /grupalmensaje [mensaje]
                               - Envía el mensaje indicado a 4 compañeros. Solo escribe el mensaje que quieres enviar y será compartido con ellos.
                                 Ejemplo: /grupalmensaje ¡Hola!


                            ¡Estoy aquí para acompañarte en lo que necesites!""",nombre
                );

            } else if (message_text.equalsIgnoreCase("/info")) {
                mensaje_para_enviar = """
                            Aquí tienes la información solicitada:

                            Nombre: Mijeli Azucena Lucero Burgos
                            Número de Carnet: 0905-23-5501
                            Semestre en curso: 4to semestre""";

            } else if (message_text.equalsIgnoreCase("/progra")) {
                mensaje_para_enviar = """
                            Programar es como resolver un rompecabezas complejo donde cada pieza encaja para formar una
                            solución completa. A través de la programación, transformamos ideas abstractas en proyectos
                            concretos, y cada error que corregimos nos acerca un paso más a perfeccionar nuestra creación.
                            
                            Estoy satisfecha con la forma en que se abordan los temas en la clase de programación II.""";

            }else if(message_text.equalsIgnoreCase("/hola")){
                LocalDateTime fechaHoraActual = LocalDateTime.now();

                DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("EEEE d 'de' MMMM");
                String fechaFormateada = fechaHoraActual.format(formatoFecha);

                DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");
                String horaFormateada = fechaHoraActual.format(formatoHora);

                mensaje_para_enviar = String.format("Hola, %s, hoy es día %s con hora %s",nombre, fechaFormateada, horaFormateada);

            }else if(message_text.toLowerCase().startsWith("/cambio")) {
                String[] partes_comando = message_text.split(" ");

                if (partes_comando.length > 1) {
                    try {
                        double euros = Double.parseDouble(partes_comando[1]);
                        double quetzales = euros * tipo_cambio;

                        mensaje_para_enviar = String.format("La cantidad en euros que ingresaste equivale a Q. %.2f", quetzales);
                    } catch (NumberFormatException e) {
                        mensaje_para_enviar = "Por favor, ingresa una cantidad válida de Euros.";
                    }
                } else {
                    mensaje_para_enviar = "Por favor, añade una cantidad en Euros después de '/cambio'\nEjemplo: /cambio 897";
                }

            }else if(message_text.toLowerCase().startsWith("/grupalmensaje")) {

                int i_inicio = message_text.toLowerCase().indexOf("/grupalmensaje") + "/grupalmensaje".length();

                String mensaje_grupal = message_text.substring(i_inicio).trim();

                if (mensaje_grupal.isEmpty()){
                    mensaje_para_enviar = "Por favor, añade el mensaje que deseas enviar a los demás después de '/grupalmensaje'\n Ejemplo: /grupalmensaje ¡Hola a todos! ¿Cómo están?";
                }else{
                    List<Long>destinatarios =List.of(6828570294L,7161518818L,6688363556L,1262374416L);

                    for (long usuario:destinatarios){
                        sendText(usuario,mensaje_grupal);
                    }

                    mensaje_para_enviar = String.format("Tu mensaje \"%s\" ha sido enviado exitosamente a los 4 destinatarios.", mensaje_grupal);
                }

            }else{
                mensaje_para_enviar = "Comando inválido. Por favor, ingresa uno de los comandos que te mostré al inicio.";
            }

            sendText(chat_id,mensaje_para_enviar);
        }
    }


    public void sendText(Long who, String what){
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString())
                .text(what).build();
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

}
