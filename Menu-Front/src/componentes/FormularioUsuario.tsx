import { useState } from "react";
import type { UsuarioDados } from "../interfaces/UsuarioDados";
import { useUsuarioDadosMutate } from "../hooks/useUsuarioDadosMutate";
import "./formularioUsuario.css";

interface FormularioUsuarioProps {
    tipoFixo: "ADM" | "USUARIO";
    onFechar?: () => void;
}

export function FormularioUsuario({ tipoFixo, onFechar }: FormularioUsuarioProps) {
    const [nome, setNome] = useState("");
    const [mesa, setMesa] = useState("");
    const [senha, setSenha] = useState("");

    const { mutate: criar } = useUsuarioDadosMutate();

    function enviar(event: React.FormEvent) {
        event.preventDefault();

        const usuario: UsuarioDados =
            tipoFixo === "USUARIO"
                ? { tipo: "USUARIO", mesa: Number(mesa) }
                : { tipo: "ADM", nome, senha };

        criar(usuario, { onSuccess: onFechar });

        setNome("");
        setMesa("");
        setSenha("");
    }

    return (
        <form className="formulario-usuario" onSubmit={enviar}>
            <h2>{tipoFixo === "ADM" ? "Novo ADM" : "Nova Mesa"}</h2>

            {tipoFixo === "USUARIO" ? (
                <input
                    type="number"
                    placeholder="Número da mesa"
                    value={mesa}
                    onChange={(e) => setMesa(e.target.value)}
                    required
                    min={1}
                />
            ) : (
                <>
                    <input
                        type="text"
                        placeholder="Nome"
                        value={nome}
                        onChange={(e) => setNome(e.target.value)}
                        required
                    />
                    <input
                        type="password"
                        placeholder="Senha"
                        value={senha}
                        onChange={(e) => setSenha(e.target.value)}
                        required
                    />
                </>
            )}

            <button type="submit">Salvar</button>
        </form>
    );
}
