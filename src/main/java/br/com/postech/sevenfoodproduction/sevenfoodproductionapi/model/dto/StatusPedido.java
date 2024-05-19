package br.com.postech.sevenfoodproduction.sevenfoodproductionapi.model.dto;

public enum StatusPedido {

    EM_PROCESSAMENTO("Em Processamento", 0),
    RECEBIDO("Recebido", 1),
    ENVIADO_PRODUCAO("Enviado para Produção", 2),
    EM_PREPARACAO("Em Preparacao", 3),
    PRONTO("Pronto", 4),
    FINALIZADO("Finalizado", 5);

    private final String status;
    private final Integer code;

    StatusPedido(String status, Integer code) {
        this.status = status;
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public Integer getCode() {
        return code;
    }

    public static StatusPedido getByCod(Long cod) {
        for (StatusPedido statusPedidoEnum : values()) {
            if (statusPedidoEnum.getCode().equals(cod)) {
                return statusPedidoEnum;
            }
        }
        throw new IllegalArgumentException("Código de status inválido: " + cod);
    }


}

