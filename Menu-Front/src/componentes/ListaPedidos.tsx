import type { PedidoListado } from "../interfaces/PedidoListado";

interface ListaPedidosProps {
    pedidos: PedidoListado[];
}

export function ListaPedidos({ pedidos }: ListaPedidosProps) {
    if (pedidos.length === 0) {
        return <p>Nenhum pedido realizado ainda.</p>;
    }

    return (
        <div>
            {pedidos.map((pedido) => (
                <div key={pedido.id} style={{ border: "1px solid #ddd", borderRadius: "8px", padding: "12px", marginBottom: "10px" }}>
                    <strong>Mesa {pedido.mesa}</strong>
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