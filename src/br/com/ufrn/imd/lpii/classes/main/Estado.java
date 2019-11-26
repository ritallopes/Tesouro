package br.com.ufrn.imd.lpii.classes.main;

/**
 *Classe enum que armazena os possiveis estados do Bot.
 *
 * @author Hilton Thallyson Vieira Machado, Igor Silva Bento, Jose Lucio da Silva Junior, Rita de Cassia Lino Lopes
 * @version 1.0
 * @since 2019.2
 */
public enum Estado {
    STANDBY, CADASTRAR_LOCALIZACAO, CADASTRAR_CATEGORIA_DO_BEM, CADASTRAR_BEM, LISTAR_LOCALIZACOES, LISTAR_CATEGORIAS, LISTAR_BENS_DE_UMA_LOCALIZACAO,
    BUSCAR_BEM_POR_CODIGO, BUSCAR_BEM_POR_NOME, BUSCAR_BEM_POR_DESCRICAO, MOVIMENTAR_BEM, GERAR_RELATORIO, GERAR_RELATORIO_ARQUIVO,
    CARREGAR_DADOS, APAGAR_LOCALIZACAO, APAGAR_CATEGORIA, APAGAR_BEM;

}
