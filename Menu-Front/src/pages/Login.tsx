import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useUsuarioDados } from "../hooks/useUsuarioDados";
import "./login.css";

export default function Login() {
    const navigate = useNavigate();
    const { data: usuarios, isLoading } = useUsuarioDados();
    const [modo, setModo] = useState<"inicio" | "mesa" | "adm">("inicio");
    const [nome, setNome] = useState("");
    const [senha, setSenha] = useState("");
    const [erro, setErro] = useState("");

    const mesas = usuarios?.filter((u) => u.tipo === "USUARIO") ?? [];

    function entrarComMesa(numeroMesa: number) {
        navigate(`/mesa/${numeroMesa}`);
    }

    function entrarComoAdm(event: React.FormEvent) {
        event.preventDefault();
        const adm = usuarios?.find(
            (u) => u.tipo === "ADM" && u.nome === nome && u.senha === senha
        );
        if (adm) {
            sessionStorage.setItem("adm-autenticado", "true");
            navigate("/adm");
        } else {
            setErro("Nome ou senha incorretos.");
        }
    }

    return (
        <div className="login-container">
            <h1>Xis do Ramires</h1>

            {modo === "inicio" && (
                <div className="login-opcoes">
                    <button className="login-card login-card-mesa" onClick={() => setModo("mesa")}>
                        <span className="login-card-titulo">Sou uma Mesa</span>
                        <span className="login-card-sub">Selecione sua mesa e faça seu pedido</span>
                    </button>
                    <button className="login-card login-card-adm" onClick={() => setModo("adm")}>
                        <span className="login-card-titulo">Sou ADM</span>
                        <span className="login-card-sub">Gerencie produtos e mesas</span>
                    </button>
                </div>
            )}

            {modo === "mesa" && (
                <div className="login-secao">
                    <button className="btn-voltar" onClick={() => setModo("inicio")}>
                        &larr; Voltar
                    </button>
                    <h2>Selecione sua mesa</h2>
                    {isLoading && <p className="login-info">Carregando mesas...</p>}
                    {!isLoading && mesas.length === 0 && (
                        <p className="login-info">Nenhuma mesa disponível. Fale com o atendente.</p>
                    )}
                    <div className="grade-mesas">
                        {mesas
                            .sort((a, b) => (a.mesa ?? 0) - (b.mesa ?? 0))
                            .map((u) => (
                                <button
                                    key={u.id}
                                    className="btn-mesa"
                                    onClick={() => entrarComMesa(u.mesa!)}
                                >
                                    Mesa {u.mesa}
                                </button>
                            ))}
                    </div>
                </div>
            )}

            {modo === "adm" && (
                <div className="login-secao">
                    <button className="btn-voltar" onClick={() => setModo("inicio")}>
                        &larr; Voltar
                    </button>
                    <h2>Login ADM</h2>
                    <form className="login-form" onSubmit={entrarComoAdm}>
                        <input
                            type="text"
                            placeholder="Nome"
                            value={nome}
                            onChange={(e) => {
                                setNome(e.target.value);
                                setErro("");
                            }}
                            required
                        />
                        <input
                            type="password"
                            placeholder="Senha"
                            value={senha}
                            onChange={(e) => {
                                setSenha(e.target.value);
                                setErro("");
                            }}
                            required
                        />
                        {erro && <p className="login-erro">{erro}</p>}
                        <button type="submit">Entrar</button>
                    </form>
                </div>
            )}
        </div>
    );
}
