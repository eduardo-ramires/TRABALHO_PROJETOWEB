import "./listaUsuarios.css";
import type { UsuarioDados } from "../interfaces/UsuarioDados";
import { useUsuarioDadosDelete } from "../hooks/useUsuarioDadosDelete";

interface ListaUsuariosProps {
    usuarios: UsuarioDados[];
}

export function ListaUsuarios({ usuarios }: ListaUsuariosProps) {
    const { mutate: deletar } = useUsuarioDadosDelete();

    const mesas = usuarios.filter((u) => u.tipo === "USUARIO");
    const adms = usuarios.filter((u) => u.tipo === "ADM");

    return (
        <div className="lista-usuarios">
            <div className="grupo-usuarios">
                <h3>Mesas cadastradas</h3>
                {mesas.length === 0 ? (
                    <p className="lista-vazio">Nenhuma mesa cadastrada.</p>
                ) : (
                    <div className="grade-usuarios">
                        {mesas.map((u) => (
                            <div key={u.id} className="item-usuario item-mesa">
                                <span>Mesa {u.mesa}</span>
                                <button
                                    className="btn-deletar-usuario"
                                    onClick={() => deletar(u.id!)}
                                >
                                    Remover
                                </button>
                            </div>
                        ))}
                    </div>
                )}
            </div>

            <div className="grupo-usuarios">
                <h3>Administradores</h3>
                {adms.length === 0 ? (
                    <p className="lista-vazio">Nenhum ADM cadastrado.</p>
                ) : (
                    <div className="grade-usuarios">
                        {adms.map((u) => (
                            <div key={u.id} className="item-usuario item-adm">
                                <span>{u.nome}</span>
                                <button
                                    className="btn-deletar-usuario"
                                    onClick={() => deletar(u.id!)}
                                >
                                    Remover
                                </button>
                            </div>
                        ))}
                    </div>
                )}
            </div>
        </div>
    );
}
