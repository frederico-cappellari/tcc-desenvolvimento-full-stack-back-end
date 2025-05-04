package br.com.gestaofinanceira.infra.enums;

public enum TipoTransacaoFinanceiraEnum {
    RECEITA("RECEITA", "Receita"),
    DESPESA("DESPESA", "Despesa");

    private String tipo;
    private String descricao;

    TipoTransacaoFinanceiraEnum(String tipo, String descricao) {
        this.tipo = tipo;
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoTransacaoFinanceiraEnum parse(String tipo) {
        for (TipoTransacaoFinanceiraEnum tipoTransacao : TipoTransacaoFinanceiraEnum.values()) {
            if (tipoTransacao.getTipo().equalsIgnoreCase(tipo)) {
                return tipoTransacao;
            }
        }
        throw new IllegalArgumentException("Tipo de transação financeira inválido: " + tipo);
    }
}
