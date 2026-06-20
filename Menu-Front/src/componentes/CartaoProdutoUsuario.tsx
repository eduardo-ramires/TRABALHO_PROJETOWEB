import "./cartaoProdutoUsuario.css";
import type { ProdutoDados } from "../interfaces/ProdutoDados";

interface CartaoProdutoUsuarioProps {
    produto: ProdutoDados;
    onAdicionarAoCarrinho: (produto: ProdutoDados) => void;
}

export function CartaoProdutoUsuario({ produto, onAdicionarAoCarrinho }: CartaoProdutoUsuarioProps) {
    return (
        <div className="cartao-usuario">
            <img src={produto.imagem} alt={`Imagem de ${produto.nome}`} />
            <div className="cartao-usuario-conteudo">
                <h2>{produto.nome}</h2>
                <p>{produto.descricao}</p>
                <p>
                    <strong>Categoria:</strong> {produto.categoria}
                </p>
                <p className="preco-usuario">R$ {produto.preco.toFixed(2)}</p>
            </div>
            <button
                className="btn-adicionar"
                onClick={() => onAdicionarAoCarrinho(produto)}
            >
                + Adicionar ao Carrinho
            </button>
        </div>
    );
}
