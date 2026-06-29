import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { CartaoProduto } from "../componentes/CartaoProduto";
import { FormularioProduto } from "../componentes/FormularioProduto";
import { FormularioUsuario } from "../componentes/FormularioUsuario";
import { ListaUsuarios } from "../componentes/ListaUsuarios";
import { useProdutoDados } from "../hooks/useProdutoDados";
import { useUsuarioDados } from "../hooks/useUsuarioDados";
import type { ProdutoDados } from "../interfaces/ProdutoDados";
import { ListaPedidos } from "../componentes/ListaPedidos";
import { usePedidoDados } from "../hooks/usePedidoDados";
import "./painelAdm.css";

export default function PainelAdm() {
    const navigate = useNavigate();
    const [aba, setAba] = useState<"produtos" | "usuarios" | "pedidos">("produtos");
    const [modalProdutoAberto, setModalProdutoAberto] = useState(false);
    const [produtoParaEditar, setProdutoParaEditar] = useState<ProdutoDados | null>(null);
    const [somenteLeitura, setSomenteLeitura] = useState(false);
    const [modalUsuarioAberto, setModalUsuarioAberto] = useState(false);
    const [tipoNovoUsuario, setTipoNovoUsuario] = useState<"ADM" | "USUARIO">("USUARIO");

    const { data: produtos } = useProdutoDados();
    const { data: usuarios } = useUsuarioDados();
    const { data: pedidos } = usePedidoDados();

    useEffect(() => {
        if (!sessionStorage.getItem("adm-autenticado")) {
            navigate("/login");
        }
    }, [navigate]);

    function handleEditar(produto: ProdutoDados) {
        setProdutoParaEditar(produto);
        setSomenteLeitura(false);
        setModalProdutoAberto(true);
    }

    function handleVisualizar(produto: ProdutoDados) {
        setProdutoParaEditar(produto);
        setSomenteLeitura(true);
        setModalProdutoAberto(true);
    }

    function handleFecharModalProduto() {
        setModalProdutoAberto(false);
        setProdutoParaEditar(null);
        setSomenteLeitura(false);
    }

    function abrirModalUsuario(tipo: "ADM" | "USUARIO") {
        setTipoNovoUsuario(tipo);
        setModalUsuarioAberto(true);
    }

    function sair() {
        sessionStorage.removeItem("adm-autenticado");
        navigate("/login");
    }

    return (
        <main className="painel-container">
            <div className="painel-cabecalho">
                <h1>Xis do Ramires</h1>
                <div className="painel-header-acoes">
                    <span className="badge-adm">ADM</span>
                    <button className="btn-sair" onClick={sair}>Sair</button>
                </div>
            </div>

            <div className="painel-abas">
                <button
                    className={`aba ${aba === "produtos" ? "aba-ativa" : ""}`}
                    onClick={() => setAba("produtos")}
                >
                    Produtos
                </button>
                <button
                    className={`aba ${aba === "usuarios" ? "aba-ativa" : ""}`}
                    onClick={() => setAba("usuarios")}
                >
                    Mesas &amp; ADMs
                </button>
                <button
                    className={`aba ${aba === "pedidos" ? "aba-ativa" : ""}`}
                    onClick={() => setAba("pedidos")}
                >
                    Pedidos
                </button>
            </div>

            {aba === "produtos" && (
                <div className="painel-secao">
                    <div className="secao-acoes">
                        <button
                            className="btn-novo-produto"
                            onClick={() => setModalProdutoAberto(true)}
                        >
                            + Novo Produto
                        </button>
                    </div>
                    <section className="grade-cartoes">
                        {produtos?.map((produto) => (
                            <CartaoProduto
                                key={produto.id}
                                produto={produto}
                                onEditar={handleEditar}
                                onVisualizar={handleVisualizar}
                            />
                        ))}
                    </section>
                </div>
            )}

            {aba === "usuarios" && (
                <div className="painel-secao">
                    <div className="secao-acoes">
                        <button
                            className="btn-nova-mesa"
                            onClick={() => abrirModalUsuario("USUARIO")}
                        >
                            + Nova Mesa
                        </button>
                        <button
                            className="btn-novo-adm"
                            onClick={() => abrirModalUsuario("ADM")}
                        >
                            + Novo ADM
                        </button>
                    </div>
                    <ListaUsuarios usuarios={usuarios ?? []} />
                </div>
            )}

            {aba === "pedidos" && (
                <div className="painel-secao">
                    <ListaPedidos pedidos={pedidos ?? []} />
                </div>
            )}

            {modalProdutoAberto && (
                <div className="overlay">
                    <div className="modal">
                        <button className="fechar" onClick={handleFecharModalProduto}>X</button>
                        <FormularioProduto
                            produtoParaEditar={produtoParaEditar}
                            onFechar={handleFecharModalProduto}
                            somenteLeitura={somenteLeitura}
                        />
                    </div>
                </div>
            )}

            {modalUsuarioAberto && (
                <div className="overlay">
                    <div className="modal">
                        <button className="fechar" onClick={() => setModalUsuarioAberto(false)}>X</button>
                        <FormularioUsuario
                            tipoFixo={tipoNovoUsuario}
                            onFechar={() => setModalUsuarioAberto(false)}
                        />
                    </div>
                </div>
            )}
        </main>
    );
}
