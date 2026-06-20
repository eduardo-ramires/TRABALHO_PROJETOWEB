import "./carrinho.css";
import type { ProdutoDados } from "../interfaces/ProdutoDados";

interface CarrinhoProps {
    itens: ProdutoDados[];
    onRemover: (index: number) => void;
    onFechar: () => void;
    onConfirmar: () => void;
    isEnviando: boolean;
}

export function Carrinho({ itens, onRemover, onFechar, onConfirmar, isEnviando }: CarrinhoProps) {
    const total = itens.reduce((acc, item) => acc + item.preco, 0);

    return (
        <div className="carrinho-overlay" onClick={onFechar}>
            <div className="carrinho-drawer" onClick={(e) => e.stopPropagation()}>
                <div className="carrinho-header">
                    <h2>Carrinho</h2>
                    <button className="carrinho-fechar" onClick={onFechar}>X</button>
                </div>

                {itens.length === 0 ? (
                    <p className="carrinho-vazio">Seu carrinho está vazio.</p>
                ) : (
                    <>
                        <ul className="carrinho-lista">
                            {itens.map((item, index) => (
                                <li key={index} className="carrinho-item">
                                    <div className="carrinho-item-info">
                                        <span className="carrinho-item-nome">{item.nome}</span>
                                        <span className="carrinho-item-preco">
                                            R$ {item.preco.toFixed(2)}
                                        </span>
                                    </div>
                                    <button
                                        className="carrinho-item-remover"
                                        onClick={() => onRemover(index)}
                                    >
                                        Remover
                                    </button>
                                </li>
                            ))}
                        </ul>

                        <div className="carrinho-rodape">
                            <div className="carrinho-total">
                                <strong>Total</strong>
                                <span>R$ {total.toFixed(2)}</span>
                            </div>
                            <button
                                className="btn-confirmar-pedido"
                                onClick={onConfirmar}
                                disabled={isEnviando}
                            >
                                {isEnviando ? "Enviando..." : "Confirmar Pedido"}
                            </button>
                        </div>
                    </>
                )}
            </div>
        </div>
    );
}
