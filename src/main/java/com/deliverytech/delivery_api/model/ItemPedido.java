package com.deliverytech.delivery_api.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "item_pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(name = "preco_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoUnitario;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    // Explicação destas anotações (gestão de eventos) 
    //
    // @PrePersist: Esta anotação diz ao Hibernate: "Execute este método antes de salvar a entidade no banco pela primeira vez (antes de um INSERT)".
    // @PreUpdate: Esta anotação diz: "Execute este método antes de atualizar a entidade no banco (antes de um UPDATE)".

    @PrePersist 
    @PreUpdate
    private void calcularSubtotal() {
        if (precoUnitario != null && quantidade != null) {
            this.subtotal = precoUnitario.multiply(BigDecimal.valueOf(quantidade));
        }
     }

     public void setSubtotal() {
        calcularSubtotal();
     }

     public BigDecimal getValorTotal() {
        return subtotal != null ? subtotal : BigDecimal.ZERO;
     }

}
