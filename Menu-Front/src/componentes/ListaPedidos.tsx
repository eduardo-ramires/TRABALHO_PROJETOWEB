import { useState } from "react";
import type { PedidoListado } from "../interfaces/PedidoListado";

interface ListaPedidosProps {
    pedidos: PedidoListado[];
}

export function ListaPedidos({ pedidos }: ListaPedidosProps) {
    const [selecionados, setSelecionados] = useState<number[]>([]);

    function toggleSelecionado(id: number) {
        setSelecionados(prev =>
            prev.includes(id) ? prev.filter(s => s !== id) : [...prev, id]
        );
    }

    function imprimir() {
        const conteudo = pedidos
            .filter(p => selecionados.includes(p.id))
            .map(p =>
                `<div style="border:1px solid #ddd;border-radius:8px;padding:12px;margin-bottom:10px">
                    <strong>Mesa ${p.mesa}</strong>
                    <ul>${p.produtos.map(prod => `<li>${prod.nome} - R$ ${prod.preco.toFixed(2)}</li>`).join("")}</ul>
                </div>`
            ).join("");

        const janela = window.open("", "_blank");
        if (!janela) return;
        janela.document.write(`<html><body>${conteudo}</body></html>`);
        janela.document.close();
        janela.print();
        janela.close();
    }

    if (pedidos.length === 0) {
        return <p>Nenhum pedido realizado ainda.</p>;
    }

    return (
        <div>
            {selecionados.length > 0 && (
                <button onClick={imprimir} style={{ marginBottom: "12px" }}>
                    Imprimir {selecionados.length} pedido(s)
                </button>
            )}
            {pedidos.map((pedido) => (
                <div key={pedido.id} style={{ border: "1px solid #ddd", borderRadius: "8px", padding: "12px", marginBottom: "10px" }}>
                    <label style={{ display: "flex", alignItems: "center", gap: "8px", cursor: "pointer" }}>
                        <input
                            type="checkbox"
                            checked={selecionados.includes(pedido.id)}
                            onChange={() => toggleSelecionado(pedido.id)}
                        />
                        <strong>Mesa {pedido.mesa}</strong>
                    </label>
                    <ul>
                        {pedido.produtos.map((produto, index) => (
                            <li key={`${pedido.id}-${produto.id}-${index}`}>
                                {produto.nome} - R$ {produto.preco.toFixed(2)}
                            </li>
                        ))}
                    </ul>
                </div>
            ))}
        </div>
    );
}