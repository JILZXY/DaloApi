package org.example.model;

import java.sql.Timestamp;

public class Chat {
    private int idChat; // id_chat
    private int idCliente; // id_cliente
    private int idAbogado; // id_abogado
    private Timestamp fechaInicio; // fecha_inicio (TIMESTAMP)

    public Chat(){}

    public Chat(int idChat, int idCliente, int idAbogado, Timestamp fechaInicio) {
        this.idChat = idChat;
        this.idCliente = idCliente;
        this.idAbogado = idAbogado;
        this.fechaInicio = fechaInicio;
    }

    public int getIdChat() {
        return idChat;
    }

    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdAbogado() {
        return idAbogado;
    }

    public void setIdAbogado(int idAbogado) {
        this.idAbogado = idAbogado;
    }

    public Timestamp getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Timestamp fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
}
