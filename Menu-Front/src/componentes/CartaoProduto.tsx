import "./cartaoProduto.css";
import type { ProdutoDados } from "../interfaces/ProdutoDados";
import { useState, useEffect } from "react";
import { useProdutoDadosDelete } from "../hooks/useProdutoDadosDelete";
import { useProdutoDadosEdit } from "../hooks/useProdutoDadosEdit";

interface CartaoProdutoProps {
    produto: ProdutoDados;
    onEditar: (produto: ProdutoDados) => void;
    onVisualizar: (produto: ProdutoDados) => void;
}

export function CartaoProduto({ produto, onEditar, onVisualizar }: CartaoProdutoProps) {
    const [disponivel, setDisponivel] = useState(produto.disponibilidade);
    const { mutate: deletar } = useProdutoDadosDelete();
    const { mutate: editar } = useProdutoDadosEdit();

    useEffect(() => {
        setDisponivel(produto.disponibilidade);
    }, [produto.disponibilidade]);

    function alternarDisponibilidade() {
        const novaDisponibilidade = !disponivel;
        setDisponivel(novaDisponibilidade);
        editar({ ...produto, disponibilidade: novaDisponibilidade });
    }

    return (
        <div className="cartao">
            <img src={produto.imagem} alt={`Imagem de ${produto.nome}`} />

            <div className="cartao-conteudo">
                <h2>{produto.nome}</h2>

                <p>{produto.descricao}</p>

                <p>
                    <strong>Categoria:</strong> {produto.categoria}
                </p>

                <p>
                    <strong>Valor:</strong> R$ {produto.preco.toFixed(2)}
                </p>

                <div className="status-linha">
                    <label className="toggle-label">
                        <input
                            type="checkbox"
                            className="toggle-input"
                            checked={disponivel}
                            onChange={alternarDisponibilidade}
                        />
                        <span className="toggle-slider" />
                        <span className={disponivel ? "status-disponivel" : "status-indisponivel"}>
                            {disponivel ? "Disponível" : "Indisponível"}
                        </span>
                    </label>
                </div>
            </div>

            <div className="cartao-acoes">
                <button className="btn-visualizar" onClick={() => onVisualizar(produto)}>Ver</button>
                <button className="btn-editar" onClick={() => onEditar(produto)}>Editar</button>
                <button className="btn-excluir" onClick={() => deletar(produto.id!)}>Excluir</button>
            </div>
        </div>
    );
}