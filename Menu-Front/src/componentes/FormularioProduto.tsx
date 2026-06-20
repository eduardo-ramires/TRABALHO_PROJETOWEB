import { useState, useEffect } from "react";
import type { ProdutoDados } from "../interfaces/ProdutoDados";
import { useProdutoDadosMutate } from "../hooks/useProdutoDadosMutate";
import { useProdutoDadosEdit } from "../hooks/useProdutoDadosEdit";
import "./formularioProduto.css";

interface FormularioProdutoProps {
    produtoParaEditar?: ProdutoDados | null;
    onFechar?: () => void;
    somenteLeitura?: boolean;
}

export function FormularioProduto({ produtoParaEditar, onFechar, somenteLeitura = false }: FormularioProdutoProps) {

    const [nome, setNome] = useState("");
    const [descricao, setDescricao] = useState("");
    const [preco, setPreco] = useState("");
    const [categoria, setCategoria] = useState("");
    const [imagem, setImagem] = useState("");
    const [disponibilidade, setDisponibilidade] = useState(true);

    const { mutate: criar } = useProdutoDadosMutate();
    const { mutate: editar } = useProdutoDadosEdit();

    useEffect(() => {
        if (produtoParaEditar) {
            setNome(produtoParaEditar.nome);
            setDescricao(produtoParaEditar.descricao);
            setPreco(String(produtoParaEditar.preco));
            setCategoria(produtoParaEditar.categoria);
            setImagem(produtoParaEditar.imagem);
            setDisponibilidade(produtoParaEditar.disponibilidade);
        }
    }, [produtoParaEditar]);

    function enviarFormulario(event: React.FormEvent) {
        event.preventDefault();

        const produto: ProdutoDados = {
            nome,
            descricao,
            preco: Number(preco),
            categoria,
            imagem,
            disponibilidade
        };

        if (produtoParaEditar?.id) {
            editar({ ...produto, id: produtoParaEditar.id }, { onSuccess: onFechar });
        } else {
            criar(produto, { onSuccess: onFechar });
        }

        setNome("");
        setDescricao("");
        setPreco("");
        setCategoria("");
        setImagem("");
        setDisponibilidade(true);
    }

    const titulo = somenteLeitura ? "Visualizar Produto" : produtoParaEditar ? "Editar Produto" : "Novo Produto";

    return (
        <form className="formulario" onSubmit={enviarFormulario}>

            <h2>{titulo}</h2>

            <input
                type="text"
                placeholder="Nome"
                value={nome}
                onChange={(e) => setNome(e.target.value)}
                disabled={somenteLeitura}
            />

            <textarea
                placeholder="Descrição"
                value={descricao}
                onChange={(e) => setDescricao(e.target.value)}
                disabled={somenteLeitura}
            />

            <input
                type="number"
                placeholder="Preço"
                value={preco}
                onChange={(e) => setPreco(e.target.value)}
                disabled={somenteLeitura}
            />

            <input
                type="text"
                placeholder="Categoria"
                value={categoria}
                onChange={(e) => setCategoria(e.target.value)}
                disabled={somenteLeitura}
            />

            <input
                type="text"
                placeholder="URL da imagem"
                value={imagem}
                onChange={(e) => setImagem(e.target.value)}
                disabled={somenteLeitura}
            />

            <label>
                <input
                    type="checkbox"
                    checked={disponibilidade}
                    onChange={(e) => setDisponibilidade(e.target.checked)}
                    disabled={somenteLeitura}
                />

                Produto disponível
            </label>

            {!somenteLeitura && (
                <button type="submit">
                    {produtoParaEditar ? "Atualizar Produto" : "Salvar Produto"}
                </button>
            )}

        </form>
    );
}