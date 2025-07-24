package org.example.model;

import java.sql.Timestamp;

public class Mensaje {
    private int idMensaje; // id_mensaje
    private int idChat; // id_chat
    private int idRemitente; // id_remitente
    private String mensaje; // mensaje
    private Timestamp fechaEnvio; // fecha_envio (TIMESTAMP)

    public Mensaje(){}

    public Mensaje(int idMensaje, int idChat, int idRemitente, String mensaje, Timestamp fechaEnvio) {
        this.idMensaje = idMensaje;
        this.idChat = idChat;
        this.idRemitente = idRemitente;
        this.mensaje = mensaje;
        this.fechaEnvio = fechaEnvio;
    }

    public int getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(int idMensaje) {
        this.idMensaje = idMensaje;
    }

    public int getIdChat() {
        return idChat;
    }

    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }

    public int getIdRemitente() {
        return idRemitente;
    }

    public void setIdRemitente(int idRemitente) {
        this.idRemitente = idRemitente;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Timestamp getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Timestamp fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }
}
