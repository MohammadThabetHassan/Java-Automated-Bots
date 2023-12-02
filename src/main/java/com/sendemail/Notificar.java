

package com.sendemail;



import javax.websocket.Session;
import javax.ws.rs.client.Client;

import javax.ws.rs.client.ClientBuilder;

import javax.ws.rs.client.Entity;

import javax.ws.rs.core.Response;
import java.util.regex.Pattern;
public class Notificar {

    public  void Whatsapp(String masge ) {


                String cleanedText = masge.replaceAll("[^\\x20-\\x7E]", "");

                // Remove escape characters
                cleanedText = cleanedText.replaceAll("\\\\", "");

                System.out.println("Original Text: " + masge);
                System.out.println("Cleaned Text: " + cleanedText);


        String token = "GA231120050210";

        String api = "https://script.google.com/macros/s/AKfycbyoBhxuklU5D3LTguTcYAS85klwFINHxxd-FroauC4CmFVvS0ua/exec";

        String payload = "{\"op\" : \"registermessage\", "

                + "\"token_qr\": \"" + token + "\","

                + "\"mensajes\" : ["
                + "{\"numero\": \"971508762028\",\"mensaje\": \""+cleanedText+"\"}]}";
        Client client = ClientBuilder.newClient();

        System.out.println("" + payload);

        Response post = client.target(api).request().post(Entity.json("" + payload));

        String responseJson = post.readEntity(String.class);

        System.out.println("Estatus: " + post.getStatus());

        if (post.getStatus() == 302) {

            if (post.getLocation() == null) {

                System.out.println("Location: " + responseJson);

            } else {

                String uri = post.getLocation().toString();

                Response get = client.target(uri).request().get();

                responseJson = get.readEntity(String.class);

            }

        }

        System.out.println("" + responseJson);



    }

}
