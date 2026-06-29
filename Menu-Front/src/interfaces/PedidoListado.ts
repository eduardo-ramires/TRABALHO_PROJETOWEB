export interface ProdutoDoPedido {
    id: number;
    nome: string;
    preco: number;
}

export interface PedidoListado {
    id: number;
    mesa: number;
    produtos: ProdutoDoPedido[];
}