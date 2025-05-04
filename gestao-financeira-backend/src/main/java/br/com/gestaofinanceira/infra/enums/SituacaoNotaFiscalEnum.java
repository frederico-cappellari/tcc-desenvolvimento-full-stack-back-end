package br.com.gestaofinanceira.infra.enums;

public enum SituacaoNotaFiscalEnum {
    PROCESSADA("PROCESSADA", "Processada"),
    NAO_PROCESSADA("NAO_PROCESSADA", "Não Processada"),
    PROCESSADA_NAO_ENCONTRADA("PROCESSADA_NAO_ENCONTRADA", "Processada Não Encontrada");

    private String situacao;
    private String descricao;

    SituacaoNotaFiscalEnum(String situacao, String descricao) {
        this.situacao = situacao;
        this.descricao = descricao;
    }

    public String getSituacao() {
        return situacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static SituacaoNotaFiscalEnum parse(String tipo) {
        for (SituacaoNotaFiscalEnum tipoTransacao : SituacaoNotaFiscalEnum.values()) {
            if (tipoTransacao.getSituacao().equalsIgnoreCase(tipo)) {
                return tipoTransacao;
            }
        }
        throw new IllegalArgumentException("Situação Nota Fiscal inválida: " + tipo);
    }
}
