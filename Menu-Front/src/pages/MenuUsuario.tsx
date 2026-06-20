import { useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { useProdutoDados } from "../hooks/useProdutoDados";
import { usePedidoDadosMutate } from "../hooks/usePedidoDadosMutate";
import { CartaoProdutoUsuario } from "../componentes/CartaoProdutoUsuario";
import { Carrinho } from "../componentes/Carrinho";
import type { ProdutoDados } from "../interfaces/ProdutoDados";
import "./menuUsuario.css";

export default function MenuUsuario() {
    const { mesa } = useParams<{ mesa: string }>();
    const navigate = useNavigate();
    const { data: produtos } = useProdutoDados();
    const { mutate: criarPedido, isPending } = usePedidoDadosMutate();

    const [carrinho, setCarrinho] = useState<ProdutoDados[]>([]);
    const [carrinhoAberto, setCarrinhoAberto] = useState(false);
    const [pedidoConfirmado, setPedidoConfirmado] = useState(false);

    const produtosDisponiveis = produtos?.filter((p) => p.disponibilidade) ?? [];
    const numeroMesa = Number(mesa);

    function adicionarAoCarrinho(produto: ProdutoDados) {
        setCarrinho((prev) => [...prev, produto]);
    }

    function removerDoCarrinho(index: number) {
        setCarrinho((prev) => prev.filter((_, i) => i !== index));
    }

    function confirmarPedido() {
        criarPedido(
            {
                mesa: numeroMesa,
                produtos: carrinho.map((p) => ({ id: p.id! })),
            },
            {
                onSuccess: () => {
                    setCarrinho([]);
                    setCarrinhoAberto(false);
                    setPedidoConfirmado(true);
                    setTimeout(() => setPedidoConfirmado(false), 4000);
                },
            }
        );
    }

    return (
        <main className="menu-container">
            <div className="menu-cabecalho">
                <div className="menu-titulo">
                    <h1>Xis do Ramires</h1>
                    <p className="menu-subtitulo">Mesa {mesa}</p>
                </div>
                <button
                    className="btn-abrir-carrinho"
                    onClick={() => setCarrinhoAberto(true)}
                >
                    Carrinho
                    {carrinho.length > 0 && (
                        <span className="carrinho-badge">{carrinho.length}</span>
                    )}
                </button>
            </div>

            {pedidoConfirmado && (
                <div className="aviso-confirmado">
                    Pedido enviado com sucesso! Aguarde seu lanche.
                </div>
            )}

            {produtosDisponiveis.length === 0 && (
                <p className="menu-vazio">Nenhum produto disponível no momento.</p>
            )}

            <section className="grade-cartoes-usuario">
                {produtosDisponiveis.map((produto) => (
                    <CartaoProdutoUsuario
                        key={produto.id}
                        produto={produto}
                        onAdicionarAoCarrinho={adicionarAoCarrinho}
                    />
                ))}
            </section>

            {carrinhoAberto && (
                <Carrinho
                    itens={carrinho}
                    onRemover={removerDoCarrinho}
                    onFechar={() => setCarrinhoAberto(false)}
                    onConfirmar={confirmarPedido}
                    isEnviando={isPending}
                />
            )}

            <button className="btn-sair-mesa" onClick={() => navigate("/login")}>
                Sair
            </button>
        </main>
    );
}
