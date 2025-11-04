package com.deliverytech.delivery_api.model;

public enum StatusPedido {
    CRIADO ("Criado"),                        // O momento em que o pedido foi criado pelo cliente, porém não foi confirmado pelo restaurante (RESTAURANTE).
    PENDENTE ("Pendente"),                    // Aguardando ser confirmado pelo restaurante (RESTAURANTE).
    CONFIRMADO ("Confirmado"),                // O restaurante confirmou que recebeu o pedido e que vai prepará-lo (RESTAURANTE).
    PREPARANDO ("Preparado"),                 // O restaurante está preparando o pedido (RESTAURANTE).
    SAIU_PARA_ENTREGA ("Saiu para Entrega"),  //  Saio o pedido para entregar (ENTREGADOR).
    ENTREGUE ("Entregue"),                    // Entrege o pedido (ENTREGADOR).
    CANCELADO ("Cancelado");                  // O usuário realiza o cancelamento (CLIENTE).

    private final String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
