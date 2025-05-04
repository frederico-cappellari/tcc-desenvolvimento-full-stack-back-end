package br.com.gestaofinanceira.domain.notafiscal.service;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class NotaFiscalMessageService {

    @Channel("nota-fiscal") 
    Emitter<JsonObject> notaficalEmitter; 
    
    public void sendMessage(String chaveDeAcesso) {
        System.out.println("Enviando mensagem para o canal nota-fiscal: " + chaveDeAcesso);
        JsonObject mensagem = new JsonObject();
        mensagem.put("chaveDeAcesso", chaveDeAcesso);
        notaficalEmitter.send(mensagem);
    }
}
